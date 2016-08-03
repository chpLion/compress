package com.chen.fileoperator;

import java.io.BufferedReader;
import java.io.IOException;

import com.chen.model.Record;

public class ReadFileTask extends Thread{

	//监听读取文件状态的接口
	private onReadListener listener;
	private BufferedReader reader;
	
	private Record tempRecord = new Record();
	public ReadFileTask(onReadListener listener, BufferedReader reader) {
		super();
		this.listener = listener;
		this.reader = reader;
	}
	public void setListener(onReadListener listener) {
		this.listener = listener;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		super.run();
		String temp = "";
		long currentLenhgth = 0;
		int current = 0;
		try {
			while((temp = reader.readLine())!=null){
				tempRecord.setTitle(temp);
				currentLenhgth += temp.length();
				if (current == 0) {
					listener.onBegin(temp);
				}
				temp = reader.readLine();
				tempRecord.setDNBSequence(temp);
				currentLenhgth += temp.length();
				
				temp = reader.readLine();
				tempRecord.setThirdStr(temp);
				currentLenhgth += temp.length();
				
				temp = reader.readLine();
				tempRecord.setQualityScore(temp);
				currentLenhgth += temp.length();
				current++;
				
				//回调
				listener.onProgress(tempRecord,currentLenhgth);
			}
			listener.onComplete();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			listener.onFail(e.toString());
		}
	}
}
