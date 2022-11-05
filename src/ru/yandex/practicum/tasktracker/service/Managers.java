package ru.yandex.practicum.tasktracker.service;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}