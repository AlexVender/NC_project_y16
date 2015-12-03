package org.netcracker.unc.group16.view.reflection;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;


public class TaskIntegerFieldPanel extends TaskFieldPanel {
    JSpinner textField;
    private final SpinnerModel spinnerModel;

    public TaskIntegerFieldPanel(Field field, String displayName, Integer order, Boolean editable) {
        super(field, displayName, order, editable);

        spinnerModel = new SpinnerNumberModel(0, null, null, 1);
        textField = new JSpinner(spinnerModel);
        textField.setEnabled(editable);
        textField.setPreferredSize(new Dimension(100, 20));
        add(textField);
    }

    @Override
    public boolean isValidData() {
        try {
            Integer integer  = Integer.parseInt(spinnerModel.getValue().toString());
        } catch(NumberFormatException ex ) {
            return false;
        }
        return true;
    }

    @Override
    public Object getData() {
        return spinnerModel.getValue();
    }
}
