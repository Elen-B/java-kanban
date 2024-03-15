import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager taskManager;

    @BeforeEach
    void resetTaskManager() {
        taskManager = Managers.getDefault();
    }

    @Test
    void shouldReturn2ForNextTaskId() {
        int nextId = taskManager.getNextTaskId();
        nextId = taskManager.getNextTaskId();
        assertEquals(2, nextId, "Неверное значение счетчика");
    }

    @Test
    void shouldAddTaskToMap() {
        Task task = new Task("Задача 1", "описание задачи");
        taskManager.addTask(task);

        List<Task> list = taskManager.getTaskList();
        assertNotNull(list);
        assertTrue(list.contains(task), "Задача не добавлена");
    }

    @Test
    void shouldNotAddEpicToTaskMap() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        int id1 = taskManager.addTask(epic1);

        assertEquals(-1, id1, "Эпик был добавлен в список задач, а не эпиков");
    }

    @Test
    void shouldAddEpicToMap() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        taskManager.addEpic(epic1);

        List<Epic> list = taskManager.getEpicList();
        assertNotNull(list, "Список эпиков пуст");
        assertTrue(list.contains(epic1), "Эпик не добавлен");
    }

    @Test
    void shouldAddSubtaskToMap() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        int epicId = taskManager.addEpic(epic1);
        Subtask subtask = new Subtask("Подзадача 1", "описание подзадачи 1", epicId);
        taskManager.addSubtask(subtask);

        List<Subtask> subtaskList = taskManager.getSubtaskList();
        List<Subtask> subtaskListByEpic = taskManager.getSubtaskList(epicId);
        assertNotNull(subtaskList, "Список подзадач пуст");
        assertNotNull(subtaskListByEpic, "Список подзадач по эпику пуст");
        assertTrue(subtaskList.contains(subtask), "Подзадача не добавлена в общий список подзадач");
        assertTrue(subtaskListByEpic.contains(subtask), "Подзадача не добавлена в список подзадач эпика");
    }

    @Test
    void shouldNotAddSubtaskWithIncorrectEpicId() {
        Subtask subtask = new Subtask("Подзадача 1", "описание подзадачи 1", 1);
        int subtaskId = taskManager.addSubtask(subtask);

        assertEquals(-1, subtaskId, "Добавлена подзадача с неверным ид эпика");
    }

    @Test
    void updateTask() {
        Task task = new Task("Задача 1", "описание задачи");
        int id = taskManager.addTask(task);
        String name = "task 1";
        String description = "description for task 1";
        Task updTask = new Task(id, name, description, TaskStatus.IN_PROGRESS);
        taskManager.updateTask(updTask);

        Task addedTask = taskManager.getTask(id);
        assertNotNull(addedTask, "Задача не найдена");
        assertEquals(id, addedTask.getId(), "Ид полученной задачи отличается от ид записанной");
        assertEquals(name, addedTask.getName(), "Название полученной задачи отличается от названия записанной");
        assertEquals(description, addedTask.getDescription(), "Описание полученной задачи отличается от описания записанной");
        assertEquals(TaskStatus.IN_PROGRESS, addedTask.getStatus(), "Статус полученной задачи отличается от статуса записанной");
    }

    @Test
    void updateEpic() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        String name = "Epic 1";
        String description = "description for epic 1";
        int id = taskManager.addEpic(epic1);
        Epic epic2 = new Epic(id, name, description, null);
        taskManager.updateEpic(epic2);

        Epic addedEpic = taskManager.getEpic(id);
        assertNotNull(addedEpic, "Эпик не найден");
        assertEquals(id, addedEpic.getId(), "Ид полученного эпика отличается от ид записанного");
        assertEquals(name, addedEpic.getName(), "Название полученного эпика отличается от названия записанного");
        assertEquals(description, addedEpic.getDescription(), "Описание полученного эпика отличается от описания записанного");
        assertEquals(TaskStatus.NEW, addedEpic.getStatus(), "Статус нового эпика рассчитан неверно");
    }

    @Test
    void updateSubtask() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        int epicId = taskManager.addEpic(epic1);
        Subtask subtask = new Subtask("Подзадача 1", "описание подзадачи 1", epicId);
        int subtaskId = taskManager.addSubtask(subtask);
        String name = "subtask 1";
        String description = "description for subtask 1";
        Subtask updSubtask = new Subtask(subtaskId, name, description, TaskStatus.DONE, epicId);
        taskManager.updateSubtask(updSubtask);

        Subtask resultSubtask = taskManager.getSubtask(subtaskId);
        assertNotNull(resultSubtask, "Подзадача не найдена");
        assertEquals(subtaskId, resultSubtask.getId(), "Ид полученной подзадачи отличается от ид записанного");
        assertEquals(name, resultSubtask.getName(), "Название подзадачи не соответствует новому названию");
        assertEquals(description, resultSubtask.getDescription(), "Описание подзадачи не соответствует новому описанию");
        assertEquals(TaskStatus.DONE, resultSubtask.getStatus(), "Статус подзадачи не соответствует новому статусу");
        assertEquals(epicId, resultSubtask.getEpicId(), "Эпик подзадачи неверен");
    }

    @Test
    void shouldDeleteTask() {
        Task task1 = new Task("Задача 1", "описание задачи");
        Task task2 = new Task("Задача 2", "описание задачи");
        int id1 = taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.deleteTask(id1);

        assertNull(taskManager.getTask(id1), "Задача не удалена");
    }

    @Test
    void shouldDeleteEpic() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        int id1 = taskManager.addTask(epic1);
        taskManager.addEpic(epic1);
        taskManager.deleteEpic(id1);

        assertNull(taskManager.getEpic(id1), "Эпик не удален");
    }

    @Test
    void shouldDeleteSubtask() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        int epicId = taskManager.addTask(epic1);
        taskManager.addEpic(epic1);
        String name = "Подзадача 1";
        String description = "описание подзадачи 1";
        Subtask subtask = new Subtask(name, description, epicId);
        int subtaskId = taskManager.addSubtask(subtask);
        taskManager.deleteSubtask(subtaskId);

        assertNull(taskManager.getSubtask(subtaskId), "Подзадача не удалена");
    }

    @Test
    void shouldGetTaskById() {
        Task task = new Task("Задача 1", "описание задачи");
        int id = taskManager.addTask(task);

        Task addedTask = taskManager.getTask(id);
        assertNotNull(addedTask, "Задача не найдена");
        assertSame(task, addedTask, "Полученная задача отличается от найденной");
    }

    @Test
    void shouldContainCorrectDataForTask() {
        String name = "Задача 1";
        String description = "описание задачи";
        Task task = new Task(name, description);
        int id = taskManager.addTask(task);

        Task addedTask = taskManager.getTask(id);
        assertNotNull(addedTask, "Задача не найдена");
        assertEquals(id, addedTask.getId(), "Ид полученной задачи отличается от ид записанной");
        assertEquals(name, addedTask.getName(), "Название полученной задачи отличается от названия записанной");
        assertEquals(description, addedTask.getDescription(), "Описание полученной задачи отличается от описания записанной");
        assertEquals(TaskStatus.NEW, addedTask.getStatus(), "Статус полученной задачи отличается от статуса записанной");
    }

    @Test
    void shouldGetEpicById() {
        Epic epic = new Epic("Эпик 1", "описание эпика");
        int id = taskManager.addEpic(epic);

        Epic addedEpic = taskManager.getEpic(id);
        assertNotNull(addedEpic, "Эпик не найден");
        assertSame(epic, addedEpic, "Полученный эпик отличается от найденного");
    }

    @Test
    void shouldContainCorrectDataForEpic() {
        String name = "Эпик 1";
        String description = "описание эпика";
        Epic epic = new Epic(name, description);
        int id = taskManager.addEpic(epic);

        Epic addedEpic = taskManager.getEpic(id);
        assertNotNull(addedEpic, "Эпик не найден");
        assertEquals(id, addedEpic.getId(), "Ид полученного эпика отличается от ид записанного");
        assertEquals(name, addedEpic.getName(), "Название полученного эпика отличается от названия записанного");
        assertEquals(description, addedEpic.getDescription(), "Описание полученного эпика отличается от описания записанного");
        assertEquals(TaskStatus.NEW, addedEpic.getStatus(), "Статус нового эпика рассчитан неверно");
    }

    @Test
    void shouldGetSubtaskById() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        int epicId = taskManager.addEpic(epic1);
        String name = "Подзадача 1";
        String description = "описание подзадачи 1";
        Subtask subtask = new Subtask(name, description, epicId);
        int subtaskId = taskManager.addSubtask(subtask);

        Subtask addedSubtask = taskManager.getSubtask(subtaskId);
        assertNotNull(addedSubtask, "Подзадача не найдена");
        assertEquals(subtaskId, addedSubtask.getId(), "Ид полученноой подзадачи отличается от ид записанной");
        assertEquals(name, addedSubtask.getName(), "Название полученноой подзадачи отличается от названия записанной");
        assertEquals(description, addedSubtask.getDescription(), "Описание полученноой подзадачи отличается от описания записанной");
        assertEquals(TaskStatus.NEW, addedSubtask.getStatus(), "Статус нового подзадачи неверен");
    }

    @Test
    void shouldGetTaskList() {
        Task task1 = new Task("Задача 1", "описание задачи");
        Task task2 = new Task("Задача 2", "описание задачи");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        List<Task> taskList = taskManager.getTaskList();

        assertNotNull(taskList, "Список задач пуст");
        assertEquals(2, taskList.size(), "Список задач содержит неверное количество задач");
        assertTrue(taskList.contains(task1), "Список задач не содержит задачу 1");
        assertTrue(taskList.contains(task2), "Список задач не содержит задачу 2");
    }

    @Test
    void shouldGetEpicList() {
        Epic epic = new Epic("Эпик 1", "описание эпика");
        taskManager.addEpic(epic);
        List<Epic> epicList = taskManager.getEpicList();

        assertNotNull(epicList, "Список эпиков пуст");
        assertEquals(1, epicList.size(), "Список эпиков содержит неверное количество эпиков");
        assertTrue(epicList.contains(epic), "Список эпиков не содержит эпик 1");
    }

    @Test
    void shouldGetSubtaskList() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        int epicId = taskManager.addEpic(epic1);
        String name = "Подзадача 1";
        String description = "описание подзадачи 1";
        Subtask subtask = new Subtask(name, description, epicId);
        taskManager.addSubtask(subtask);

        List<Subtask> list = taskManager.getSubtaskList();
        assertNotNull(list, "Список подзадач пуст");
        assertEquals(1, list.size(), "Список подзадач содержит неверное количество подзадач");
        assertTrue(list.contains(subtask), "Список подзадач не содержит подзадачу 1");
    }

    @Test
    void shouldGetSubtaskListByEpicId() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        int epicId = taskManager.addEpic(epic1);
        String name = "Подзадача 1";
        String description = "описание подзадачи 1";
        Subtask subtask = new Subtask(name, description, epicId);
        taskManager.addSubtask(subtask);

        List<Subtask> list = taskManager.getSubtaskList(epicId);
        assertNotNull(list, "Список подзадач пуст");
        assertEquals(1, list.size(), "Список подзадач содержит неверное количество подзадач");
        assertTrue(list.contains(subtask), "Список подзадач не содержит подзадачу 1");
    }

    @Test
    void shouldDeleteTaskList() {
        Task task1 = new Task("Задача 1", "описание задачи");
        Task task2 = new Task("Задача 2", "описание задачи");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.deleteTaskList();
        List<Task> taskList = taskManager.getTaskList();

        assertEquals(0, taskList.size(), "Список задач должен быть пустым");
    }

    @Test
    void shouldDeleteEpicList() {
        Epic epic = new Epic("Эпик 1", "описание эпика");
        taskManager.addEpic(epic);
        taskManager.deleteEpicList();
        List<Epic> epicList = taskManager.getEpicList();

        assertEquals(0, epicList.size(), "Список эпиков должен быть пустым");
    }

    @Test
    void shouldDeleteSubTaskList() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        int epicId = taskManager.addEpic(epic1);
        String name = "Подзадача 1";
        String description = "описание подзадачи 1";
        Subtask subtask = new Subtask(name, description, epicId);
        taskManager.addSubtask(subtask);
        taskManager.deleteSubTaskList();

        List<Subtask> subtaskList = taskManager.getSubtaskList();

        assertEquals(0, subtaskList.size(), "Список подзадач должен быть пустым");
    }

    @Test
    void shouldDeleteSubTaskListByEpicId() {
        Epic epic1 = new Epic("Эпик 1", "описание эпика");
        Epic epic2 = new Epic("Эпик 2", "описание эпика");
        int epic1Id = taskManager.addEpic(epic1);
        int epic2Id = taskManager.addEpic(epic2);
        Subtask subtask1 = new Subtask("Подзадача 1", "описание подзадачи 1", epic1Id);
        Subtask subtask2 = new Subtask("Подзадача 2", "описание подзадачи 2", epic1Id);
        Subtask subtask3 = new Subtask("Подзадача 3", "описание подзадачи 3", epic2Id);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        taskManager.deleteSubTaskList(epic1Id);

        List<Subtask> subtaskList = taskManager.getSubtaskList();

        assertNotNull(subtaskList, "Список подзадач пуст");
        assertEquals(1, subtaskList.size(), "Неверное количество подзадач в списке");
        assertTrue(subtaskList.contains(subtask3), "Из списка подзадач удалена подзадача другого эпика");
    }
}