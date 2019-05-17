package com.meek.donald.github;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonParseException;
import com.meek.donald.github.model.UserModel;
import com.meek.donald.github.model.RepositoryModel;
import com.meek.donald.github.model.RepositoryOwnerModel;
import com.meek.donald.github.tree.FollowerTreeNode;
import com.meek.donald.github.tree.StargazerTreeNode;
import com.meek.donald.github.tree.TreeNode;

@RestController
@RequestMapping("/github")
public class GitHubController {
	
	@Autowired
	GitHubService gitHubService;
	
	private RepositoryOwnerModel ownerModel;
	private TreeNode rootNode;
	
	
	@GetMapping(value="/followers/{id}", 
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getFollowers(@PathVariable("id") String id) {
		List<UserModel> childrenFollowers = null;
		rootNode = new FollowerTreeNode();
		UserModel userModel = new UserModel();
		userModel.setLogin(id);
		((FollowerTreeNode)rootNode).setUserModel(userModel);
		try {
			childrenFollowers = gitHubService.
					getFollowersById(id);
			getMaxFollowers(rootNode, childrenFollowers);
			return parseResultJson(((FollowerTreeNode)rootNode).getJson(" "));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return HttpStatus.INTERNAL_SERVER_ERROR.toString();
	}
	
	public String parseResultJson(String parseableResultJson) {
		return "{" + parseableResultJson.substring(0, 
				parseableResultJson.length() - 1) + "}";
	}
	
	private void getMaxFollowers(TreeNode node, List<UserModel> childrenFollowers) 
		throws JsonParseException, 
				IOException {
		TreeNode childNode = null;
		UserModel userModel = null;
		List<UserModel> followers;
		for (int i = 0; i < ((FollowerTreeNode) node).getMaxFollowers(); i++)
		{
			childNode = new FollowerTreeNode();
			if (childrenFollowers != null && 
					i < childrenFollowers.size() &&
						!childrenFollowers.isEmpty())
			{
				userModel = childrenFollowers.get(i);
			}
			else return;
			if (userModel == null) return;
			((FollowerTreeNode)childNode).setUserModel(userModel);
			((FollowerTreeNode) node).addChild(
					childNode, rootNode);
			followers = gitHubService.getFollowersById(userModel.getLogin());
			if (followers == null) return;
			getFollowersToMaxHeight(childNode, followers);
		}
	}
	private void getFollowersToMaxHeight(TreeNode followerNode, 
			List<UserModel> users)
					throws JsonParseException, 
							IOException {
		if (followerNode.getDepth() >= ((FollowerTreeNode) followerNode).
				getMaxDepth()) return;
		getMaxFollowers(followerNode, users);
	}
	
	@GetMapping(value="/stargazers/{id}", 
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getStargazers(@PathVariable("id") String id) {
		rootNode = new StargazerTreeNode();
		UserModel userModel = new UserModel();
		userModel.setLogin(id);
		try {
			List<UserModel> users = getStargazerUsers(id);
			((StargazerTreeNode)rootNode).setRepositories(
					ownerModel.getRepositories());
			((StargazerTreeNode)rootNode).setUserModel(userModel);
			getMaxStargazers(rootNode, users);
			return parseResultJson(((StargazerTreeNode) rootNode).getJson(" "));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return HttpStatus.INTERNAL_SERVER_ERROR.toString();
	}
	
	private List<UserModel> getStargazerUsers(String id) 
			throws JsonParseException, IOException {
		RepositoryOwnerModel owner = null;
		List<RepositoryModel> repo = gitHubService.getRepositoriesById(id);
		if (repo != null && !repo.isEmpty())
		{
			owner = repo.get(0).getOwner();
		}
		else 
		{
			return null;
		}
		addRepositoriesToOwnerModel(repo);
		return gitHubService.getStargazersByUser(
				owner.getLogin(), repo.get(0).getName());
	}
	private void addRepositoriesToOwnerModel(List<RepositoryModel> repositories) {
		List<String> repoList = new ArrayList<String>();
		ListIterator<RepositoryModel> repoListIterator = 
				repositories.listIterator();
		for (int i = 0; i < StargazerTreeNode.MAX_REPOSITORIES;i++) {
			if (repoListIterator.hasNext()) {
				repoList.add(repoListIterator.next().getName());
			}
			else break;
		}
		if (ownerModel == null) ownerModel = new RepositoryOwnerModel();
		ownerModel.setRepositories(repoList);
	}
	
	private void getMaxStargazers(TreeNode node, 
			List<UserModel> childrenStargazers) 
			throws JsonParseException, 
			IOException {
		TreeNode childNode = null;
		UserModel userModel = null;
		List<UserModel> stargazers;
		for (int i = 0; i < ((StargazerTreeNode) node).getMaxStargazers(); i++)
		{
			childNode = new StargazerTreeNode();
			if (childrenStargazers != null && 
					i < childrenStargazers.size() &&
						!childrenStargazers.isEmpty())
			{
				userModel = childrenStargazers.get(i);
			}
			else return;
			if (userModel == null) return;
			((StargazerTreeNode)childNode).setUserModel(userModel);
			((StargazerTreeNode)childNode).setRepositories(
					ownerModel.getRepositories());
			((StargazerTreeNode) node).addChild(
					childNode, rootNode);
			stargazers = getStargazerUsers(userModel.getLogin());
			if (stargazers == null) return;
			getStargazersToMaxHeight(childNode, stargazers);
		}
	}
	
	private void getStargazersToMaxHeight(TreeNode stargazerNode, 
			List<UserModel> users)
			throws JsonParseException, 
			IOException {
		if (stargazerNode.getDepth() >= ((StargazerTreeNode) stargazerNode).
				getMaxDepth()) return;
		getMaxStargazers(stargazerNode, users);
	}
	
}
