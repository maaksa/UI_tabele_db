package gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.TableModel;
import resource.data.Row;

public class ReportView extends JDialog{

	public ReportView(ArrayList<Row> rows) {
		TableModel tm = new TableModel();
		tm.setRows(rows);

		JTable table = new JTable(tm);
		setPreferredSize(new Dimension(500, 500));
		add(new JScrollPane(table));
		setVisible(true);
	}

}
