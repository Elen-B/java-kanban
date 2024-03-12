import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> taskList;
    private final HashMap<Integer, Epic> epicList;
    private final HashMap<Integer, Subtask> subtaskList;

    private final ArrayList<Task> historyList;
    private int nextTaskId;

    public InMemoryTaskManager() {
        this.taskList = new HashMap<>();
        this.epicList = new HashMap<>();
        this.subtaskList = new HashMap<>();
        this.historyList = new ArrayList<>();
        this.nextTaskId = 1;
    }

    @Override
    public int getNextTaskId() {
        return nextTaskId++;
    }

    @Override
    public void addTask(Task task) {
        if (task.getId() == 0) {
            task.setId(getNextTaskId());
        }
        taskList.put(task.getId(), task);
    }

    @Override
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

    @Override
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

    @Override
    public void updateTask(Task task) {
        taskList.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epicList.put(epic.getId(), epic);
        syncSubtaskListWithEpic(epic);
    }

    @Override
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

    @Override
    public void deleteTask(int id) {
        taskList.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        subtaskList.entrySet().removeIf(item -> item.getValue().getEpicId() == id);
        epicList.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        if (!subtaskList.containsKey(id)) {
            return;
        }
        Epic epic = epicList.get(subtaskList.get(id).getEpicId());
        epic.deleteSubtask(subtaskList.get(id));
        syncSubtaskListWithEpic(epic);
    }

    @Override
    public Task getTask(int id) {
        Task task = taskList.get(id);
        addTaskToHistory(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epicList.get(id);
        addTaskToHistory(epic);
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        return subtaskList.get(id);
    }

    @Override
    public List<Task> getTaskList() {
        return new ArrayList<>(taskList.values());
    }

    @Override
    public List<Epic> getEpicList() {
        return new ArrayList<>(epicList.values());
    }

    @Override
    public List<Subtask> getSubtaskList() {
        return new ArrayList<>(subtaskList.values());
    }

    @Override
    public List<Subtask> getSubtaskList(int epicId) {
        return epicList.containsKey(epicId) ? epicList.get(epicId).getSubtaskList() : new ArrayList<>();
    }

    @Override
    public void deleteTaskList() {
        taskList.clear();
    }

    @Override
    public void deleteEpicList() {
        subtaskList.clear();
        epicList.clear();
    }

    @Override
    public void deleteSubTaskList() {
        for (Epic epic: epicList.values()) {
            epic.deleteSubtaskList();
        }
        subtaskList.clear();
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }

    private void addTaskToHistory(Task task) {
        if (task == null) {
            return;
        }
        if (historyList.size() == TaskManager.DEFAULT_HISTORY_SIZE) {
            historyList.removeFirst();
        }
        historyList.add(task);
    }

}
