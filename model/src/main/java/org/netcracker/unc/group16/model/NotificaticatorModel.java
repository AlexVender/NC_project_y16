/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */

package org.netcracker.unc.group16.model;


import java.util.concurrent.ScheduledFuture;

public class NotificaticatorModel implements Observer {
    private Task currentTask;

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

    @Override
    public void update(Task task) {
        if (task.getId() == currentTask.getId()){
            currentTask = task;
        }
    }
}
