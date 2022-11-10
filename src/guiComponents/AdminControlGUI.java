package guiComponents;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminControlGUI extends JFrame {

	
	private JTextField textAddUser;
	private JTextField textAddGroup;
	private JTree tree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminControlGUI frame = new AdminControlGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminControlGUI() {
		setTitle("Admin Control");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 631, 498);
		getContentPane().setLayout(null);
		
		tree = new JTree();
		tree.setBounds(10, 11, 260, 439);
		getContentPane().add(tree);
		
		JButton btnNewButton = new JButton("Add User");
		btnNewButton.setBounds(448, 11, 159, 48);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add Group");
		btnNewButton_1.setBounds(448, 70, 159, 54);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Show Positive Percentage");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(448, 402, 159, 48);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Show Messages Total");
		btnNewButton_3.setBounds(280, 402, 159, 48);
		getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Show User Total");
		btnNewButton_4.setBounds(279, 343, 160, 48);
		getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Show Group Total");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_5.setBounds(448, 343, 159, 48);
		getContentPane().add(btnNewButton_5);
		
		textAddUser = new JTextField();
		textAddUser.setBounds(280, 11, 158, 48);
		getContentPane().add(textAddUser);
		textAddUser.setColumns(10);
		
		textAddGroup = new JTextField();
		textAddGroup.setBounds(280, 70, 158, 54);
		getContentPane().add(textAddGroup);
		textAddGroup.setColumns(10);
		
		
	}
}
