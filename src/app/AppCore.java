package app;

import database.Database;
import database.DatabaseImplementation;
import database.MSSQLrepository;
import database.Repository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.MainFrame;
import model.TableModel;
import model.Tree;
import model.TreeModel;
import observer.Notification;
import observer.Publisher;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.data.Row;
import resource.implementation.Entity;
import resource.implementation.InformationResource;
import resource.implementation.TreeNode;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AppCore implements Publisher {

    private Database database;
    private Settings settings;
    private TableModel tableModel;
    private TreeModel treeModel;
    private TreeNode treeNode;
    List<Subscriber> subscribers = new ArrayList<Subscriber>();
    private InformationResource ir;

    public AppCore() {
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MSSQLrepository(this.settings));
        tableModel = new TableModel();

    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mssql_ip", Constants.MSSQL_IP);
        settingsImplementation.addParameter("mssql_database", Constants.MSSQL_DATABASE);
        settingsImplementation.addParameter("mssql_username", Constants.MSSQL_USERNAME);
        settingsImplementation.addParameter("mssql_password", Constants.MSSQL_PASSWORD);
        return settingsImplementation;
    }

    public void loadResource(){
        ir = (InformationResource) this.database.loadResource();
        treeNode = new TreeNode(ir);
        treeModel = new TreeModel();
        MainFrame.getInstance().getTree().setModel(treeModel);

        treeModel.setRoot(treeNode.getDbNode());
    }

    public void readDataFromTable(String fromTable){

        tableModel.setRows(this.database.readDataFromTable(fromTable));

        this.notifySubscribers(new Notification(NotificationCode.SHOW_ENTITY, tableModel));
    }

    public ArrayList<Row> getAllRows(String fromTable) {
    	return this.database.readDataFromTable(fromTable);
    }

    public void add(String name, Row row) {
        this.database.add(name, row);
    }

    public void delete(String name, Row row) {
        this.database.delete(name, row);
    }

    public void update(String name, Row old, Row neww) {
        this.database.update(name, old, neww);
    }

    public ArrayList<Row> filterAndSort(String name, ArrayList<String> filters, ArrayList<String> sorts, ArrayList<Boolean> isDesList){
        return this.database.filterAndSort(name, filters, sorts, isDesList);
    }

    public ArrayList<Row> count(String name, ArrayList<String> counted, ArrayList<String> forView, ArrayList<String> groupedBy) {
    	return this.database.count(name, counted, forView, groupedBy);
    }

    public ArrayList<Row> average(String name, ArrayList<String> averaged, ArrayList<String> forView, ArrayList<String> groupedBy) {
        return this.database.average(name, averaged, forView, groupedBy);
    }

    public ArrayList<Row> search(String name, ArrayList<String> selectedColumns, ArrayList<String> conditions, ArrayList<String> conj) {
    	return this.database.search(name, selectedColumns, conditions, conj);
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void addSubscriber(Subscriber sub) {
		if(sub == null)
			return;
		if(this.subscribers ==null)
			this.subscribers = new ArrayList<>();
		if(this.subscribers.contains(sub))
			return;
		this.subscribers.add(sub);
	}

	public void removeSubscriber(Subscriber sub) {
		if(sub == null || this.subscribers == null || !this.subscribers.contains(sub))
			return;
		this.subscribers.remove(sub);
	}

	public void notifySubscribers(Notification notification) {
		if(notification == null || this.subscribers == null || this.subscribers.isEmpty())
			return;

		for(Subscriber listener : subscribers){
			listener.update(notification);
		}
	}

	public InformationResource getIr() {
		return ir;
	}

	public void setIr(InformationResource ir) {
		this.ir = ir;
	}


}
