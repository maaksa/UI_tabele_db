package model;

import controller.tree.TreeMouseController;
import gui.MainFrame;
import gui.tree.TreeCellRender;
import gui.tree.TreeEditor;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tree extends JTree{

    public Tree() {
        setCellEditor(new TreeEditor(this, new DefaultTreeCellRenderer()));
        setCellRenderer(new TreeCellRender());
        addMouseListener(new TreeMouseController());
        setEditable(true);
    }

}