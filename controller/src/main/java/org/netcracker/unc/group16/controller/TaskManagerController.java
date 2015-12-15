package org.netcracker.unc.group16.controller;

import org.netcracker.unc.group16.model.Appointment;
import org.netcracker.unc.group16.model.Task;
import org.netcracker.unc.group16.model.TaskManagerModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class TaskManagerController implements TaskManager {
//    private Map<Integer, Task> hashMapTasks = new HashMap<>();
    private TaskManagerModel taskManagerModel;

    public TaskManagerController(TaskManagerModel taskManagerModel) {
        this.taskManagerModel = taskManagerModel;

    }

    public void add(Task task) throws IllegalArgumentException {
        taskManagerModel.add(task);
    }

    public void add(Task ... tasks) throws IllegalArgumentException {
        for (Task task : tasks) {
            add(task);
        }
    }

    public Task get(Integer id) {
        return taskManagerModel.get(id);
    }

    public void edit(Integer id, Task task) throws IllegalArgumentException {
        taskManagerModel.edit(id, task);
    }

    public void remove(Integer id) {
        taskManagerModel.remove(id);
    }

    public List getByDay(Class requiredClass, Calendar date) {
        if (!(requiredClass.equals(Task.class)) && !requiredClass.equals(Appointment.class)) {
            throw new IllegalArgumentException();
        }

        List<Task> result = new ArrayList<>();

        int year  = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day   = date.get(Calendar.DAY_OF_MONTH);
        Calendar dateStart = new GregorianCalendar(year, month, day);
        Calendar dateEnd   = new GregorianCalendar(year, month, day + 1);

        for (int i = 1; i <= taskManagerModel.getTasksCnt(); i++) {
            Task task = taskManagerModel.get(i);
            if (task == null) {
                continue;
            }

            Calendar taskTime = task.getTime();

            if (task.getClass().equals(requiredClass)) {
                if (requiredClass.equals(Task.class)) {
                    if ((taskTime.compareTo(dateStart) >= 0) && (taskTime.compareTo(dateEnd) < 0)) {
                        result.add(task);
                    }
                } else if (requiredClass.equals(Appointment.class)) {
                    Calendar taskEndTime = ((Appointment) task).getEndTime();
                    if ((taskTime.compareTo(dateStart) >= 0) && (taskTime.compareTo(dateEnd) < 0) ||
                            (taskEndTime.compareTo(dateStart) > 0) && (taskEndTime.compareTo(dateEnd) <= 0) ||
                            (taskTime.compareTo(dateStart) < 0) && (taskEndTime.compareTo(dateEnd) >= 0)) {
                        result.add(task);
                    }
                }
            }
        }
        result.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));

        return result;
    }

    public List getAll(Class requiredClass) {
        List<Task> result = new ArrayList<>();

        for (int i = 1; i <= taskManagerModel.getTasksCnt(); i++) {
            Task task = taskManagerModel.get(i);

            if (task != null && task.getClass().equals(requiredClass)) {
                result.add(task);
            }
        }

        result.sort((o1, o2) -> o1.getTime().compareTo(o2.getTime()));

        return result;
    }

    public List getClosestNext(Class requiredClass){
        // Отсортированный список всех тасок
        List<Task> result = getAll(requiredClass);

        // Убираем завершённые
        Calendar currentDate = Calendar.getInstance();
        result.removeIf(task -> task.getTime().compareTo(currentDate) < 0);

        if (result.size() == 0) {
            return null;
        }

        // Убираем все после минимальной
        Calendar minDate = result.get(0).getTime();
        result.removeIf(task -> task.getTime().compareTo(minDate) > 0);

        return result;
    }


    public Integer getTasksCnt() {
        return taskManagerModel.getTasksCnt();
    }

    public void setTitle(Integer id, String title) {
        taskManagerModel.get(id).setTitle(title);
    }

    public void setTime(Integer id, Calendar time) {
        taskManagerModel.get(id).setTime(time);
    }

    public void setEndTime (Integer id, Calendar endTime) {
        Task task = taskManagerModel.get(id);
        if (task instanceof Appointment) {
            Appointment appointment = (Appointment) task;
            appointment.setEndTime(endTime);
        } else {
            throw new IllegalArgumentException("Task by this id is not appointment");
        }
    }

    public void setDescription(Integer id, String description) {
        taskManagerModel.get(id).setDescription(description);
    }

    public TaskManagerModel getTaskManagerModel() {
        return taskManagerModel;
    }

    public void setTaskManagerModel(TaskManagerModel taskManagerModel) {
        this.taskManagerModel = taskManagerModel;
    }
}
