package ru.yandex.practicum.tasktracker.service;

import ru.yandex.practicum.tasktracker.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final List<Task> historyTask = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (historyTask.size() > 10) {
            historyTask.remove(0);
        }
        historyTask.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyTask;
    }
}