package ru.yandex.practicum.tasktracker.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    @BeforeEach
    public void beforeEach() {
        File file = new File("test.csv");
        taskManager = new FileBackedTasksManager(file);
    }

    @Test
    void test(){
        taskManager.createEpic(newEpic1);
        taskManager.createSubtask(newSubtask1);
        taskManager.createSubtask(newSubtask2);
        taskManager.createTask(newTask1);
        taskManager.createTask(newTask2);
        taskManager.createEpic(newEpic2);

        FileBackedTasksManager newTaskManager = FileBackedTasksManager.loadFromFile(
                new File("test.csv"));

        final List<Task> tasks = newTaskManager.getListedOfAllTasks();
        final List<Epic> epics = newTaskManager.getListedOfAllEpics();
        final List<Subtask> subtasks = newTaskManager.getListedOfAllSubtasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(2, tasks.size(), "Неверное количество задач.");
        assertTrue(tasks.contains(taskManager.getByIDTask(4)), "Задачи не совпадают.");

        assertNotNull(epics, "Задачи не возвращаются.");
        assertEquals(2, epics.size(), "Неверное количество задач.");
        assertTrue(epics.contains(taskManager.getByIDEpic(1)), "Задачи не совпадают.");

        assertNotNull(subtasks, "Задачи не возвращаются.");
        assertEquals(3, subtasks.size(), "Неверное количество задач.");
        assertTrue(subtasks.contains(taskManager.getByIDSubtask(1)), "Задачи не совпадают.");
    }
}