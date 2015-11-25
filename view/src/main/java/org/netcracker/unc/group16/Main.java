package org.netcracker.unc.group16;

import org.netcracker.unc.group16.controller.TaskManager;
import org.netcracker.unc.group16.model.JAXB;
import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.model.TaskManagerModel;
import org.netcracker.unc.group16.view.TaskManagerView;

import java.util.*;

public class Main {
    public static void main(String[] args) {
      //  System.out.println("Hello world !");
        //Test comment

      //  TaskManagerView taskManagerView = new TaskManagerView(new TaskManagerModel());

        TaskManagerModel taskManagerModel = new TaskManagerModel();
        taskManagerModel.setHashMapTasks(new HashMap<Integer, Task>());
        Task task1 = new Task(1, "Test1", Calendar.getInstance(), "Comment1");
        Task task2 = new Task(2, "Test2", Calendar.getInstance(), "Comment2");

        taskManagerModel.getHashMapTasks().put(1, task1);
        taskManagerModel.getHashMapTasks().put(2, task2);

        JAXB jaxb = new JAXB();
        jaxb.write(taskManagerModel);
        jaxb.read();

    }
}
