package ru.yandex.practicum.tasktracker.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.server.KVTaskClient;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager{
    private final Gson gson = Managers.getGson();
    private final KVTaskClient kvTaskClient;

    public HttpTaskManager(URI url) throws IOException, InterruptedException {
        super(null);
        kvTaskClient = new KVTaskClient(url);
    }

    @Override
    protected void save() { // Реализуем сохранение состояние менеджера на сервере.
        kvTaskClient.put("tasks", gson.toJson(tasks));
        kvTaskClient.put("epics", gson.toJson(epics));
        kvTaskClient.put("subtasks", gson.toJson(subtasks));
        kvTaskClient.put("history", gson.toJson(historyManager.getHistory()));
        kvTaskClient.put("id", gson.toJson(id)); // сохраняем значение последнего сгенерированного ИД.
    }

    protected void loadFromServer() { // Реализуем восстановление состояния менеджера из сервера.
        tasks = gson.fromJson( // восстанавливаем задачи.
                kvTaskClient.load("tasks"),
                new TypeToken<HashMap<Integer, Task>>() {
                }.getType());
        epics = gson.fromJson( // восстанавливаем эпики.
                kvTaskClient.load("epics"),
                new TypeToken<HashMap<Integer, Epic>>() {
                }.getType());
        subtasks = gson.fromJson( // восстанавливаем subtask.
                kvTaskClient.load("subtasks"),
                new TypeToken<HashMap<Integer, Subtask>>() {
                }.getType());
        List<Task> historyList = gson.fromJson( // восстанавливаем список истории просмотров.
                kvTaskClient.load("history"),
                new TypeToken<List<Task>>() {
                }.getType());

        historyList.forEach(historyManager::add); // что бы правильно сформировались Node.

        id = Integer.parseInt(kvTaskClient.load("startId"));

        // Добавим задачи и подзадачи в список в порядке приоритета.
        prioritizedTasks.addAll(tasks.values());
        prioritizedTasks.addAll(subtasks.values());
    }
}
