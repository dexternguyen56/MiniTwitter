package guiComponents;
import visitor.GroupTotalVisitor;
import visitor.MessagesTotalVisitor;
import visitor.PositivePercentageVisitor;
import visitor.UserTotalVisitor;

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
	
	private static AdminControlPanel instance; 
	private DefaultMutableTreeNode currentNode;
	private TreeData treeData;
	
	private JTree tree;
	private JScrollPane scrollPane;
	private DefaultTreeModel treeModel;
	
	JFrame frame;
	
	JTextField textUser;
	JTextField textGroup;
	
	JButton btnPosPercent;
	JButton btnMsgTotal;
	JButton btnGroupTotal;
	JButton btnUserTotal ;
	JButton btnOpenUserView;
	
	int statistic;
	String message;

	public static AdminControlPanel getInstance() {
		if (instance == null) {
			instance = new AdminControlPanel();
		}
		return instance;
	}
	

	private AdminControlPanel() {
		treeData = new TreeData();
		createPanel();
	    frame.setVisible(true); 
		
	}
	

	private void createPanel() {
		
		// Initiate the admid panel
		frame = new JFrame("Admin Control");
		frame.setBounds(100, 100, 631, 498);
	    frame.getContentPane().setLayout(null);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	   

	    tree = treeData.getTree();
	   
	    tree.setCellRenderer(new CustomDefaultTreeCellRenderer());
	    // Get the latest selecth path or return null
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
					DefaultMutableTreeNode newNode;

					if (currentNode != null && currentNode.getUserObject() instanceof UserGroup) {
						newNode = treeData.addUser(textUser.getText(), currentNode);
					} else {
						
						currentNode = (DefaultMutableTreeNode)currentNode.getParent();
						newNode = treeData.addUser(textUser.getText(),currentNode);
					} 
					
					if (newNode != null) {
						treeModel.reload();
					}
						
		
					textUser.setText("");
					currentNode = (DefaultMutableTreeNode)treeModel.getRoot();
		
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
					DefaultMutableTreeNode newNode;
					if (currentNode != null && currentNode.getUserObject() instanceof UserGroup) {
						newNode = treeData.addGroup(textGroup.getText(), currentNode);
					} else {
						currentNode = (DefaultMutableTreeNode)currentNode.getParent();
						newNode = treeData.addGroup(textGroup.getText(),currentNode);
					} 
					if (newNode != null) {
						treeModel.reload();
					}
					textGroup.setText("");
					currentNode = (DefaultMutableTreeNode)treeModel.getRoot();
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
	
				statistic = ((Entry)treeData.getRoot().getUserObject()).accept(new UserTotalVisitor());
				

				message = String.format("Mini Twitter has %d users.", statistic);
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("User Total");
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
	    btnPosPercent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic = ((Entry)treeData.getRoot().getUserObject()).accept(new PositivePercentageVisitor());
				

			    int totalMsg = ((Entry)treeData.getRoot().getUserObject()).accept(new MessagesTotalVisitor());
				
			    
			    double result = (totalMsg == 0) ? 0.0 : (((double) statistic / totalMsg) * 100);
			   
			  
				message = String.format("%.1f percent are positive messages",result);
	
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("Positive Percentage");
				statPanel.setVisible(true);
			}
		});

	    
	    frame.getContentPane().add(btnPosPercent);

	}
	



}
