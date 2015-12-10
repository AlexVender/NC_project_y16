package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.controller.TaskManagerController;
import org.netcracker.unc.group16.model.Task;

import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.util.*;


public class TasksTableModel extends DefaultTableModel {
    private TaskManagerController taskManagerController;
    private Boolean onDay;
    private Calendar calendar;
    private List tasksList;

    private static final DateFormat dateFormat = DateFormat.getDateInstance();

    private static final String[] columnNames  = {
            "ID",
            "Дата",
            "Тема",
            "Описание",
            ""
    };

    private static final Class[] columnClasses  = {
            Integer.class,
            String.class,
            String.class,
            String.class,
            String.class
    };

    public TasksTableModel(TaskManagerController controller, Calendar calendar, boolean onDay) {
        if (controller == null || calendar == null) {
            throw new NullPointerException();
        }

        this.taskManagerController = controller;
        this.calendar = calendar;
        this.onDay = onDay;

        updateData();
    }

    public void updateData() {
        if (onDay) {
            tasksList = taskManagerController.getByDay(Task.class, calendar);
        } else {
            tasksList = taskManagerController.getAll(Task.class);
        }

        fireTableDataChanged();
    }


    @Override
    public int getRowCount() {
        if (tasksList == null) {
            return 0;
        }
        return tasksList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }

    @Override
    public Class getColumnClass(int index) {
        return columnClasses[index];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:     return ((Task)tasksList.get(rowIndex)).getId();
            case 1:     return dateFormat.format(((Task)tasksList.get(rowIndex)).getTime().getTime());
            case 2:     return ((Task)tasksList.get(rowIndex)).getTitle();
            case 3:     return ((Task)tasksList.get(rowIndex)).getDescription();
            case 4:     return "X";
            default:    return null;
        }
    }

    public Calendar getCalendar() {
        return (Calendar) calendar.clone();
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = (Calendar) calendar.clone();
    }

    public Boolean isOnDay() {
        return onDay;
    }

    public void setOnDay(Boolean onDay) {
        this.onDay = onDay;
    }
}
