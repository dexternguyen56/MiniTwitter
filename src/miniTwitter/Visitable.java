package miniTwitter;

import visitor.EntryVisitor;


/**
 * @author Tran Nguyen
 * 
 * Visitable interface for the Entry class
 *
 */
public interface Visitable {
	int accept(EntryVisitor visitor);
}
