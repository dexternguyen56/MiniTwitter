package miniTwitter;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;  
import javax.swing.tree.*;

/**
 * @author Tran Nguyen
 * 
 * TreeData class that holds the name of users, groups, and Jtree as composite pattern
 *
 */
public class TreeData {

	private DefaultMutableTreeNode root;
	private JTree tree;
	

	
	private ArrayList<String> users;
	private ArrayList<String> groups;
	

	public TreeData() {
		
		users = new ArrayList<String>();
		groups = new ArrayList<String>();
		
		
		root = addGroup("Root",null);
		tree = new JTree(root);
	}
	
	
	/**
	 * call searchTree to find the user name in the tree
	 */
	public DefaultMutableTreeNode searchUser(String name) {
		return searchTree( name, root);
	}
	
	
	// Check for valid user name in the list
	public boolean checkUser(String ID) {
//		return users.contains(ID);
		return users.contains(ID);
	}
	
	
	
	/**
	 * Find the user by name recursively by checking the current node ID with target
	 */
	private DefaultMutableTreeNode searchTree( String ID, DefaultMutableTreeNode node) {
		String nodeName = node.toString();
		DefaultMutableTreeNode child;
		
		if (nodeName.equals(ID)) {
			return node;
		} 
		// If the current node is not a leaf
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildCount(); i++) {
				// Find the ID recursively with the current child node
				child = searchTree( ID, (DefaultMutableTreeNode)node.getChildAt(i));
				if (child != null) {
					return child;
				}
			}
			
		}
		
		return null;
		
	}
	


	/**
	 * Add user to the current user name list and the tree node 
	 */
	public DefaultMutableTreeNode addUser(String ID, DefaultMutableTreeNode node) {
		if (!users.contains(ID)) {
			
			User user = new User(ID);
			users.add(ID);
			
			UserGroup parentGroup = (UserGroup)node.getUserObject();
			parentGroup.addChildNode(user);
			
			DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
			node.add(userNode);
			
			return userNode;
		}

		return null;		
	}
	
	/**
	 * Add group to the current user group list and the tree node 
	 */
	public DefaultMutableTreeNode addGroup(String ID, DefaultMutableTreeNode node) {
		if (!groups.contains(ID)) {
			groups.add(ID);
			
			UserGroup group = new UserGroup(ID);
			DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
			
			if (node != null) {
				UserGroup parentGroup = (UserGroup)node.getUserObject();
				parentGroup.addChildNode(group);
				node.add(groupNode);
			}
			
			
			return groupNode;
		}

		return null;
	}
	
	public JTree getTree() {
		return tree;
	}

	public DefaultMutableTreeNode getRoot() {
		return root;
	}
	


}
