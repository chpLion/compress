package com.chen.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 使用java的zip库进行压缩
 * 使用zip压缩，普通的压缩器
 * 实现压缩接口
 * @author chen
 *
 */
public class ZipCompressor extends ICompressor{

	//需要压缩的原文件
	private File file;
	
	/**
	 * 构造方法，传入需要压缩的文件
	 * @param file
	 */
	public ZipCompressor(File file) {
		super();
		this.file = file;
	}


	@Override
	public
	 void compress() {
		// TODO Auto-generated method stub
		
		//压缩之后存放的临时文件
		File zipFile = new File("/Users/chen/Desktop/temp.zip");
		//创建需要压缩的文件流
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStream inputStream = new FileInputStream(file);
			//创建zip文件流
			ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
			//zip压缩流
			zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
			
			int temp = 0;
			long totol = file.length();
			float count = 0;
			while((temp = inputStream.read())!=-1){
				zipOutputStream.write(temp);
				count++;
				
			}
			zipOutputStream.flush();
			zipOutputStream.close();
			inputStream.close();
			System.out.println("二次压缩完成");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
