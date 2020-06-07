package resource.implementation;

import resource.DBNode;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNode extends DefaultMutableTreeNode {

    private DefaultMutableTreeNode dbNode;

    public TreeNode(DefaultMutableTreeNode dbNode){
        this.dbNode = dbNode;
    }

    public DefaultMutableTreeNode getDbNode() {
        return dbNode;
    }

    public void setDbNode(DefaultMutableTreeNode dbNode) {
        this.dbNode = dbNode;
    }

}
