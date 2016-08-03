package com.chen.huffman;

import java.util.ArrayList;
import java.util.List;

import com.chen.util.MethodUtil;

public class Replace {

	private String string;
	public Replace(String string) {
		// TODO Auto-generated constructor stub
		this.string = string;
	}

	/**
	 * 替换
	 * @return
	 */
	public List<Integer> replace() {
		String afterStr = "";
		String tempStr = "";
		List<Integer> wts = new ArrayList<>();
		//记录压缩类型的，替换用0，哈夫曼用1
		wts.add(0);
		for (int i = 0; i < string.length(); i += 4) {
			String temp = "";
			for (int j = 0; j < 4; j++) {
				temp = string.charAt(i + j) + "";
				if (temp.equals("A") || temp.equals("0")) {
					tempStr += "00";
				}
				if (temp.equals("C")) {
					tempStr += "01";
				}
				if (temp.equals("T")) {
					tempStr += "10";
				}
				if (temp.equals("G")) {
					tempStr += "11";
				}
				if (temp.equals("")) {
					break;
				}
			}
			
			//tempStr = MethodUtil.getBitStr(tempStr);
			
			int w = MethodUtil.getIntFromBanaryStr(tempStr);
			
			wts.add(w);
			
			tempStr = "";
		}
		return wts;
	}
}
