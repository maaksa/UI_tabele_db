package resource;

import javax.swing.tree.MutableTreeNode;
import java.util.ArrayList;
import java.util.List;

public abstract class DBNodeComposite extends DBNode{

	private static final long serialVersionUID = -2943054456278808513L;
	private List<DBNode> children;


    public DBNodeComposite(String name, DBNode parent) {
        super(name, parent);
        this.children = new ArrayList<>();
    }

    public DBNodeComposite(String name, DBNode parent, ArrayList<DBNode> children) {
        super(name, parent);
        this.children = children;
    }

    public DBNode getChildByName(String name) {
        for (DBNode child: this.getChildren()) {
            if (name.equals(child.getName())) {
                return child;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public DBNode getParent() {
        return super.getParent();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public List<DBNode> getChildren() {
        return children;
    }

    @Override
    public void setParent(DBNode parent) {
        super.setParent(parent);
    }

}
