package view;

import gui.MainFrame;
import observer.Notification;
import observer.Publisher;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.implementation.InformationResource;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class TableView extends JPanel implements Subscriber {

    private JTable jTable;
    private ArrayList<JPanel> jPanelList;
    private JLabel labelTitle;
    private JButton btnClose;
    private JScrollPane jScrollPane;
    private JTabbedPane jTabbedPane;

    public TableView(JTable jTable){
        MainFrame.getInstance().getAppCore().addSubscriber(this);
        this.jTable = jTable;
        labelTitle = new JLabel();
        btnClose = new JButton();

        jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        ///////////////////////////////

        ///////////////////
        add(jTabbedPane, BorderLayout.CENTER);
        initTabelView();
    }

    public void initTabelView(){
        if(jPanelList == null) {
            jPanelList = new ArrayList<>();
        }
    }

    public void renderTabs(){
        for(int i = 0 ; i < jPanelList.size(); i++){
            jTabbedPane.addTab("x",jPanelList.get(i));
            jTabbedPane.setTabComponentAt(jTabbedPane.getTabCount()-1, jPanelList.get(i).getComponent(i));
        }
    }

    @Override
    public void update(Notification notification) {
    }
}
