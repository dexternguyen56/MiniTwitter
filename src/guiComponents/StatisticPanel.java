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
		setType(Type.POPUP);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 300, 150);
		getContentPane().setLayout(null);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(111, 72, 73, 23);
		getContentPane().add(btnClose);
		
		mainText = new JTextArea();
	
		mainText.setBackground(UIManager.getColor("Button.background"));
		mainText.setLineWrap(true);
		mainText.setWrapStyleWord(true);
		mainText.setEditable(false);
		mainText.setBounds(33, 11, 216, 50);
		getContentPane().add(mainText);
	}
	



}