package com.chen.compress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.chen.analyse.AnalyseQuality;
import com.chen.lfcode.LfCode;

/**
 * 使用游程编码进行压缩
 * @author chen
 *
 */
public class RunLehgthCodeCompressor extends ICompressor{
	
	private String text;
	private AnalyseQuality analyseQuality;
	private int min = Integer.MAX_VALUE;
	private int max = Integer.MIN_VALUE;
	private String codeStr = "";
	
	//压缩之后的编码集合
	private List<Integer>codeList = new ArrayList<>();
	public RunLehgthCodeCompressor(String text) {
		// TODO Auto-generated constructor stub
		this.text = text;
	}
	
	public RunLehgthCodeCompressor() {
		// TODO Auto-generated constructor stub
		analyseQuality = new AnalyseQuality();
	}
	public void setQuality(String text) {
		this.text = text;
	}
	
	/**
	 * 获取压缩后的编码
	 * @return
	 */
	public List<Integer>getCodeList(){
		
		//重置
		codeList.clear();
		compress();
		return codeList;
	}
	
	/**
	 * 获取压缩之后的字符串
	 * @return
	 */
	public String getCodeStr(){
		
		
		codeStr = "";
		compress();
		return codeStr;
	}
	/**
	 * 游程编码
	 * @return
	 */
	private List<Integer> encode(){
		
		
		List<Integer>encodeList = new ArrayList<>();
		String encodeStr = "";
		int count = 0;
		for(int i=0;i<text.length()-1;i++){
			
			if (text.charAt(i)==text.charAt(i+1)) {
				count ++;
			}else{
				encodeList.add((int)text.charAt(i));
				if (count!=0) {
					encodeList.add(count);
				}
				

				count = 0;
			}
		}
		if (count == 0) {

			encodeList.add((int)text.charAt(text.length()-1));
		}
		else{

			encodeList.add((int)text.charAt(text.length()-1));
			encodeList.add(count);
		}
		return encodeList;
	}
	
	public int getMax() {
		return max;
	}
	
	public int getMin() {
		return min;
	}
	
	/**
	 * 压缩字符串 返回压缩之后的字符串
	 * @param score
	 * @return 压缩之后的字符串
	 */
	public String encode(String score){

		String encodeStr = "";
		int count = 0;
		for(int i=0;i<text.length()-1;i++){
			
			if (text.charAt(i)==text.charAt(i+1)) {
				count ++;
			}else{
				encodeStr += text.charAt(i)+"";
				if (count !=0) {
					encodeStr += ""+count;
				}
				
				count = 0;
			}
		}
		if (count == 0) {
			encodeStr += text.charAt(text.length()-1)+"";
		}
		else{
			encodeStr += text.charAt(text.length()-1)+""+count;
		}
		return encodeStr;
	}

	@Override
	void compress() {
		// TODO Auto-generated method stub
//		analyseQuality.setQualityStr(text);
//		int type = analyseQuality.anylyse();
//		if (type == AnalyseQuality.TYPE_RUNLEGTH) {
//			codeList = encode();
//		}
//		else{
//			byte b[] = text.getBytes();
//			for(int i=0;i<b.length;i++){
//				codeList.add((int)b[i]);
//			}
//			//codeList = new LfCode(text).encode();
//		}	
		
		codeStr = encode(text);

	}
}
