package org.netcracker.unc.group16;

import java.util.Date;
import java.util.List;



/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */
public class TaskManagerXML implements TaskManager {
    private List<Task> TaskList;

    public void editTask(int id){

    }

    public void addTask(){

    }

    public void deleteTask(int id){

    }

    public Task getTask(int id){
        return TaskList.get(id);
    }

    public List<Task> getTasks(Date date){
        return TaskList;
    }

    public void load(){

    }

    public void save(){

    }

    public void init(){

    }
}