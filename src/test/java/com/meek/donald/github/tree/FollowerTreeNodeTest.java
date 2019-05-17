package com.meek.donald.github.tree;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import com.meek.donald.GitHubServicesApplication;
import com.meek.donald.github.GitHubController;
import com.meek.donald.github.model.RepositoryOwnerModel;
import com.meek.donald.github.model.UserModel;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GitHubServicesApplication.class)
public class FollowerTreeNodeTest {

    @Autowired
    private GitHubController controller;
	
	private TreeNode testRootNode;
	private UserModel user;

	private TreeNode testTempNode;
	
	@Before
	public void testBeforeFollowerTreeNode() {
	
		testRootNode = new FollowerTreeNode();
		defineNode("TestLogin");
		((FollowerTreeNode) testRootNode).addChild(testTempNode, testRootNode);
	}
	
	private void defineNode(String test) {
		user = new UserModel();
		user.setLogin(test);
		testTempNode = new FollowerTreeNode();
		((FollowerTreeNode)testTempNode).setUserModel(user);
	}
	
	@Test
	public void testAddNodesToMaxDepth() {
		for (int i = 0; i < ((FollowerTreeNode)testRootNode).
				getMaxDepth(); i++)
		{
			defineNode("Test1");
			addChildToAnyNodeAtHeight(i+1);
		}
	}
	
	private void addChildToAnyNodeAtHeight(int height) {
		int runningHeight = 0;
		if (testRootNode.getChildren() != null && 
				!testRootNode.getChildren().isEmpty()) {
			if (height == 1) {
				defineNode("Test2");
				((FollowerTreeNode)testRootNode.getChildren().get(0)).addChild(
						testTempNode, testRootNode);
			}
			if (height > 1) {
				
				addChildToAnyNodeAtHeight(height, runningHeight, 
						testRootNode.getChildren().get(0));
			}
		}
		defineNode("Test4");
		((FollowerTreeNode)testRootNode).addChild(testTempNode, testRootNode);
	}
	
	private void addChildToAnyNodeAtHeight(int height, int runningHeight, 
			TreeNode climbableNode) {
		runningHeight++;

		if (height > runningHeight) {
			defineNode("Test6");
			addChildToAnyNodeAtHeight(height, runningHeight, 
					climbableNode.getChildren().get(0));
			return;
		}
		else if (runningHeight == height) {
			if (!climbableNode.isLeafNode());
			else {
				defineNode("Test7");
				((FollowerTreeNode)climbableNode).addChild(
					testTempNode, testRootNode);
			}
			return;
		}
		throw new IndexOutOfBoundsException(
				"User error - invalid height of child leaf");	
	}
	
    @Test
    public void testAppContexLoad() throws Exception {
        assertTrue(controller != null);
    }
}
