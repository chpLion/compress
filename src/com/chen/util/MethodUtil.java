package com.chen.util;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.NumberUpSupported;

public class MethodUtil {

	private static MethodUtil mInstance = null;
	
	private MethodUtil(){
		
	}
	
	/**
	 * 工具类使用单例模式
	 * @return
	 */
	public static MethodUtil getInstance(){
		
		if (mInstance == null) {
			synchronized (MethodUtil.class) {
				if (mInstance == null) {
					return new MethodUtil();
				}
			}
		}
		return mInstance;
	}
	/**
	 * 将一个八位字符串转换成一个整数
	 * @param s
	 * @return
	 */
	public static int changeString(String s){
		
		int n = ((int)s.charAt(0)-48)*128+((int)s.charAt(1)-48)*64+((int)s.charAt(2)-48)*32  
	            +((int)s.charAt(3)-48)*16+((int)s.charAt(4)-48)*8+((int)s.charAt(5)-48)*4  
	            +((int)s.charAt(6)-48)*2+((int)s.charAt(7)-48);  
		return n;
	}
	
	/**
	 * 将不足八的整数倍的字符串补齐成八位的整数倍
	 * @param str
	 * @return
	 */
	public static String getBitStr(String str){
		int count = str.length()%8;
		for(int i=0;i<8-count;i++){
			str = "0"+str ;
		}
		return str;
	}
	
	/**
	 * 将十进制的整数转换成二进制的字符串
	 * @param dec
	 * @return
	 */
	public static String getBitFromDec(int dec){
		String bitStr = "";
		bitStr = Integer.toBinaryString(dec);
		bitStr = getBitStr(bitStr);
		return bitStr;
	}
	
	/**
	 * 计算一组数据的方差
	 * @param mumbers 包含了数据的数组
	 * @return 这组数据的方差
	 */
	public static double caculateVariace(int nums[]){
		
		//计算方差的方式是用数组里面每一个数和平均值的差的平方的和再除以数组的个数n
		//初始化和
		double sum = 0;
		//初始化平均值
		double avg = 0;
		//计算和
		for(int i=0;i<nums.length;i++){
			sum+=nums[i];
		}
		
		//计算平均值
		avg = sum/nums.length;
		//计算数组每一个数字和平均值的差的平方之和
		//初始化平方差之和
		double sqrtSum = 0;
		for(int i=0;i<nums.length;i++){
			sqrtSum += Math.pow(nums[i]-avg, 2);
		}
		return sqrtSum/nums.length;
	}
	
	/**
	 * 计算标准差
	 * 因为方差是标准差的平方
	 * 因此会造成数据与原数据相差太大的情况
	 * 因此可以使用标准差
	 * 对计算的方差进行开方的处理，可以让数据更加直观一点
	 * @param nums 需要计算标准差的样本数据
	 * @return 标准差
	 */
	public static double caculateStandardDeviation(int nums[]){
		return Math.sqrt(caculateVariace(nums));
	}
	
	/**
	 * 统计连续字符出现的次数
	 * @param text
	 * @return 统计的索引表，里面的内容是字符和它对应的最长出现连续次数
	 */
	public static HashMap<Character, Integer> caculateSeStr(String text){
		int current = 1;
		HashMap<Character , Integer> countMap = new HashMap<>();
		//先将第一个字符放入
		countMap.put(text.charAt(0), 1);
		int count = 1;
		while(current <= text.length()){
			for(int i = current;i<text.length();i++){
				if (text.charAt(i-1) == text.charAt(i)) {
					count ++;
					if (i == text.length()-1) {
						if (countMap.containsKey(text.charAt(i-1))) {
							//比较当前count和已有记录的count大小，如果更大，则替换
							if (countMap.get(text.charAt(i-1))<count) {
								countMap.put(text.charAt(i-1), count);
							}
							
						}
					}
				}else{
					//退出当前for循环
					//将count记录在索引
					//判断是否存在当前索引
					if (countMap.containsKey(text.charAt(i-1))) {
						//比较当前count和已有记录的count大小，如果更大，则替换
						if (countMap.get(text.charAt(i-1))<count) {
							countMap.put(text.charAt(i-1), count);
						}
						
					}
					countMap.put(text.charAt(i), 1);
					//退出当前循环
					i = text.length();
				}
			}
			//当前指针指向下移动count个位置
			current += count;
			count = 1;
			
		}

		return countMap;

	}

	/**
	 * 将二进制数转换成int型
	 * 这个二进制字符串不能超过八位
	 * @param banary 二进制字符串
	 * @return 转成二进制串对应的int数
	 */
	public static int getIntFromBanaryStr(String banary){
		int w = 0;
		if (banary.length()>8) {
			return -1;
		}
		for(int i=banary.length();i>=1;i--){
			
			w +=Integer.valueOf(banary.charAt(banary.length()-i)+"")*Math.pow(2, i-1);
			
		}
		return w;
	}
	
}
