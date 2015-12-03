/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */

package org.netcracker.unc.group16.model;


import java.util.concurrent.ScheduledFuture;

public class NotificatorModel implements Observer {
    private Task currentTask;

    private TaskManagerModel taskManagerModel;


    public NotificatorModel(TaskManagerModel taskManagerModel){
        this.taskManagerModel = taskManagerModel;
    }

    public void notificate(){

    }

    public void postpone(){

    }

    public void dismiss(){

    }

    public void getLastTask(){

    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public TaskManagerModel getTaskManagerModel(){
        return this.taskManagerModel;
    }

    public void setTaskManagerModel(TaskManagerModel taskManagerModel){
        this.taskManagerModel = taskManagerModel;
    }

    @Override
    public void update(Task task) {
        if (task.getId() == currentTask.getId()){
            currentTask = task;
        }
    }
}
