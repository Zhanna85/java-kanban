public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");

        TaskManager manager = new TaskManager();

        Task newTask1 = new Task();
        newTask1.setName("Задача №1");
        newTask1.setDescription("Описание задачи №1");
        newTask1.setStatus(TaskStatus.IN_PROGRESS);
        manager.creatTask(newTask1);

        Task newTask2 = new Task();
        newTask2.setName("Задача №2");
        newTask2.setDescription("Описание задачи №2");
        manager.creatTask(newTask2);

        Epic newEpic1 = new Epic();
        newEpic1.setName("Эпик №1");
        newEpic1.setDescription("Описание эпика №1");
        manager.creatEpic(newEpic1);

        Subtask newSubtask1 = new Subtask();
        newSubtask1.setEpicId(1);
        newSubtask1.setStatus(TaskStatus.IN_PROGRESS);
        newSubtask1.setName("Подзадача №1 эпика №1");
        newSubtask1.setDescription("Описание подзадачи №1 эпика №1");
        manager.creatSubtask(newSubtask1);

        Subtask newSubtask2 = new Subtask();
        newSubtask2.setEpicId(1);
        newSubtask2.setName("Подзадача №2 эпика №1");
        newSubtask2.setDescription("Описание подзадачи №2 эпика №1");
        manager.creatSubtask(newSubtask2);

        Epic newEpic2 = new Epic();
        newEpic2.setName("Эпик №2");
        newEpic2.setDescription("Описание эпика №2");
        manager.creatEpic(newEpic2);

        Subtask newSubtask = new Subtask();
        newSubtask.setEpicId(2);
        newSubtask.setName("Подзадача №1 эпика №2");
        newSubtask.setDescription("Описание подзадачи №1 эпика №2");
        manager.creatSubtask(newSubtask);

        System.out.println(manager.getListedOfAllEpics());
        System.out.println(manager.getListedOfAllTasks());
        System.out.println(manager.getListedOfAllSubtasks());

        //меняем статусы созданных объектов
        newTask1.setUin(1);
        newTask1.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(newTask1);

        newTask2.setUin(2);
        newTask2.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(newTask2);

        newSubtask1.setEpicId(1);
        newSubtask1.setUin(1);
        newSubtask1.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(newSubtask1);

        newSubtask2.setEpicId(1);
        newSubtask2.setUin(2);
        newSubtask2.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(newSubtask2);

        newSubtask.setEpicId(2);
        newSubtask.setUin(3);
        newSubtask.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(newSubtask);

        System.out.println(manager.getListedOfAllEpics());
        System.out.println(manager.getListedOfAllTasks());
        System.out.println(manager.getListedOfAllSubtasks());

        //удаляем эпик и задачу
        manager.deletEpicByID(2);
        manager.deletTaskByID(1);

        System.out.println(manager.getListedOfAllEpics());
        System.out.println(manager.getListedOfAllTasks());
        System.out.println(manager.getListedOfAllSubtasks());

    }
}
