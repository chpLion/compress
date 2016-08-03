package com.chen.compress;

import java.util.ArrayList;
import java.util.List;

import com.chen.analyse.Analyse;
import com.chen.huffman.HuffmanTree;
import com.chen.huffman.Replace;

/**
 * NDA序列的压缩器
 * 实现压缩接口
 * 使用的是单例模式，创建对象时调用getInstance方法获取对象
 * @author chen
 *
 */
public class DNASqCompressor extends ICompressor{

	private String dna;
	private List<Integer>mCompressList = new ArrayList<>();
	private static DNASqCompressor mInstance = null;
	
	private DNASqCompressor(String dna){
		this.dna = dna;
	}
	
	/**
	 * 单例模式的入口方法
	 * @param dna
	 * @return
	 */
	public static DNASqCompressor getInstance(String dna){
		if (mInstance == null) {
			synchronized (DNASqCompressor.class) {
				if (mInstance == null) {
					mInstance = new DNASqCompressor(dna);
				}
			}
		}
		mInstance.dna = dna;
		mInstance.mCompressList.clear();
		return mInstance;
	}
	public void setDna(String dna) {
		this.dna = dna;
	}
	public static DNASqCompressor getInstance(){
		return getInstance("");
	}
	public List<Integer> getCompressList(){
		mCompressList.clear();
		compress();
	
		return mCompressList;
	}
	@Override
	void compress() {
		// TODO Auto-generated method stub
		
		//对DNA序列进行分析，得出应该使用何种压缩算法
		Analyse analyse = new Analyse(dna);
		double d = analyse.getType(dna);
		HuffmanTree hTree = new HuffmanTree();
		
		if (d>30||analyse.getCount(dna)==2||analyse.getCount(dna)==5) {
			mCompressList = hTree.encodeWritable(dna);
		}else{
			mCompressList = new Replace(dna).replace();
		}
	}



}
