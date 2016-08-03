package com.chen.main;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.chen.analyse.Analyse;
import com.chen.analyse.AnalyseQuality;
import com.chen.compress.DNASqCompressor;
import com.chen.compress.RunLehgthCodeCompressor;
import com.chen.compress.TitleCompressor;
import com.chen.compress.ZipCompressor;
import com.chen.decompress.TitleDecompress;
import com.chen.fileoperator.FastqFileReader;
import com.chen.fileoperator.FastqFileWriter;
import com.chen.fileoperator.onReadCompleteListener;
import com.chen.fileoperator.onReadListener;
import com.chen.huffman.HuffmanTree;
import com.chen.huffman.Replace;
import com.chen.lfcode.LfCode;
import com.chen.model.Record;
import com.chen.util.MethodUtil;

import SevenZip.Compression.LZMA.Encoder;

public class Test implements onReadListener{

	String path = "/Users/chen/Downloads/SRR099966.filt.fastq";
	String pathPlf = "/Users/chen/Downloads/SRR062634.filt.fastq";
	String pathencode = "/Users/chen/Desktop/10.chpdsrc";
	String pathMax = "/Users/chen/Downloads/SRR042437.filt.fastq";
	String pathmin = "/Users/chen/Desktop/9.fastq";
	
	//写入标题行临时文件
	private FastqFileWriter titleWriter = new FastqFileWriter(new File("./title.temp"));
	//写入DNA行临时文件
	private FastqFileWriter dnaWriter = new FastqFileWriter(new File("./dna.temp"));
	//写入质量行临时文件
	private FastqFileWriter qualityWriter = new FastqFileWriter(new File("./quality.temp"));

	File file = new File(path);
	AnalyseQuality analyseQuality = new AnalyseQuality();
	RunLehgthCodeCompressor codeCompressor = new RunLehgthCodeCompressor();
	DNASqCompressor dnaSqCompressor = DNASqCompressor.getInstance();
	FastqFileReader reader;
	public void testCompressTitle(){

		reader = new FastqFileReader(this,pathPlf);
		reader.read();
	}

	
	@Override
	public synchronized void onProgress(Record record,long currentLength) {
		// TODO Auto-generated method stub

		String title = record.getTitle();
		String dna = record.getDNBSequence();
		String thirdStr = record.getThirdStr();
		String quality = record.getQualityScore();
		
		TitleCompressor titileCompressor = TitleCompressor.getInstance(title);		
		
		List<Integer> dnaList = new ArrayList<>();
		
		dna = MethodUtil.getBitStr(dna);
		dnaSqCompressor.setDna(dna);
		dnaList = dnaSqCompressor.getCompressList();
		
		titileCompressor = TitleCompressor.getInstance(title);
		List<Integer> compressInt = titileCompressor.getCompressInt();
		//将压缩的标题行写入标题行的临时文件
		titleWriter.write(compressInt);
		
		//将dna压缩之后写入DNA临时文件
		dnaWriter.write(dnaList);
		
		codeCompressor.setQuality(quality);
		String codeStr = codeCompressor.getCodeStr();
		//将压缩之后的质量行写入质量行临时文件
		qualityWriter.write(codeStr);

		//System.out.println("max = "+codeCompressor.getMax()+"min = "+codeCompressor.getMin());
//		if (per%2>1.5) {
//			System.out.println("已完成"+per+"%");
//		}
		

	}

	@Override
	public void onFail(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		LzCompress compress = new LzCompress();
		compress.compress();
		
		
	}

	@Override
	public void onBegin(String title) {
		// TODO Auto-generated method stub
		
	}

	
}
