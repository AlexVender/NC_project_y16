import java.util.Date;

/**
 * Created by Ivan.Chikhanov on 08.11.2015.
 */
public interface TaskManager {
    void editTask(int id);
    void addTask();
    void deleteTask(int id);
    Task getTask(int id);
    List<Task> getTasks(Date date);
    void load();
    void save();
    void init();
}
