package com.chen.fileoperator;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class FastqFileWriter {
	
	private String banary;
	private File file;
	private FileOutputStream fout;
	public FastqFileWriter(File file){
		this.file = file;
		try {
			fout = new FileOutputStream(file,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 将文件写入
	 */
	public void write(String banary){
		//先将字符串转换成八位的整数倍
		//banary= getBitStr(banary);
		//将字符串转换成int
		//int b = chanegStringToInt(banary);
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fout));
		try {
			
			bufferedWriter.write(banary);
			bufferedWriter.flush();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 写入int集合
	 * @param list
	 */
	public void write(List<Integer>list){
		for(int i=0;i<list.size();i++){
			write(list.get(i));
		}
	}
	
	/**
	 * 写入int
	 * @param n
	 */
	public void write(int n){
		try {
			fout.write(n);
			fout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭流
	 */
	public void close(){
		try {
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 将八位的字符串转化成int型
	 * @param str
	 * @return
	 */
	private int chanegStringToInt(String s){
		
	 return ((int)s.charAt(0)-48)*128+((int)s.charAt(1)-48)*64+((int)s.charAt(2)-48)*32  
	            +((int)s.charAt(3)-48)*16+((int)s.charAt(4)-48)*8+((int)s.charAt(5)-48)*4  
	            +((int)s.charAt(6)-48)*2+((int)s.charAt(7)-48); 
	}
	
	/**
	 * 将字符串长度补齐成八的整数倍
	 * @param str
	 * @return
	 */
	private String getBitStr(String str){
		int count = str.length()%8;
		   for(int i=0;i<8-count;i++){
			   str = "0"+str ;
		   }
		   return str;
	}
	
	/**
	 * 将十进制转换成二进制
	 * @param dec
	 * @return
	 */
	private String getBitFromDec(int dec){
		
		String bitStr = "";
		bitStr = Integer.toBinaryString(dec);
		bitStr = getBitStr(bitStr);
		return bitStr;

	}
	
}
