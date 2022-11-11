package miniTwitter;

import java.awt.EventQueue;

import guiComponents.AdminControlPanel;



/**
 * @author Tran Nguyen
 *
 * Driver for mini twitter
 */
public class MiniTwitterDriver {
	public static void main(String args[]) {
		AdminControlPanel.getInstance();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminControlPanel.getInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
