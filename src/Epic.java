import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{
    private final ArrayList<Subtask> subtaskList;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskList = new ArrayList<>();
    }

    public Epic(int id, String name, String description, ArrayList<Subtask> subtaskList) {
        super(id, name, description, getStatus(subtaskList));
        this.subtaskList = subtaskList;
    }

    public void deleteSubtaskList() {
        subtaskList.clear();
        this.setStatus(Epic.getStatus(subtaskList));
    }

    public void deleteSubtask(Subtask subtask) {
        subtaskList.remove(subtask);
        this.setStatus(Epic.getStatus(subtaskList));
    }

    public void addSubtask(Subtask subtask) {
        if (!subtaskList.contains(subtask)) {
            subtaskList.add(subtask);
            this.setStatus(Epic.getStatus(subtaskList));
        }
    }

    public void updateSubtask(Subtask subtask) {
        subtaskList.removeIf(item -> item.equals(subtask));
        subtaskList.add(subtask);
        this.setStatus(Epic.getStatus(subtaskList));
    }

    public ArrayList<Subtask> getSubtaskList() {
        return subtaskList;
    }

    // если статусы саб-тасков одинаковы, то статус эпика соответствует статусу сабтасков
    // если есть саб-таски с разными статусами, то эпик в работе
    private static TaskStatus getStatus(List<Subtask> subtaskList) {
        if (subtaskList == null || subtaskList.isEmpty()) {
            return TaskStatus.NEW;
        }

        TaskStatus status = subtaskList.getFirst().getStatus();
        for (Subtask subtask: subtaskList) {
            if (subtask.getStatus() != status) {
                return TaskStatus.IN_PROGRESS;
            }
        }

        return status;
    }

    @Override
    public String toString() {
        return "\nEpic{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description.length=" + this.getDescription().toString().length()  +
                ", status=" + this.getStatus() +
                ", subtaskList=" + subtaskList +
                '}';
    }
}
