package org.netcracker.unc.group16.view.reflection;


import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class TaskCalendarFieldPanel extends TaskFieldPanel {
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");

    private final DateModel dateModel;
    private JSpinner timeSpinner;
    private JDatePickerImpl datePicker;

    public TaskCalendarFieldPanel(Field field, String displayName, Integer order, Boolean editable) {
        super(field, displayName, order, editable);

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 1;
        timeSpinner = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setPreferredSize(new Dimension(55, 23));
        add(timeSpinner, c);

        c.gridx = 2;
        add(Box.createRigidArea(new Dimension(10,0)), c);

        c.gridx = 3;
        dateModel = new UtilDateModel();
        dateModel.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Сегодня");
        p.put("text.month", "Месяц");
        p.put("text.year", "Год");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {
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
        datePicker.setTextEditable(true);
        datePicker.setPreferredSize(new Dimension(110, 23));

        add(datePicker, c);
    }

    @Override
    public boolean isValidData() {
        return true;
    }

    @Override
    public Object getData() {
        Calendar calendar = Calendar.getInstance();
        // Setting time
        calendar.setTime((Date)timeSpinner.getModel().getValue());

        // Setting date
        calendar.set(Calendar.YEAR, dateModel.getYear());
        calendar.set(Calendar.MONTH, dateModel.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, dateModel.getDay());

        return calendar;
    }
}
