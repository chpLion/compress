package com.chen.lfcode;

import java.util.ArrayList;
import java.util.List;

import com.chen.util.MethodUtil;

/**
 * 大发神的编码格式
 * 质量行的范围是从ASCII码的33-74
 * 将质量行的字符向下偏移33个位，变成0-40的范围
 * 之后使用6位二进制编码这40个可能的字符，将原先需要八位的变成了六位
 * 可以得到理论上的75%的压缩率，比一部分使用游程编码只能达到80%-95%的压缩率更高
 * 
 * @author chen
 *
 */
public class LfCode {

	//需要编码的质量行的字符串
	private String quliatyStr;
	
	public LfCode() {
		// TODO Auto-generated constructor stub
	}
	
	public LfCode(String quliatyStr){
		this.quliatyStr = quliatyStr;
	}
	
	/**
	 * 重置质量行字符串
	 * @param quliatyStr
	 */
	public void setQuliatyStr(String quliatyStr) {
		this.quliatyStr = quliatyStr;
	}
	
	public List<Integer>encode(){
		
		//最终生成经过压缩的可写入文件的int集合
		List<Integer> codeList = new ArrayList<>();
		//通过转化之后的字符串
		String finalStr = "";
		//遍历整个字符串
		for(int i=0;i<quliatyStr.length();i++){
			
			//获取当前的字符
			char c = quliatyStr.charAt(i);
			//将字符向下偏移
			int cInt = (char)((int)c - 30);
			//将字符表示的ASCII码转化成二进制
			String binaryString = Integer.toBinaryString(cInt);
			//补齐六位
			binaryString = getCountBit(binaryString, 6);
			finalStr += binaryString;
			//重置临时二进制字符串
			binaryString = "";
		}
		//补齐八位
		finalStr = getCountBit(finalStr, 8);
		
		//将已经补齐八位的字符串转化成int类型的集合
		for(int i=0;i<finalStr.length()-8;i+=8){
			int tempInt = MethodUtil.changeString(finalStr.substring(i, i+8));
			codeList.add(tempInt);
		}
		return codeList;
		
	}
	
	/**
	 * 将一个二进制的字符串补齐到一定位数
	 * @param binary 二进制数	
	 * @param count 需要补齐的位数
	 * @return
	 */
	private String getCountBit(String binary,int count){
		int mod = binary.length()%count;
		if (mod == 0) {
			return binary;
		}
		for(int i=count - mod;i>0;i--){
			binary = "0" + binary;
		}
		
		return binary;
	}
	
	
}
