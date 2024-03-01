import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private final HashMap<Integer, Task> taskList;
    private final HashMap<Integer, Epic> epicList;
    private final HashMap<Integer, Subtask> subtaskList;
    private int nextTaskId;

    public TaskManager() {
        this.taskList = new HashMap<>();
        this.epicList = new HashMap<>();
        this.subtaskList = new HashMap<>();
        this.nextTaskId = 1;
    }

    public int getNextTaskId() {
        return nextTaskId++;
    }

    public void addTask(Task task) {
        if (task.getId() == 0) {
            task.setId(getNextTaskId());
        }
        taskList.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        if (epic.getId() == 0) {
            epic.setId(getNextTaskId());
        }
        epicList.put(epic.getId(), epic);
        if (epic.getSubtaskList() != null) {
            for (Subtask subtask: epic.getSubtaskList()) {
                subtaskList.put(subtask.getId(), subtask);
            }
        }
    }

    public void addSubtask(Subtask subtask) {
        if (!epicList.containsKey(subtask.getEpicId())) {
            return;
        }

        if (subtask.getId() == 0) {
            subtask.setId(getNextTaskId());
        }
        Epic epic = epicList.get(subtask.getEpicId());
        epic.addSubtask(subtask);
        updateEpic(epic);
    }

    public void updateTask(Task task) {
        taskList.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
        syncSubtaskListWithEpic(epic);
    }

    public void updateSubtask(Subtask subtask) {
        int oldEpicId = subtaskList.get(subtask.getId()).getEpicId();
        if (oldEpicId != subtask.getEpicId()) {
            Epic oldEpic = epicList.get(oldEpicId);
            oldEpic.deleteSubtask(subtask);
            syncSubtaskListWithEpic(oldEpic);
        }
        Epic epic = epicList.get(subtask.getEpicId());
        epic.updateSubtask(subtask);
        syncSubtaskListWithEpic(epic);
    }

    private void syncSubtaskListWithEpic(Epic epic) {
        if (epic.getSubtaskList() == null) {
            subtaskList.entrySet().removeIf(item -> item.getValue().getEpicId() == epic.getId());
        } else {
            subtaskList.entrySet().removeIf(item -> item.getValue().getEpicId() == epic.getId()
                    && !epic.getSubtaskList().contains(item.getValue()));
            for (Subtask subtask: epic.getSubtaskList()) {
                subtaskList.put(subtask.getId(), subtask);
            }
        }
    }

    public void deleteTask(int id) {
        taskList.remove(id);
    }

    public void deleteEpic(int id) {
        subtaskList.entrySet().removeIf(item -> item.getValue().getEpicId() == id);
        epicList.remove(id);
    }

    public void deleteSubtask(int id) {
        if (!subtaskList.containsKey(id)) {
            return;
        }
        Epic epic = getEpic(getSubtask(id).getEpicId());
        epic.deleteSubtask(subtaskList.get(id));
        syncSubtaskListWithEpic(epic);
    }

    public Task getTask(int id) {
        return taskList.get(id);
    }

    public Epic getEpic(int id) {
        return epicList.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtaskList.get(id);
    }

    public List<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    public List<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    public List<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }

    public List<Subtask> getSubtaskList(int epicId) {
        return epicList.containsKey(epicId) ? epicList.get(epicId).getSubtaskList() : new ArrayList<>();
    }

    public void deleteTaskList() {
        taskList.clear();
    }

    public void deleteEpicList() {
        subtaskList.clear();
        epicList.clear();
    }

    public void deleteSubTaskList() {
        for (Epic epic: epicList.values()) {
            epic.deleteSubtaskList();
        }
        subtaskList.clear();
    }

}
