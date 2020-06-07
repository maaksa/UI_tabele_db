package model;

import lombok.Data;
import resource.data.Row;
import resource.implementation.Attribute;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

@Data
public class TableModel extends DefaultTableModel {

    private List<Row> rows = new ArrayList<>();


    private void updateModel(){

        int columnCount = rows.get(1).getFields().keySet().size();

        Vector columnVector = DefaultTableModel.convertToVector(rows.get(1).getFields().keySet().toArray());
        Vector dataVector = new Vector(columnCount);

        for (int i=0; i<rows.size(); i++){
            dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().values().toArray()));
        }
        setDataVector(dataVector, columnVector);
    }

    public TableModel tmCopy(){
    	TableModel tm = new TableModel();
    	for (Row row : this.rows) {
			tm.getRows().add(row);
		}
    	tm.updateModel();
    	return tm;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        updateModel();
    }

    public List<Row> getRows() {
    	return rows;
    }

}
