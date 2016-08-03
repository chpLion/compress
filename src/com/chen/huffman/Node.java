package com.chen.huffman;

public class Node implements Comparable<Node>{

	public int weight;//权值
    Node leftChild;//左孩子结点
    Node rightChild;//右孩子结点
    String huffCode;
    private boolean isLeaf;//是否是叶子
    Character value;

    public Node(Character value, int weight) {
        this.value = value;
        this.weight = weight;
        this.isLeaf = true;
    }

    public Node(int weight, Node leftChild, Node rightChild) {
        this.weight = weight;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public void increaseWeight(int i) {
        weight += i;
    }

    public boolean isLeaf() {
        return isLeaf;
    }
    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

}
