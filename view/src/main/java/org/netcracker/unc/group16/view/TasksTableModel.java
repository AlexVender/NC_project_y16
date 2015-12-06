package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.model.Appointment;
import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.model.TaskManagerModel;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;


public class TasksTableModel extends DefaultTableModel {
    private Boolean onDay;
    private TaskManagerModel taskManagerModel;
    private ArrayList<Task> taskList;

    // FIXME:  2 конструктора
    public TasksTableModel(TaskManagerModel model, Calendar calendar, boolean onDay) {
        if (model == null || calendar == null) {
            throw new NullPointerException();
        }

        this.onDay = onDay;
        taskManagerModel = model;

        Map<Integer, Task> taskMap;
        if (onDay) {
            taskMap = model.getTasksByDate(Task.class, calendar);
        } else {
            taskMap = model.getHashMapTasks();
        }

        taskList = new ArrayList<>(taskMap.values());

        // Sort by time
        taskList.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));


    }

    public int getRowCount() {
        if (taskList == null) {
            return 0;
        }
        int size = taskList.size();
        System.out.println(size);
        return size;
    }

    public String getColumnName(int index) {
        switch (index) {
            case 0:     return "ID";
            case 1:     return "Название";
            default:    return "";
        }
    }


    public Class getColumnClass(int index) {
        switch (index) {
            case 0:     return Integer.class;
            case 1:     return String.class;
            default:    return Object.class;
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:     return taskList.get(rowIndex).getId() ;
            case 1:     return taskList.get(rowIndex).getTitle() ;
            default:    return null;
        }
    }

//    public void setValueAt(Object value, int rowIndex, int columnIndex) {
//    }

    public Boolean isOnDay() {
        return onDay;
    }

    public void setOnDay(Boolean onDay) {
        this.onDay = onDay;
    }
}
