package org.netcracker.unc.group16;

import java.util.Date;
import java.util.List;

public interface TaskManager {
    void editTask(int id);
    void addTask();
    void deleteTask(int id);
    Task getTask(int id);
    List<Task> getTasks(Date date);
    void load();
    void save();
    void init();
}
