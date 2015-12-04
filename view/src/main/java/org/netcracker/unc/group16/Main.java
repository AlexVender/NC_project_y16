package org.netcracker.unc.group16;

import org.netcracker.unc.group16.controller.TaskManager;
import org.netcracker.unc.group16.model.*;
import org.netcracker.unc.group16.view.TaskManagerView;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Main {
    public static void main(String[] args) {
        TaskManagerModel taskManagerModel = new TaskManagerModel();

        Calendar ts1 = Calendar.getInstance();
        Calendar te1 = new GregorianCalendar(2015, Calendar.DECEMBER, ts1.get(Calendar.DAY_OF_MONTH), ts1.get(Calendar.HOUR_OF_DAY), 30);
//        taskManagerModel.addAppointment("Test1", ts1, te1, "TestTask");
        taskManagerModel.addTask("Test2", Calendar.getInstance(),"TestTask");
        taskManagerModel.addTask("TestTask", Calendar.getInstance(),"TestTask");
        taskManagerModel.addTask("TestTask", Calendar.getInstance(),"TestTask");

        JAXB jaxb = new JAXB();
      //  jaxb.write(taskManagerModel);

  //      System.out.println (Calendar.getInstance());
        TaskManagerView taskManagerView = new
                TaskManagerView(new NotificatorModel(taskManagerModel));

//        TaskManagerView taskManagerView = new TaskManagerView(taskManagerModel);

    }
}
