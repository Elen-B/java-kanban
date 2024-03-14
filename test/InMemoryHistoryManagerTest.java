import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void shouldAddTaskToHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task(1, "Задача 1", "", TaskStatus.NEW);
        historyManager.add(task);

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
        String nameInHist = historyManager.getHistory().getFirst().getName();
        String descriptionInHist = historyManager.getHistory().getFirst().getDescription();
        TaskStatus statusInHist = historyManager.getHistory().getFirst().getStatus();

        assertEquals(name, nameInHist, "Изменение данных (название) задачи не должно менять данных, сохраненных в истории по этой задаче");
        assertEquals(description, descriptionInHist, "Изменение данных (описание) задачи не должно менять данных, сохраненных в истории по этой задаче");
        assertEquals(status, statusInHist, "Изменение данных (статус) задачи не должно менять данных, сохраненных в истории по этой задаче");
    }
}