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

public class NotificationController implements org.netcracker.unc.group16.model.Observer, NotificationObservable {
    private Map<Integer, Task> currentTasks;

    private List<NotificationObserver> notificationObservers = new ArrayList<>();

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

        //Если таски для нотификатора есть
        if (!(getCurrentTasks().isEmpty())){
            initNC();
        }
    }

    public void initNC(){

      //  System.out.println("В нотификаторе лежат следующие таски:");
        for (HashMap.Entry<Integer, Task> entry : getCurrentTasks().entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();
          //  System.out.println("ID:" + key + ";unix timestamp время:" + (value.getTime().getTimeInMillis() / 1000));

        }
        ScheduledFuture scheduledFuture = scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                notifyObservers();

            }
        }, getTimeBeforeExecution(), TimeUnit.SECONDS);
        if (scheduledFuture.isDone()){
            initNC();
        }

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

        //Ищем таску с минимальной датой и датой > sysdate
        long min = 0;
        for (HashMap.Entry<Integer, Task> entry : getTaskManagerModel().getHashMapTasks().entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();
            long valueMS = value.getTime().getTimeInMillis();
            long sysdate = Calendar.getInstance().getTimeInMillis();



            if(value.getClass().getSimpleName().equals("Appointment")){// Только для встреч
                if (min == 0) {
                    min = valueMS;
                }

                if ((valueMS > sysdate) && (valueMS > min) && (min < sysdate)){
                    min = valueMS;
                }
                else if ((valueMS > sysdate) && (valueMS < min)){
                        min = valueMS;
                }
            }

        }

        //Добавляем таски
        if (min != 0){
            for (HashMap.Entry<Integer, Task> entry : getTaskManagerModel().getHashMapTasks().entrySet()) {
                Integer key = entry.getKey();
                Task value = entry.getValue();
                long valueMS = value.getTime().getTimeInMillis();

                if (valueMS == min){
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

          //  System.out.println("ID:" + entry.getKey() + ";Time:" + entry.getValue().getTime().getTimeInMillis());



            if (min == null || min.getValue().getTime().getTimeInMillis() > value.getTime().getTimeInMillis()){
                min = entry;
            }


        }
        try{
         //   System.out.println("Нотификатор сработает через " + ((int) (min.getValue().getTime().getTimeInMillis() - cal.getTimeInMillis()) / 1000) + " секунд.");


        }
        catch (NullPointerException e){

        }

        if (min == null){
            return 0;
        }
        else{
            return (int) (min.getValue().getTime().getTimeInMillis() - cal.getTimeInMillis()) / 1000;

        }
    }


    public TaskManagerController getTaskManagerController() {
        return taskManagerController;
    }

    public void setTaskManagerController(TaskManagerController taskManagerController) {
        this.taskManagerController = taskManagerController;
    }

    @Override
    public void registerObserver(NotificationObserver o){
        notificationObservers.add(o);
    }
    @Override
    public void removeObserver(NotificationObserver o){
        notificationObservers.remove(o);
    }
    @Override
    public void notifyObservers(){
        for (NotificationObserver notificationObserver: notificationObservers){
            notificationObserver.updateFromNotification();
        }
    }
}
