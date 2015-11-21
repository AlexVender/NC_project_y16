package org.netcracker.unc.group16;

import java.util.*;

public class TaskManagerController extends Observable implements TaskManager {
    Map<Integer, Task> hashMapTasks = new HashMap<Integer, Task>();

    public void editTask(int id, String title, Calendar time, String comment){
        hashMapTasks.get(id).setTitle(title);
        hashMapTasks.get(id).setTime(time);
        hashMapTasks.get(id).setComment(comment);
        changedTasks();
    }

    public void addTask(int id, String title, Calendar time, String comment){
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

    public Map<Integer, Task> getTasksByDate(Calendar date1, Calendar date2){
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
        TaskManagerController taskManager = new TaskManagerController();
        taskManager.addObserver(new Notification());
    }

    public Task getTestTask(){
        Task testTask = new Task(1, "TestTask", Calendar.getInstance(), "TestTask");
        return testTask;
    }
    public void load(){

    }

    public void save(){

    }

    public void init(){

    }




}
