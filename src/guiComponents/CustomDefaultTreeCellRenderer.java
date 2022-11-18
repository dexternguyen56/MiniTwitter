package guiComponents;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import miniTwitter.UserGroup;

/**
 * Custom TreeCellRenderer to display directory Icon
 *
 */
class CustomDefaultTreeCellRenderer extends DefaultTreeCellRenderer {
	
	private static final long serialVersionUID = 2L;
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			if (((DefaultMutableTreeNode)value).getUserObject() instanceof UserGroup) {
				setIcon(UIManager.getIcon("FileView.directoryIcon"));
			}
		return this;
	}
}