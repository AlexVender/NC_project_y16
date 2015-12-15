package org.netcracker.unc.group16.controller;

import org.junit.Before;
import org.junit.Test;
import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.model.TaskManagerModel;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;


public class TaskManagerControllerTest {
    TaskManagerModel taskManagerModel;
    TaskManagerController controller;
    
    @Before
    public void setUp() throws Exception {
        taskManagerModel = new TaskManagerModel();
        controller = new TaskManagerController(taskManagerModel);
    }
    
    @Test
    public void testAdd() throws Exception {
        Calendar t1 = Calendar.getInstance();
        Task task1 = new Task("", t1, "");
        controller.add(task1);
        assertEquals(controller.getTasksCnt(), new Integer(1));

        controller.add(task1);
        assertEquals(controller.getTasksCnt(), new Integer(1));

        Calendar t2 = Calendar.getInstance();
        Task task2 = new Task("", t2, "");
        controller.add(task2);
        assertEquals(controller.getTasksCnt(), new Integer(2));
    }
}