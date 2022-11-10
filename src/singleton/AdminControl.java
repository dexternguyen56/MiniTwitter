package singleton;

public class AdminControl {
	private static AdminControl admin = null;
	
	private AdminControl() {
		
	}
	
	private static AdminControl getInstance () {
		if (admin == null) {
			admin = new AdminControl();
		}
		return admin;
	}
	
	
}
