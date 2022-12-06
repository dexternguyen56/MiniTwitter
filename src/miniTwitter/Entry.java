package miniTwitter;

import java.text.DateFormat;

import visitor.EntryVisitor;
import visitor.Visitable;

/**
 * @author Tran Nguyen
 * 
 * Entry class will implements the Visitable interface
 * 
 */
public abstract class Entry implements Visitable {
	
	private String ID;
	private long creationTime;
	
	public Entry(String ID) { 
		this.ID = ID;
		creationTime = System.currentTimeMillis();

	}
	
	public String getID() {
		return ID;
	}
	
	public String toString() {
		return ID;
	}
	
	public Long getCreationTime() {
		
		
		return creationTime;
	}
	
	
	public int accept(EntryVisitor visitor) { 
		return visitor.visit(this); 
	}
}