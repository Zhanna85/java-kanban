package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskStatus;
import ru.yandex.practicum.tasktracker.service.Managers;
import ru.yandex.practicum.tasktracker.service.TaskManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefault();

        Task newTask1 = new Task();
        newTask1.setName("Задача №1");
        newTask1.setDescription("Описание задачи №1");
        newTask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.createTask(newTask1);

        Task newTask2 = new Task();
        newTask2.setName("Задача №2");
        newTask2.setDescription("Описание задачи №2");
        taskManager.createTask(newTask2);

        Epic newEpic1 = new Epic();
        newEpic1.setName("Эпик №1");
        newEpic1.setDescription("Описание эпика №1");
        taskManager.createEpic(newEpic1);

        Subtask newSubtask1 = new Subtask();
        newSubtask1.setEpicId(3);
        newSubtask1.setStatus(TaskStatus.IN_PROGRESS);
        newSubtask1.setName("Подзадача №1 эпика №1");
        newSubtask1.setDescription("Описание подзадачи №1 эпика №1");
        taskManager.createSubtask(newSubtask1);

        Subtask newSubtask2 = new Subtask();
        newSubtask2.setEpicId(3);
        newSubtask2.setName("Подзадача №2 эпика №1");
        newSubtask2.setDescription("Описание подзадачи №2 эпика №1");
        taskManager.createSubtask(newSubtask2);

        Subtask newSubtask3 = new Subtask();
        newSubtask3.setEpicId(3);
        newSubtask3.setName("Подзадача №3 эпика №1");
        newSubtask3.setDescription("Описание подзадачи №3 эпика №1");
        taskManager.createSubtask(newSubtask3);

        Epic newEpic2 = new Epic();
        newEpic2.setName("Эпик №2");
        newEpic2.setDescription("Описание эпика №2");
        taskManager.createEpic(newEpic2);


        System.out.println("Список созданных задач:");
        System.out.println(taskManager.getListedOfAllTasks());
        System.out.println(taskManager.getListedOfAllEpics());
        System.out.println(taskManager.getListedOfAllSubtasks());

        // Запрашиваем созданные задачи несколько раз и проверяем историю на дубли и порядок.
        System.out.println("Запрашиваем созданные задачи (1):");
        System.out.println(taskManager.getByIDTask(1));
        System.out.println(taskManager.getByIDEpic(3));
        System.out.println(taskManager.getByIDSubtask(5));
        System.out.println("История просмотра: " + taskManager.getHistory());
        System.out.println("Запрашиваем созданные задачи (2):");
        System.out.println(taskManager.getByIDTask(2));
        System.out.println(taskManager.getByIDTask(1));
        System.out.println(taskManager.getByIDSubtask(5));
        System.out.println(taskManager.getByIDSubtask(6));
        System.out.println(taskManager.getByIDEpic(3));
        System.out.println("История просмотра: " + taskManager.getHistory());
        System.out.println("Запрашиваем созданные задачи (3):");
        System.out.println(taskManager.getByIDTask(2));
        System.out.println(taskManager.getByIDEpic(7));
        System.out.println("История просмотра: " + taskManager.getHistory());

        // Удаляем созданные задачи:
        taskManager.deleteEpicByID(3);

        // Проверяем, что эпика и подзадач нет.
        System.out.println("История просмотра после удаления Эпика ID3: " + taskManager.getHistory());

        // Удаляем задачу и проверяем, что его нет в истории.
        taskManager.deleteTaskByID(2);
        System.out.println("История просмотра после удаления Задачи ID2: " + taskManager.getHistory());
    }
}