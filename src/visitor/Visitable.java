package visitor;

/**
 * @author Tran Nguyen
 * 
 * Visitable interface for the Entry class
 *
 */
public interface Visitable {
	abstract public int accept(EntryVisitor visitor);
}
