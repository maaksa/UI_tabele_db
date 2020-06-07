package actions;

import gui.FilterAndSortView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FilterAndSortAction extends AbstractAction {

    public FilterAndSortAction(){
        putValue(NAME, "Filter&Sort");
        putValue(SHORT_DESCRIPTION, "Filter&Sort");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FilterAndSortView filterAndSortView = new FilterAndSortView();
        filterAndSortView.setVisible(true);
    }
}
