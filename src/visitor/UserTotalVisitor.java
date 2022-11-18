package visitor;
import java.util.ArrayList;

import miniTwitter.Entry;
import miniTwitter.UserGroup;


/**
 * @author Tran Nguyen
 * 
 * Find the total number of users by visit the entries list recursively
 *
 */
public class UserTotalVisitor implements EntryVisitor {
	
	private ArrayList<Entry> entries;
	private int counter;
	
	@Override
	public int visit(Entry treeLevel) {
		
		// Get the list of entries in the current tree level
		entries = ((UserGroup)treeLevel).getChildNode();
	
		counter = 0;
		
		for (Entry entry: entries) {
			
			if(entry instanceof UserGroup) { 
				counter += visit(entry); 
			}
			else {
				counter++;
			}
		}
		return counter;
	}
	

}
