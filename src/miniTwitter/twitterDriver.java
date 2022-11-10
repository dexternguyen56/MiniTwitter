package miniTwitter;

import java.awt.EventQueue;

import guiComponents.AdminControlGUI;

public class twitterDriver {

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

}
