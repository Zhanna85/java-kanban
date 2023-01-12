package ru.yandex.practicum.tasktracker.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.service.ManagerException;
import ru.yandex.practicum.tasktracker.service.Managers;
import ru.yandex.practicum.tasktracker.service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import static ru.yandex.practicum.tasktracker.server.HttpTaskServer.DEFAULT_CHARSET;
import static ru.yandex.practicum.tasktracker.server.HttpTaskServer.sendText;

public class TaskHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = Managers.getGson();
    private boolean thisGson;

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    //В этом методе будет находиться код, который определяет логику работы эндпоинтов.
    @Override
    public void handle(HttpExchange httpExchange) {
        try {
            if (getThisJson(httpExchange)) {
                String method = httpExchange.getRequestMethod();
                String path = httpExchange.getRequestURI().getPath();
                Endpoint endpoint = getEndpoint(path, method);

                switch (endpoint) {
                    case GET_ALL: { // Запрос всех задач.
                        String response = gson.toJson(taskManager.getListedOfAllTasks());
                        sendText(httpExchange, response, 200);
                        break;
                    }
                    case GET_ID: { //Запрос задачи по ИД.
                        String pathId = path.split("/")[4];
                        int id = getId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getByIDTask(id));
                            sendText(httpExchange, response, 200);
                        } else {
                            sendText(httpExchange, "Получен некорректный идентификатор" + pathId
                                    , 400);
                        }

                        break;
                    }

                    case ADD: { // Добавляем задачу.
                        Task task = deserializationTasks(httpExchange, Task.class);
                        if (task == null) return;
                        taskManager.createTask(task);
                        String response = "Задача создана.";
                        sendText(httpExchange, response, 200);
                        break;
                    }
                    case UPDATE: { // Обновляем задачу.
                        String pathId = path.split("/")[4];
                        int id = getId(pathId);
                        if (id != -1) {
                            Task task = deserializationTasks(httpExchange, Task.class);
                            try {
                                taskManager.updateTask(task);
                            } catch (ManagerException exception) {
                                sendText(httpExchange, exception.getMessage(), 400);
                            }
                            String response = "Задача с ИД - " + id + " обновлена.";
                            sendText(httpExchange, response, 200);
                        } else {
                            sendText(httpExchange, "Получен некорректный идентификатор" + pathId
                                    , 400);
                        }
                        break;
                    }
                    case DELETE_ALL: { // Удаляем все задачи.
                        taskManager.deleteAllTasks();
                        sendText(httpExchange, "Задачи удалены", 200);
                        break;
                    }
                    case DELETE_ID: { // Удаляем задачу по ИД.
                        String pathId = path.split("/")[4];
                        int id = getId(pathId);
                        if (id !=-1) {
                            try {
                                taskManager.deleteTaskByID(id);
                            } catch (ManagerException exception) {
                                sendText(httpExchange, exception.getMessage(), 400);
                            }

                            sendText(httpExchange, "Задача с ИД - " + id + "удалена", 200);
                        } else {
                            sendText(httpExchange, "Получен некорректный идентификатор" + pathId
                                    , 400);
                        }
                        break;
                    }
                    default:
                        String text = "Получен не допустимый метод запроса. Ожидалось GET, POST или DELETE";
                        sendText(httpExchange, text, 400);
                }
            } else {
                sendText(httpExchange, "Полученный запрос не в формате JSON", 400);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private Endpoint getEndpoint(String path, String method) {
        switch (method) {
            case "GET":
                if (Pattern.matches("^/tasks/task$", path)) {
                    return Endpoint.DELETE_ALL;
                }
                if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                    return Endpoint.GET_ID;
                }
                break;
            case "POST":
                if (Pattern.matches("^/tasks/task$", path)) {
                    return Endpoint.ADD;
                }
                if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                    return Endpoint.UPDATE;
                }
                break;
            case "DELETE":
                if (Pattern.matches("^/tasks/task$", path)) {
                    return Endpoint.DELETE_ALL;
                }
                if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                    return Endpoint.DELETE_ID;
                }
                break;
        }
        return Endpoint.UNKNOWN;
    }

    protected boolean getThisJson(HttpExchange httpExchange) { // Проверяем тип передаваемых данных
        Headers requestHeaders = httpExchange.getRequestHeaders();
        List<String> contentTypeValues = requestHeaders.get("Content-type");

        // тело запроса должно передаваться в формате JSON.
        if ((contentTypeValues != null) && (contentTypeValues.contains("application/json"))) {
            thisGson = true;
        }
        return thisGson;
    }

    protected  <T> T deserializationTasks(HttpExchange httpExchange, Class<T> task) throws IOException {
        try {
            // Извлекаем тело запроса.
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
            try {
                return gson.fromJson(body, task);
            } catch (JsonSyntaxException e) {
                sendText(httpExchange, "Получен некорректный JSON", 400);
            }
        } catch (IOException exception) {
            sendText(httpExchange, exception.getMessage(), 400);
        }

        return null;
    }

    protected int getId(String pathId) { // Форматируем ИД, если формат не верный вернет -1.
        try {
            return Integer.parseInt(pathId);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}