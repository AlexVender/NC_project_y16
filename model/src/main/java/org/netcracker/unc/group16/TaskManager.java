package org.netcracker.unc.group16;

import java.util.*;

public class TaskManager extends Observable{
    Map<Integer, Task> hashMapTasks = new HashMap<Integer, Task>();

    public void editTask(int id, String title, Date time, String comment){
        hashMapTasks.get(id).setTitle(title);
        hashMapTasks.get(id).setTime(time);
        hashMapTasks.get(id).setComment(comment);
        changedTasks();
    }

    public void addTask(int id, String title, Date time, String comment){
        Task task = new Task(id, title, time, comment);
        hashMapTasks.put(id, task);
        changedTasks();
    }
    public void deleteTask(int id){
        hashMapTasks.remove(id);
        changedTasks();
    }

    public Task getTask(int id){
        return hashMapTasks.get(id);
    }

    public Map<Integer, Task> getTasksByDate(Date date1, Date date2){
        Map<Integer, Task> tempHashMapTasks= new HashMap<Integer, Task>();
        for (HashMap.Entry<Integer, Task> entry : hashMapTasks.entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();
            if (value.getTime().after(date1) &&
                    value.getTime().before(date2)){
                tempHashMapTasks.put(key, value);
            }
        }
        return  tempHashMapTasks;
    }

    private void changedTasks(){
        super.setChanged();
        super.notifyObservers();
    }


    public void execute(){
        TaskManager taskManager = new TaskManager();
        taskManager.addObserver(new Notification());
    }
    public void load(){

    }

    public void save(){

    }

    public void init(){

    }




}