import java.util.List;

public interface HistoryManager {
    int DEFAULT_HISTORY_SIZE = 10;

    void add(Task task);

    List<Task> getHistory();
}
