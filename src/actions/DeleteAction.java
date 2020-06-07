package actions;

import gui.MainFrame;
import resource.data.Row;
import view.EntityView;
import view.InfoResourceView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteAction extends AbstractAction {

    public DeleteAction(){
        putValue(NAME, "Delete");
        putValue(SHORT_DESCRIPTION, "Delete");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	Row r = MainFrame.getInstance().getInfoResourceView().getSelectedEntity().returnSelectedRow();
    	MainFrame.getInstance().getAppCore().delete(MainFrame.getInstance().getInfoResourceView().getSelectedEntity().getLabel().getText(), r);
    }
}
