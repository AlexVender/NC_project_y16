package org.netcracker.unc.group16.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement(name="tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskManagerModel {

    @XmlElement(name = "task")
    private Map<Integer, Task> hashMapTasks = null;

    public void editTask(int id, String title, Calendar time, String comment){
        getHashMapTasks().get(id).setTitle(title);
        getHashMapTasks().get(id).setTime(time);
        getHashMapTasks().get(id).setComment(comment);
    }

    public void addTask(int id, String title, Calendar time, String comment){
        Task task = new Task(id, title, time, comment);
        getHashMapTasks().put(id, task);
    }
    public void deleteTask(int id){
        getHashMapTasks().remove(id);
    }

    public Task getTask(int id){
        return getHashMapTasks().get(id);
    }

    public Map<Integer, Task> getTasksByDate(Calendar date1, Calendar date2){
        Map<Integer, Task> tempHashMapTasks= new HashMap<Integer, Task>();
        for (HashMap.Entry<Integer, Task> entry : getHashMapTasks().entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();
            if (value.getTime().after(date1) &&
                    value.getTime().before(date2)){
                tempHashMapTasks.put(key, value);
            }
        }
        return  tempHashMapTasks;
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


    public Map<Integer, Task> getHashMapTasks() {
        return hashMapTasks;
    }

    public void setHashMapTasks(Map<Integer, Task> hashMapTasks) {
        this.hashMapTasks = hashMapTasks;
    }
}
