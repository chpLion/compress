package com.chen.decompress;

public class TitleDecompress {

	//需要解压的标题
	private String title;
	
	//解压之后的原标题
	private String originTitle = "";
	
	//文件头
	private String headInfo = "";
	
	/**
	 * 需要解压的标题
	 * @param title 需要解压的标题
	 */
	public TitleDecompress(String title) {
		// TODO Auto-generated constructor stub
		this.title = title;
	}
	
	public void setHeadInfo(String headInfo) {
		this.headInfo = headInfo;
	}
	
	public String decodeTitle(){
		
		if (headInfo == "") {
			System.out.println("缺少文件头，可能文件已经损坏");
			return "";
		}
		
		/**
		 * 使用空格来区分文件头的两个部分
		 * 同样的 使用空格来区分压缩之后的标题的两个部分
		 * 这是由写文件头的格式来确定的		
		 */
		String infos[] = headInfo.split(" ");
		String titles[] = title.split(" ");
		/**
		 * 压缩之后的标题如11559 1954:1946/2:
		 * 原先的标题应该是@SRR099966.11559 80C29ABXX110103:2:1:1954:1946/2
		 * 那么首先是用信息头加上“.”再加上压缩之后的标题得到
		 */
		originTitle += infos[0]+"."+titles[0]+" ";
		//加入第二部分
		originTitle += infos[1]+titles[1];
		return originTitle;
	}
}
