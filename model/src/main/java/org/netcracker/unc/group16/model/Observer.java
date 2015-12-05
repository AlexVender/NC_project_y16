package org.netcracker.unc.group16.model;

import java.util.Map;

/**
 * Created by Ivan on 30.11.2015.
 */
public interface Observer {
    void update (Map<Integer, Task> hashMapTasks, Integer tasksCnt);
}
