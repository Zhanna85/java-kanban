package ru.yandex.practicum.tasktracker.service;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskStatus;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefault();

        Task newTask1 = new Task();
        newTask1.setName("Задача №1");
        newTask1.setDescription("Описание задачи №1");
        newTask1.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.creatTask(newTask1);

        Task newTask2 = new Task();
        newTask2.setName("Задача №2");
        newTask2.setDescription("Описание задачи №2");
        taskManager.creatTask(newTask2);

        Epic newEpic1 = new Epic();
        newEpic1.setName("Эпик №1");
        newEpic1.setDescription("Описание эпика №1");
        taskManager.creatEpic(newEpic1);

        Subtask newSubtask1 = new Subtask();
        newSubtask1.setEpicId(1);
        newSubtask1.setStatus(TaskStatus.IN_PROGRESS);
        newSubtask1.setName("Подзадача №1 эпика №1");
        newSubtask1.setDescription("Описание подзадачи №1 эпика №1");
        taskManager.creatSubtask(newSubtask1);

        Subtask newSubtask2 = new Subtask();
        newSubtask2.setEpicId(1);
        newSubtask2.setName("Подзадача №2 эпика №1");
        newSubtask2.setDescription("Описание подзадачи №2 эпика №1");
        taskManager.creatSubtask(newSubtask2);

        Epic newEpic2 = new Epic();
        newEpic2.setName("Эпик №2");
        newEpic2.setDescription("Описание эпика №2");
        taskManager.creatEpic(newEpic2);

        Subtask newSubtask = new Subtask();
        newSubtask.setEpicId(2);
        newSubtask.setName("Подзадача №1 эпика №2");
        newSubtask.setDescription("Описание подзадачи №1 эпика №2");
        taskManager.creatSubtask(newSubtask);

        System.out.println("Список созданных задач:");
        System.out.println(taskManager.getListedOfAllEpics());
        System.out.println(taskManager.getListedOfAllTasks());
        System.out.println(taskManager.getListedOfAllSubtasks());

        System.out.println("История вызова задач:");
        System.out.println(taskManager.getByIDTask(1));
        System.out.println("Список задач : " + taskManager.getHistoryManager());
        System.out.println(taskManager.getByIDEpic(1));
        System.out.println(taskManager.getByIDSubtask(2));
        System.out.println("Список задач : " + taskManager.getHistoryManager());
    }
}