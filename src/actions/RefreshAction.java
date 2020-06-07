package actions;

import javax.swing.*;

import gui.MainFrame;
import resource.data.Row;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RefreshAction extends AbstractAction {

    public RefreshAction(){
        putValue(NAME, "Refresh");
        putValue(SHORT_DESCRIPTION, "Refresh");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	String s = MainFrame.getInstance().getInfoResourceView().getSelectedEntity().getEntity().getName();
    	ArrayList<Row> rows = MainFrame.getInstance().getAppCore().getAllRows(s);
    	MainFrame.getInstance().getInfoResourceView().getSelectedEntity().setTableView(rows);
    }
}
