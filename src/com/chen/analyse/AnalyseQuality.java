package com.chen.analyse;

import java.util.HashMap;

import com.chen.compress.RunLehgthCodeCompressor;
import com.chen.util.MethodUtil;

public class AnalyseQuality {

	//使用游程编码效果更好的质量类型
	public static final int TYPE_RUNLEGTH = 0;
	
	//不适用编码，用zip二次压缩效果更好
	public static final int TYPE_ZIP = 1;
	//质量分数
	private String qualityStr;
	
	public AnalyseQuality(){
		
	}
	public void setQualityStr(String qualityStr) {
		this.qualityStr = qualityStr;
	}
	
	public int anylyse(){
		
		//计算质量分数中各连续出现字符最大值
		HashMap<Character, Integer> strDic = MethodUtil.caculateSeStr(qualityStr);
		//判断最大值占总长度的大小
		float per = strDic.values().size()/qualityStr.length();
		//获取所有连续出现字符频率的标准差
		int[] counts = getValueAarryFromDic(strDic);
		double d = MethodUtil.caculateStandardDeviation(counts);
		String encode= new RunLehgthCodeCompressor(qualityStr).encode(qualityStr);
		double percent = (double)encode.length()/(double)qualityStr.length();
	
		if (percent<0.4||counts.length>3) {
			
			return TYPE_RUNLEGTH;
		}
		return TYPE_ZIP;
	}
	
	/**
	 * 从字典中获取值的数组
	 * @param mp 字典
	 * @return 数组
	 */
	public int [] getValueAarryFromDic(HashMap<Character, Integer> mp){

		Integer counts[] = new Integer[mp.values().size()];
		counts = mp.values().toArray(counts);
		int seriNum[] = new int[counts.length];
		for(int i=0;i<counts.length;i++){
			seriNum[i] = counts[i];
		}
		
		return seriNum;
	}
	
	
}
