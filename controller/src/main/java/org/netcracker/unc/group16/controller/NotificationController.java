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

public class NotificationController implements org.netcracker.unc.group16.model.Observer {
    private Map<Integer, Task> currentTasks;

    private TaskManagerModel taskManagerModel;

    private TaskManagerController taskManagerController;

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


//    public NotificationController(TaskManagerModel taskManagerModel){
//        this.taskManagerModel = taskManagerModel;
//        Регистрируем наблюдателя
//        taskManagerModel.registerObserver(this);
//    }

    public NotificationController(TaskManagerController taskManagerController){
        this.setTaskManagerController(taskManagerController);
        this.setTaskManagerModel(taskManagerController.getTaskManagerModel());
        this.setCurrentTasks(getTasksForNotification());
        initNC();
    }

    public void initNC(){

        System.out.println("В нотификаторе лежат следующие таски:");
        for (HashMap.Entry<Integer, Task> entry : getCurrentTasks().entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();
            System.out.println("ID:" + key + ";unix timestamp время:" + (value.getTime().getTimeInMillis() / 1000));

        }
        ScheduledFuture scheduledFuture = scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("Окно появилось");
                System.out.println("postpone - напомнить через 10 секунд\n" +
                        "dismiss - отклонить, перейти к следующей такске\n" +
                        "close закрыть окно = postpone");
            }
        }, getTimeBeforeExecution(), TimeUnit.SECONDS);

        if (dismiss()){

        }
    }

    public void notificate(){

    }

    public void postpone(){

    }
    public boolean dismiss(){
        return true;
    }

    public void getLastTask(){

    }

    public Map<Integer, Task> getCurrentTasks() {
        return currentTasks;
    }

    public void setCurrentTasks(Map<Integer, Task> currentTasks) {
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
    private Map<Integer, Task> getTasksForNotification(){
        Map<Integer, Task> tempHashMapTasks = new HashMap<>();

        //Ищем таску с минимальной датой
        Map.Entry<Integer, Task> min = null;
        for (HashMap.Entry<Integer, Task> entry : getTaskManagerModel().getHashMapTasks().entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();
            if (min == null || min.getValue().getTime().getTimeInMillis() > value.getTime().getTimeInMillis()){
                min = entry;
            }

        }

        //У таски время > sysdate
        if(min.getValue().getTime().getTimeInMillis() > Calendar.getInstance().getTimeInMillis()){
            tempHashMapTasks.put(min.getKey(), min.getValue());
        }

        //Добавляем остальные таски
        if (min != null){
            for (HashMap.Entry<Integer, Task> entry : getTaskManagerModel().getHashMapTasks().entrySet()) {
                Integer key = entry.getKey();
                Task value = entry.getValue();

                if (!(entry.equals(min)) //Повторяющаяся таска
                        &&(value.getTime().getTimeInMillis() == min.getValue().getTime().getTimeInMillis())){
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

    public int getTimeBeforeExecution(){
        Calendar cal = Calendar.getInstance();
        Map.Entry<Integer, Task> min = null;
        for (HashMap.Entry<Integer, Task> entry : getCurrentTasks().entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();



            if (min == null || min.getValue().getTime().getTimeInMillis() > value.getTime().getTimeInMillis()){
                min = entry;            }


        }
        System.out.println("Нотификатор сработает через " + ((int) (min.getValue().getTime().getTimeInMillis() - cal.getTimeInMillis()) / 1000) + " секунд.");
        return (int) (min.getValue().getTime().getTimeInMillis() - cal.getTimeInMillis()) / 1000;
    }

    public  long tempFunction(){
        Calendar cal = Calendar.getInstance();
//        System.out.println(cal.getTimeInMillis()/1000 + 5);
        return cal.getTimeInMillis() / 1000 + 5;
    }

    public TaskManagerController getTaskManagerController() {
        return taskManagerController;
    }

    public void setTaskManagerController(TaskManagerController taskManagerController) {
        this.taskManagerController = taskManagerController;
    }
}
