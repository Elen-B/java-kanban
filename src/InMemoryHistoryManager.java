import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final ArrayList<Task> historyList;

    public InMemoryHistoryManager() {
        historyList = new ArrayList<>();
    }
    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (historyList.size() == HistoryManager.DEFAULT_HISTORY_SIZE) {
            historyList.removeFirst();
        }
        historyList.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
