package com.meek.donald.github.tree;

import java.util.ArrayList;
import com.meek.donald.github.model.UserModel;

public class FollowerTreeNode extends TreeNode 
		implements Climbable {
	private static final int MAX_FOLLOWERS = 5; 
	
	private UserModel userModel;
	@Override
	public void addChild(TreeNode node, TreeNode rootNode) {
		if(userWasAdded(rootNode, node)) return;
		if (super.children == null) super.children = new ArrayList<TreeNode>();
		else if (super.children.size() == FollowerTreeNode.MAX_FOLLOWERS || 
				getDepth() >= super.getMaxDepth()) return;
		node.setParent(this);
		this.children.add(((FollowerTreeNode)(node)));
	}
	
	private boolean userWasAdded(TreeNode rootNode, TreeNode currentNode) {
		String userId = ((FollowerTreeNode) currentNode).getUserModel().getLogin();
		String json = ((FollowerTreeNode) rootNode).getJson(" ");
		boolean jsonContainsUserId = json.contains(userId);
		json = null;
		return jsonContainsUserId;
	}
	public int getMaxFollowers() {
		return FollowerTreeNode.MAX_FOLLOWERS;
	}
	public UserModel getUserModel() {
		return userModel;
	}
	public void setUserModel(UserModel follower) {
		this.userModel = follower;
	}
	
	@Override
	public String getJson(String json) {

		if (children == null || children.isEmpty()) {
			return json;
		}
		
		for(TreeNode startNode : (children))
		{
			json = getJsonForNode(startNode, json);
			json = ((FollowerTreeNode)startNode).getJson(json);
			
		}
		json = getJsonForNode(this, json);
		return json;
	}
	
	private String getJsonForNode(TreeNode tn, String json) {
		if (tn == null || ((FollowerTreeNode)tn).getUserModel() == null) {
			return " ";
		}
		json = json + "follower:"+
				((FollowerTreeNode) tn).getUserModel().getLogin() +
				",";
		json = json.substring(0, json.length() - 1) + ",";
		return json;
	}
}
