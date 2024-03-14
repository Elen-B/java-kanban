import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void shouldBeEqualedById() {
        int id = 1;
        String name1 = "Подзадача 1";
        String description = "Описание подзадачи 1";
        Subtask subtask1 = new Subtask(id, name1, description, TaskStatus.NEW, 1);

        String name2 = "Другая подзадача";
        Subtask subtask2 = new Subtask(id, name2, description, TaskStatus.DONE, 2);

        id = 2;
        Subtask subtask3 = new Subtask(id, name1, description, TaskStatus.NEW, 1);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковыми ИД должны быть равны");
        assertNotEquals(subtask1, subtask3, "Подзадачи с разными ИД должны быть не равны");
    }

    @Test
    void shouldSetNewStatus() {
        int id = 1;
        String name1 = "Подзадача 1";
        String description = "Описание подзадачи 1";
        Subtask subtask1 = new Subtask(id, name1, description, TaskStatus.NEW, 1);

        TaskStatus status = subtask1.getStatus();
        assertEquals(TaskStatus.NEW, status, "При создании подзадачи со статусом NEW статус должен быть NEW, получен " + status);
    }

    @Test
    void shouldSetInProgressStatus() {
        int id = 1;
        String name1 = "Подзадача 1";
        String description = "Описание подзадачи 1";
        Subtask subtask1 = new Subtask(id, name1, description, TaskStatus.NEW, 1);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        TaskStatus status = subtask1.getStatus();
        assertEquals(TaskStatus.IN_PROGRESS, status, "При смене статуса подзадачи на IN_PROGRESS получен статус " + status);
    }
}