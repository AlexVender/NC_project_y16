/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */

package org.netcracker.unc.group16.model;

import java.util.Observable;
import java.util.Observer;

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


    public void update(Observable o, Object arg) {

    }

}
