package observer;
import java.util.ArrayList;
import java.util.List;

import miniTwitter.Entry;

/**
 * 
 * 
 * @author Tran Nguyen
 * 
 * Subject abstract class
 * 
 */

public abstract class Subject extends Entry {
	
	public Subject(String iD) {
		super(iD);
	
	}

	// Followers of the current user
	private List<Observer> observers = new ArrayList<Observer>();
	
	/**
	 * add a new follower
	 * 
	 */
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	
	/**
	 * Remove the current follower
	 * Not yet implemented
	 * 
	 */
	public void detach(Observer observer) {

		observers.remove(observer);
	}
	
	/**
	 * notify other observers
	 * 
	 */
	public void notifyObservers(String message, long updatedTime) {
		
		for (Observer obs: observers) {
			obs.update(this, message, updatedTime);
		}
	}
}
