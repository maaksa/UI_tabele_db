package resource.implementation;

import lombok.Data;
import lombok.ToString;
import resource.DBNode;
import resource.enums.ConstraintType;

import javax.swing.tree.DefaultMutableTreeNode;


public class AttributeConstraint extends DefaultMutableTreeNode {

    private ConstraintType constraintType;
    private String name;
    private Attribute parent;

    public AttributeConstraint(String name, Attribute parent, ConstraintType constraintType) {
        super();
        this.name = name;
        this.parent = parent;
        this.constraintType = constraintType;
    }

    @Override
    public String toString() {
        return name;
    }
}
