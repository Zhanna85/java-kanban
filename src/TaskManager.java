import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    HashMap<Integer, Subtask> subtasks = new HashMap<>(); //таблица подзадач
    HashMap<Integer, Task> tasks = new HashMap<>(); //таблица задач
    HashMap<Integer, Epic> epics = new HashMap<>(); //таблица эпиков

    int idTask = 0; //УИН задач
    int idEpic = 0; //УИН эпиков
    int idSubtask = 0; //УИН подзадач

    public void creatTask(Task task) { //Функция создания задачи. Сам объект должен передаваться в качестве параметра.

        if (task != null) {
            idTask++;
            task.setStatus(TaskStatus.NEW); // при создании статус всегда new
            task.setUin(idTask);
            tasks.put(idTask, task); // добавили задачу в мапу
        }

    }

    public void creatEpic(Epic epic) { //Функция создания эпика. Сам объект должен передаваться в качестве параметра.
        if (epic != null) {
            idEpic++;
            epic.setStatus(TaskStatus.NEW); // при создании статус всегда new
            epic.setUin(idEpic);
            epics.put(idEpic, epic); // добавили эпик в мапу
            epic.listIdSubtasks = new ArrayList<>(); //создаем список подзадач, т.к. подразумевается, что у эпика будут
            //подзадачи
        }

    }

    public void creatSubtask(Subtask subtask) { //Функция создания подзадачи.
        // Сам объект должен передаваться в качестве параметра.
        // Для каждой подзадачи известно, в рамках какого эпика она выполняется.
        // id эписка указывается при создании объекта и передается с объектом.
        // подзадача может создаваться даже, если эпик в статусе DONE (уточненное инфо)

        if (subtask != null) {

            if (epics.containsKey(subtask.getEpicId())) { //проверяем наличие эпика

                idSubtask++;
                subtask.setStatus(TaskStatus.NEW); // при создании статус всегда new
                subtask.setUin(idSubtask);
                subtasks.put(idSubtask, subtask); // добавили подзадачу в мапу
                Epic epic = epics.get(subtask.getEpicId());
                epic.listIdSubtasks.add(idSubtask); // добавили id в список подзадач эпика
                if (epic.getStatus() == TaskStatus.DONE) { // если стаус эпика завершен, меняем на в работе
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }

            } else {
                System.out.println("Эпик не создан или не найден!"); // если эпик не найден
            }
        }

    }

    public ArrayList<Task> getListedOfAllTasks() { //Получение списка всех задач.

        ArrayList<Task> taskArrayList = new ArrayList<>(); //создаем список задач

        taskArrayList.addAll(tasks.values()); //добавим в список все значения HashMap tasks

        return taskArrayList; //возвращаем список

    }

    public ArrayList<Epic> getListedOfAllEpics() { //Получение списка всех эпиков.

        ArrayList<Epic> epicArrayList = new ArrayList<>();

        epicArrayList.addAll(epics.values());

        return epicArrayList;

    }

    public ArrayList<Subtask> getListedOfAllSubtasks() { //Получение списка всех подзадач.

        ArrayList<Subtask> subtaskArrayList = new ArrayList<>();

        subtaskArrayList.addAll(subtasks.values());

        return subtaskArrayList;

    }


    public void deletAllTasks() { //Удаление всех задач.

        if (!tasks.isEmpty()) {

            tasks.clear();
        } else {

            System.out.println("Список задач пуст!");
        }
    }


    public void deletAllEpics() { //Удаление всех эпиков и соотвественно подзадач, т.к. согласно ТЗ:
        //"Для каждой подзадачи известно, в рамках какого эпика она выполняется."
        if (!epics.isEmpty()) {
            epics.clear();
            subtasks.clear();
        } else {

            System.out.println("Список эпиков пуст!");
        }

    }

    public void deletAllSubtasks() { //Удаление всех подзадач, переводим связанный эпик в статус new, т.к. согласно ТЗ:
        //"если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW."

        for (Subtask subtask : subtasks.values()) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic.getStatus() != TaskStatus.NEW) {
                epic.setStatus(TaskStatus.NEW);

            }
            subtasks.remove(subtask.getUin());
        }


    }

    public Task getByIDTask(int id) { //Получение по идентификатору.

        if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else {

            System.out.println("Идентификатор задачи указан не верно!");
            return null;
        }

    }

    public Epic getByIDEpic(int id) { //Получение по идентификатору.

        if (epics.containsKey(id)) {
            return epics.get(id);
        } else {

            System.out.println("Идентификатор эпика указан не верно!");
            return null;
        }

    }

    public Subtask getByIDSubtask(int id) { //Получение по идентификатору.

        if (subtasks.containsKey(id)) {

            return subtasks.get(id);
        } else {

            System.out.println("Идентификатор подзадачи указан не верно!");
            return null;
        }

    }

    public void updateTask(Task task) { //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.

        tasks.put(task.getUin(), task);

    }

    public void updateEpic(Epic epic) { //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.

        epics.put(epic.getUin(), epic);

    }

    public void updateSubtask(Subtask subtask) { //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.

        subtasks.put(subtask.getUin(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        updateStatusEpic(epic);


    }

    public void deletTaskByID(int id) {//Удаление по идентификатору задачи.

        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {

            System.out.println("Идентификатор задачи указан не верно!");
        }
    }

    public void deletEpicByID(int id) {//Удаление по идентификатору эпика.

        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (int idSubtask : epic.listIdSubtasks) { //удаляем все подзадачи данного эпика
                subtasks.remove(idSubtask);
            }

            epics.remove(id);

        } else {

            System.out.println("Идентификатор эпика указан не верно!");
        }

    }

    public void deletSubtaskByID(int id) { //Удаление по идентификатору подзадачи.

        if (subtasks.containsKey(id)) {

            Epic epic = epics.get(subtasks.get(id).getEpicId());
            for (int i = 0; i < epic.listIdSubtasks.size(); i++) { //удалаем id подзадачи из списка эпика
                if (epic.listIdSubtasks.get(i) == id) {
                    epic.listIdSubtasks.remove(i);
                }
            }

            subtasks.remove(id); // удаляем подзадачу

            if (epic.getStatus() != TaskStatus.NEW && epic.listIdSubtasks.size() == 0) {

                epic.setStatus(TaskStatus.NEW);

            } else if (epic.getStatus() == TaskStatus.IN_PROGRESS && epic.listIdSubtasks.size() > 1) { // если у эпика больше 1 подзадачи
                updateStatusEpic(epic);
            }

        }

    }

    public void updateStatusEpic(Epic epic) {

        int newStatus = 0;
        int doneStatus = 0;

        for (int idSubtask : epic.listIdSubtasks) { // Проверяем статусы задач
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
        if (newStatus == epic.listIdSubtasks.size()) {

            epic.setStatus(TaskStatus.NEW);

        } else if (doneStatus == epic.listIdSubtasks.size()) {

            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public ArrayList<Subtask> getListAllSubtasksOfEpic(int id) { //Получение списка всех подзадач определённого эпика.

        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Subtask> subtaskListOfEpic = new ArrayList<>();
            for (int idSubtask : epic.listIdSubtasks) {
                subtaskListOfEpic.add(subtasks.get(idSubtask));
            }

            return subtaskListOfEpic;

        } else {

            System.out.println("Идентификатор эпика указан не верно!");
            return null;
        }

    }
}
