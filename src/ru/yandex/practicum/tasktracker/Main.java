package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.model.*;
import ru.yandex.practicum.tasktracker.service.Managers;
import ru.yandex.practicum.tasktracker.service.TaskManager;

import static ru.yandex.practicum.tasktracker.model.TaskStatus.*;
import static ru.yandex.practicum.tasktracker.model.TypesTasks.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefaultNewManager();

        Task newTask1 = new Task(1, TASK, "Задача №1", IN_PROGRESS, "Описание задачи №1");
        taskManager.createTask(newTask1);

        Task newTask2 = new Task(2, TASK, "Задача №2", NEW, "Описание задачи №2");
        taskManager.createTask(newTask2);

        Epic newEpic1 = new Epic(3, EPIC, "Эпик №1", NEW, "Описание эпика №1");
        taskManager.createEpic(newEpic1);

        Subtask newSubtask1 = new Subtask(4, SUBTASK, "Подзадача №1 эпика №1", IN_PROGRESS,"Описание подзадачи №1 эпика №1", 3);
        taskManager.createSubtask(newSubtask1);

        Subtask newSubtask2 = new Subtask(5, SUBTASK, "Подзадача №2 эпика №1", NEW, "Описание подзадачи №2 эпика №1", 3);
        taskManager.createSubtask(newSubtask2);

        Epic newEpic2 = new Epic(6, EPIC, "Эпик №2", NEW, "Описание эпика №2");
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
        System.out.println(taskManager.getByIDEpic(3));
        System.out.println("История просмотра: " + taskManager.getHistory());
    }
}