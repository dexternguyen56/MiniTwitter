package guiComponents;
import visitor.GroupTotalVisitor;
import visitor.IDValidationVisitor;
import visitor.LastUpdatedUserVisitor;
import visitor.MessagesTotalVisitor;
import visitor.PositivePercentageVisitor;
import visitor.UserTotalVisitor;
import visitor.Visitable;

import javax.swing.*;
import javax.swing.tree.*;

import miniTwitter.Entry;
import miniTwitter.TreeData;
import miniTwitter.User;
import miniTwitter.UserGroup;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Tran Nguyen
 * 
 * Admin Control Panel uses singleton pattern to show all components.
 *
 */
public class AdminControlPanel {
	
	//Singleton pattern: instance of AdminControlPanel 
	private static AdminControlPanel instance; 
	
	//CurrentNode, and newNode to be added
	private DefaultMutableTreeNode currentNode;
	private DefaultMutableTreeNode newNode;
	
	//Tree data 
	private TreeData treeData;
	
	// UI Components
	private JTree tree;
	private JScrollPane scrollPane;
	private DefaultTreeModel treeModel;
	
	// Main Jframe
	private JFrame frame;
	
	// TexField
	private JTextField textUser;
	private JTextField textGroup;
	
	// Buttons
	private JButton btnPosPercent;
	private JButton btnMsgTotal;
	private JButton btnGroupTotal;
	private JButton btnUserTotal ;
	private JButton btnOpenUserView;
	
	IDValidationVisitor validVisitor;
	LastUpdatedUserVisitor idVisitor;
	
	// Staistic and message to display for visitor pattern
	
	int statistic;
	private String message;

	public static AdminControlPanel getInstance() {
		if (instance == null) {
			instance = new AdminControlPanel();
		}
		return instance;
	}
	

	private AdminControlPanel() {
		treeData = TreeData.getInstance();
		createPanel();
	    frame.setVisible(true); 
		
	}
	
	
	// create panel for admin control
	private void createPanel() {
		// Initiate the admid panel
		frame = new JFrame("Admin Control");
		frame.setBounds(100, 100, 631, 498);
	    frame.getContentPane().setLayout(null);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	   

	    tree = treeData.getTree();
	    tree.setCellRenderer(new CustomDefaultTreeCellRenderer());
	    
	    // Get the latest selected path or return null for the currentNode is being selected
	    tree.addTreeSelectionListener(new TreeSelectionListener() {
	        public void valueChanged(TreeSelectionEvent e) {
	        	DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
	        	if (node != null) {
	        		currentNode = node;
	        	}
	        }
	    });
	    
	    // Build the tree view
	    scrollPane  = new JScrollPane();
		scrollPane.setBounds(10, 11, 260, 439);
		scrollPane.setViewportView(tree);
		frame.getContentPane().add(scrollPane);
		
	    
	    treeModel = (DefaultTreeModel)tree.getModel();
	    
	    // Build add user text and button
	    textUser = new JTextField();
	    textUser.setBounds(280, 11, 158, 48);
	    frame.getContentPane().add(textUser);
	    textUser.setColumns(10);
		
	    JButton addUser = new JButton("Add User");
	    addUser.setBounds(448, 11, 159, 48);
	    frame.getContentPane().add(addUser);
	    
	    addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if the text is not empty
				if (!textUser.getText().equals("")) {
					
					// Set currentNode = root if it's null or to the parent of the current user
					if (currentNode == null) {
						currentNode = (DefaultMutableTreeNode)treeModel.getRoot();
					}
					else if (currentNode.getUserObject() instanceof User) {
						currentNode = (DefaultMutableTreeNode)currentNode.getParent();
					}
					
					newNode = treeData.addUser(textUser.getText(),currentNode);
					
					renderTree(textUser);
					
	
		
				}
				
				
			}
	    });
	    
	    // Build add group text and button
	    textGroup = new JTextField();
	    textGroup.setBounds(280, 70, 158, 54);
	    frame.getContentPane().add(textGroup);
	    textGroup.setColumns(10);
	    
	    JButton addGroup = new JButton("Add Group");
	    addGroup.setBounds(448, 70, 159, 54);
	    frame.getContentPane().add(addGroup);
	    addGroup.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//if the text is not empty
				if (!textGroup.getText().equals("")) {
					
					// Set currentNode = root if it's null or to the parent of the current group
					if (currentNode == null) {
						currentNode = (DefaultMutableTreeNode)treeModel.getRoot();
					}
					else if (currentNode.getUserObject() instanceof User) {
						currentNode = (DefaultMutableTreeNode)currentNode.getParent();
					}
					
					newNode = treeData.addGroup(textGroup.getText(),currentNode);
					
					renderTree(textGroup);
				}
				
		    }
	    });
	   
	    // Build open user view button
	    btnOpenUserView = new JButton("Open User View");
	    btnOpenUserView.setBounds(280, 135, 327, 48);
	    frame.getContentPane().add(btnOpenUserView);
	    btnOpenUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentNode != null && currentNode.getUserObject() instanceof User) {
					new UserView(treeData, currentNode);
	    		} 
			}
		});
	    
	    
	    // Build show user total
	    btnUserTotal = new JButton("Show User Total");
	    btnUserTotal.setBounds(279, 343, 160, 48);
	    frame.getContentPane().add(btnUserTotal );
	    btnUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				statistic = ((Entry) treeData.getRoot().getUserObject()).accept(new UserTotalVisitor());
				
				message = String.format("Mini Twitter has %d users.", statistic);
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("User Total");
				statPanel.setVisible(true);
			}
		});
	    
	    // Build show invalid ID
	    btnUserTotal = new JButton("ID Verifitication");
	    btnUserTotal.setBounds(279, 285, 160, 48);
	    frame.getContentPane().add(btnUserTotal );
	    btnUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
	
				statistic = ((Entry) treeData.getRoot().getUserObject()).accept(validVisitor = new IDValidationVisitor());
				
				if(statistic == 0) {
					message = "There is no invalid ID!";
				}
				else{
					//Output the total of invalid ID and the invalid ID list
					message = String.format("Total of invalid ID: %d\n[", statistic);
					
					for(String id: validVisitor.getInvalidID()) {
						message += id + ", ";
					}
					
					message = message.substring(0, message.length() - 2) + "]"; 
				}
			
				
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("Total of Invalid ID");
				statPanel.setVisible(true);
			}
		});


	    
	    
	    
	    
	    
	    // Build show group total
	    btnGroupTotal = new JButton("Show Group Total");
	    btnGroupTotal.setBounds(448, 343, 159, 48);
	    frame.getContentPane().add(btnGroupTotal);  
	    btnGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic = ((Entry)treeData.getRoot().getUserObject()).accept(new GroupTotalVisitor());
				
				message = String.format("Mini Twitter has %d groups.", statistic);
	
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("Group Total");
				statPanel.setVisible(true);
			}
		});
	    
	    // Last updated user
	    btnGroupTotal = new JButton("Last Updated User");
	    btnGroupTotal.setBounds(448, 285, 159, 48);
	    frame.getContentPane().add(btnGroupTotal);  
	    btnGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				
				statistic = ((Entry)treeData.getRoot().getUserObject()).accept(idVisitor = new LastUpdatedUserVisitor());
				
				if(statistic == 0) {
					message = "There is no user!";
				}
				else {
					User user = idVisitor.getLastUpdatedUser();
					
					message = String.format("User: @%s\n%s", user.getID(),user.getConvertedTime());
				}
						
				
	
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("Last Updated User");
				statPanel.setVisible(true);
			}
		});


	    
	    

	    // build show messages total
	    btnMsgTotal= new JButton("Show Messages Total");
	    btnMsgTotal.setBounds(280, 402, 159, 48);
	    frame.getContentPane().add(btnMsgTotal);
	    btnMsgTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic = ((Entry)treeData.getRoot().getUserObject()).accept(new MessagesTotalVisitor());
				

				message= String.format("Mini Twitter has %d messages.", statistic);
	
				StatisticPanel statPanel = new StatisticPanel(message);
				
				statPanel.setTitle("Messages Total");
				statPanel.setVisible(true);
			}
		});

    
	    // Build show positive percentage 
	    btnPosPercent= new JButton("Show Positive Percentage");
	    btnPosPercent.setBounds(448, 402, 159, 48);
	    frame.getContentPane().add(btnPosPercent);
	    btnPosPercent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Count Positive words
				statistic = ((Entry)treeData.getRoot().getUserObject()).accept(new PositivePercentageVisitor());
				
				//Get total messages
			    int totalMsg = ((Entry)treeData.getRoot().getUserObject()).accept(new MessagesTotalVisitor());
				
			    //Calculate the percentage of positive msg
			    double result = (totalMsg == 0) ? 0.0 : (((double) statistic / totalMsg) * 100);
			   
			  
				message = String.format("%.1f percent are positive messages",result);
	
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("Positive Percentage");
				statPanel.setVisible(true);
			}
		});

	}
	
	
	/**
	 * Reset the current text field
	 * Reload the treeModel
	 * Expand all rows for the JTree since reload will collapse the new added fields 
	 */
	private void renderTree(JTextField field) {
		field.setText("");
		treeModel.reload();
		currentNode = (DefaultMutableTreeNode)treeModel.getRoot();
		for (int i = 0; i < tree.getRowCount(); i++) {
	    	   tree.expandRow(i);
	    	}
	}
	



}
