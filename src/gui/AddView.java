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

public class AddView extends JDialog {

    private JButton commit;
    private JButton exit;
    private Entity entity;
    private ArrayList<String> attributesName;
    private ArrayList<TextField> textFields;
    private ArrayList<Label> labels;

    public AddView(){
        init();
    }

    public void init(){

        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();

        textFields = new ArrayList<>();
        attributesName = new ArrayList<>();
        labels = new ArrayList<>();
        setPreferredSize(new Dimension(600,400));
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

        for (int i = 0; i < n; i++){
            Attribute attribute = (Attribute) entity.getChildAt(i);
            attributesName.add(attribute.getName());
            labels.add(new Label(attributesName.get(i)));
            centerPanel.add(labels.get(i));
            textFields.add(new TextField());
            centerPanel.add(textFields.get(i));
        }

        this.setMaximumSize(new Dimension(200, 800));

        this.setTitle("INSERT INTO " + entity.getName());

        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Row row = new Row();
                for(int i = 0; i < n; i++){
                	Attribute attr = (Attribute)entity.getChildAt(i);
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
                    		if (ap == 1) {
                    			row.addField(labels.get(i).getText().toUpperCase(), textFields.get(i).getText());
                    		}
                    		else {
                    			row.addField(labels.get(i).getText().toUpperCase(), "0");
                    		}

                    	} else if (attr.getAttributeType() == AttributeType.CHAR ||
                    			   attr.getAttributeType() == AttributeType.VARCHAR) {
                    		char[] c = textFields.get(i).getText().toCharArray();
                    		int ap = 1;
                    		for (char d : c) {
								if (!Character.isLetter(d)) {
									ap = 0;
									break;
								}
							}
                    		if (ap == 1) {
                    			row.addField(labels.get(i).getText().toUpperCase(), textFields.get(i).getText());
                    		}
                    		else {
                    			row.addField(labels.get(i).getText().toUpperCase(), "");
                    		}
                    	}
                			else {
                				row.addField(labels.get(i).getText().toUpperCase(), textFields.get(i).getText());
                			}
                }

                MainFrame.getInstance().getAppCore().add(entity.getName(), row);

                dispose();
            }
        });

    }


}
