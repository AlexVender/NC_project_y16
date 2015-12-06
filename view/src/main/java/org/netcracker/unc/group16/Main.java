package org.netcracker.unc.group16;

import org.netcracker.unc.group16.controller.NotificatorModel;
import org.netcracker.unc.group16.controller.TaskManagerController;
import org.netcracker.unc.group16.model.*;
import org.netcracker.unc.group16.view.TaskManagerView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Main {
    public static void main(String[] args) {
        TaskManagerModel taskManagerModel = new TaskManagerModel();
        NotificatorModel notificatorModel = new NotificatorModel(taskManagerModel);

        TaskManagerController taskManagerController = new TaskManagerController(taskManagerModel);
        TaskManagerView taskManagerView = new TaskManagerView(taskManagerController);
//        TaskManagerView taskManagerView = new TaskManagerView(notificatorModel);


        Calendar t1 = new GregorianCalendar(2015, Calendar.DECEMBER, 3, 0, 0);
        Calendar t2 = new GregorianCalendar(2015, Calendar.DECEMBER, 3, 2, 0);
        Calendar t3 = new GregorianCalendar(2015, Calendar.DECEMBER, 6, 15, 31);
        taskManagerModel.addAppointment("Test1", t1, t2, "TestTask");

        taskManagerModel.addTask("Test2", Calendar.getInstance(), "TestTask");
        taskManagerModel.addTask("TestTask", t3, "TestTask");
        taskManagerModel.addTask("TestTask2", Calendar.getInstance(), "TestTask");

        JAXB jaxb = new JAXB();
        //  jaxb.write(taskManagerModel);


//        TaskManagerView taskManagerView = new TaskManagerView(taskManagerModel);

    }
}
