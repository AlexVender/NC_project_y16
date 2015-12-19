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


public class Main{
    private TaskManagerModel taskManagerModel;
    private TaskManagerController taskManagerController;
    private NotificationController notificationController;
    public static void main(String[] args) {
        Main main = new Main();
//        main.taskManagerModel = new TaskManagerModel();
        JAXB jaxb = new JAXB();
        main.taskManagerModel = jaxb.read();
        main.taskManagerController = new TaskManagerController(main.taskManagerModel);

/*

        Calendar t1 = new GregorianCalendar(2015, Calendar.DECEMBER, 22, 10, 0);
        Calendar t2 = new GregorianCalendar(2015, Calendar.DECEMBER, 25, 15, 0);
        main.taskManagerController.add(new Appointment("Long Appointment", t1, t2, "Test Appointment"));
        Calendar t3 = Calendar.getInstance();
        Calendar t4 = Calendar.getInstance();
        t4.add(Calendar.HOUR_OF_DAY, 2);
//        taskManagerController.add(new Appointment("Today Appointment", t3, t4, "Appointment of the today"));

        Calendar t5 = new GregorianCalendar(2015, Calendar.DECEMBER, 6, 15, 31);
        main.taskManagerController.add(new Task("Today Task", Calendar.getInstance(), "Task of the today"));
        main.taskManagerController.add(new Task("TestTask", t5, "TestTask"));
        main.taskManagerController.add(new Task("TestTask2", Calendar.getInstance(), "TestTask"));

        main.taskManagerController.add(new Task("111", new GregorianCalendar(2015, Calendar.DECEMBER, 15, 17, 0), ""));
        main.taskManagerController.add(new Task("222", new GregorianCalendar(2015, Calendar.DECEMBER, 15, 17, 0), ""));
        main.taskManagerController.add(new Task("333", new GregorianCalendar(2015, Calendar.DECEMBER, 17, 18, 15), ""));
        Calendar t12 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 8, 0);
        Calendar t13 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 9, 0);

        main.taskManagerController.add(new Appointment("12 Dec", t12, t13, "12DecComment"));
        Calendar t14 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 15, 0);
        Calendar t15 = new GregorianCalendar(2015, Calendar.DECEMBER, 12, 17, 0);
        main.taskManagerController.add(new Appointment("12 Dec 2", t14, t15, "12DecComment 2"));

        Calendar t16 = Calendar.getInstance();
//        System.out.println("Время t16:" + t16.getTime());
        Calendar t17 = Calendar.getInstance();
        t16.add(Calendar.MINUTE, 1);
//        System.out.println("Время t16+30sec:" + t16.getTimeInMillis() / 1000);
        t17.add(Calendar.HOUR_OF_DAY, 2);
        main.taskManagerController.add(new Appointment("2Today Appointment", t16, t17, "Task of the today2"));*/


        main.notificationController = new NotificationController(main.taskManagerController);
        TaskManagerView taskManagerView = new TaskManagerView(main.notificationController);



//          jaxb.write(main.taskManagerModel);

    }
    class MyRunnable implements Runnable{
        @Override
        public void  run(){

        }
    }

}
