package org.netcracker.unc.group16.view.reflection;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;


public class TaskIntegerFieldPanel extends TaskFieldPanel {
    JTextField textField;

    public TaskIntegerFieldPanel(Field field, String displayName, Integer order, Boolean editable) {
        super(field, displayName, order, editable);

        textField = new JTextField();
        textField.setEnabled(editable);
        textField.setPreferredSize(new Dimension(100, 20));
        add(textField);
    }

    @Override
    public boolean isValidData() {
        try {
            Integer integer  = Integer.parseInt(textField.getText());
        } catch(NumberFormatException ex ) {
            return false;
        }
        return true;
    }

    @Override
    public Object getData() {
        return Integer.parseInt(textField.getText());
    }
}
