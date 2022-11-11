package guiComponents;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.Window.Type;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Tran Nguyen
 * 
 * Statistics panel to show the results from visitors
 *
 */
public class StatisticPanel extends JDialog {
	
	private JButton btnClose;
	
	private JTextArea mainText;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StatisticPanel dialog = new StatisticPanel();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public StatisticPanel(String text) {
		this();
		mainText.setText(text);
	}
	
	public StatisticPanel() {
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		setModal(true);
		setResizable(false);
		
		setType(Type.POPUP);
		setBounds(200, 200, 400, 200);
	
		
		btnClose = new JButton("Close");
		btnClose.setBounds(150, 100, 100, 30);
		getContentPane().add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		mainText = new JTextArea();
	
		mainText.setBackground(UIManager.getColor("Button.background"));
    	mainText.setEditable(false);
		mainText.setBounds(100, 30, 200, 60);
		getContentPane().add(mainText);
	}
	



}