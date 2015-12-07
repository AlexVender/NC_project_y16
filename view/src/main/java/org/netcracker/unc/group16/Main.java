package org.netcracker.unc.group16;

import org.netcracker.unc.group16.controller.NotificationController;
import org.netcracker.unc.group16.controller.TaskManagerController;
import org.netcracker.unc.group16.model.*;
import org.netcracker.unc.group16.view.TaskManagerView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Main {
    public static void main(String[] args) {
        TaskManagerModel taskManagerModel = new TaskManagerModel();

        Calendar t1 = new GregorianCalendar(2015, Calendar.DECEMBER, 3, 0, 0);
        Calendar t2 = new GregorianCalendar(2015, Calendar.DECEMBER, 3, 2, 0);
        taskManagerModel.addAppointment("Test1", t1, t2, "TestTask");
        Calendar t3 = Calendar.getInstance();
        Calendar t4 = Calendar.getInstance();
        t4.add(Calendar.HOUR_OF_DAY, 2);
        taskManagerModel.addAppointment("Today Appointment", t3, t4, "Task of the today");

        Calendar t5 = new GregorianCalendar(2015, Calendar.DECEMBER, 6, 15, 31);
        taskManagerModel.addTask("Test2", Calendar.getInstance(), "TestTask");
        taskManagerModel.addTask("TestTask", t3, "TestTask");
        taskManagerModel.addTask("TestTask2", Calendar.getInstance(), "TestTask");

        TaskManagerController taskManagerController = new TaskManagerController(taskManagerModel);

        NotificationController notificationController = new NotificationController(taskManagerController);

// TaskManagerView taskManagerView = new TaskManagerView(taskManagerController);
        TaskManagerView taskManagerView = new TaskManagerView(notificationController);


        JAXB jaxb = new JAXB();
//          jaxb.write(taskManagerModel);


//        TaskManagerView taskManagerView = new TaskManagerView(taskManagerModel);

    }
}
