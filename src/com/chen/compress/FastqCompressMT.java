package com.chen.compress;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.chen.dsrc.IDsrcOperator;
import com.chen.fileoperator.FastqFileReader;
import com.chen.fileoperator.IFastqFileReader;
import com.chen.fileoperator.onReadCompleteListener;
import com.chen.fileoperator.onReadListener;
import com.chen.model.Record;
import com.chen.model.SuperBlock;

/**
 * 为了防止节省内存，使用单例模式创建fastq压缩对象
 * 是多线程并行压缩的方法
 * 实现压缩操作的接口
 * @author chen
 *
 */
public class FastqCompressMT implements ICompressOperator,onReadListener,onProcessCompleteListener{

	//单例模式里面的对象
	private static FastqCompressMT mInstance = null;
	private String filePath;
	//超级块
	private List<Record> superBlock = new ArrayList<>();
	
	//超级的集合
	private List<List<Record>> superBlocks = new ArrayList<>();
	//线程池
	private ExecutorService mThreadPool;
	
	//后台轮询线程
	private Thread mPoolThread;
		
	//线程池任务数量
	private int DEAFULT_THREAD_COUNT = 4;
	
	//任务队列，当文件过大而需要多开线程的时候，则需要加入后备队列
	private LinkedList<Runnable>mTaskQueue = new LinkedList<>();
	
	//线程池的信号量
	private Semaphore mSemaphoreThreadPool = new Semaphore(DEAFULT_THREAD_COUNT);

	
	//记录
	private List<Record> records = new ArrayList<>();
	private FastqCompressMT(String filePath){
		this.filePath = filePath;
		//开启一个默认数量为4的适应性线程池
		mThreadPool = Executors.newFixedThreadPool(DEAFULT_THREAD_COUNT);
		
	}
	
	/**
	 * 单例模式中的入口方法
	 * @param filePath fastq文件的文件路径
	 * @return
	 */
	public static FastqCompressMT getInstance(String filePath){
		if(mInstance == null){ 
			//为防止并发操作中多次创建对象，必须使用同步代码块的方式
			synchronized (FastqCompressMT.class) {
				if(mInstance == null){
					//加锁判断，防止多次创建对象
					mInstance = new FastqCompressMT(filePath);
				}
			}
		}
		return mInstance;
	}

	@Override
	public void compressFastq() {
		// TODO Auto-generated method stub
		System.out.println("this is dsrc");
		
		//使用同步
		synchronized (this) {
			//首先读取文件
			IFastqFileReader reader = new FastqFileReader(this,filePath);
			//在read内部使用了线程读取文件，所以需要等待read读取完才能继续下一步操作
			reader.read();
			//分析
			try {
				//阻塞住 等待文件读取完毕再执行下一步操作
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			//读取完成之后的操作
			System.out.println("这是读取完成之后的操作"+records.size());
			//分析记录 建立模型
			//将记录放入超级块
			//512*32个记录作为一个超级块
			//记录的条数
			int count = records.size();
			
			//要分成的等分数量默认是4
			int num = 4;
			//将记录分成四等分
			int perCount = count/num;
			if (perCount>16384) {
				//分成四等分之后每一个超级块的记录数量大于默认值
				//那么需要分成这多块
				int temp = records.size()%16384;
				//获取需要分成的块数，在不能整除的情况下需要加1
				num = temp==0?temp:temp+1;
			}
			
			//开始组合成超级块
			for(int i=0;i<num;i++){
				
				//重置
				superBlock = new ArrayList<>();
				for(int j=0;j<perCount;j++){
					superBlock.add(records.get(j));
				}
				//初始化任务对象 加入任务队列
				addTask(new CompressTask(this,superBlock,i));
			}
					
		}
	}


	
	/**
	 * 将需要执行的任务加入到任务队列中
	 * 加入队列的操作需要同步
	 * @param task
	 */
	private synchronized void addTask(Runnable task){
		mTaskQueue.add(task);
		//让线程池执行这个任务
		mThreadPool.execute(getTask());
		//请求一个信号量
		try {
			mSemaphoreThreadPool.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 从任务队列中取一个任务
	 * @return
	 */
	private Runnable getTask(){
		return mTaskQueue.removeFirst();
	}
	//压缩过程完成之后的回调
	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		//释放一个信号量
		mSemaphoreThreadPool.release();
		synchronized (mSemaphoreThreadPool) {
			if(mSemaphoreThreadPool.availablePermits()==DEAFULT_THREAD_COUNT){
				//此时线程池的所有任务都已经执行完毕 可以开始写入文件了
				
			}

		}
	}

	@Override
	public void onProgress(Record record,long currentLength) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFail(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBegin(String title) {
		// TODO Auto-generated method stub
		
	}
	
}
