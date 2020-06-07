package model;

import resource.implementation.InformationResource;
import resource.implementation.TreeNode;

import javax.swing.tree.DefaultTreeModel;

public class TreeModel extends DefaultTreeModel {

    public TreeModel(){
        super(new InformationResource("VANJAiMAXA"));
    }
}
