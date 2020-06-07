package database;

import resource.DBNode;
import resource.data.Row;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public interface Repository {

    DefaultMutableTreeNode getSchema();

    ArrayList<Row> get(String from);

    void add(String name, Row row);

    void delete(String name, Row row);

    void update(String name, Row old, Row neww);

    ArrayList<Row> search(String name, ArrayList<String> selectedColumns, ArrayList<String> conditions, ArrayList<String> conj);

    ArrayList<Row> filterAndSort(String name, ArrayList<String> filters, ArrayList<String> sorts, ArrayList<Boolean> isDesList);

    ArrayList<Row> count(String name, ArrayList<String> counted, ArrayList<String> forView, ArrayList<String> groupedBy);

    ArrayList<Row> average(String name, ArrayList<String> averaged, ArrayList<String> forView, ArrayList<String> groupedBy);

}
