package visitor;
import java.util.ArrayList;

import miniTwitter.Entry;
import miniTwitter.User;
import miniTwitter.UserGroup;


/**
 * @author Tran Nguyen
 * 
 * Find the total number of users by visit the entries list recursively
 *
 */
public class LastUpdatedUserVisitor implements EntryVisitor {
	
	private ArrayList<Entry> entries;
	private long lastedTime = 0;
	private int result;
	private User user;
	


	@Override
	public int visit(Entry treeLevel) {
		
		// Get the list of entries in the current tree level
		entries = ((UserGroup)treeLevel).getChildNode();
		
		// We only need this to traverse the tree to get the ID updated
		result = 0;
		
		for (Entry entry: entries) {
			
			if(entry instanceof UserGroup) { 
				result = visit(entry); 
			}
			else {
				if (lastedTime < ((User) entry).getLastUpdated()) {
					
					lastedTime = ((User) entry).getLastUpdated();
					
					user = ((User) entry);
					
					//We found a lastestUpdated time that > 0 
					result = 1;
				}
				
			}
		}
		return result;
	}
	
	public User getLastUpdatedUser() {
		return user;
	}
	

}
