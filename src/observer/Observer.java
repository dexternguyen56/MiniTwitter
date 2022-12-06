package observer;

/**
 * @author Tran Nguyen
 * 
 * Observer pattern interface
 */
public interface Observer {
	public void update(Subject user, String tweet, long time);
}
