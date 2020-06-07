package controller.tree;

import app.Main;
import gui.MainFrame;
import resource.implementation.Entity;

import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TreeMouseController implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getClickCount() == 2){
            TreePath path = MainFrame.getInstance().getTree().getSelectionPath();
            if(path != null){
                if(path.getLastPathComponent() instanceof Entity){
                		Entity ee = (Entity) path.getLastPathComponent();
                        MainFrame.getInstance().getInformationResource().generateOpened(ee);
                        MainFrame.getInstance().getActionManager().getAddAction().setEnabled(true);
                        MainFrame.getInstance().getActionManager().getDeleteAction().setEnabled(true);
                        MainFrame.getInstance().getActionManager().getUpdateAction().setEnabled(true);
                        MainFrame.getInstance().getActionManager().getRefreshAction().setEnabled(true);
                        MainFrame.getInstance().getActionManager().getFilterAndSortAction().setEnabled(true);
                        MainFrame.getInstance().getActionManager().getSearchAction().setEnabled(true);
                        MainFrame.getInstance().getActionManager().getCountAction().setEnabled(true);
                        MainFrame.getInstance().getActionManager().getAverageAction().setEnabled(true);
                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
