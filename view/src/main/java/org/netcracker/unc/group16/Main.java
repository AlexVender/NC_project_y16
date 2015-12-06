package org.netcracker.unc.group16;

import org.netcracker.unc.group16.controller.NotificatorModel;
import org.netcracker.unc.group16.model.*;
import org.netcracker.unc.group16.view.TaskManagerView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Main {
    public static void main(String[] args) {
        TaskManagerModel taskManagerModel = new TaskManagerModel();

        Calendar t1 = new GregorianCalendar(2015, Calendar.DECEMBER, 4, 10, 30);
        Calendar t2 = new GregorianCalendar(2015, Calendar.DECEMBER, 4, 12, 0);
        taskManagerModel.addAppointment("Test1", t1, t2, "TestTask");
        taskManagerModel.addTask("Test2", Calendar.getInstance(),"TestTask");
        taskManagerModel.addTask("TestTask", Calendar.getInstance(),"TestTask");
        taskManagerModel.addTask("TestTask", Calendar.getInstance(),"TestTask");

        JAXB jaxb = new JAXB();
        //  jaxb.write(taskManagerModel);

        TaskManagerView taskManagerView = new TaskManagerView(new NotificatorModel(taskManagerModel));

//        TaskManagerView taskManagerView = new TaskManagerView(taskManagerModel);

    }
}
