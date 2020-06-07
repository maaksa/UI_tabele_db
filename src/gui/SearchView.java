package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.TreePath;

import erorr_handler.ErrorHandler;
import erorr_handler.ExceptionEnum;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;

public class SearchView extends JDialog{

	private String entityName;
	private JButton btnSelectCols;
	private JButton btnAnd;
    private JButton btnOr;
    private JButton commit;
    private Entity entity;
    private ArrayList<String> attributesName;
    private ArrayList<String> selectedColumns;
    private ArrayList<String> conj;
    private ArrayList<String> conditions;
    private ArrayList<TextField> textFields;
    private ArrayList<JComponent> components;
    private ArrayList<Label> labels;

	public SearchView() {
		JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();

        textFields = new ArrayList<>();
        attributesName = new ArrayList<>();
        selectedColumns = new ArrayList<>();
        labels = new ArrayList<>();
        components = new ArrayList<>();
        conj = new ArrayList<>();
        conditions = new ArrayList<>();
        setPreferredSize(new Dimension(600,400));
        btnSelectCols = new JButton("Ubaci selektovanu kolonu");
        btnAnd = new JButton("AND");
        btnOr = new JButton("OR");
        commit = new JButton("Commit");
        southPanel.add(btnSelectCols);
        southPanel.add(btnAnd);
        southPanel.add(btnOr);
        southPanel.add(commit);

        btnSelectCols.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = MainFrame.getInstance().getInfoResourceView().getSelectedEntity().returnSelectedColumn();
				selectedColumns.add(s);
			}
		});

        TreePath path = MainFrame.getInstance().getTree().getSelectionPath();

		try {
			entity = (Entity) path.getLastPathComponent();
		}catch (NullPointerException e){
			ErrorHandler.error(ExceptionEnum.NO_SELECTED_TAB);
			e.printStackTrace();
		}

        int n = entity.getChildCount();


        centerPanel.setLayout(new GridLayout(n, 3));

        add(southPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        for (int i = 0; i < n; i++){
            Attribute attribute = (Attribute) entity.getChildAt(i);
            attributesName.add(attribute.getName());
            labels.add(new Label(attributesName.get(i)));
            centerPanel.add(labels.get(i));
            if (attribute.getAttributeType() == AttributeType.CHAR || attribute.getAttributeType() == AttributeType.VARCHAR) {
            	JLabel lbl = new JLabel("LIKE");
            	components.add(lbl);
            	centerPanel.add(lbl);
            }
            else {
            	String[] signs = {">", "<", "="};
            	JComboBox<String> jcb = new JComboBox<String>(signs);
            	components.add(jcb);
            	centerPanel.add(jcb);
            }
            textFields.add(new TextField());
            centerPanel.add(textFields.get(i));
        }

        this.setMaximumSize(new Dimension(200, 800));

        btnAnd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addToConditions();
				conj.add("AND");
			}
		});

        btnOr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addToConditions();
				conj.add("OR");
			}
		});



        this.setTitle("SEARCH " + entity.getName());

        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToConditions();
                ArrayList<Row> rows = MainFrame.getInstance().getAppCore().search(entity.getName(), selectedColumns, conditions, conj);
                MainFrame.getInstance().getInfoResourceView().getSelectedEntity().setTableView(rows);
                dispose();
            }
        });

        setVisible(true);
	}

	public void addToConditions() {
		String condition = "";
		int cnt = 0;
		for (int i = 0; i < attributesName.size(); i++) {
			if (!textFields.get(i).getText().isEmpty()) {
				if (cnt == 1) {
					return;
				}
				condition += labels.get(i).getText();
				if (components.get(i) instanceof JLabel) {
					condition += " LIKE ";
					condition +=  "\'" + textFields.get(i).getText() + "\'";
				}
				else {
					JComboBox<String> jj1 = (JComboBox<String>) components.get(i);
					String s = (String)jj1.getSelectedItem();
					condition += " " + s + " ";
					condition += textFields.get(i).getText();
				}
				textFields.get(i).setText("");
				cnt++;
			}
		}

		if (condition.isEmpty())
			return;
		else
			conditions.add(condition);
	}
}
