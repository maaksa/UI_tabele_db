package gui.tree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class TreeEditor extends DefaultTreeCellEditor implements ActionListener {

    private Object polje = null;
    private JTextField edit = null;

    public TreeEditor(JTree arg0, DefaultTreeCellRenderer arg1) {
        super(arg0, arg1);
    }

    public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean arg4, int arg5) {
        polje=arg1;

        edit=new JTextField(arg1.toString());
        edit.addActionListener(this);
        return edit;
    }

    public boolean isCellEditable(EventObject arg0) {
        if (arg0 instanceof MouseEvent) {
            if (((MouseEvent)arg0).getClickCount()==3){
                return true;
            }
            return false;
        }
        return true;
    }

    public void actionPerformed(ActionEvent e){


    }

}