package com.chen.compress;

import java.util.List;

import com.chen.model.Record;
import com.chen.model.SuperBlock;

/**
 * 压缩器
 * 包含分析方法和压缩方法
 * @author chen
 *
 */
public class CompressTask implements Runnable{
	
	//超级块
	private SuperBlock superBlock;
	
	//压缩完成的监听器
	private onProcessCompleteListener listener;
	
	public CompressTask(onProcessCompleteListener listener,List<Record> superBlock,int blockNumber) {
		super();
		this.superBlock = new SuperBlock(superBlock,blockNumber);
		this.listener = listener;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		//开始执行 是一个耗时的操作
		superBlock.process();
		//完成操作之后的回调
		listener.onComplete();
	}

}
