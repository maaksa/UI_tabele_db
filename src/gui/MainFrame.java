package gui;

import actions.manager.ActionManager;
import app.AppCore;
import lombok.Data;
import model.Tree;
import model.TreeModel;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.implementation.InformationResource;
import view.InfoResourceView;
import view.TableView;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private AppCore appCore;
    private JTable jTable;
    private JScrollPane jScrollPaneLeft;
    private JScrollPane jScrollPaneRight;
    private JPanel panelCenter;
    private JPanel bottomStatus;
    private InformationResource informationResource;
    private ActionManager actionManager;
    private MyToolBar myToolBar;

    private JTabbedPane jTabbedPane;
    private InfoResourceView infoResourceView;

    private TableView tabelView;

    private Tree tree;
    private TreeModel treeModel;

    private MainFrame() {

    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        appCore = new AppCore();
        actionManager = new ActionManager();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenDim = toolkit.getScreenSize();
        int screenH = screenDim.height;
        int screenW = screenDim.width;
        setSize(screenW / 2, screenH / 2);
        setTitle("VIM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
        jTable.setFillsViewportHeight(true);
        jTabbedPane = new JTabbedPane();
        JPanel jpp = new JPanel();
        jpp.add(jTable);


        tabelView = new TableView(jTable);

        initialiseAppCore(appCore);

        initialiseTree();
        initialiseGUI();


        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    private void initialiseTree(){

        tree = new Tree();
        this.getAppCore().loadResource();
        informationResource = appCore.getIr();
    }

    private void initialiseGUI(){

        infoResourceView = new InfoResourceView(informationResource);

        myToolBar = new MyToolBar();

        this.getContentPane().add(myToolBar, BorderLayout.NORTH);

        jScrollPaneLeft = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPaneLeft.setMinimumSize(new Dimension(100, 150));
        this.add(jScrollPaneLeft);

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollPaneLeft, infoResourceView);
        jScrollPaneLeft.setMinimumSize(new Dimension(100, 100));
        this.add(jSplitPane, BorderLayout.CENTER);
        jSplitPane.setDividerLocation(200);
    }

    public void initTableView(){
        tabelView = new TableView(jTable);
    }

    public void initialiseAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.jTable.setModel(appCore.getTableModel());
    }


    @Override
    public void update(Notification notification) {

        if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
            System.out.println((InformationResource)notification.getData());
        }

        else{
            jTable.setModel((TableModel) notification.getData());
            //panelCenter = new TableView(jTable);
            //initTableView();
            JPanel jp = new JPanel();
            jp.add(jTable);
            jTabbedPane.add(jTable);
        }

    }

    public void repaintTable(){

    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public AppCore getAppCore() {
        return appCore;
    }

    public Tree getTree() {
        return tree;
    }

	public JTable getjTable() {
		return jTable;
	}

	public JScrollPane getjScrollPaneLeft() {
		return jScrollPaneLeft;
	}

	public JScrollPane getjScrollPaneRight() {
		return jScrollPaneRight;
	}

	public JPanel getPanelCenter() {
		return panelCenter;
	}

	public JPanel getBottomStatus() {
		return bottomStatus;
	}

	public InformationResource getInformationResource() {
		return informationResource;
	}

	public MyToolBar getMyToolBar() {
		return myToolBar;
	}

	public JTabbedPane getjTabbedPane() {
		return jTabbedPane;
	}

	public InfoResourceView getInfoResourceView() {
		return infoResourceView;
	}

	public TableView getTabelView() {
		return tabelView;
	}

	public TreeModel getTreeModel() {
		return treeModel;
	}
}
