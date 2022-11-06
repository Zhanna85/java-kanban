package ru.yandex.practicum.tasktracker.service;

import ru.yandex.practicum.tasktracker.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private static final int SIZE_LIST = 10; // Размер списка не должен превышать 10 элементов.

    private final LinkedList<Task> historyTask = new LinkedList<>(); // Список для хранения просмотренных задач.

    @Override
    public void add(Task task) {
        if (task != null) {
            if (historyTask.size() == SIZE_LIST) {
                historyTask.removeFirst();
            }
            historyTask.addLast(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyTask;
    }
}