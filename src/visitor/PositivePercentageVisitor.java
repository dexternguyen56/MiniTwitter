package visitor;


import java.util.ArrayList;
import java.util.Arrays;
import miniTwitter.Entry;
import miniTwitter.User;
import miniTwitter.UserGroup;



/**
 * @author Tran Nguyen
 * 
 * Find the total number of positive messages by visit the entries list recursively
 *
 */
public class PositivePercentageVisitor implements EntryVisitor {
	 
	private ArrayList<String> positiveWords = new ArrayList<>(Arrays.asList("amazing","great","awesome","good","nice","best"));
	private ArrayList<Entry> entries;
	private int counter;
	
	@Override
	public int visit(Entry treeLevel) {
		
		// Get the list of entries in the current tree level
		entries = ((UserGroup)treeLevel).getChildNode();
	
		counter = 0;
		
		for (Entry entry: entries) {
			
			// Recursive call if entry is a UserGroup
			if(entry instanceof UserGroup) { 
				counter += visit(entry); 
			}
			else {
				// Get the messages if entry is a User
				ArrayList<String> messages = ((User) entry).getMessages();	
				// Traverse the messages and positivewords to count
				for (String tweet: messages) {
					for (String good: positiveWords) {
						if (tweet.contains(good)) {
							counter++;
						}
					}
				}
				
			}
		}
		return counter;
	}



}
