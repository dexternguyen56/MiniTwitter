package guiComponents;


import javax.swing.*;  
import javax.swing.tree.*;

import miniTwitter.TreeData;
import miniTwitter.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



/**
 * @author Tran Nguyen
 * 
 * User View panel class
 *
 */
public class UserView {
	
	
	private User user;
	private TreeData tree;
	private DefaultMutableTreeNode userNode;
	private DefaultListModel <String> newsFeedModel;
	private ArrayList<String> sourceData ;
	
	private DefaultListModel<String> followingModel;
	
	private boolean skip;
	
	
	private JFrame userFrame;
	
	private JButton btnFollow;
	private JButton btnTweet;
	
	private JLabel labelFollowing;
	private JLabel labelNewsFeed;
	
	private JTextField textUser;
	private JTextField testMsg;
	
	private JScrollPane paneFollowing;
	private JScrollPane paneNewsFeed;
	
	private JList<String> listFollowing;
	private JList<String> listNewsFeed;
	
	

	/**
	 * Create treeView from the current tree data and node entry
	 */
	public UserView(TreeData tree, DefaultMutableTreeNode entry) {
		this.tree = tree;
		userNode = entry;
		user = (User)userNode.getUserObject();
		user.setUserView(this);
		userFrame = new JFrame("User View: " + user);
		createPanel();
		userFrame.setVisible(true); 
	}
	
	
	public void addNewsFeed(String ID, String message) {
		newsFeedModel.addElement("@" + ID + ": " + message);
	}
	

	private void createPanel() {

		userFrame.setResizable(false);
		userFrame.setBounds(100, 100, 388, 422);
		userFrame.setLayout(null);
		userFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		followingModel = new DefaultListModel<String>();
		sourceData = user.getFollowings();
		skip = true;
		for(String user: sourceData) {
			if(skip) {
				skip = false;
				continue;
			}
			followingModel.addElement(user.toString());
		}
		

		textUser = new JTextField();
		textUser.setBounds(10, 11, 163, 44);
		userFrame.add(textUser);
		
		// Build the following list text and button
		btnFollow = new JButton("Follow User");
		btnFollow.setBounds(183, 11, 181, 44);
		userFrame.add(btnFollow);
		btnFollow.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
				String newFollower = textUser.getText();
				//  Check if the new follower name is valid and exist
				if (!newFollower.equals("")) {
					if (tree.checkUser(newFollower)) { 
						followingModel.addElement(newFollower);
						user.setFollowings(newFollower);
						
						// Find the follower and use observer
						User follower = (User)tree.searchUser(newFollower).getUserObject();
						follower.attach(user);
					
					} 
					textUser.setText("");
				}

				
			}
		});
		
		
		// Build the tweet posting text and button
		testMsg = new JTextField();
		testMsg.setBounds(10, 177, 163, 44);
		userFrame.add(testMsg);
		
		btnTweet = new JButton("Post Tweet");
		btnTweet.setBounds(183, 177, 181, 44);
		userFrame.add(btnTweet);
		btnTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newMsg = testMsg.getText();
				if (!newMsg.equals("")) {

					user.setMessage(newMsg);
				
					
				}
				testMsg.setText("");
			}
		});
	
		
		
		
		// Build the following pane
		paneFollowing = new JScrollPane();
		paneFollowing.setBounds(10, 66, 354, 100);
		
		
		listFollowing = new JList<String>(followingModel);
		paneFollowing.setViewportView(listFollowing);
		
		
		labelFollowing = new JLabel("Current Following");
		paneFollowing.setColumnHeaderView(labelFollowing);
		userFrame.add(paneFollowing);

		
		// Build the news feed pane
		newsFeedModel = new DefaultListModel<String>();
		listNewsFeed = new JList<String>(newsFeedModel);
		paneNewsFeed = new JScrollPane(listNewsFeed);
		
		paneNewsFeed.setBounds(10, 232, 354, 142);
		paneNewsFeed.setViewportView(listNewsFeed);
		
		labelNewsFeed = new JLabel("News Feed");
		paneNewsFeed.setColumnHeaderView(labelNewsFeed);
		
		userFrame.add(paneNewsFeed);
	
	}
	

}
