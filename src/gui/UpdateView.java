package gui;

import erorr_handler.ErrorHandler;
import erorr_handler.ExceptionEnum;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class UpdateView extends JDialog {

    private String entityName;
    private JButton commit;
    private JButton exit;
    private Entity entity;
    private ArrayList<String> attributesName;
    private ArrayList<TextField> textFields;
    private ArrayList<Label> labels;

    public UpdateView() {
        init();
    }

    public void init() {

        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();
        Row row = MainFrame.getInstance().getInfoResourceView().getSelectedEntity().returnSelectedRow();

        textFields = new ArrayList<>();
        attributesName = new ArrayList<>();
        labels = new ArrayList<>();
        setPreferredSize(new Dimension(600, 400));
        commit = new JButton("Commit");
        exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        southPanel.add(exit);
        southPanel.add(commit);

        TreePath path = MainFrame.getInstance().getTree().getSelectionPath();

        try {
            entity = (Entity) path.getLastPathComponent();
        }catch (NullPointerException e){
            ErrorHandler.error(ExceptionEnum.NO_SELECTED_TAB);
            e.printStackTrace();
        }

        int n = entity.getChildCount();

        centerPanel.setLayout(new GridLayout(n, 2));

        add(southPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        for (int i = 0; i < n; i++) {
            Attribute attribute = (Attribute) entity.getChildAt(i);
            attributesName.add(attribute.getName());
            labels.add(new Label(attributesName.get(i)));
            centerPanel.add(labels.get(i));
            Object oo = row.getObject(attributesName.get(i));
            String ss = "";
            if (oo != null) {
            	ss = oo.toString();
            }
            if (ss.isEmpty()) ss = "";
            TextField tf = new TextField();
            tf.setText(ss);
            textFields.add(tf);
            centerPanel.add(textFields.get(i));
        }

        this.setMaximumSize(new Dimension(200, 800));


        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Boolean> flags = new ArrayList<>();
                Row old = new Row();
                Row neww = new Row();
                for (int i = 0; i < n; i++) {
                    Attribute attr = (Attribute) entity.getChildAt(i);
                    if (textFields.get(i).getText().equals("")) {
                        flags.add(false);
                    } else {
                    	if (attr.getAttributeType() == AttributeType.BIGINT   ||
                    		attr.getAttributeType() == AttributeType.DECIMAL  ||
                    		attr.getAttributeType() == AttributeType.FLOAT    ||
                    		attr.getAttributeType() == AttributeType.INT      ||
                    		attr.getAttributeType() == AttributeType.REAL     ||
                    		attr.getAttributeType() == AttributeType.SMALLINT ||
                    		attr.getAttributeType() == AttributeType.NUMERIC) {

                    		char[] c = textFields.get(i).getText().toCharArray();
                    		int ap = 1;
                    		for (char d : c) {
								if (Character.isLetter(d)) {
									ap = 0;
									break;
								}
							}
                    		System.out.println(ap);
                    		if (ap == 1) {
                    			neww.addField(attr.getName().toUpperCase(), textFields.get(i).getText());
                    			flags.add(true);
                    		}
                    		else {
                    			Object ooo = row.getObject(attributesName.get(i));
                                String sss = "";
                                if (ooo != null) {
                                    sss = ooo.toString();
                                }
                                if (sss.isEmpty()) sss = "";
                    			neww.addField(attr.getName().toUpperCase(), sss);
                    			flags.add(false);
                    		}

                    	} else {
                    		neww.addField(attr.getName().toUpperCase(), textFields.get(i).getText());
                        	flags.add(true);
                    	}

                    }
                }
                for (int i = 0; i < n; i++) {
                    Attribute at = (Attribute) entity.getChildAt(i);
                    Object oo = row.getObject(attributesName.get(i));
                    String ss = "";
                    if (oo != null) {
                        ss = oo.toString();
                    }
                    if (ss.isEmpty()) ss = "";
                    if (flags.get(i).equals(true)) {
                        old.addField(labels.get(i).getText().toUpperCase(), ss);
                    }
                }

                MainFrame.getInstance().getAppCore().update(entity.getName(), old, neww);

                dispose();
            }
        });

    }


}