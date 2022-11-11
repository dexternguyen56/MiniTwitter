package miniTwitter;

import java.util.ArrayList;

import guiComponents.UserView;
import observer.Observer;
import observer.Subject;
import visitor.EntryVisitor;


/**
 * User class extends Subjects for Observer and Visitor patterns
 * 
 * 
 * @author Tran Nguyen
 *
 */
public class User extends Subject implements Observer  {
	


	private UserView userView;
	
	private ArrayList<String> followings;
	private ArrayList<String> followers;
	private ArrayList<String> messages;


	public User(String ID) {
		super(ID);
		
		followings = new ArrayList<String>();
		followers = new ArrayList<String>();
		messages = new ArrayList<String>();
		
		// User will follow themselves to get the message updated but won't show on the following list
		setFollowings(this.getID());
		
		attach(this);
	}
	
	public ArrayList<String> getFollowings() {
		return followings;
	}
	
	public void setFollowings(String ID) {
		followings.add(ID);
	}
	

	public UserView getUserView() {
		return userView;
	}
	
	public void setUserView(UserView view) {
		userView = view;
	}


	public ArrayList<String> getMessages() {
		return messages;
	}
	
	public void addMessage(String message) {
		messages.add(message);
	}
	

	@Override
	public int accept(EntryVisitor visitor) {
		return visitor.visit(this);
	}
	

	/**
	 * Update this the news feed for this user
	 */
	public void update(Subject subject, String message) {
		
		String ID = subject.getID();
		
		if(!followers.contains(ID)) {
			followers.add(ID);
		}
		
		userView.addNewsFeed(subject.toString(), message);
	}

	
 }
