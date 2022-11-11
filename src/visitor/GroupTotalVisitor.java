package visitor;


import java.util.ArrayList;

import miniTwitter.Entry;
import miniTwitter.UserGroup;


/**
 * @author Tran Nguyen
 * 
 * Find the total number of groups by visit the entries list recursively
 *
 */
public class GroupTotalVisitor implements EntryVisitor {

	private ArrayList<Entry> entries;
	int counter;
	
	@Override
	public int visit(Entry treeLevel) {
		// Get the list of entries in the current tree level
		entries = ((UserGroup)treeLevel).getChildNode();
	
		counter = 0;
		
		for (Entry entry: entries) {
			// Increment by one and add a recursive call
			if(entry instanceof UserGroup) { 
				counter += 1 + visit(entry); 
				}
		}
		return counter;
	}

}
