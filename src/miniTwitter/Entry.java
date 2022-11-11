package miniTwitter;

import visitor.EntryVisitor;

/**
 * @author Tran Nguyen
 * 
 * Entry class will implements the Visitable interface
 * 
 */
public abstract class Entry implements Visitable {
	
	private String ID;
	
	public Entry(String ID) { 
		this.ID = ID;

	}
	
	public String getID() {
		return ID;
	}
	
	public String toString() {
		return ID;
	}
	
	
	public int accept(EntryVisitor visitor) { 
		return visitor.visit(this); 
	}
}