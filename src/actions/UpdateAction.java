package actions;

import gui.UpdateView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UpdateAction extends AbstractAction {

    public UpdateAction(){
        putValue(NAME, "Update");
        putValue(SHORT_DESCRIPTION, "Update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        UpdateView updateView = new UpdateView();
        updateView.setVisible(true);
    }
}
