package org.netcracker.unc.group16.controller;

import org.netcracker.unc.group16.model.Task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */
public class TaskManagerController implements TaskManager {
    private Map<Integer, Task> hashMapTasks = new HashMap<Integer, Task>();
//    private TaskManager taskManager;


    //Только по идее id не должно передаваться, а должно генерироваться в model
    public void performedAddTask(int id, String title, Date time, String comment){
//        taskManager.addTask(id, title, time, comment);
    }

//    public void performedDeleteTask(int id){
//        taskManager.deleteTask(id);
//    }
//
//    public  void performedEditTask(int id, String title, Date time, String comment){
//        taskManager.editTask(id, title, time, comment);
//    }
//
//
//    public Map<Integer, Task> performedGetTasks(Date date1, Date date2){
//        return hashMapTasks = taskManager.getTasksByDate(date1, date2);
//    }

}
