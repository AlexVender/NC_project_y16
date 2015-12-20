package org.netcracker.unc.group16;

import org.netcracker.unc.group16.controller.NotificationController;
import org.netcracker.unc.group16.controller.TaskManagerController;
import org.netcracker.unc.group16.model.JAXB;
import org.netcracker.unc.group16.model.TaskManagerModel;
import org.netcracker.unc.group16.view.TaskManagerView;


public class Main{
    public static TaskManagerModel taskManagerModel;
    public static TaskManagerController taskManagerController;
    public static NotificationController notificationController;


    public static void main(String[] args) {
        JAXB jaxb = new JAXB();
        taskManagerModel = jaxb.read();
        taskManagerController = new TaskManagerController(taskManagerModel);

        notificationController = new NotificationController(taskManagerController);

        new TaskManagerView(notificationController);
    }
}
