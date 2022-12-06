package visitor;
import java.util.ArrayList;

import miniTwitter.Entry;
import miniTwitter.User;
import miniTwitter.UserGroup;

/**
 * @author Tran Nguyen
 * 
 * Find the total number of positive messages by visit the entries list recursively
 *
 */
public class MessagesTotalVisitor implements EntryVisitor {


	private ArrayList<Entry> entries;
	private int counter;
	
	@Override
	public int visit(Entry treeLevel) {
		
		// Get the list of entries in the current tree level
		entries = ((UserGroup)treeLevel).getChildNode();
	
		counter = 0;
		
		for (Entry entry: entries) {
			// Recursive call to get messages from that group
			if(entry instanceof UserGroup) { 
				counter += visit(entry);   
			}
			else {
				counter += ((User)entry).getCurrentMsg(); // Get the size of the messages list holds by the User
			}
		}
		return counter;
	}
	

}
