package guiComponents;


import javax.swing.*;  
import javax.swing.tree.*;

import miniTwitter.TreeData;
import miniTwitter.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;



/**
 * @author Tran Nguyen
 * 
 * User View panel class to display current User
 *
 */
public class UserView {
	
	// UserView holds reference to TreeData for searching, and get User info from the current node
	private User user;
	private TreeData tree;
	private DefaultMutableTreeNode userNode;
	
	//Models
	private DefaultListModel <String> newsFeedModel;
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
	
	private JTextField created;
	private JTextField updated;
	
	
	

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
		userFrame.setBounds(100, 100, 388, 455);
		userFrame.setLayout(null);
		

		followingModel = new DefaultListModel<String>();
	
		skip = true;
		for(String following: user.getFollowings()) {
			if(skip) {
				skip = false;
				continue;
			}
			followingModel.addElement(following);
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
				String newFollowing = textUser.getText();
				//  Check if the new follower name is valid and exist
				if (!newFollowing.equals("")) {
					if (tree.searchUser(newFollowing) != null) { 
						
						//Add to following model and set following for user
						followingModel.addElement(newFollowing);
						user.setFollowings(newFollowing);
						
						// Find the follower and use observer
						User follower = (User)tree.searchUser(newFollowing).getUserObject();
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
		
		
		
		created = new JTextField();
		updated = new JTextField();
		
		created.setEditable(false);
		updated.setEditable(false);
		
		created.setBounds(10, 375, 354, 20);
		updated.setBounds(10, 395, 354, 20);
		
		created.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		updated.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		updateDates();
		
		
		userFrame.add(created);
		userFrame.add(updated);
		
		//Updated time
	
	}
	
	private String timeConvert(long time) {
		return DateFormat.getDateTimeInstance().format(time);
	}
	

	
	public void updateDates() {
		created.setText("Creation Time: " + timeConvert(user.getCreationTime()));
		updated.setText("Last Updated: " + timeConvert(user.getLastUpdated()));
	}
	

}
