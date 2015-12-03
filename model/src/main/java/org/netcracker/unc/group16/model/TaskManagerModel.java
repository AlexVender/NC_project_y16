package org.netcracker.unc.group16.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;


@XmlRootElement(name="tasks")
//@XmlAccessorType(XmlAccessType.FIELD)
public class TaskManagerModel implements Observable {


    private Map<Integer, Task> hashMapTasks;

    @XmlTransient
    private List<Observer> observers;

    private TreeSet<Integer> availableIDs;

    public TaskManagerModel() {
        hashMapTasks = new HashMap<>();
        availableIDs = new TreeSet<>();
        // Стартовый ID тасок
        availableIDs.add(1);
//        Thread myThready = new Thread(new Runnable()
//        {
//            public void run() //Этот метод будет выполняться в побочном потоке
//            {
//                NotificatorModel notificaticatorModel = new NotificatorModel();
//                System.out.println("Привет из побочного потока!");
//            }
//        });
//        myThready.start();



    }

    public void editTask(Integer id, String title, Calendar time, String description) {
        Task elm = hashMapTasks.get(id);
        elm.setTitle(title);
        elm.setTime(time);
        elm.setDescription(description);
        for (Observer observer: observers){
            observer.update(elm);
        }
    }

    public void addTask(String title, Calendar time, String description) {
        Integer id = availableIDs.pollFirst();
        if (availableIDs.isEmpty()) {
            availableIDs.add(id + 1);
        }

        Task task = new Task(id, title, time, description);
        hashMapTasks.put(id, task);
    }

    public void addTask(Task task) {
        Integer id = availableIDs.pollFirst();
        if (availableIDs.isEmpty()) {
            availableIDs.add(id + 1);
        }

        hashMapTasks.put(id, task);
    }

    public void deleteTask(Integer id) {
        hashMapTasks.remove(id);
        availableIDs.add(id);
    }

    public Task getTask(Integer id) {
        return hashMapTasks.get(id);
    }

    public Map<Integer, Task> getTasksByDate(int year, int month, int day) {
        Map<Integer, Task> tempHashMapTasks = new HashMap<>();
        Calendar dateStart = new GregorianCalendar(year, month, day);
        Calendar dateEnd = new GregorianCalendar(year, month, day + 1);
        dateEnd.add(Calendar.MILLISECOND, -1);

        for (HashMap.Entry<Integer, Task> entry : hashMapTasks.entrySet()) {
            Integer key = entry.getKey();
            Task value = entry.getValue();

            if (value.getTime().after(dateStart) && value.getTime().before(dateEnd)){
                tempHashMapTasks.put(key, value);
            }
        }
        return  tempHashMapTasks;
    }





    public Task getTestTask(){
        Task testTask = new Task(1, "TestTask", Calendar.getInstance(), "TestTask");
        return testTask;
    }
    public void load(){

    }

    public void save(){

    }

    public void init(){

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
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

}
