package actions;

import gui.AverageView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AverageAction extends AbstractAction {

    public AverageAction(){
        putValue(NAME, "Average");
        putValue(SHORT_DESCRIPTION, "Average");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AverageView  averageView = new AverageView();
        averageView.setVisible(true);
    }
}
