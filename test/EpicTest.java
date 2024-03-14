import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void shouldBeEqualedById() {
        int id = 1;
        String name1 = "Эпик 1";
        String description = "Описание эпика";
        Epic epic1 = new Epic(id, name1, description, null);

        String name2 = "Другой эпик";
        Epic epic2 = new Epic(id, name2, description, null);

        id = 2;
        Epic epic3 = new Epic(id, name1, description, null);

        assertEquals(epic1, epic2, "Эпики с одинаковыми ИД должны быть равны");
        assertNotEquals(epic1, epic3, "Эпики с разными ИД должны быть не равны");
    }

    @Test
    void shouldCopyEpic() {
        int id = 1;
        String name1 = "Эпик 1";
        String description = "Описание эпика";
        Epic epic1 = new Epic(id, name1, description, null);
        Epic epic2 = epic1.copy();

        assertEquals(epic1.getId(), epic2.getId(), "В копии эпика отличается ид");
        assertEquals(epic1.getName(), epic2.getName(), "В копии эпика отличается наименование");
        assertEquals(epic1.getDescription(), epic2.getDescription(), "В копии эпика отличается описание");
        assertNotSame(epic1, epic2, "Эпик и копия эпика должны быть разными объектами");
    }

    @Test
    void shouldAddSubtaskToEpic() {
        int id = 1;
        String name1 = "Эпик 1";
        String description = "Описание эпика";
        Epic epic1 = new Epic(id, name1, description, null);

        Subtask subtask1 = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW, 1);
        epic1.addSubtask(subtask1);
        Subtask subtask2 = epic1.getSubtaskList().getLast();

        assertSame(subtask1, subtask2, "Новая подзадача эпика должна добавляться в конец списка");
    }

    @Test
    void shouldBeNewStatusForEmptyEpic() {
        int id = 1;
        String name1 = "Эпик 1";
        String description = "Описание эпика";
        Epic epic1 = new Epic(id, name1, description, null);

        TaskStatus status = epic1.getStatus();
        assertEquals(TaskStatus.NEW, status, "Для эпика с пустым списком подзадач статус должен быть NEW, получен " + status.toString());
    }

    @Test
    void shouldBeInProgressStatusWhenDifferentSubtaskStatusExists() {
        int id = 1;
        String name1 = "Эпик 1";
        String description = "Описание эпика";
        Epic epic1 = new Epic(id, name1, description, null);

        Subtask subtask = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", TaskStatus.NEW, 1);
        epic1.addSubtask(subtask);
        subtask = new Subtask(3, "Подзадача 2", "Описание подзадачи 2", TaskStatus.DONE, 1);
        epic1.addSubtask(subtask);
        TaskStatus status = epic1.getStatus();
        assertEquals(TaskStatus.IN_PROGRESS, status, "Для эпика с разными статусами подзадач статус эпика должен быть IN_PROGRESS, получен " + status.toString());
    }

    @Test
    void shouldBeDoneStatusWhenAllSubtasksDone() {
        int id = 1;
        String name1 = "Эпик 1";
        String description = "Описание эпика";
        Epic epic1 = new Epic(id, name1, description, null);

        Subtask subtask = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", TaskStatus.DONE, 1);
        epic1.addSubtask(subtask);
        subtask = new Subtask(3, "Подзадача 2", "Описание подзадачи 2", TaskStatus.DONE, 1);
        epic1.addSubtask(subtask);
        TaskStatus status = epic1.getStatus();
        assertEquals(TaskStatus.DONE, status, "Для эпика со статусами подзадач DONE статус эпика должен быть DONE, получен " + status.toString());
    }

    @Test
    void shouldBeDoneStatusWhenSubtaskGotDone() {
        int id = 1;
        String name1 = "Эпик 1";
        String description = "Описание эпика";
        Epic epic1 = new Epic(id, name1, description, null);

        Subtask subtask = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", TaskStatus.DONE, 1);
        epic1.addSubtask(subtask);
        subtask = new Subtask(3, "Подзадача 2", "Описание подзадачи 2", TaskStatus.NEW, 1);
        epic1.addSubtask(subtask);
        subtask.setStatus(TaskStatus.DONE);
        epic1.updateSubtask(subtask);
        TaskStatus status = epic1.getStatus();
        assertEquals(TaskStatus.DONE, status, "При смене статуса всех подзадач на DONE статус эпика должен быть DONE, получен " + status.toString());
    }

    @Test
    void shouldBeNewStatusAfterDeletingSubtaskList() {
        int id = 1;
        String name1 = "Эпик 1";
        String description = "Описание эпика";
        Epic epic1 = new Epic(id, name1, description, null);
        Subtask subtask = new Subtask(2, "Подзадача 1", "Описание подзадачи 1", TaskStatus.DONE, 1);
        epic1.addSubtask(subtask);
        subtask = new Subtask(3, "Подзадача 2", "Описание подзадачи 2", TaskStatus.NEW, 1);
        epic1.addSubtask(subtask);
        epic1.deleteSubtaskList();

        TaskStatus status = epic1.getStatus();
        assertEquals(TaskStatus.NEW, status, "Для эпика с пустым списком подзадач статус должен быть NEW, получен " + status.toString());
    }
}