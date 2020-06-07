package gui;

import actions.FilterAndSortAction;
import erorr_handler.ErrorHandler;
import erorr_handler.ExceptionEnum;
import resource.data.Row;
import resource.implementation.Attribute;
import resource.implementation.Entity;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Map;

public class FilterAndSortView extends JDialog {

    private String entityName;
    private JButton commit;
    private JButton exit;
    private Entity entity;
    private ArrayList<String> attributesName;
    private ArrayList<TextField> textFields;
    private ArrayList<Label> labels;

    public FilterAndSortView(){
        init();
    }

    public void init(){

        JPanel centerPanel = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel northPanel = new JPanel();

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
        ArrayList<JCheckBox> sorts = new ArrayList<>();
        ArrayList<JCheckBox> filters = new ArrayList<>();
        ArrayList<JRadioButton> asc = new ArrayList<>();
        ArrayList<JRadioButton> desc = new ArrayList<>();
        attributesName = new ArrayList<>();

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
        add(northPanel, BorderLayout.NORTH);

        northPanel.add(new JLabel("filter: "));

        for (int i = 0; i < n; i++){
            Attribute attribute = (Attribute) entity.getChildAt(i);
            attributesName.add(attribute.getName());
            filters.add(new JCheckBox(attributesName.get(i)));
            northPanel.add(filters.get(i));
            sorts.add(new JCheckBox(attributesName.get(i)));
            centerPanel.add(new JLabel("sort: "));
            centerPanel.add(sorts.get(i));
            asc.add(new JRadioButton("Ascending order"));
            centerPanel.add(asc.get(i));
            desc.add(new JRadioButton("Descending order"));
            centerPanel.add(desc.get(i));
            textFields.add(new TextField());
        }

        this.setMaximumSize(new Dimension(200, 800));

        ArrayList<String> sortNames = new ArrayList<>();
        ArrayList<String> filterNames = new ArrayList<>();
        ArrayList<Boolean> isDecs = new ArrayList<>();


        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Row> rows = new ArrayList<>();

                for(int i = 0; i < n; i++){
                    if(filters.get(i).isSelected()){
                        filterNames.add(filters.get(i).getText());
                    }
                    if(sorts.get(i).isSelected()){
                        sortNames.add(sorts.get(i).getText());
                    }
                    if(asc.get(i).isSelected()){
                        isDecs.add(false);
                    }
                    if(!asc.get(i).isSelected()){
                        isDecs.add(true);
                    }
                }

                rows = MainFrame.getInstance().getAppCore().filterAndSort(entity.getName(), filterNames, sortNames, isDecs);
                MainFrame.getInstance().getInfoResourceView().getSelectedEntity().setTableView(rows);

                dispose();

            }
        });

    }


}