package ru.yandex.practicum.tasktracker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.practicum.tasktracker.adapter.LocalDateTimeAdapter;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultNewManager() {
       return FileBackedTasksManager.loadFromFile(new File("tasks.csv"));
    }

    public static TaskManager getDefaultHttpManager() throws IOException, InterruptedException {
        return new HttpTaskManager(URI.create("http://localhost:8078"));
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }
}