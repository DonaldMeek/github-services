package com.meek.donald.github.tree;

import java.util.List;

public class TreeNode{
	private TreeNode parent;
	protected List<TreeNode> children;
	private static final int MAX_DEPTH = 3;
	
	public boolean isRootNode() {
		return (parent == null);
	}
	
	public boolean isLeafNode() {
		return (children == null || children.isEmpty());
	}
	
	public int getDepth() {
		return getDepth(0);
	}
	
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	private int getDepth(int depthCount) {
		depthCount++;
		if (isRootNode()) return depthCount;
		return parent.getDepth(depthCount);
	}
	
	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}
	
	public int getMaxDepth() {
		return TreeNode.MAX_DEPTH;
	}


}
