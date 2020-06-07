package actions;

import gui.AddView;
import gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddAction extends AbstractAction {

    public AddAction(){
        putValue(NAME, "Add");
        putValue(SHORT_DESCRIPTION, "Add");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AddView addDialog = new AddView();
        addDialog.setVisible(true);
    }
}
