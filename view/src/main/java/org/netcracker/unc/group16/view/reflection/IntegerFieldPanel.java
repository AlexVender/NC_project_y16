package org.netcracker.unc.group16.view.reflection;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;


public class IntegerFieldPanel extends FieldPanel {
    private final JTextField textField;

    public IntegerFieldPanel(Field field, Object defaultVal, String displayName, Integer order, Boolean editable) {
        super(field, displayName, order, editable);

        textField = new JTextField();
        if (defaultVal != null) {
            textField.setText(defaultVal.toString());
        }
        textField.setEnabled(editable);
        textField.setPreferredSize(new Dimension(100, 20));
        add(textField);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public boolean isValidData() {
        if (textField.getText().equals("")) {
            return true;
        }

        try {
            Integer.parseInt(textField.getText());
        } catch(NumberFormatException ex ) {
            return false;
        }
        return true;
    }

    @Override
    public Object getData() {
        if (!textField.getText().equals("")) {
            return Integer.parseInt(textField.getText());
        } else {
            return null;
        }
    }
}
