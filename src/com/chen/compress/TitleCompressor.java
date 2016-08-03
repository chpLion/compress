package com.chen.compress;
import java.util.ArrayList;
import java.util.List;

/**
 * 标题的压缩器 实现压缩接口 用空格将标题分割成不同的部分， 例如 @SRR099966.11559
 * 80C29ABXX110103:2:1:1954:1946/2 length = 48 @SRR099966.11591
 * 80C29ABXX110103:2:1:3770:1948/2 @SRR062634.321
 * HWI-EAS110_103327062:6:1:1446:951/2 @SRR062634.488
 * HWI-EAS110_103327062:6:1:1503:935/2
 * 第一部分是@开头的字符串，再用“.”分割，就可以得到两个部分的序列，其中第一部分是相同的，可以放在文件头 “.”后面的部分需要分别记录，
 * 第二部分的字符串用“：”分割，可以得到若干部分的序列，其中前三个部分的序列又是相同的，因此可以提取到文件头
 * 那么标题的字符串重复性小，使用哈夫曼效果并不太好，如果使用提取到文件头的办法，当文件很大的时候，文件头
 * 的信息可以忽略不计，那么可以计算出他的压缩率大概为16/48 = 30%，而使用哈夫曼大约为24/48在加上关键字大于50%
 * 因此标题行可以采用提取相同部分到文件头的方式来提高压缩效率
 * 通过本类的操作，可以使用getCompressStr()方法得到压缩之后的字符串
 * 但是必须要使用getHeadInfoStr()方法得到需要记录在文件头的部分
 * @author chen
 *
 */
public class TitleCompressor extends ICompressor{

	private String title;
	
	//单例模式中的对象
	private static TitleCompressor mInstance = null;
	
	//需要记录在文件头的信息
	private String headInfoStr = "";
	
	//用于记录前一行的标题数字信息
	private int preNumber = -1;
	
	//记录前一行标题的y坐标信息
	private int preY = -1;
	
	//压缩之后的标题字符串
	private String compressStr = "";
	
	//用于写文件的int集合
	private List<Integer>list = new ArrayList<>();
	
	private TitleCompressor(String title) {
		// TODO Auto-generated constructor stub
		this.title = title;
	}
	
	/**
	 * 使用单例模式常见对象的入口方法
	 * @param title 需要压缩的标题字符串
	 * @return 创建的对象
	 */
	public static TitleCompressor getInstance(String title){
		if (mInstance == null) {
			synchronized (TitleCompressor.class) {
				if (mInstance == null) {
					mInstance = new TitleCompressor(title);
				}
			}
		}
		mInstance.title = title;
		mInstance.list.clear();
		return mInstance;
	}
	/**
	 * 返回压缩之后的字符串
	 * @return 压缩之后的字符串
	 */
	public List<Integer> getCompressInt(){
		
		this.compress();
		return list;
	}
	
	/**
	 * 返回需要写入文件头的字符串
	 * @return 需要写入文件头的字符串
	 */
	public String getHeadInfoStr() {
		return headInfoStr;
	}
	
	@Override
	void compress() {
		// TODO Auto-generated method stub
		/**
		 * 用空格将标题分割成不同的部分，
		 * 例如
		 * @SRR099966.11559 80C29ABXX110103:2:1:1954:1946/2 length = 48
		 * @SRR099966.11591 80C29ABXX110103:2:1:3770:1948/2
		 * @SRR062634.321 HWI-EAS110_103327062:6:1:1446:951/2
		 * @SRR062634.488 HWI-EAS110_103327062:6:1:1503:935/2
		 * 第一部分是@开头的字符串，再用“.”分割，就可以得到两个部分的序列，其中第一部分是相同的，可以放在文件头
		 * “.”后面的部分需要分别记录，
		 * 第二部分的字符串用“：”分割，可以得到若干部分的序列，其中前三个部分的序列又是相同的，因此可以提取到文件头
		 * 那么标题的字符串重复性小，使用哈夫曼效果并不太好，如果使用提取到文件头的办法，当文件很大的时候，文件头
		 * 的信息可以忽略不计，那么可以计算出他的压缩率大概为16/48 = 30%，而使用哈夫曼大约为24/48在加上关键字大于50%
		 * 因此标题行可以采用提取相同部分到文件头的方式来提高压缩效率
		 */
		
		String titles[] = title.split(" ");
		compressStr = "";
		
		/**
		 * @SRR099966.11559
		 * 第一部分，按照"."进行分割
		 * 得到的字符串数组的第一部分就是公共字符串 可以提取到文件头
		 * @SRR099966
		 * 还需要记录分隔符"."
		 */
		String firstPart[] = titles[0].split("\\.");
		//加上空格区分两个部分压缩出来的字符串
		if (preNumber == -1) {
			//当前是第一行标题行
			
			//由于0是空，所以为了方便，选择记录差值+1，防止空
			list.add(1);
			//将当前数字作为前一行的数字
			//以作为下一行的参考
			preNumber = Integer.valueOf(firstPart[1]);
		}
		else{
			//当不是第一行标题行的时候
			//将当前编码为与上一行的差值
			//并将上一行的值修改为当前行
			list.add(Integer.valueOf(firstPart[1])-preNumber+1);
			
			preNumber = Integer.valueOf(firstPart[1]);
		}
		
		//使用冒号作为分隔
		/**
		 * 80C29ABXX110103:2:1:1954:1946/2
		 * 前三个部分是相同的，需要记录的从第四个部分开始的
		 * 1954 1946/2
		 */
		String secondPart[] = titles[1].split(":");
		for(int i=3;i<secondPart.length;i++){
			compressStr += secondPart[i]+":";
		}
		//将字符串转换成byte
		byte[] bytes = compressStr.getBytes();
		//将byte加入到int集合
		for(int i=0;i<bytes.length;i++){
			list.add((int)bytes[i]);
		}
		/**
		 * 应该是公共的部分
		 * firstpart[0]= @SRR099966
		 * secondpart[0...2]80C29ABXX110103:2:1
		 * secondpart[4] = 1946 记录作为第一行的模板
		 */
		if (headInfoStr == "") {
			//返回信息头应该记录的部分使用空格对两个部分的公共信息进行分隔
			headInfoStr = firstPart[0]+":"+firstPart[1]+" ";
			for(int i=0;i<3;i++){
				headInfoStr += secondPart[i]+":";
			}
			
		}
		
	}

	/**
	 * 将二进制数转换成int型
	 * 这个二进制字符串不能超过八位
	 * @param banary 二进制字符串
	 * @return 转成二进制串对应的int数
	 */
	public int getIntFromBanaryStr(String banary){
		int w = 0;
		if (banary.length()>8) {
			return -1;
		}
		for(int i=banary.length();i>=1;i--){
			
			w +=Integer.valueOf(banary.charAt(banary.length()-i)+"")*Math.pow(2, i-1);
			
		}
		return w;
	}
	
	/**
	 * 将字符串转换成int集合
	 * @param string
	 * @return int类型集合
	 */
	public List<Integer> getIntListFromStr(String string){
		
		int count = string.length()/8;
		
		for(int i=0;i<count;i+=8){
			list.add(getIntFromBanaryStr(string.substring(i, i+8)));
		}
		if (string.length()%8!=0) {
			list.add(getIntFromBanaryStr(string.substring(string.length()-string.length()%8,string.length())));
		}
		return list;
		
	}
}
