package org.netcracker.unc.group16;

import org.netcracker.unc.group16.controller.NotificationController;
import org.netcracker.unc.group16.controller.TaskManagerController;
import org.netcracker.unc.group16.model.Appointment;
import org.netcracker.unc.group16.model.JAXB;
import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.model.TaskManagerModel;
import org.netcracker.unc.group16.view.TaskManagerView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Main {
    public static void main(String[] args) {
        TaskManagerModel taskManagerModel = new TaskManagerModel();
        TaskManagerController taskManagerController = new TaskManagerController(taskManagerModel);

        Calendar t1 = new GregorianCalendar(2015, Calendar.DECEMBER, 15, 10, 0);
        Calendar t2 = new GregorianCalendar(2015, Calendar.DECEMBER, 19, 15, 0);
        taskManagerController.add(new Appointment("Test1", t1, t2, "TestTask"));
        Calendar t3 = Calendar.getInstance();
        Calendar t4 = Calendar.getInstance();
        t4.add(Calendar.HOUR_OF_DAY, 2);
        taskManagerController.add(new Appointment("Today Appointment", t3, t4, "Appointment of the today"));

        Calendar t5 = new GregorianCalendar(2015, Calendar.DECEMBER, 6, 15, 31);
        taskManagerController.add(new Task("Today Task", Calendar.getInstance(), "Task of the today"));
        taskManagerController.add(new Task("TestTask", t5, "TestTask"));
        taskManagerController.add(new Task("TestTask2", Calendar.getInstance(), "TestTask"));

        taskManagerController.add(new Task("111", new GregorianCalendar(2015, Calendar.DECEMBER, 15, 17, 0), ""));
        taskManagerController.add(new Task("222", new GregorianCalendar(2015, Calendar.DECEMBER, 15, 17, 0), ""));
        taskManagerController.add(new Task("333", new GregorianCalendar(2015, Calendar.DECEMBER, 17, 18, 15), ""));
        Calendar t12 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 8, 0);
        Calendar t13 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 9, 0);

        taskManagerController.add(new Appointment("12 Dec", t12, t13, "12DecComment"));
        Calendar t14 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 15, 0);
        Calendar t15 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 17, 0);
        taskManagerController.add(new Appointment("12 Dec 2", t14, t15, "12DecComment 2"));

        Calendar t16 = Calendar.getInstance();
        Calendar t17 = Calendar.getInstance();
        t17.add(Calendar.HOUR_OF_DAY, 2);
        taskManagerController.add(new Appointment("2Today Appointment", t16, t17, "Task of the today2"));


        NotificationController notificationController = new NotificationController(taskManagerController);
        TaskManagerView taskManagerView = new TaskManagerView(notificationController);


        JAXB jaxb = new JAXB();
//          jaxb.write(taskManagerModel);

    }
}
