package visitor;

import java.util.ArrayList;
import java.util.HashSet;

import miniTwitter.Entry;
import miniTwitter.User;
import miniTwitter.UserGroup;

/**
 * @author Tran Nguyen
 * 
 * Find the total number of invalid ID by visit the entries list recursively
 *
 */
public class IDValidationVisitor implements EntryVisitor {


	private ArrayList<Entry> entries;
	private HashSet<String> idSet ;
	private ArrayList<String> invalidID ;
	private int counter;
	
	public IDValidationVisitor() {
		idSet = new HashSet<>();
		invalidID = new ArrayList<>();
	}
	
	/**
	 *
	 */
	@Override
	public int visit(Entry treeLevel) {
		
		// Get the list of entries in the current tree level
		entries = ((UserGroup)treeLevel).getChildNode();

		counter = 0;
		
		for (Entry entry: entries) {
			
			// Since we already validated duplication ID for user and group
			// We will add ID to the HashSet and increment the counter if we found duplicated
			// user between group ID, or if there is any space
			
			if(idSet.contains(entry.getID()) || entry.getID().contains(" ")){
				counter++;
				invalidID.add(entry.getID());
			}
			else {
				idSet.add(entry.getID());
			}
			// Recursive to traverse that group
			if(entry instanceof UserGroup) {
				counter += visit(entry);   
			}
			
		}
		return counter;
	}
	
	public ArrayList<String> getInvalidID(){
		return invalidID;
	}
	

}