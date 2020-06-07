package erorr_handler;

import app.Main;
import gui.MainFrame;

import javax.swing.*;

public abstract class ErrorHandler {

    public static void error(ExceptionEnum exception){
        switch (exception){
            case NO_SELECTED_TAB:
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "You haven't selected a tab!", "No selection", JOptionPane.WARNING_MESSAGE);
                break;
            case WRONG_INPUT:
                JOptionPane.showMessageDialog(MainFrame.getInstance(), "You entered a worng value!", "Wrong input", JOptionPane.WARNING_MESSAGE);
                break;
        }
    }
}
