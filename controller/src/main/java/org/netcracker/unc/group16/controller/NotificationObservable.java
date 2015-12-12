package org.netcracker.unc.group16.controller;

import org.netcracker.unc.group16.model.Task;

import java.util.List;
import java.util.Map;

/**
 * Created by Ivan on 07.12.2015.
 */
public interface NotificationObservable {
    void registerObserver(NotificationObserver o);
    void removeObserver(NotificationObserver o);
    void notifyObservers(Map<Integer, Task> currentTasks);
}
