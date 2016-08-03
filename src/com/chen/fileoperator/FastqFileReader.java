package com.chen.fileoperator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Handler;

import com.chen.compress.FastqCompressMT;
import com.chen.model.Record;

public class FastqFileReader implements IFastqFileReader,onReadListener {

	private String filePath;
	
	private File file;
	
	private BufferedReader reader;
	
	//记录读出来的字符串集合
	private List<String> fileStrs = new ArrayList<>();
	
	//读取完成的回调
	private onReadListener listener;
	
	//记录集合
	private List<Record> records = new ArrayList<>();
	
	//计时线程
	private Thread timerTread ;
	
	long size = 0;
	
	//时间
	private int time = 0;
	
	public FastqFileReader(onReadListener listener,String filePath) {
		super();
		this.filePath = filePath;
		this.listener = listener;
	}

	public long getSize() {
		return size;
	}
	@Override
	public void read() {
		// TODO Auto-generated method stub
		
		//使用自适应的线程池
		try {
			file = new File(filePath);
			size = file.length();
			System.out.println("size = "+size/1024/1024+"Mb");
			FileReader fr = new FileReader(file);
			reader = new BufferedReader(fr);
			//用mb为单位			
			//读取文件放进临时集合
			//开启线程读文件并且计时
			ReadFileTask task = new ReadFileTask(this, reader);
			task.start();
			
			//计时线程
			System.out.println("正在读取");
			timerTread = new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					try {
						while(true){
							Thread.sleep(1000);
							time++;
							//System.out.println(""+time);
						}
						
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			timerTread.start();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


	//实现读取文件状态接口的方法
	//正在过程中
	@Override
	public void onProgress(Record record,long currentLength) {
		// TODO Auto-generated method stub
		//加入临时变量
		listener.onProgress(record,currentLength);
	}


	@Override
	public void onFail(String error) {
		// TODO Auto-generated method stub
		System.out.println(error);
	}


	//读取完成
	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
	
		System.out.println("共耗时"+time);
		//停止计时
		timerTread.stop();
		System.out.println("读取完成");
		//回调唤醒等待本线程的对象
		listener.onComplete();
		//System.out.println(records.get(4).getDNBSequence().length());
		//关闭流
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onBegin(String title) {
		// TODO Auto-generated method stub
		
	}
	
	
}

