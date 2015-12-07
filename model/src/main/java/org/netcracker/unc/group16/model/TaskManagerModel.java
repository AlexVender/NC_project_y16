package org.netcracker.unc.group16.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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


    public void add(Task task) throws IllegalArgumentException {
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

    public Task get(Integer id) {
        Task result = hashMapTasks.get(id);
        return (result != null) ? (Task) result.clone() : null;
    }

    public void edit(Integer id, Task task) throws IllegalArgumentException {
        if (hashMapTasks.get(id) == null) {
            throw new IllegalArgumentException("Could not find task with this ID");
        }
        Task result = (Task) task.clone();
        result.setId(id);

        hashMapTasks.put(id, result);
        notifyObservers();
    }

    public void remove(Integer id) {
        hashMapTasks.remove(id);
        notifyObservers();
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
