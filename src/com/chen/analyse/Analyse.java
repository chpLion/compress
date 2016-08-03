package com.chen.analyse;
import java.io.ObjectInputStream.GetField;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.chen.huffman.Node;
import com.chen.util.MethodUtil;

public class Analyse {

	//存放对字符串统计结果的字典
	 private HashMap<Character, Node> nodeMap = new HashMap<Character, Node>();
	 private String text;
	 public Analyse(String text) {
		// TODO Auto-generated constructor stub
		 this.text = text;
		 calculateWeight(text);
	}
	/**
	 * 计算叶子权值
	 * 
	 * @param text
	 */
	private void calculateWeight(String text) {
		for (Character c : text.toCharArray()) {
			if (nodeMap.containsKey(c)) {
				nodeMap.get(c).increaseWeight(1);// 权值加1
			} else {
				Node leafNode = new Node(c, 1);
				nodeMap.put(c, leafNode);
			}
		}
	}
	
	public int getCount(String text){
		//calculateWeight(text);
		return nodeMap.values().size();
	}
	public double getType(String text){
		//calculateWeight(text);
		int nums[] = new int[nodeMap.values().size()];
		Node[] nodes = new Node[nodeMap.values().size()];
		nodes = nodeMap.values().toArray(nodes);
		for(int i=0;i<nodes.length;i++){
			nums[i] = nodes[i].weight;
		}
		
		return MethodUtil.caculateStandardDeviation(nums);
	}
}
