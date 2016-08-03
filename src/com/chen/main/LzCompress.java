package com.chen.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

import SevenZip.Compression.LZMA.Encoder;

public class LzCompress {

	private String path = "/Users/chen/Desktop/10.chpdsrc";
	String encodePath = "/Users/chen/Desktop/temp2.hahah";
	private String titleTempPath = "./title.temp";
	private String dnaTempPath = "./dna.temp";
	private String qualityTempPath = "./quality.temp";
	
	private FileInputStream finTitle;
	private FileInputStream finDna;
	private FileInputStream finQuality;
	private FileOutputStream fout ;
	
	
	public void compress(){
		
		try {
			finTitle = new FileInputStream(new File(titleTempPath));
			finDna = new FileInputStream(new File(dnaTempPath));
			finQuality = new FileInputStream(new File(qualityTempPath));
			fout = new FileOutputStream(new File(encodePath));
			Encoder encoder = new Encoder();
			encoder.Code(finTitle, fout, -1, -1, null);
			encoder.Code(finDna, fout, -1, -1, null);
			encoder.Code(finQuality, fout, -1, -1, null);
			
			System.out.println("完成");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
