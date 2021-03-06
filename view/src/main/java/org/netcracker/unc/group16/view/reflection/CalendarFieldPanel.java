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


public class CalendarFieldPanel extends FieldPanel {
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    private final DateModel<Date> dateModel;

    private static final Integer MINUTES_SPLIT = 15;

    private final JSpinner timeSpinner;
    private final JDatePickerImpl datePicker;

    @SuppressWarnings("MagicConstant")
    class SpinnerTimeModel extends SpinnerDateModel {
        public SpinnerTimeModel() {
            super(new Date(), null, null, Calendar.DAY_OF_MONTH);
        }

        @Override
        public Object getNextValue() {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) getValue());
            if (getCalendarField() == Calendar.MINUTE) {
                cal.add(getCalendarField(), MINUTES_SPLIT);
            } else {
                cal.add(getCalendarField(), 1);
            }
            Date next = cal.getTime();
            return ((getEnd() == null) || (((Date)getEnd()).compareTo(next) >= 0)) ? next : null;
        }

        @Override
        public Object getPreviousValue() {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) getValue());
            if (getCalendarField() == Calendar.MINUTE) {
                cal.add(getCalendarField(), -MINUTES_SPLIT);
            } else {
                cal.add(getCalendarField(), -1);
            }
            Date prev = cal.getTime();
            return ((getStart() == null) || (((Date)getStart()).compareTo(prev) <= 0)) ? prev : null;
        }

        @Override
        public void setValue(Object value) {
            if ((value == null) || !(value instanceof Date)) {
                throw new IllegalArgumentException("illegal value");
            }

            Date time = (Date) value;
            if (time.getMinutes() % MINUTES_SPLIT != 0) {
                time.setMinutes(time.getMinutes() + (MINUTES_SPLIT - (time.getMinutes() % MINUTES_SPLIT)));
            }

            super.setValue(time);
        }
    }

    public CalendarFieldPanel(Field field, Object defaultVal, String displayName, Integer order, Boolean editable) {
        super(field, displayName, order, editable);

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 1;
        SpinnerTimeModel spinnerDateModel = new SpinnerTimeModel();
        spinnerDateModel.setValue(Calendar.getInstance().getTime());

        timeSpinner = new JSpinner(spinnerDateModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setEnabled(editable);
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
        datePanel.setEnabled(editable);
        datePicker.setPreferredSize(new Dimension(110, 23));

        add(datePicker, c);


        if (defaultVal != null) {
            Calendar calendar = (Calendar) defaultVal;

            // Setting spinner
            spinnerDateModel.setCalendarField(Calendar.HOUR_OF_DAY);
            spinnerDateModel.setValue(calendar.getTime());
            spinnerDateModel.setCalendarField(Calendar.MINUTE);
            spinnerDateModel.setValue(calendar.getTime());

            // Setting calendar
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dateModel.setDate(year, month, day);
        }
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
