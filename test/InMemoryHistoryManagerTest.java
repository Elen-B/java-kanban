import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void shouldAddTaskToHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task(1, "Задача 1", "", TaskStatus.NEW);
        historyManager.add(task);

        assertNotNull(historyManager.getHistory(), "История пустая");
        assertEquals(1, historyManager.getHistory().size(), "Задача не добавлена в историю просмотра");
    }

    @Test
    void shouldSaveOtherTaskObjectToHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        String name = "Задача 1";
        String description = "Описание";
        TaskStatus status = TaskStatus.NEW;
        Task task = new Task(1, name, description, status);
        historyManager.add(task);
        task.setName("Модифицированная задача");
        task.setDescription(null);
        task.setStatus(TaskStatus.DONE);
        List<Task> history = historyManager.getHistory();

        assertNotNull(history, "История должна содержать 1 элемент");
        String nameInHist = history.getFirst().getName();
        String descriptionInHist = history.getFirst().getDescription();
        TaskStatus statusInHist = history.getFirst().getStatus();

        assertEquals(name, nameInHist, "Изменение данных (название) задачи не должно менять данных, сохраненных в истории по этой задаче");
        assertEquals(description, descriptionInHist, "Изменение данных (описание) задачи не должно менять данных, сохраненных в истории по этой задаче");
        assertEquals(status, statusInHist, "Изменение данных (статус) задачи не должно менять данных, сохраненных в истории по этой задаче");
    }
}