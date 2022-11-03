package ru.yandex.practicum.tasktracker.service;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void creatTask(Task task);

    void creatEpic(Epic epic);

    void creatSubtask(Subtask subtask);

    ArrayList<Task> getListedOfAllTasks();

    ArrayList<Epic> getListedOfAllEpics();

    ArrayList<Subtask> getListedOfAllSubtasks();

    void deletAllTasks();

    void deletAllEpics();

    void deletAllSubtasks();

    Task getByIDTask(int id);

    Epic getByIDEpic(int id);

    Subtask getByIDSubtask(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deletTaskByID(int id);

    void deletEpicByID(int id);

    void deletSubtaskByID(Integer id);

    ArrayList<Subtask> getListAllSubtasksOfEpic(int id);

    List<Task> getHistoryManager();
}