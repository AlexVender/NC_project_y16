package org.netcracker.unc.group16.view.reflection;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;


public class TaskStringFieldPanel extends TaskFieldPanel {
    private JTextField textField;

    public TaskStringFieldPanel(Field field, String displayName, Integer order, Boolean editable) {
        super(field, displayName, order, editable);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        textField = new JTextField();
        textField.setEnabled(editable);
        textField.setPreferredSize(new Dimension(200, 20));
        add(textField, c);
    }

    @Override
    public boolean isValidData() {
        return true;
    }

    @Override
    public Object getData() {
        return textField.getText();
    }
}