package gui;

import javax.swing.*;

public class MyToolBar extends JToolBar {

    public MyToolBar(){
        super();
        add(MainFrame.getInstance().getActionManager().getAddAction());
        add(MainFrame.getInstance().getActionManager().getDeleteAction());
        add(MainFrame.getInstance().getActionManager().getUpdateAction());
        add(MainFrame.getInstance().getActionManager().getRefreshAction());

        MainFrame.getInstance().getActionManager().getAddAction().setEnabled(false);
        MainFrame.getInstance().getActionManager().getDeleteAction().setEnabled(false);
        MainFrame.getInstance().getActionManager().getUpdateAction().setEnabled(false);
        MainFrame.getInstance().getActionManager().getRefreshAction().setEnabled(false);

        addSeparator();

        add(MainFrame.getInstance().getActionManager().getFilterAndSortAction());

        MainFrame.getInstance().getActionManager().getFilterAndSortAction().setEnabled(false);

        addSeparator();

        add(MainFrame.getInstance().getActionManager().getSearchAction());
        MainFrame.getInstance().getActionManager().getSearchAction().setEnabled(false);

        addSeparator();

        add(MainFrame.getInstance().getActionManager().getCountAction());
        add(MainFrame.getInstance().getActionManager().getAverageAction());

        MainFrame.getInstance().getActionManager().getCountAction().setEnabled(false);
        MainFrame.getInstance().getActionManager().getAverageAction().setEnabled(false);
    }

}
