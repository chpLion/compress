package com.chen.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.chen.analyse.AnalyseDNA;
import com.chen.analyse.IAnalyse;
import com.chen.analyse.ModelType;
import com.chen.fileoperator.FastqFileWriter;
import com.chen.huffman.HuffmanTree;
import com.chen.huffman.Replace;


/**
 * 超级块，用于具体执行压缩
 * @author chen
 *
 */
public class SuperBlock {

	private List<Record> superBlock = new ArrayList<>();
	private IAnalyse analyse;
	private int bolckNumber;
	//测试路径
    String path1= "/Users/chen/Desktop/decode.txt";
	
	public SuperBlock(List<Record> superBlock,int bolckNumber) {
		super();
		this.superBlock = superBlock;
		this.bolckNumber = bolckNumber;
	}
	
	public int getBolckNumber() {
		return bolckNumber;
	}
	/**
	 * 具体执行的操作
	 */
	public void process(){
		FastqFileWriter writer = new FastqFileWriter(new File(path1));
		//逐个分析
		for(int i=0;i<superBlock.size();i++){
			//获取一个记录
			Record record = superBlock.get(i);
			Replace replace = new Replace(record.getDNBSequence());
			List<Integer> list = replace.replace();
			for(int j=0;j<list.size();j++){
				writer.write(list.get(j));
			}
			
//			//压缩分析标题
//			//writer.write(record.getTitle());
//			//分析压缩序列行
//			//得到使用哪种模型
//			analyse = new AnalyseDNA(record.getDNBSequence());
//			ModelType type = analyse.analyseStr();
//			if (type == ModelType.HUFFMAN) {
//				//使用哈夫曼编码
//				HuffmanTree huffmanTree = HuffmanTree.getInstance();
//				//得到压缩后需要写入文件的二进制流
//				String banry = huffmanTree.encode(record.getDNBSequence());
//				writer.write(banry);
//			}
//			else{
//				//使用替换
//			}
//			//质量
//			
		}
		
		
	}

}
