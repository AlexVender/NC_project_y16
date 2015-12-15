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

    public NotificationController(TaskManagerController taskManagerController){
        this.setTaskManagerController(taskManagerController);
        this.setTaskManagerModel(taskManagerController.getTaskManagerModel());
        this.setCurrentTasks(getTasksForNotification());

        //Если таски для нотификатора есть
        if (!(getCurrentTasks().isEmpty())){
            initNC();
        }/*
        while (!(getCurrentTasks().isEmpty())){
            initNC();
        }*/
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledFuture<?> futureTask;
    private Runnable myTask;
/*
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Your executor, you should instanciate it once for all
        scheduledExecutorService = Executors.newScheduledThreadPool(5);

        // Since your task won't change during runtime, you can instanciate it too once for all
        myTask = new Runnable()
        {
            @Override
            public void run()
            {
                // Your code to run periodically
            }
        };
    }


    public void changeReadInterval(long time)
    {
        if(time > 0)
        {
            if (futureTask != null)
            {
                futureTask.cancel(true);
            }

            futureTask = scheduledExecutorService.scheduleAtFixedRate(myTask, 0, time, TimeUnit.SECONDS);
        }
    }*/

    public void initNC(){

//        System.out.println("Sysdate:" + Calendar.getInstance().getTimeInMillis() / 1000);
//        System.out.println("В нотификаторе лежат следующие таски:");
        for (HashMap.Entry<Integer, Task> entry : getCurrentTasks().entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();
//            System.out.println("ID:" + key + ";unix timestamp время:" + (value.getTime().getTimeInMillis() / 1000));

        }
        ScheduledFuture scheduledFuture = scheduler.schedule(() -> notifyObservers(currentTasks), getTimeBeforeExecution() , TimeUnit.SECONDS);
//        System.out.println(scheduledFuture.isCancelled());


//        scheduledFuture.cancel(true);
//        System.out.println(scheduledFuture.isCancelled());


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
//                    System.out.println("Задали min значение " + valueMS / 1000);
                    min = valueMS;
                }
//                System.out.println("Зашли с Appointment Comment:" + value.getDescription() + ";ID:" + key + ";Time:" + value.getTime().getTimeInMillis()/1000 +
//                        ";Time2:" + value.getTime().getTime().getTime()/1000);

                if ((valueMS > sysdate) && (valueMS > min) && (min < sysdate)){
//                    System.out.println("Поменяли минимальное значение с " + min/1000 + " на " + valueMS/1000);
                    min = valueMS;
                }
                else if ((valueMS > sysdate) && (valueMS < min)){
//                    System.out.println("Поменяли минимальное значение с " + min/1000 + " на " + valueMS/1000);
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
            System.out.println("Время до выполнения нотификатора:"  + (int) (min.getValue().getTime().getTimeInMillis() - cal.getTimeInMillis()) / 1000 + " секунд");
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
    public void notifyObservers(Map<Integer, Task> currentTasks){
        for (NotificationObserver notificationObserver: notificationObservers){
            notificationObserver.updateFromNotification(currentTasks);
        }
    }


}
