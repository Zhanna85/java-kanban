import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    HashMap<Integer, Subtask> subtasks = new HashMap<>(); //таблица подзадач
    HashMap<Integer, Task> tasks = new HashMap<>(); //таблица задач
    HashMap<Integer, Epic> epics = new HashMap<>(); //таблица эпиков

    int id; //УИН

    public void creatTask(Task task){ //Функция создания задачи. Сам объект должен передаваться в качестве параметра.

        if (task!= null) {
            int id = 0;
            task.setStatus(TaskStatus.NEW); // при создании статус всегда new

            if (tasks.isEmpty()) {
                id++;
            } else {
                id = tasks.size() + 1;
            }
            task.setUin(id);
            tasks.put(id, task); // добавили задачу в мапу
        }

    }

    public void creatEpic(Epic epic){ //Функция создания эпика. Сам объект должен передаваться в качестве параметра.
        if (epic!= null) {
            int id = 0;
            epic.setStatus(TaskStatus.NEW); // при создании статус всегда new

            if (epics.isEmpty()) {
                id++;
            } else {
                id = epics.size() + 1;
            }
            epic.setUin(id);
            epics.put(id, epic); // добавили эпик в мапу
            epic.listIdSubtasks = new ArrayList<>(); //создаем список подзадач, т.к. подразумевается, что у эпика будут
            //подзадачи
        }

    }

    public void creatSubtask(Subtask subtask) { //Функция создания подзадачи.
        // Сам объект должен передаваться в качестве параметра.
        // Для каждой подзадачи известно, в рамках какого эпика она выполняется.
        // id эписка указывается при создании объекта и передается с объектом.
        // подзадача может создаваться даже, если эпик в статусе DONE (уточненное инфо)

        if (subtask!= null) {

            if (epics.containsKey(subtask.getEpicId())) { //проверяем наличие эпика

                int id = 0;
                subtask.setStatus(TaskStatus.NEW); // при создании статус всегда new

                if (subtasks.isEmpty()) {
                    id++;

                } else {
                    id = subtasks.size() + 1;

                }
                subtask.setUin(id);
                subtasks.put(id, subtask); // добавили подзадачу в мапу
                Epic epic = epics.get(subtask.getEpicId());
                epic.listIdSubtasks.add(id); //добавили id в список подзадач эпика
                if (epic.getStatus() == TaskStatus.DONE) { //сли стаус эпика завершен, меняем на в работе
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }

            } else {
                System.out.println("Эпик не создан или не найден!"); //если эпик не найден
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

        for (Subtask subtask: subtasks.values()) {
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

    public void updateTask() { //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.

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

            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getEpicId());
            if (epic.getStatus() == TaskStatus.DONE || epic.getStatus() == TaskStatus.IN_PROGRESS) {
                if (epic.listIdSubtasks.size() == 1) {
                    epic.setStatus(TaskStatus.NEW);
                } else if (epic.listIdSubtasks.size() > 1) {

                }

                subtasks.remove(id);

            } else if (epic.getStatus() == TaskStatus.IN_PROGRESS){

            }

        }


    }


    public ArrayList<Subtask> getListAllSubtasksOfEpic(int id) { //Получение списка всех подзадач определённого эпика.

        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList<Subtask> subtaskListOfEpic = new ArrayList<>();
            for (int idSubtask : epic.listIdSubtasks) { //удаляем все подзадачи данного эпика
                subtaskListOfEpic.add(subtasks.get(idSubtask));
            }

            return subtaskListOfEpic;

        } else {

            System.out.println("Идентификатор эпика указан не верно!");
            return null;
        }

    }
}
