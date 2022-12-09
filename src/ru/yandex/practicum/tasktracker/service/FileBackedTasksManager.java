package ru.yandex.practicum.tasktracker.service;

import ru.yandex.practicum.tasktracker.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.tasktracker.model.TypesTasks.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final String header = "id,type,name,status,description,epic";
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    //Метод будет восстанавливать данные менеджера из файла при запуске программы
    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager tasksManager = new FileBackedTasksManager(file);

        // Считываем файл.
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            Task task;
            String value = reader.readLine(); // Считаем сначала первую строку.

            // Считываем остальные строки в цикле.
            while (!(value = reader.readLine()).isBlank()) { // До пустой строки.
                task = tasksManager.fromString(value);
                tasksManager.addTasksToTheMap(task);
            }

            String lineHistoryTask = reader.readLine();
            if (!lineHistoryTask.isBlank()) {
                List<Integer> idHistoryTask = historyFromString(lineHistoryTask);

                for (Integer id : idHistoryTask) {
                    task = tasksManager.getTaskForHistory(id);
                    tasksManager.historyManager.add(task);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при чтении данных менеджера из файла.");
        }
        return tasksManager;
    }

    private Task getTaskForHistory(Integer id) { // Ищем задачу в мапах по iD.
        if (tasks.get(id) != null) {
            return tasks.get(id);
        }
        if (epics.get(id) != null) {
            return epics.get(id);
        }
        if (subtasks.get(id) != null) {
            return subtasks.get(id);
        }
        return null;
    }

    private Task fromString(String value) { //Создание задачи из строки.
        String[] split = value.split(",");
        final Integer uin = Integer.parseInt(split[0]); // ID задачи.
        final TypesTasks type = TypesTasks.valueOf(split[1]); // Тип задачи.
        final String name = split[2]; // Название.
        final TaskStatus status = TaskStatus.valueOf(split[3]); // Статус.
        final String description = split[4]; // Описание.

        if (type == SUBTASK) {
            final Integer epicId = Integer.valueOf(split[5]); // ID эпика.
            return new Subtask(uin, type, name, status, description, epicId);
        }
        if (type == TASK) {
            return new Task(uin, type, name, status, description);
        }
        return new Epic(uin, type, name, status, description);
    }

    private void addTasksToTheMap(Task task) { // Добовляем задачи в мапу.
        final int idTask = task.getUin(); // ID задачи.
        switch (task.getType()) {
            case EPIC:
                epics.put(idTask, (Epic) task);
                break;
            case TASK:
                tasks.put(idTask, task);
                break;
            case SUBTASK:
                subtasks.put(idTask, (Subtask) task);
                Epic epic = epics.get(task.getEpicId());
                epic.addListIdSubtasks(idTask); // Добавили id в список подзадач эпика
                break;
        }
    }

    private String toString(Task task) { //Сохранение задачи в строку.
        return String.format("%d,%s,%s,%s,%s,%d", task.getUin(), task.getType(), task.getName()
                , task.getStatus(), task.getDescription(), task.getEpicId());
    }

    private void save() { // Сохраняем текущее состояние менеджера в указанный файл.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write(header); // Добавили заголовок.
            writer.append(System.lineSeparator());
            saveTasksToFile(writer, tasks);
            saveTasksToFile(writer, epics);
            saveTasksToFile(writer, subtasks);
            writer.write(""); // Добовляем пустую строку.
            writer.append(System.lineSeparator());
            final String lineHistoryTask = historyToString(historyManager);
            writer.write(lineHistoryTask);
        } catch (IOException exception) {
            throw new ManagerSaveException("Произошла ошибка при сохранении данных.");
        }
    }

    //Добавляем задачи в файл.
    private void saveTasksToFile(BufferedWriter writer, Map<Integer, ? extends Task> tasks) throws IOException {
        for (Task task : tasks.values()) {
            String lineTask = toString(task);
            writer.write(lineTask);
            writer.append(System.lineSeparator());
        }
    }

    public static String historyToString(HistoryManager manager) { // Сохранение менеджера истории в строку.
        List<Task> historyTask = manager.getHistory(); // Передаем список задач из истории просмотров.
        StringBuilder builder = new StringBuilder();

        if (historyTask.isEmpty()) {
            return "";
        } else {
            builder.append(historyTask.get(0).getUin());
            for (int i = 1; i < historyTask.size(); i++) {
                builder.append(",");
                builder.append(historyTask.get(i).getUin()); // Последовательно добавляем в список id задач.
            }
            return builder.toString();
        }
    }

    public static List<Integer> historyFromString(String value) { // Восстановление списка id из CSV для менеджера истории.
        List<Integer> history = new ArrayList<>();
        if (!value.isEmpty()) {
            String[] split = value.split(",");

            for (String id : split) {
                history.add(Integer.parseInt(id));
            }
        }
        return history;
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    public ArrayList<Task> getListedOfAllTasks() {
        return super.getListedOfAllTasks();
    }

    @Override
    public ArrayList<Epic> getListedOfAllEpics() {
        return super.getListedOfAllEpics();
    }

    public ArrayList<Subtask> getListedOfAllSubtasks() {
        return super.getListedOfAllSubtasks();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Task getByIDTask(int id) {
        historyManager.add(tasks.get(id));
        save();
        return tasks.get(id);
    }

    @Override
    public Epic getByIDEpic(int id) {
        historyManager.add(epics.get(id));
        save();
        return epics.get(id);
    }

    @Override
    public Subtask getByIDSubtask(int id) {
        historyManager.add(subtasks.get(id));
        save();
        return subtasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTaskByID(int id) {
        super.deleteTaskByID(id);
        save();
    }

    @Override
    public void deleteEpicByID(int id) {
        super.deleteEpicByID(id);
        save();
    }

    @Override
    public void deleteSubtaskByID(Integer id) {
        super.deleteSubtaskByID(id);
        save();
    }

    @Override
    public ArrayList<Subtask> getListAllSubtasksOfEpic(int id) {
        return super.getListAllSubtasksOfEpic(id);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }
}