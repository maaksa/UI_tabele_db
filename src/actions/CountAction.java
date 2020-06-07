package actions;

import gui.AddView;
import gui.CountView;
import gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CountAction extends AbstractAction {

	private static final long serialVersionUID = 3364008310096843004L;

	public CountAction(){
        putValue(NAME, "Count");
        putValue(SHORT_DESCRIPTION, "Count");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CountView cw = new CountView();
        cw.setVisible(true);
    }
}

