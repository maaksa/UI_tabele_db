package actions;

import javax.swing.*;

import gui.SearchView;

import java.awt.event.ActionEvent;

public class SearchAction extends AbstractAction {

    public SearchAction(){
        putValue(NAME, "Search");
        putValue(SHORT_DESCRIPTION, "Search");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	SearchView sv = new SearchView();
    }
}
