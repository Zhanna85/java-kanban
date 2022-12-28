package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.model.*;
import ru.yandex.practicum.tasktracker.service.Managers;
import ru.yandex.practicum.tasktracker.service.TaskManager;

import java.time.LocalDateTime;

import static ru.yandex.practicum.tasktracker.model.TaskStatus.*;
import static ru.yandex.practicum.tasktracker.model.TypesTasks.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefaultNewManager();

        System.out.println("Список: " + taskManager.getListedOfAllEpics());
        System.out.println();
        System.out.println("Список: " + taskManager.getListedOfAllTasks());
        System.out.println();
        System.out.println("Список: " + taskManager.getListedOfAllSubtasks());
        System.out.println();
        System.out.println("История: " + taskManager.getHistory());

        Task newTask1 = new Task(1, TASK, "Задача №1", IN_PROGRESS, "Описание задачи №1"
                , 0, null);
        taskManager.createTask(newTask1);

        Task newTask2 = new Task(2, TASK, "Задача №2", NEW, "Описание задачи №2"
                , 50, LocalDateTime.of(2022, 12, 27, 20, 0));
        taskManager.createTask(newTask2);

        Epic newEpic1 = new Epic(3, EPIC, "Эпик №1", NEW, "Описание эпика №1"
                , 50, LocalDateTime.of(2022, 12, 27, 22, 0));
        taskManager.createEpic(newEpic1);

        Subtask newSubtask1 = new Subtask(4, SUBTASK, "Подзадача №1 эпика №1", IN_PROGRESS
                ,"Описание подзадачи №1 эпика №1",  0, null, 3);
        taskManager.createSubtask(newSubtask1);

        Subtask newSubtask2 = new Subtask(5, SUBTASK, "Подзадача №2 эпика №1", NEW
                , "Описание подзадачи №2 эпика №2", 30
                , LocalDateTime.of(2022, 12, 27, 21, 0),  3);
        taskManager.createSubtask(newSubtask2);

        Subtask newSubtask3 = new Subtask(6, SUBTASK, "Подзадача №2 эпика №1", NEW
                , "Описание подзадачи №2 эпика №2", 40
                , LocalDateTime.of(2022, 12, 27, 22, 0),  3);
        taskManager.createSubtask(newSubtask3);

        Epic newEpic2 = new Epic(7, EPIC, "Эпик №2", NEW, "Описание эпика №2", 30
                , LocalDateTime.of(2022, 12, 27, 21, 0));
        taskManager.createEpic(newEpic2);

        System.out.println("Список созданных задач:");
        System.out.println(taskManager.getListedOfAllTasks());
        System.out.println(taskManager.getListedOfAllEpics());
        System.out.println(taskManager.getListedOfAllSubtasks());

        // Запрашиваем созданные задачи несколько раз и проверяем историю на дубли и порядок.
        System.out.println("Запрашиваем созданные задачи:");
        System.out.println(taskManager.getByIDEpic(7));
        System.out.println(taskManager.getByIDSubtask(4));
        System.out.println(taskManager.getByIDSubtask(5));
        System.out.println(taskManager.getByIDTask(1));
        System.out.println(taskManager.getByIDSubtask(4));
        System.out.println(taskManager.getByIDSubtask(6));
        System.out.println(taskManager.getByIDEpic(3));
        System.out.println("История просмотра: " + taskManager.getHistory());
        System.out.println("\n" + "Список задач в порядке приоритета: ");
        taskManager.getPrioritizedTasks().forEach(System.out::println);
    }
}