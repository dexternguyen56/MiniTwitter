package miniTwitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.*;  
import javax.swing.tree.*;

import guiComponents.AdminControlPanel;

/**
 * @author Tran Nguyen
 * 
 * TreeData class that holds the name of users, groups, and Jtree as composite pattern
 * TreeData also follows singleton Pattern
 *
 */
public class TreeData {
	
	private static TreeData instance;
	
	private DefaultMutableTreeNode root;
	private JTree tree;
		
	//Value will be <Entry, DefaultMutableTreeNode>
	//These HashMaps will hold reference to the Entry objects, and Its DefaultMutableTreeNode
	//We only need users for now but we can send messages to the whole group if needed in the future
	private HashMap<String,HashMap<String,Object>> users;
	private HashMap<String,HashMap<String,Object>> groups;
	
	private String lastUpdatedUserID;


	public static TreeData getInstance() {
		if (instance == null) {
			instance = new TreeData();
		}
		return instance;
	}
	

	private TreeData() {

		users = new HashMap<>();
		groups = new HashMap<>();
		
		root = addGroup("Root", null);
		tree = new JTree(root);
		

	}
	
	
	/**
	 * Return the node reference from the current user name
	 * Constant look up time O(1)
	 */
	public DefaultMutableTreeNode searchUser(String name) {
		
		if (users.containsKey(name)) {
			return (DefaultMutableTreeNode)users.get(name).get("node");
		}
		
		return null;
	}
	
	
	/**
	 * Add user to the current user name list and the tree node 
	 */
	public DefaultMutableTreeNode addUser(String ID, DefaultMutableTreeNode node) {
		if (!users.containsKey(ID)) {
			
			//create the value hashmap
			HashMap<String,Object> valueInfo = new HashMap<>();
			
			//create new user and add to the hashmap
			User user = new User(ID);
			valueInfo.put("entry", user);
			
			//Get the current node's group parent and add user to that group
			UserGroup parentGroup = (UserGroup)node.getUserObject();
			parentGroup.addChildNode(user);
			
			// Create a new tree node and add user to that node
			DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
			node.add(userNode);
			valueInfo.put("node", userNode);
			
			// Save the current tree value to the list
			users.put(ID, valueInfo);
			
			return userNode;
		}

		return null;		
	}
	
	/**
	 * Add group to the current user group list and the tree node 
	 * Future implementation if we need to send msg to the whole group
	 * 
	 */
	public DefaultMutableTreeNode addGroup(String ID, DefaultMutableTreeNode node) {
		if (!groups.containsKey(ID)) {
			
			//create the value hashmap
			HashMap<String,Object> valueInfo = new HashMap<>();
			
			//create new  group and add to the hashmap
			UserGroup group = new UserGroup(ID);
			valueInfo.put("entry", group);
			
			
			DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
			
			//If parent group is no null, we'll add this group the the parentGroup
			if (node != null) {
				UserGroup parentGroup = (UserGroup)node.getUserObject();
				parentGroup.addChildNode(group);
				node.add(groupNode);
			}
			
			valueInfo.put("node", groupNode);
		
			// Save the current tree value to the list
			groups.put(ID, valueInfo);
			
			
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
