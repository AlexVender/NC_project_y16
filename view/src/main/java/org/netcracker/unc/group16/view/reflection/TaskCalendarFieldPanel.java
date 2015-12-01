package org.netcracker.unc.group16.view.reflection;


import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;


public class TaskCalendarFieldPanel extends TaskFieldPanel {

    public TaskCalendarFieldPanel(Field field, String displayName, Integer order, Boolean editable) {
        super(field, displayName, order, editable);

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 1;



        c.gridx = 2;
        /*
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {
            private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

            @Override
            public Object stringToValue(String text) throws ParseException {
                return dateFormatter.parseObject(text);
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value != null) {
                    Calendar cal = (Calendar) value;
                    return dateFormatter.format(cal.getTime());
                }

                return "";
            }
        });
        datePicker.setPreferredSize(new Dimension(110,25));

        add(datePicker, c);*/
    }

    @Override
    public boolean isValidData() {
        return true;
    }

    @Override
    public Object getData() {
        return Calendar.getInstance();
    }
}
