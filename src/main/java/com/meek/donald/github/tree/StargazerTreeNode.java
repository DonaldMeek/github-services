package com.meek.donald.github.tree;

import java.util.ArrayList;
import java.util.List;

import com.meek.donald.github.model.UserModel;

public class StargazerTreeNode extends TreeNode 
		implements Climbable{
	public static final int MAX_REPOSITORIES = 5;
	private static final int MAX_STARGAZERS = 3;
	private List<String> repositories;
	private UserModel users;
	
	public void addRepository(String repo) {
		if (repositories == null) repositories = new ArrayList<>();
		else if (repositories.size() == StargazerTreeNode.MAX_REPOSITORIES) return;
		repositories.add(repo);
	}
	private boolean userWasAdded(TreeNode rootNode, TreeNode currentNode) {
		String userId = ((StargazerTreeNode) currentNode).getUserModel().getLogin();
		String json = ((StargazerTreeNode) rootNode).getJson(" ");
		boolean jsonContainsUserId = json.contains(userId);
		json = null;
		return jsonContainsUserId;
	}
	
	public List<String> getRepositories() {
		return repositories;
	}
	public void setRepositories(List<String> repositories) {
		this.repositories = repositories;
	}
	@Override
	public void addChild(TreeNode node, TreeNode rootNode) {
		if (userWasAdded(rootNode, node)) return;
		if (super.children == null) {
			super.children = new ArrayList<TreeNode>();
		}
		else if (super.children.size() == StargazerTreeNode.MAX_STARGAZERS || 
				getDepth() >= super.getMaxDepth()) return;
		node.setParent(this);
		this.children.add(((StargazerTreeNode)(node)));
	}
	
	public int getMaxStargazers() {
		return StargazerTreeNode.MAX_STARGAZERS;
	}
	public String getRepositoryAtIndex(int index) {
		if (repositories == null) return " ";
		if (!repositories.isEmpty() && repositories.size() > index) 
		{
			return repositories.get(index);
		}
		return " ";
	}
	public UserModel getUserModel() {
		return users;
	}
	public void setUserModel(UserModel follower) {
		this.users = follower;
	}
	
	public String getJson(String json) {

		if (children == null || children.isEmpty()) {
			return json;
		}
		
		for(TreeNode startNode : (children))
		{
			json = getJsonForNode(startNode, json);
			json = ((StargazerTreeNode)startNode).getJson(json);
			
		}
		json = getJsonForNode(this, json);
		return json;
	}
	
	public UserModel getUsers() {
		return users;
	}
	public void setUsers(UserModel users) {
		this.users = users;
	}
	public String getJsonForNode(TreeNode tn, String json) {
		if (tn == null || ((StargazerTreeNode)tn).getUserModel() == null
				|| ((StargazerTreeNode)tn).getRepositories() == null) {
			return " ";
		}
		json = json + "stargazer:"+
				((StargazerTreeNode) tn).getUserModel().getLogin() +
				",";
		json = json + "repositories:[";
		for (String repo : ((StargazerTreeNode)tn).getRepositories()) {
			json = json + repo + ",";
		}
		json = json.substring(0, json.length() - 1) + "],";
		return json;
	}
}
