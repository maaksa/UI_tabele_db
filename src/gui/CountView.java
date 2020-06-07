package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import resource.data.Row;

public class CountView extends JDialog{

	private ArrayList<String> counts = new ArrayList<>();
	private ArrayList<String> views = new ArrayList<>();
	private ArrayList<String> groups = new ArrayList<>();
	private String name;
	private JButton jbUzmi;
	private JButton jbNastavi;
	private int indeksReda = 1;

	public CountView() {
		JLabel jCount = new JLabel("Selektuj kolone za Count:");
		JLabel jView = new JLabel("Selektuj kolone za Prikaz:");
		JLabel jGroup = new JLabel("Selektuj kolone za grupisanje:");

		jbUzmi = new JButton("Uzmi");
		jbNastavi = new JButton("Nastavi");
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 400));
		name = MainFrame.getInstance().getInfoResourceView().getSelectedEntity().getLabel().getText();

		add(jbUzmi, BorderLayout.EAST);
		add(jbNastavi, BorderLayout.WEST);

		add(jCount, BorderLayout.NORTH);
		jbUzmi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ss = MainFrame.getInstance().getInfoResourceView().getSelectedEntity().returnSelectedColumn();
				System.out.println(ss);
				switch (indeksReda) {
				case 1:
					counts.add(ss);
					break;
				case 2:
					views.add(ss);
					break;
				case 3:
					groups.add(ss);
					break;
				default:
					break;
				}
			}
		});

		jbNastavi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				indeksReda++;
				if (indeksReda == 4) {
					ArrayList<Row> rows = MainFrame.getInstance().getAppCore().count(name, counts, views, groups);
					ReportView rv = new ReportView(rows);
				}
			}
		});

	}
}
