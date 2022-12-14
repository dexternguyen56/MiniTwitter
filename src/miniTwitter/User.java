package miniTwitter;

import java.text.DateFormat;
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
	private long lastUpdatedTime;
	private int currentMsg;


	public User(String ID) {
		super(ID);
		
		lastUpdatedTime = getCreationTime();
		currentMsg = 0;
		
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
	
	public long getLastUpdated() {
		return lastUpdatedTime;
	}
	
	public void setUserView(UserView view) {
		userView = view;
	}


	public ArrayList<String> getMessages() {
		return messages;
	}
	
	public void setMessage(String message) {
		
		currentMsg++;
		notifyObservers(message, System.currentTimeMillis());
	}
	

	@Override
	public int accept(EntryVisitor visitor) {
		return visitor.visit(this);
	}
	

	/**
	 * Update this the news feed for this user
	 */
	public void update(Subject subject, String message, long updatedTime) {
		
		String ID = subject.getID();
		
		if(!followers.contains(ID)) {
			followers.add(ID);
		}
		
		lastUpdatedTime = updatedTime;
		
		messages.add(message);
		

		
		//Update UI
		userView.updateDates();
		
		userView.addNewsFeed(message);
	}
	
	//
	public int getCurrentMsg() {
		return currentMsg;
	}
	
	
	public String getConvertedTime() {
		return DateFormat.getDateTimeInstance().format(lastUpdatedTime);
	}

	
 }
