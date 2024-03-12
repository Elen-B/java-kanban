import java.util.List;

public interface TaskManager {
    int getNextTaskId();

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    List<Task> getTaskList();

    List<Epic> getEpicList();

    List<Subtask> getSubtaskList();

    List<Subtask> getSubtaskList(int epicId);

    void deleteTaskList();

    void deleteEpicList();

    void deleteSubTaskList();

    List<Task> getHistory();
}
