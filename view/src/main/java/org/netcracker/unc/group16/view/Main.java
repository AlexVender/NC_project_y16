package org.netcracker.unc.group16.view;

import org.netcracker.unc.group16.model.JAXB;
import org.netcracker.unc.group16.model.TaskManagerModel;

import java.util.Calendar;

public class Main {
    public static void main(String[] args) {
        TaskManagerModel taskManagerModel = new TaskManagerModel();

        taskManagerModel.addTask("Test1", Calendar.getInstance(),"TestTask");
        taskManagerModel.addTask("Test2", Calendar.getInstance(),"TestTask");
        taskManagerModel.addTask("TestTask", Calendar.getInstance(),"TestTask");
        taskManagerModel.addTask("TestTask", Calendar.getInstance(),"TestTask");

        JAXB jaxb = new JAXB();
//        jaxb.write(taskManagerModel);
        TaskManagerView taskManagerView = new TaskManagerView(taskManagerModel);

    }
}
