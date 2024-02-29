import java.util.Scanner;

public class Test {
    static TaskManager taskManager = new TaskManager();
    static Scanner scanner = new Scanner(System.in);

    public static void start() {
        printMenu();
        int command = Integer.parseInt(scanner.nextLine());
        while (command != 0) {
            switch (command) {
                case 1: /* getTaskList */
                    testGetTaskList();
                    break;

                case 2: /* getEpicList */
                    testGetEpicList();
                    break;

                case 3: /* getSubtaskList */
                    testGetSubtaskList();
                    break;

                case 4: /* getSubtaskList */
                    testGetSubtaskListById();
                    break;

                case 5: /* addTask */
                    testAddTask();
                    break;

                case 6: /* addEpic */
                    testAddEpic();
                    break;

                case 7: /* addSubtask */
                    testAddSubtask();
                    break;

                case 8: /* updateTask */
                    testUpdateTask();
                    break;

                case 9: /* updateEpic */
                    testUpdateEpic();
                    break;

                case 10: /* updateSubtask */
                    testUpdateSubtask();
                    break;

                case 11: /* deleteTask */
                    testDeleteTask();
                    break;

                case 12: /* deleteEpic */
                    testDeleteEpic();
                    break;

                case 13: /* deleteSubtask */
                    testDeleteSubtask();
                    break;

                case 14: /* deleteTaskList */
                    testDeleteTaskList();
                    break;

                case 15: /* deleteEpicList */
                    testDeleteEpicList();
                    break;

                case 16: /* deleteSubTaskList */
                    testDeleteSubtaskList();
                    break;

                case 17: /* getTask */
                    testGetTask();
                    break;

                case 18: /* getEpic */
                    testGetEpic();
                    break;
                case 19: /* getSubtask */
                    testGetSubtask();
                    break;
            }
            System.out.println("Нажмите Enter для продолжения");
            scanner.nextLine();
            printMenu();
            if (scanner.hasNextInt()) {
                command = Integer.parseInt(scanner.nextLine());
            } else {
                scanner.nextLine();
            }
        }
    }

    public static void printMenu() {
        System.out.println(" 1 - получить список задач (getTaskList)");
        System.out.println(" 2 - получить список эпиков (getEpicList)");
        System.out.println(" 3 - получить список подзадач (getSubtaskList)");
        System.out.println(" 4 - получить список подзадач по эпику (getSubtaskList)");
        System.out.println("---");
        System.out.println(" 5 - добавить задачу (addTask)");
        System.out.println(" 6 - добавить эпик (addEpic)");
        System.out.println(" 7 - добавить подзадачу (addSubtask)");
        System.out.println("---");
        System.out.println(" 8 - обновить задачу (updateTask)");
        System.out.println(" 9 - обновить эпик (updateEpic)");
        System.out.println("10 - обновить подзадачу (updateSubtask)");
        System.out.println("---");
        System.out.println("11 - удалить задачу по идентификатору (deleteTask)");
        System.out.println("12 - удалить эпик по идентификатору (deleteEpic)");
        System.out.println("13 - удалить подзадачу по идентификатору (deleteSubtask)");
        System.out.println("---");
        System.out.println("14 - удалить все задачи (deleteTaskList)");
        System.out.println("15 - удалить все эпики (deleteEpicList)");
        System.out.println("16 - удалить все подзадачи (deleteSubTaskList)");
        System.out.println("---");
        System.out.println("17 - получить задачу по идентификатору (getTask)");
        System.out.println("18 - получить эпик по идентификатору (getEpic)");
        System.out.println("19 - получить подзадачу по идентификатору (getSubtask)");
        System.out.println("---");
        System.out.println(" 0 - ВЫХОД");
    }
    public static void printAll() {
        System.out.print("Tasks: ");
        System.out.println(taskManager.getTaskList());
        System.out.print("Epics: ");
        System.out.println(taskManager.getEpicList());
        System.out.print("Subtasks: ");
        System.out.println(taskManager.getSubtaskList());
        System.out.println();
    }

    public static void testGetTaskList() {
        System.out.println(" 1 - получить список задач (getTaskList): ");
        System.out.println(taskManager.getTaskList());
    }

    public static void testGetEpicList() {
        System.out.println(" 2 - получить список эпиков (getEpicList): ");
        System.out.println(taskManager.getEpicList());
    }

    public static void testGetSubtaskList() {
        System.out.println(" 3 - получить список подзадач (getSubtaskList): ");
        System.out.println(taskManager.getSubtaskList());
    }

    public static void testGetSubtaskListById() {
        System.out.println(" 4 - получить список подзадач по эпику (getSubtaskList): ");

        System.out.println("Введите ид эпика из списка эпиков: ");
        testGetEpicList();
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(taskManager.getSubtaskList(id));
    }

    public static void testAddTask() {
        System.out.println(" 5 - добавить задачу (addTask): ");
        System.out.print("Введите наименование задачи: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание задачи: ");
        String description = scanner.nextLine();
        Task task = new Task(name, description);
        taskManager.addTask(task);
        testGetTaskList();
    }

    public static void testAddEpic() {
        System.out.println(" 6 - добавить эпик (addEpic):");
        System.out.print("Введите наименование эпика: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание эпика: ");
        String description = scanner.nextLine();
        Epic task = new Epic(name, description);
        taskManager.addEpic(task);
        testGetEpicList();
    }

    public static void testAddSubtask() {
        System.out.println(" 7 - добавить подзадачу (addSubtask):");
        System.out.print("В какой из эпиков добавить подзадачу, ид: ");
        System.out.println(taskManager.getEpicList());
        int epicId = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите наименование подзадачи: ");
        String name = scanner.nextLine();
        System.out.print("Введите описание подзадачи: ");
        String description = scanner.nextLine();
        Subtask subtask = new Subtask(name, description, epicId);
        taskManager.addSubtask(subtask);
        System.out.print("Список подзадач: ");
        System.out.println(taskManager.getSubtaskList());
        System.out.print("Список подзадач по эпику: ");
        System.out.println(taskManager.getSubtaskList(epicId));
    }

    public static void testUpdateTask() {
        System.out.println(" 8 - обновить задачу (updateTask): ");
        System.out.print("Какую задачу обновить, ид: ");
        System.out.println(taskManager.getTaskList());
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Новый статус (1 - новый, 2 - в работе, 3 - выполнено)");
        int codeStatus = Integer.parseInt(scanner.nextLine());
        TaskStatus status = switch (codeStatus) {
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.DONE;
            default -> TaskStatus.NEW;
        };
        Task task = taskManager.getTask(id);
        if (task == null) {
            System.out.println("такой задачи нет");
            return;
        }
        task.setStatus(status);
        taskManager.updateTask(task);
        System.out.println(taskManager.getTask(id));
    }

    public static void testUpdateEpic() {
        System.out.println(" 9 - обновить эпик (updateEpic):");
        System.out.print("Какой эпик обновить, ид: ");
        System.out.println(taskManager.getEpicList());
        int id = Integer.parseInt(scanner.nextLine());
        Epic epic = taskManager.getEpic(id);
        if (epic == null) {
            System.out.println("такого эпика нет");
            return;
        }
        System.out.print("Введите наименование эпика: ");
        String name = scanner.nextLine();
        Epic updEpic = new Epic(id, name, epic.getDescription(), epic.getSubtaskList());
        updEpic.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateEpic(updEpic);
        System.out.println(taskManager.getEpicList());
    }

    public static void testUpdateSubtask() {
        System.out.println("10 - обновить подзадачу (updateSubtask): ");
        System.out.print("Какую подзадачу обновить, ид: ");
        System.out.println(taskManager.getSubtaskList());
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Новый статус (1 - новый, 2 - в работе, 3 - выполнено)");
        int codeStatus = Integer.parseInt(scanner.nextLine());
        TaskStatus status = switch (codeStatus) {
            case 2 -> TaskStatus.IN_PROGRESS;
            case 3 -> TaskStatus.DONE;
            default -> TaskStatus.NEW;
        };
        Subtask task = taskManager.getSubtask(id);
        if (task == null) {
            System.out.println("такой подзадачи нет");
            return;
        }
        task.setStatus(status);
        taskManager.updateSubtask(task);
        System.out.println(taskManager.getSubtask(id));
        System.out.println("Эпик: ");
        System.out.println(taskManager.getEpic(task.getEpicId()));
    }

    public static void testDeleteTask() {
        System.out.println("11 - удалить задачу по идентификатору (deleteTask):");
        System.out.print("Какую задачу удалить, ид: ");
        System.out.println(taskManager.getTaskList());
        int id = Integer.parseInt(scanner.nextLine());
        taskManager.deleteTask(id);
        System.out.println("Список задач:");
        System.out.println(taskManager.getTaskList());
    }

    public static void testDeleteEpic() {
        System.out.println("12 - удалить эпик по идентификатору (deleteEpic): ");
        System.out.print("Какой эпик удалить, ид: ");
        System.out.println(taskManager.getEpicList());
        int id = Integer.parseInt(scanner.nextLine());
        taskManager.deleteEpic(id);
        System.out.println("Список эпиков:");
        System.out.println(taskManager.getEpicList());
        System.out.println("Список подзадач:");
        System.out.println(taskManager.getSubtaskList());
    }

    public static void testDeleteSubtask() {
        System.out.println("13 - удалить подзадачу по идентификатору (deleteSubtask): ");
        System.out.print("какую подзадачу удалить, ид: ");
        System.out.println(taskManager.getSubtaskList());
        int id = Integer.parseInt(scanner.nextLine());
        if (taskManager.getSubtask(id) == null) {
            System.out.println("такой подзадачи нет");
            return;
        }
        int epicId = taskManager.getSubtask(id).getEpicId();
        taskManager.deleteSubtask(id);
        System.out.println("Список подзадач:");
        System.out.println(taskManager.getSubtaskList());
        System.out.println("Эпик после удаления подзадачи:");
        System.out.println(taskManager.getEpic(epicId));
    }

    public static void testDeleteTaskList() {
        System.out.println("14 - удалить все задачи (deleteTaskList):");
        taskManager.deleteTaskList();
        System.out.println("Список задач:");
        System.out.println(taskManager.getTaskList());
    }

    public static void testDeleteEpicList() {
        System.out.println("15 - удалить все эпики (deleteEpicList): ");
        taskManager.deleteEpicList();
        System.out.println("Список эпиков:");
        System.out.println(taskManager.getEpicList());
    }

    public static void testDeleteSubtaskList() {
        System.out.println("16 - удалить все подзадачи (deleteSubTaskList)");
        taskManager.deleteSubTaskList();
        System.out.println("Список подзадач:");
        System.out.println(taskManager.getSubtaskList());
        System.out.println("Список эпиков:");
        System.out.println(taskManager.getEpicList());
    }

    public static void testGetTask() {
        System.out.println("17 - получить задачу по идентификатору (getTask):");
        System.out.print("какую задачу получить, ид: ");
        System.out.println(taskManager.getTaskList());
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(taskManager.getTask(id));
    }

    public static void testGetEpic() {
        System.out.println("18 - получить эпик по идентификатору (getEpic):");
        System.out.print("какой эпик получить, ид: ");
        System.out.println(taskManager.getEpicList());
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(taskManager.getEpic(id));
    }

    public static void testGetSubtask() {
        System.out.println("19 - получить подзадачу по идентификатору (getSubtask):");
        System.out.print("какую подзадачу получить, ид: ");
        System.out.println(taskManager.getSubtaskList());
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(taskManager.getSubtask(id));
    }
}
