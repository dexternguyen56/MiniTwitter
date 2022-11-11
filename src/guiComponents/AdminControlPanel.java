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
 * Admin Control Panel uses singleton pattern to show all components
 *
 */
public class AdminControlPanel {
	
	private static AdminControlPanel instance; 
	private DefaultMutableTreeNode currentNodeSelection;
	private TreeData userTree;
	
	private JTree tree;
	
	private JScrollPane scrollPane;
	
	private DefaultTreeModel model;
	
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
		userTree = new TreeData();
		createPanel();
	    frame.setVisible(true); 
		
	}
	

	private void createPanel() {
	
		frame = new JFrame("Admin Control");
		frame.setBounds(100, 100, 631, 498);
	    frame.getContentPane().setLayout(null);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	   

	    tree = userTree.getTree();
	   
	    tree.setCellRenderer(new CustomDefaultTreeCellRenderer());
	    
	    tree.addTreeSelectionListener(new TreeSelectionListener() {
	        public void valueChanged(TreeSelectionEvent e) {
	        	DefaultMutableTreeNode current = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
	        	if (current != null) {
	        		currentNodeSelection = current;
	        	}
	        }
	    });
	    
	    
	    scrollPane  = new JScrollPane();
		scrollPane.setBounds(10, 11, 260, 439);
		scrollPane.setViewportView(tree);
		frame.getContentPane().add(scrollPane);
		
	    
	    model = (DefaultTreeModel)tree.getModel();
	    

	    textUser = new JTextField();
	    textUser.setBounds(280, 11, 158, 48);
	    frame.getContentPane().add(textUser);
	    textUser.setColumns(10);
		
	    JButton addUser = new JButton("Add User");
	    addUser.setBounds(448, 11, 159, 48);
	    

	    frame.getContentPane().add(addUser);
	    
	    addUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!textUser.getText().equals("")) {
					DefaultMutableTreeNode newNode;

					if (currentNodeSelection != null && 
							currentNodeSelection.getUserObject() instanceof UserGroup) {
						newNode = userTree.addUser(textUser.getText(), currentNodeSelection);
					} else {
						
						currentNodeSelection = (DefaultMutableTreeNode)currentNodeSelection.getParent();
						newNode = userTree.addUser(textUser.getText(),currentNodeSelection);
					} 
					
					if (newNode != null) {
						model.reload();
					}
						
		
					textUser.setText("");
					currentNodeSelection = (DefaultMutableTreeNode)model.getRoot();
		
				}
				
				
			}
	    });
	    
	    
	    textGroup = new JTextField();
	    textGroup.setBounds(280, 70, 158, 54);
	    frame.getContentPane().add(textGroup);
	    textGroup.setColumns(10);
	    
	    JButton addGroup = new JButton("Add Group");
	    addGroup.setBounds(448, 70, 159, 54);
	    frame.getContentPane().add(addGroup);
	    addGroup.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
				if (!textGroup.getText().equals("")) {
					DefaultMutableTreeNode newNode;
					if (currentNodeSelection != null && 
							currentNodeSelection.getUserObject() instanceof UserGroup) {
						newNode = userTree.addGroup(textGroup.getText(), currentNodeSelection);
					} else {
						currentNodeSelection = (DefaultMutableTreeNode)currentNodeSelection.getParent();
						newNode = userTree.addGroup(textGroup.getText(),currentNodeSelection);
					} 
					if (newNode != null) {
						model.reload();
					}
					textGroup.setText("");
					currentNodeSelection = (DefaultMutableTreeNode)model.getRoot();
				}
				
		    }
	    });
	   
	    
	    btnOpenUserView = new JButton("Open User View");
	    btnOpenUserView.setBounds(280, 135, 327, 48);
	    btnOpenUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentNodeSelection != null && currentNodeSelection.getUserObject() instanceof User) {
					new UserView(userTree, currentNodeSelection);
	    		} 
			}
		});
	    frame.getContentPane().add(btnOpenUserView);
	    

	    btnUserTotal = new JButton("Show User Total");
	    btnUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				statistic = ((Entry)userTree.getRoot().getUserObject()).accept(new UserTotalVisitor());
				

				message = String.format("Mini Twitter has %d users.", statistic);
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("User Total");
				statPanel.setVisible(true);
			}
		});
	    btnUserTotal.setBounds(279, 343, 160, 48);
	    frame.getContentPane().add(btnUserTotal );
	    
	    
	    
	    
	    

	    btnGroupTotal = new JButton("Show Group Total");
	    btnGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic = ((Entry)userTree.getRoot().getUserObject()).accept(new GroupTotalVisitor());
				
				message = String.format("Mini Twitter has %d groups.", statistic);
	
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("Group Total");
				statPanel.setVisible(true);
			}
		});

	    btnGroupTotal.setBounds(448, 343, 159, 48);
	    frame.getContentPane().add(btnGroupTotal);
	    
	    
	    

	    
	    btnMsgTotal= new JButton("Show Messages Total");
	    btnMsgTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic = ((Entry)userTree.getRoot().getUserObject()).accept(new MessagesTotalVisitor());
				

				message= String.format("Mini Twitter has %d messages.", statistic);
	
				StatisticPanel statPanel = new StatisticPanel(message);
				
				statPanel.setTitle("Messages Total");
				statPanel.setVisible(true);
			}
		});

	    btnMsgTotal.setBounds(280, 402, 159, 48);
	    frame.getContentPane().add(btnMsgTotal);
	    
    

	    btnPosPercent= new JButton("Show Positive Percentage");
	    btnPosPercent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistic = ((Entry)userTree.getRoot().getUserObject()).accept(new PositivePercentageVisitor());
				

			    int totalMsg = ((Entry)userTree.getRoot().getUserObject()).accept(new MessagesTotalVisitor());
				
			    
			    double result = (totalMsg == 0) ? 0.0 : (((double) statistic / totalMsg) * 100);
			   
			  
				message = String.format("%.1f percent are positive messages",result);
	
				StatisticPanel statPanel = new StatisticPanel(message);
				statPanel.setTitle("Positive Percentage");
				statPanel.setVisible(true);
			}
		});

	    btnPosPercent.setBounds(448, 402, 159, 48);
	    frame.getContentPane().add(btnPosPercent);

	}
	



}
