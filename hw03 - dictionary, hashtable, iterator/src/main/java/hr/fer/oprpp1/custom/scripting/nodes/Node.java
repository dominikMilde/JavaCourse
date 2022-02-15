package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {
	private boolean hasChild = false;
	private ArrayIndexedCollection<Node> childNodes;
	public void addChildNode(Node child) {
		if(!hasChild) childNodes = new ArrayIndexedCollection<>();
		
		childNodes.add(child);
	}
	public int numberOfChildren() {
		return childNodes.size();
	}
	public Node getChild(int index) {
		return childNodes.get(index);
	}
}
