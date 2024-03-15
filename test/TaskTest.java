import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaskTest {

    @Test
    void shouldCreateTaskWithFilledFields() {
        int id = 1;
        String name = "Задача 1";
        String description = "Описание задачи";
        TaskStatus status = TaskStatus.NEW;
        Task task = new Task(id, name, description, status);

        assertEquals(id, task.getId(), "Задача создана с другим ид");
        assertEquals(name, task.getName(), "Задача создана с другим названием");
        assertEquals(description, task.getDescription(), "Задача создана с другим описанием");
        assertEquals(status, task.getStatus(), "Задача создана с другим статусом");
    }

    @Test
    void shouldBeEqualedById() {
        int id = 1;
        String name1 = "Задача 1";
        String description = "Описание задачи";
        TaskStatus status = TaskStatus.NEW;
        Task task1 = new Task(id, name1, description, status);

        String name2 = "Другая задача";
        Task task2 = new Task(id, name2, description, status);
        assertEquals(task1, task2, "Задачи с одинаковыми ИД должны быть равны");
    }

    @Test
    void shouldCopyTask() {
        int id = 1;
        String name1 = "Задача 1";
        String description = "Описание задачи";
        TaskStatus status = TaskStatus.NEW;
        Task task1 = new Task(id, name1, description, status);
        Task task2 = task1.copy();

        assertNotSame(task1, task2, "Копирование не выполнено");
    }
}