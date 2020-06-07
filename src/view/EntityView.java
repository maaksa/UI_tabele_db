package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import gui.MainFrame;
import model.TableModel;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.data.Row;
import resource.implementation.Entity;

public class EntityView extends JPanel implements Subscriber{

	private JTable jTable;
	private Entity entity;
	private JLabel label;
	private JScrollPane jsPane;

	public EntityView(Entity entity) {
		this.entity = entity;
		this.entity.addSubscriber(this);

		setPreferredSize(new Dimension(450,450));
		generateTable();

		label = new JLabel(this.entity.getName());
		jsPane = new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(jsPane);
	}

	private void generateTable() {
		MainFrame.getInstance().getAppCore().readDataFromTable(entity.getName());
		TableModel tm = MainFrame.getInstance().getAppCore().getTableModel().tmCopy();
		jTable = new JTable(tm);
		jTable.setPreferredScrollableViewportSize(new Dimension(1000, 600));
		JTableHeader jtHeader = jTable.getTableHeader();
		jtHeader.setReorderingAllowed(false);
		jtHeader.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickedIndex = jtHeader.columnAtPoint(e.getPoint());
				jTable.setColumnSelectionInterval(clickedIndex, clickedIndex);
				jTable.setRowSelectionInterval(0, jTable.getRowCount() - 1);
			}
		});
		jTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jTable.setColumnSelectionAllowed(true);
				jTable.setRowSelectionAllowed(true);
				returnSelectedRow();
			}
		});
	}

	public Row returnSelectedRow() {
		Row rr = new Row();
		int ri = jTable.getSelectedRow();
		for (int i = 0; i < jTable.getColumnCount(); i++) {
			rr.addField(jTable.getColumnName(i), jTable.getValueAt(ri, i));
		}
		return rr;
	}

	public String returnSelectedColumn() {
		int rr = jTable.getSelectedColumn();
		return jTable.getColumnName(rr);
	}

	public void setTableView(ArrayList<Row> rows) {
		TableModel tm = new TableModel();
		tm.setRows(rows);
		jTable = new JTable(tm);
		jTable.setPreferredScrollableViewportSize(new Dimension(1000, 600));
		JTableHeader jtHeader = jTable.getTableHeader();
		jtHeader.setReorderingAllowed(false);
		jtHeader.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickedIndex = jtHeader.columnAtPoint(e.getPoint());
				jTable.setColumnSelectionInterval(clickedIndex, clickedIndex);
				jTable.setRowSelectionInterval(0, jTable.getRowCount() - 1);
			}
		});
		jTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jTable.setColumnSelectionAllowed(true);
				jTable.setRowSelectionAllowed(true);
				returnSelectedRow();
			}
		});
		this.remove(jsPane);
		jsPane = new JScrollPane(jTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(jsPane);
		repaint();
		revalidate();
	}

	@Override
	public void update(Notification notification) {
		// TODO Auto-generated method stub

	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JTable getjTable() {
		return jTable;
	}

	public Entity getEntity() {
		return entity;
	}



}
