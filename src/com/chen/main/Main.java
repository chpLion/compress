package com.chen.main;

import java.io.IOException;

import com.chen.compress.FastqCompressMT;
import com.chen.compress.ICompressOperator;
import com.chen.dsrc.IDsrcOperator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = "/Users/chen/Downloads/SRR099966.filt.fastq";
		String pathPlf = "/Users/chen/Downloads/SRR062634.filt.fastq";
		String p = "";
		ICompressOperator dsrcCompress = FastqCompressMT.getInstance(pathPlf);
		//dsrcCompress.compressFastq();
		Test test = new Test();
		test.testCompressTitle();
		//new TestDecompress().test();
		LzCompress compress = new LzCompress();
		//compress.compress();
//		Runtime runtime = Runtime.getRuntime();
//		Process process = null;
//		try {
//			String cmd = "/Users/chen/Downloads/pack-osx-clang-2/bin/dsrc c /Users/chen/Downloads/SRR042437.filt.fastq /Users/chen/Desktop/a.temp";
//			String cmdD = "/Users/chen/Downloads/pack-osx-clang-2/bin/dsrc b /Users/chen/Desktop/a.temp /Users/chen/Desktop/b.tmp";
//			runtime.exec(cmdD);
//			System.out.println("执行成功");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
