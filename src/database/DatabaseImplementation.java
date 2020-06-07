package database;

import lombok.AllArgsConstructor;
import lombok.Data;
import resource.DBNode;
import resource.data.Row;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

@Data
@AllArgsConstructor
public class DatabaseImplementation implements Database {

    private Repository repository;

    @Override
    public DefaultMutableTreeNode loadResource() {

        return repository.getSchema();
    }

    @Override
    public ArrayList<Row> readDataFromTable(String tableName) {

        return repository.get(tableName);
    }

    @Override
    public void add(String name, Row row) {

        repository.add(name, row);
    }

    @Override
    public void delete(String name, Row row) {

        repository.delete(name, row);
    }

    @Override
    public void update(String name, Row old, Row neww) {

        repository.update(name, old, neww);
    }

    @Override
    public ArrayList<Row> filterAndSort(String name, ArrayList<String> filters, ArrayList<String> sorts, ArrayList<Boolean> isDesList) {
       return repository.filterAndSort(name, filters, sorts, isDesList);
    }

	@Override
	public ArrayList<Row> count(String name, ArrayList<String> counted, ArrayList<String> forView,
			ArrayList<String> groupedBy) {
		return repository.count(name, counted, forView, groupedBy);
	}

    @Override
    public ArrayList<Row> average(String name, ArrayList<String> averaged, ArrayList<String> forView, ArrayList<String> groupedBy) {
        return repository.average(name, averaged, forView, groupedBy);
    }

	@Override
	public ArrayList<Row> search(String name, ArrayList<String> selectedColumns, ArrayList<String> conditions,
			ArrayList<String> conj) {
		return repository.search(name, selectedColumns, conditions, conj);
	}


}
