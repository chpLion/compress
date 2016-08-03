package com.chen.analyse;

import javax.jws.WebParam.Mode;

/**
 * 分析序列行
 * @author chen
 *
 */
public class AnalyseDNA extends IAnalyse{

	public AnalyseDNA(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	@Override
	ModelType analyse() {
		
		//实现分析
		int i=0;
		for(i = 0;i<anylyseStr.length();i++){
			if (anylyseStr.charAt(i) == 'N') {
				//有不确定的碱基，不可使用替换  
				break;
			}
		}
		if (i == anylyseStr.length()) {
			//说明可以使用替换法
			mType = ModelType.REPLACE;
			return mType;
		}
		//判断是使用差分还是哈夫曼
		return mType;
		
	}

}
