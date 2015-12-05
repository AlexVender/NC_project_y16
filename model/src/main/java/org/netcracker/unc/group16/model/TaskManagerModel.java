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

    private Integer tasksCnt;

    public static final String TASK = "Task";
    public static final String APPOINTMENT = "Appointment";

    public TaskManagerModel() {
        hashMapTasks = new HashMap<>();
        tasksCnt = 0;

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
        notifyObservers();
    }


    public void addTask(Integer id, String title, Calendar time, String description) {

        Task task = new Task(id, title, time, description);
        hashMapTasks.put(id, task);
        notifyObservers();
    }

    public void addTask(String title, Calendar time, String description) {
        tasksCnt++;
        addTask(tasksCnt, title, time, description);
        notifyObservers();
    }


    public void addAppointment(Integer id, String title, Calendar time, Calendar endTime, String description) {
        Task task = new Appointment(id, title, time, endTime, description);
        hashMapTasks.put(id, task);
        notifyObservers();
    }

    public void addAppointment(String title, Calendar time, Calendar endTime, String description) {
        tasksCnt++;
        addAppointment(tasksCnt, title, time, endTime, description);
    }

    public void addTask(Task task) {
        tasksCnt++;

        hashMapTasks.put(tasksCnt, (Task) task.clone());
        notifyObservers();
    }

    public void deleteTask(Integer id) {
        hashMapTasks.remove(id);
        notifyObservers();
    }

    public Task getTask(Integer id) {
        return (Task) hashMapTasks.get(id).clone();
    }

    public Map<Integer, Task> getTasksByDate(String taskType, int year, int month, int day) {
        Map<Integer, Task> tempHashMapTasks = new HashMap<>();
        Calendar dateStart = new GregorianCalendar(year, month, day);
        Calendar dateEnd = new GregorianCalendar(year, month, day + 1);
        dateEnd.add(Calendar.MILLISECOND, -1);

        for (HashMap.Entry<Integer, Task> entry : hashMapTasks.entrySet()) {
            Task value = entry.getValue();
            Calendar time = value.getTime();

            if (value.getClass().getSimpleName().equals(taskType) && time.after(dateStart) && time.before(dateEnd)) {
                tempHashMapTasks.put(entry.getKey(), (Task) value.clone());
            }
        }
        return  tempHashMapTasks;
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


    @Override
    public void notifyObservers(){
        for (Observer observer: observers){
            //Если как-то изменяются эти два поля - все наблюдатели об этом знают
            observer.update(hashMapTasks, tasksCnt);
        }
    }

}
