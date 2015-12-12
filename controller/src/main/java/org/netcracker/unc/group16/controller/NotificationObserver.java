package org.netcracker.unc.group16.controller;

import org.netcracker.unc.group16.model.Task;

import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 07.12.2015.
 */
public interface NotificationObserver {
    void updateFromNotification(Map<Integer, Task> currentTasks);
}
