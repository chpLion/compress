package com.chen.analyse;

public abstract class IAnalyse {
	
	
	//默认使用的压缩模型是哈夫曼编码
	protected ModelType mType = ModelType.HUFFMAN;
	//需要分析的字符串
	protected String anylyseStr;
	public IAnalyse(String string){
		this.anylyseStr = string;
	}
	
	public ModelType analyseStr(){
		mType = analyse();
		return mType;
	}
	/**
	 * 分析方法
	 * 抽象方法，暴露出去供实现类实现
	 */
	abstract ModelType analyse();
}
