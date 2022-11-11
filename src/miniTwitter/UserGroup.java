package miniTwitter;
import java.util.ArrayList;


import visitor.EntryVisitor;


/**
 * @author Tran Nguyen
 * 
 * User group extends entry for visitor pattern
 *
 */
public class UserGroup extends Entry{
	
	private ArrayList<Entry> childNode;

	public UserGroup(String id) {
		super(id);
		childNode = new ArrayList<Entry>();
		
	}
	

	public void addChildNode(Entry u) {
		childNode.add(u);
	}
	
	public ArrayList<Entry> getChildNode() {
		return childNode;
	}
	

	@Override
	public int accept(EntryVisitor visitor) {
		return visitor.visit(this);
	}

}
