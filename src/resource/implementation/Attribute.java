package resource.implementation;

import resource.DBNode;
import resource.DBNodeComposite;
import resource.enums.AttributeType;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

// public class Attribute extends DBNodeComposite
public class Attribute extends DefaultMutableTreeNode {


    private AttributeType attributeType;
    private int length;
    private Attribute inRelationWith;
    private String name;
    private Entity parent;


    public Attribute(String name, Entity parent, AttributeType attributeType, int length) {
        super();
        this.attributeType = attributeType;
        this.length = length;
        this.name = name;
        this.parent = parent;
    }


    @Override
    public String toString() {
        return name;
    }

    public void add(MutableTreeNode child) {
        if (child != null && child instanceof AttributeConstraint) {
            super.add(child);
        }
    }

    public void setInRelationWith(Attribute inRelationWith) {
        this.inRelationWith = inRelationWith;
    }

    public Attribute getInRelationWith() {
        return inRelationWith;
    }

    public String getName() {
        return name;
    }


	public AttributeType getAttributeType() {
		return attributeType;
	}


}
