public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description, TaskStatus status, int epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "\nSubtask{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description.length=" + this.getDescription().toString().length()  +
                ", status=" + this.getStatus() +
                ", epicId=" + epicId +
                '}';
    }
}
