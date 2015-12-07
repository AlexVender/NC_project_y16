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

        Calendar t12 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 8, 0);
        Calendar t13 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 9, 0);

        taskManagerModel.addAppointment("12 Dec", t12, t13, "12DecComment");
        Calendar t14 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 15, 0);
        Calendar t15 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 17, 0);
        taskManagerModel.addAppointment("12 Dec 2", t14, t15, "12DecComment 2");

        Calendar t16 = Calendar.getInstance();
        Calendar t17 = Calendar.getInstance();
        t16.add(Calendar.SECOND, 5);
        t17.add(Calendar.HOUR_OF_DAY, 2);
        taskManagerModel.addAppointment("2Today Appointment", t16, t17, "Task of the today2");

        TaskManagerController taskManagerController = new TaskManagerController(taskManagerModel);

        NotificationController notificationController = new NotificationController(taskManagerController);

// TaskManagerView taskManagerView = new TaskManagerView(taskManagerController);
        TaskManagerView taskManagerView = new TaskManagerView(notificationController);


        JAXB jaxb = new JAXB();
//          jaxb.write(taskManagerModel);


//        TaskManagerView taskManagerView = new TaskManagerView(taskManagerModel);

    }
}
