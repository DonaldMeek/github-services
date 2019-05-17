package com.meek.donald.github.tree;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import com.meek.donald.GitHubServicesApplication;
import com.meek.donald.github.GitHubController;
import com.meek.donald.github.model.UserModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GitHubServicesApplication.class)
public class StargazerTreeNodeTest {

    @Autowired
    private GitHubController controller;
	private TreeNode testRootNode;
	private String testRepository = "test-repository";
	private String testRepo = "test-repo";
	private String testableRepo = "testable-repo";
	private UserModel user;
	private TreeNode testTempNode;
	
	@Before
	public void testBeforeStargazerTreeNode() {
	
		testRootNode = new StargazerTreeNode();
		for (int i = 0; i <((StargazerTreeNode) testRootNode).getMaxStargazers();
				i++)
		{
			TreeNode tn = new StargazerTreeNode();
			UserModel uModel = new UserModel();
			uModel.setLogin("testLogIn");
			List<String> repos = new ArrayList<>();
			repos.add(testRepo);
			repos.add(testableRepo);
			repos.add(testRepo);
			repos.add(testableRepo);
			((StargazerTreeNode)tn).setRepositories(repos);
			((StargazerTreeNode)tn).setUserModel(uModel);
			defineNode("Test1");
			((StargazerTreeNode) testRootNode).addChild(tn, testRootNode);
		}
	}
	
	private void defineNode(String test) {
		user = new UserModel();
		user.setLogin(test);
		testTempNode = new StargazerTreeNode();
		((StargazerTreeNode)testTempNode).setUserModel(user);
	}
	
	@Test
	public void testAddNodesToMaxDepth() {
		for (int i = 0; i < ((StargazerTreeNode)testRootNode).getMaxDepth(); i++)
		{
			defineNode("Test10987654321");
			addChildToAnyStargazerNodeAtHeight(i);
		}
	}
	private void addChildToAnyStargazerNodeAtHeight(int height, int runningHeight, 
			TreeNode climbableNode) {
		runningHeight++;

		if (height > runningHeight) {
			defineNode("Test321123321");
			addChildToAnyStargazerNodeAtHeight(height, runningHeight, 
					climbableNode.getChildren().get(0));
			return;
		}
		else if (runningHeight == height) {
			if (!climbableNode.isLeafNode());
			else  {
				defineNode("Test3211235!");
				((StargazerTreeNode)climbableNode).addChild(
					testTempNode, testRootNode);
			}
			return;
		}
		throw new IndexOutOfBoundsException(
				"User error - invalid height of child leaf");	
	}
	
	@Test
	public void testAddRepositories() {
		try {
			((StargazerTreeNode)(testRootNode)).addRepository(testRepo);
			assertTrue(true);
		}
		catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetRepositoryAtIndex() {
		try {
			((StargazerTreeNode)(testRootNode)).addRepository(testRepo);
			((StargazerTreeNode)(testRootNode)).addRepository(testRepository);
			assertTrue(((StargazerTreeNode)(testRootNode)).
					getRepositoryAtIndex(0).equals(testRepo));
			assertTrue(((StargazerTreeNode)(testRootNode)).
					getRepositoryAtIndex(1).equals(testRepository));
			assertTrue(((StargazerTreeNode)(testRootNode)).
					getRepositoryAtIndex(2).equals(" "));
		}
		catch (Exception e) {
			fail();
		}
	}
	
	private void addChildToAnyStargazerNodeAtHeight(int height) {
		int runningHeight = 0;
		if (testRootNode.getChildren() != null &&
				!testRootNode.getChildren().isEmpty()) {
			if (height == 1) {
				defineNode("Test321");
				((StargazerTreeNode)testRootNode.getChildren().get(0)).addChild(
						testTempNode, testRootNode);
			}
			if (height > 1) {
				defineNode("Test54321");
				addChildToAnyStargazerNodeAtHeight(height, runningHeight, 
						testRootNode.getChildren().get(0));
			}
		}
		else {
			defineNode("Test12345");
			((StargazerTreeNode)testRootNode).addChild(
					testTempNode, testRootNode);
		}
	}
	
	
    @Test
    public void testAppContexLoad() throws Exception {
        assertTrue(controller != null);
    }
}
