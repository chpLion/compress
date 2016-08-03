package com.chen.huffman;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.chen.util.MethodUtil;

public class HuffmanTree {
	private boolean debug = false;

    private HashMap<Character, Node> nodeMap;
    private ArrayList<Node> nodeList;

    private static HuffmanTree mInstance = null;
    public HuffmanTree() {
        nodeMap = new HashMap<Character, Node>();
        nodeList = new ArrayList<Node>();
    }
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public static HuffmanTree getInstance(){
    		if(mInstance == null){
    			return new HuffmanTree();
    		}else{
    			return mInstance;
    		}
    }
    public String decode(Map<String, Character> codeTable, String binary) {
    		
        int begin = 0, end = 1, count = binary.length();
        StringBuffer sb = new StringBuffer();
        while (end <= count) {
            String key = binary.substring(begin, end);
            if (codeTable.containsKey(key)) {
                sb.append(codeTable.get(key));
                begin = end;
            } else {
            }
            end++;
        }
        return sb.toString();
    }
    public  String encode(String originText) {
        if (originText == null) return null;
        
        nodeList.clear();
        nodeMap.clear();
        calculateWeight(originText);

//        if (debug) printNodes(nodeList);
        Node root = generateHuffmanTree(nodeList);

        generateHuffmanCode(root, "");

        if (debug) printNodes(root);

        StringBuffer sb = new StringBuffer();
        for (Character key : originText.toCharArray()) {
            sb.append(nodeMap.get(key).huffCode);
        }
        if (debug) System.out.println("二进制："+sb.toString());

        return sb.toString();
    }

    /**
     * 计算叶子权值
     * @param text
     */
    private void calculateWeight(String text) {
        for (Character c : text.toCharArray()) {
            if (nodeMap.containsKey(c)) {
                nodeMap.get(c).increaseWeight(1);//权值加1
            } else {
                Node leafNode = new Node(c, 1);
                nodeList.add(leafNode);
                nodeMap.put(c, leafNode);
            }
        }
    }

    /**
     * 生成哈夫曼树
     * @param nodes
     */
    private Node generateHuffmanTree(ArrayList<Node> nodes) {
        Collections.sort(nodes);
        while(nodes.size() > 1) {
            Node ln = nodes.remove(0);
            Node rn = nodes.remove(0);
            insertSort(nodes, new Node(ln.weight + rn.weight, ln, rn));
        }
        Node root = nodes.remove(0);
        nodes = null;
        return root;
    }

    /**
     * 插入排序
     * @param sortedNodes
     * @param node
     */
    private void insertSort(ArrayList<Node> sortedNodes, Node node) {
        if (sortedNodes == null) return;
        int weight = node.weight;
        int min = 0, max = sortedNodes.size();
        int index;
        if (sortedNodes.size() == 0) {
            index = 0;
        } else if (weight < sortedNodes.get(min).weight) {
            index = min;//插入到第一个
        } else if (weight >= sortedNodes.get(max-1).weight) {
            index = max;//插入到最后
        } else {
            index = max/2;
            for (int i=0, count=max/2; i<=count; i++) {
                if (weight >= sortedNodes.get(index-1).weight && weight < sortedNodes.get(index).weight) {
                    break;
                } else if (weight < sortedNodes.get(index).weight) {
                    max = index;
                } else {
                    min = index;
                }
                index = (max + min)/2;
            }
        }
        sortedNodes.add(index, node);
    }

    private void generateHuffmanCode(Node node, String code) {
        if (node.isLeaf()) node.huffCode = code;
        else {
            generateHuffmanCode(node.leftChild, code + "0");
            generateHuffmanCode(node.rightChild, code + "1");
        }
    }

    /**
     * 生成码表
     * @return
     */
    public Map<String, Character> getCodeTable() {
        Map<String, Character> map = new HashMap<String, Character>();
        for (Node node : nodeMap.values()) {
            map.put(node.huffCode, node.value);
        }
        return map;
    }

    
    /**
     * 将源字符串编码并且转换成可以直接写文件的list集合
     * @param originText
     * @return
     */
    public List<Integer> encodeWritable(String originText){
    		List<Integer>list = new ArrayList<>();
    		//将索引表记录起来
    		//记录压缩类型的，替换用0，哈夫曼用1
    		list.add(1);
    		
    		String banary = encode(originText);
    		//记录字符的种类数
    		list.add(nodeMap.values().size());
    		//循环记录码表
    		Map<String, Character> codeTable = getCodeTable();
    		for(Map.Entry<String, Character>entry : codeTable.entrySet()){
    			//记录字符值，用int记录
    			list.add((int)entry.getValue());
    			//记录哈夫曼码，将字符串表示的二进制串转换成int型
    			String code = MethodUtil.getBitStr(entry.getKey());
    			list.add(MethodUtil.getIntFromBanaryStr(code));
    			
    		}
    		banary = MethodUtil.getBitStr(banary);
    		for(int i=0;i<banary.length();i+=8){
    			String temp = banary.substring(i, i+8);
    			int tempInt = MethodUtil.changeString(temp);
    			list.add(tempInt);
    		}
    		
    		return list;
    }
    /**
     * 打印节点信息
     * @param root
     */
    private void printNodes(Node root) {
        System.out.println("字符　　权值　　哈夫码");
        printTree(root);
    }

    private void printTree(Node root) {
        if (root.isLeaf()) System.out.println((root.value == null ? "   " : root.value)+"　　　　"+root.weight+"　　　　"+(root.huffCode == null ? "" : root.huffCode));
        if (root.leftChild != null) printTree(root.leftChild);
        if (root.rightChild != null) printTree(root.rightChild);
    }
    /**
     * 打印节点信息
     * @param nodes
     */
    private void printNodes(ArrayList<Node> nodes) {
        System.out.println("字符　　权值　　哈夫码");
        for (Node node : nodes) {
            System.out.println(node.value+"　　　　"+node.weight+"　　　　"+node.huffCode);
        }
    }
}
