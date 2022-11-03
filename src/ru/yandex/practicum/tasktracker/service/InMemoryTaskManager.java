package ru.yandex.practicum.tasktracker.service;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager{
    private Map<Integer, Subtask> subtasks = new HashMap<>(); // Tаблица подзадач
    private Map<Integer, Task> tasks = new HashMap<>(); // Таблица задач
    private Map<Integer, Epic> epics = new HashMap<>(); // Таблица эпиков

    private int idTask = 0; // УИН задач
    private int idEpic = 0; // УИН эпиков
    private int idSubtask = 0; // УИН подзадач

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getHistoryManager() {
        return historyManager.getHistory();
    }

    @Override
    public void creatTask(Task task) { // Функция создания задачи. Сам объект должен передаваться в качестве параметра.
        if (task != null) {
            idTask++;
            task.setStatus(TaskStatus.NEW); // При создании статус всегда new
            task.setUin(idTask);
            tasks.put(idTask, task); // Добавили задачу в мапу
        }
    }

   @Override
    public void creatEpic(Epic epic) { // Функция создания эпика. Сам объект должен передаваться в качестве параметра.
        if (epic != null) {
            idEpic++;
            epic.setStatus(TaskStatus.NEW); // При создании статус всегда new
            epic.setUin(idEpic);
            epics.put(idEpic, epic); // Добавили эпик в мапу
        }
    }

    @Override
    public void creatSubtask(Subtask subtask) { // Функция создания подзадачи.
        /*
        Сам объект должен передаваться в качестве параметра.
        Для каждой подзадачи известно, в рамках какого эпика она выполняется.
        id эпика указывается при создании объекта и передается с объектом.
        Подзадача может создаваться даже, если эпик в статусе DONE (уточненное инфо)
        */
        if (subtask != null) {
            if (epics.containsKey(subtask.getEpicId())) { // Проверяем наличие эпика
                idSubtask++;
                subtask.setStatus(TaskStatus.NEW); // При создании статус всегда new
                subtask.setUin(idSubtask);
                subtasks.put(idSubtask, subtask); // Добавили подзадачу в мапу
                Epic epic = epics.get(subtask.getEpicId());
                epic.addListIdSubtasks(idSubtask); // Добавили id в список подзадач эпика

                if (epic.getStatus() == TaskStatus.DONE) { // Если стаус эпика завершен, меняем на в работе
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }
            } else {
                System.out.println("Эпик не создан или не найден!"); // Если эпик не найден
            }
        }
    }

    @Override
    public ArrayList<Task> getListedOfAllTasks() { // Получение списка всех задач.
        return new ArrayList<>(tasks.values()); // Возвращаем список
    }

    @Override
    public ArrayList<Epic> getListedOfAllEpics() { // Получение списка всех эпиков.
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getListedOfAllSubtasks() { // Получение списка всех подзадач.
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deletAllTasks() { // Удаление всех задач.
        if (!tasks.isEmpty()) {
            tasks.clear();
        } else {
            System.out.println("Список задач пуст!");
        }
    }

    @Override
    public void deletAllEpics() { // Удаление всех эпиков и соотвественно подзадач
        // Согласно ТЗ: "Для каждой подзадачи известно, в рамках какого эпика она выполняется."
        if (!epics.isEmpty()) {
            epics.clear();
            subtasks.clear();
        } else {
            System.out.println("Список эпиков пуст!");
        }
    }

    @Override
    public void deletAllSubtasks() { // Удаление всех подзадач
        // Согласно ТЗ: "если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW."
        for (Subtask subtask : subtasks.values()) {
            Epic epic = epics.get(subtask.getEpicId());
            if (!epic.getListIdSubtasks().isEmpty()) { // Если список подзадач Эпика не пуст - очищаем.
                epic.clearListIdSubtasks();
                updateStatusEpic(epic);
            }
        }
        subtasks.clear(); // чистим список подзадач
    }

    @Override
    public Task getByIDTask(int id) { // Получение по идентификатору.
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getByIDEpic(int id) { // Получение по идентификатору.
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getByIDSubtask(int id) { // Получение по идентификатору.
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void updateTask(Task task) { // Обновление. Новая версия объекта с верным id передаётся в виде параметра.
        if (tasks.containsKey(task.getUin())) {
            tasks.put(task.getUin(), task);
        } else {
            System.out.println("Задача не найдена!");
        }
    }

    @Override
    public void updateEpic(Epic epic) { // Обновление. Новая версия объекта с верным id передаётся в виде параметра.
        if (epics.containsKey(epic.getUin())) {
            epics.put(epic.getUin(), epic);
        } else {
            System.out.println("Эпик не найден!");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) { // Обновление.
        if (subtasks.containsKey(subtask.getUin())) {
            subtasks.put(subtask.getUin(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            updateStatusEpic(epic);
        } else {
            System.out.println("Подзадача не найдена!");
        }
    }

    @Override
    public void deletTaskByID(int id) { // Удаление по идентификатору задачи.
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Идентификатор задачи указан не верно!");
        }
    }

    @Override
    public void deletEpicByID(int id) {// Удаление по идентификатору эпика.
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (int idSubtask : epic.getListIdSubtasks()) { // удаляем все подзадачи данного эпика
                subtasks.remove(idSubtask);
            }
            epics.remove(id); // Удаляем Эпик
        } else {
            System.out.println("Идентификатор эпика указан не верно!");
        }
    }

    @Override
    public void deletSubtaskByID(Integer id) { // Удаление по идентификатору подзадачи.
        if (subtasks.containsKey(id)) {
            Epic epic = epics.get(subtasks.get(id).getEpicId());
            epic.removeListIdSubtask(id); // Удаляем подзадачу из списка в Эпике
            subtasks.remove(id); // Удаляем подзадачу
            updateStatusEpic(epic); // Обновляем статус Эпика
        }
    }

    public void updateStatusEpic(Epic epic) {
        int newStatus = 0;
        int doneStatus = 0;

        if (epic.getListIdSubtasks().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            for (int idSubtask : epic.getListIdSubtasks()) { // Проверяем статусы задач
                TaskStatus statusSubtask = subtasks.get(idSubtask).getStatus();
                switch (statusSubtask) {
                    case NEW:
                        newStatus++;
                        break;
                    case DONE:
                        doneStatus++;
                        break;
                }
            }
            if (newStatus == epic.getListIdSubtasks().size()) {
                epic.setStatus(TaskStatus.NEW);
            } else if (doneStatus == epic.getListIdSubtasks().size()) {
                epic.setStatus(TaskStatus.DONE);
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }

    @Override
    public ArrayList<Subtask> getListAllSubtasksOfEpic(int id) { // Получение списка всех подзадач определённого эпика.
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Subtask> subtaskListOfEpic = new ArrayList<>();
            for (int idSubtask : epic.getListIdSubtasks()) {
                subtaskListOfEpic.add(subtasks.get(idSubtask));
            }
            return subtaskListOfEpic;
        } else {
            System.out.println("Идентификатор эпика указан не верно!");
            return null;
        }
    }
}