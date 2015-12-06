package org.netcracker.unc.group16.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.*;
import java.util.*;
import java.util.List;


@XmlRootElement(name="tasks")
//@XmlAccessorType(XmlAccessType.FIELD)
public class TaskManagerModel implements Observable {
    private Map<Integer, Task> hashMapTasks;

    @XmlTransient
    private List<Observer> observers;

    private Integer tasksCnt;

    public TaskManagerModel() {
        hashMapTasks = new HashMap<>();
        tasksCnt = 0;
    }

    public void editTask(Integer id, String title, Calendar time, String description) {
        Task elm = hashMapTasks.get(id);
        elm.setTitle(title);
        elm.setTime(time);
        elm.setDescription(description);
        notifyObservers();
    }


    public void addTask(Integer id, String title, Calendar time, String description) throws IllegalArgumentException{
        if (id == null || id == 0) {
            id = ++tasksCnt;
        } else if (id < 0) {
            throw new IllegalArgumentException("id should be positive");
        }

        Task task = new Task(id, title, time, description);
        hashMapTasks.put(id, task);
        //notifyObservers();
    }

    public void addTask(String title, Calendar time, String description) {
        addTask(null, title, time, description);
        //notifyObservers();
    }


    public void addAppointment(Integer id, String title, Calendar time, Calendar endTime, String description) throws IllegalArgumentException {
        if (id == null || id == 0) {
            id = ++tasksCnt;
        } else if (id < 0) {
            throw new IllegalArgumentException("id should be positive");
        }

        Task task = new Appointment(id, title, time, endTime, description);
        hashMapTasks.put(id, task);
     //   notifyObservers();
    }

    public void addAppointment(String title, Calendar time, Calendar endTime, String description) {
        addAppointment(null, title, time, endTime, description);
    }

    public void addTask(Task task) throws IllegalArgumentException {
        Integer id = task.getId();
        if (task.id == null || task.id == 0) {
            id = ++tasksCnt;
            task.setId(id);
        } else if (task.id < 0) {
            throw new IllegalArgumentException("Task id should be positive");
        }
    
        hashMapTasks.put(id, (Task) task.clone());
        notifyObservers();
    }

    public void deleteTask(Integer id) {
        hashMapTasks.remove(id);
        notifyObservers();
    }

    public Task getTask(Integer id) {
        Task result = hashMapTasks.get(id);
        return (result != null) ? (Task) result.clone() : null;
    }

    public Map<Integer, Task> getTasksByDate(Class taskClass, Calendar date) {
        Map<Integer, Task> tempHashMapTasks = new HashMap<>();
        Calendar dateStart = (Calendar) date.clone();
        Calendar dateEnd = (Calendar) date.clone();
        dateEnd.add(Calendar.DAY_OF_MONTH, 1);
        dateEnd.add(Calendar.MILLISECOND, -1);

        for (HashMap.Entry<Integer, Task> entry : hashMapTasks.entrySet()) {
            Task value = entry.getValue();
            Calendar time = value.getTime();

            if (value.getClass() == taskClass &&
                    time.after(dateStart) && time.before(dateEnd)) {
                tempHashMapTasks.put(entry.getKey(), (Task) value.clone());
            }
        }
        return  tempHashMapTasks;
    }

    public Integer getTasksCnt() {
        return tasksCnt;
    }

    public void setTasksCnt(Integer tasksCnt) {
        this.tasksCnt = tasksCnt;
    }

    //  @XmlJavaTypeAdapter(value = HashMapAdapter.class)
    public Map<Integer, Task> getHashMapTasks() {
        return hashMapTasks;
    }

    public void setHashMapTasks(Map<Integer, Task> hashMapTasks) {
        this.hashMapTasks = hashMapTasks;
    }

    @Override
    public void registerObserver(Observer o) {
//        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }


    @Override
    public void notifyObservers(){
//        if (!observers.isEmpty()){ //Временно
//            for (Observer observer: observers){
//                Если как-то изменяются эти два поля - все наблюдатели об этом знают
//                observer.update(hashMapTasks, tasksCnt);
//
//        }}
    }

}
