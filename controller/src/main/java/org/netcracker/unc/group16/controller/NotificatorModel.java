/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */

package org.netcracker.unc.group16.controller;


import org.netcracker.unc.group16.model.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NotificatorModel implements org.netcracker.unc.group16.model.Observer {
    private List<Task> currentTasks;

    private TaskManagerModel taskManagerModel;

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    ScheduledFuture scheduledFuture = scheduler.schedule(new Runnable() {
        @Override
        public void run() {
            System.out.println("Executed!");
        }
    }, tempFunction(), TimeUnit.SECONDS);



    public NotificatorModel(TaskManagerModel taskManagerModel){
        this.taskManagerModel = taskManagerModel;
        //Регистрируем наблюдателя
        taskManagerModel.registerObserver(this);
    }

    public void notificate(){

    }

    public void postpone(){

    }
    public void dismiss(){

    }

    public void getLastTask(){

    }

    public List<Task> getCurrentTasks() {
        return currentTasks;
    }

    public void setCurrentTasks(List<Task> currentTasks) {
        this.currentTasks = currentTasks;
    }

    public TaskManagerModel getTaskManagerModel(){
        return this.taskManagerModel;
    }

    public void setTaskManagerModel(TaskManagerModel taskManagerModel){
        this.taskManagerModel = taskManagerModel;
    }

    //Загружаем в нотификатор ближайшую таску
    //Причем если в одно время несколько тасок то возвращаем их
    private Map<Integer, Task> getTasksForNotificator(){
        Map<Integer, Task> tempHashMapTasks = new HashMap<>();

        //Ищем таску с минимальной датой
        Map.Entry<Integer, Task> min = null;
        for (HashMap.Entry<Integer, Task> entry : taskManagerModel.getHashMapTasks().entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();

            if (min == null || min.getValue().getTime().getTimeInMillis() > value.getTime().getTimeInMillis()){
                min = entry;
            }

        }
        tempHashMapTasks.put(min.getKey(), min.getValue());

        //Добавляем остальные таски
        if (min != null){
            for (HashMap.Entry<Integer, Task> entry : taskManagerModel.getHashMapTasks().entrySet()) {
                Integer key = entry.getKey();
                Task value = entry.getValue();

                if (value.getTime().getTimeInMillis() == min.getValue().getTime().getTimeInMillis()){
                    tempHashMapTasks.put(key, value);
                }
            }
        }
        return  tempHashMapTasks;
    }

    @Override
    public void update(Map<Integer, Task> hashMapTasks, Integer tasksCnt) {
        //Здесь будет код с апдейтом
    }

//    public long getTimeBeforExecution(){
//        Calendar cal = Calendar.getInstance();
//        return currentTasks.getTime().getTimeInMillis() - cal.getTimeInMillis();
//    }

    public  long tempFunction(){
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTimeInMillis()/1000 + 5);
        return cal.getTimeInMillis() / 1000 + 5;
    }
}
