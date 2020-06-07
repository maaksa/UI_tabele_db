package resource;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;


public abstract class DBNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 7527604596390879286L;
	protected String name;
    protected DBNode parent;

    public DBNode(String name, DBNode parent) {
        this.name = name;
        this.parent = parent;
    }

    public void add(MutableTreeNode arg0) {
    	super.add(arg0);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public DBNode getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParent(DBNode parent) {
        this.parent = parent;
    }
}
