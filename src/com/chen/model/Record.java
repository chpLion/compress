package com.chen.model;

/**
 * 一个记录
 * @author chen
 *
 */
public class Record {
	
	//标题
	private String title;
	
	//DNA序列
	private String DNBSequence;
	
	//质量评价
	private String qualityScore;
	
	//第三行
	private String thirdStr;
	
	public String getTitle() {
		return title;
	}
	
	public void setThirdStr(String thirdStr) {
		this.thirdStr = thirdStr;
	}
	public String getThirdStr() {
		return thirdStr;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDNBSequence() {
		return DNBSequence;
	}
	public void setDNBSequence(String dNBSequence) {
		DNBSequence = dNBSequence;
	}
	public String getQualityScore() {
		return qualityScore;
	}
	public void setQualityScore(String qualityScore) {
		this.qualityScore = qualityScore;
	}
	
	
}
