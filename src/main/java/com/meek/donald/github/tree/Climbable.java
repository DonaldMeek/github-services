package com.meek.donald.github.tree;

import java.util.List;

public interface Climbable {
	boolean isRootNode();
	int getDepth();
	TreeNode getParent();
	void setParent(TreeNode parent);
	boolean isLeafNode();
	List<TreeNode> getChildren();
	void addChild(TreeNode node, TreeNode rootNode);
	String getJson(String json);

}
