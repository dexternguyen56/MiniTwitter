package visitor;

import miniTwitter.Entry;


/**
 * @author Tran Nguyen
 * 
 * Interface for the visitor pattern
 *
 */
public interface EntryVisitor {

	public int visit(Entry entry);
	
}
