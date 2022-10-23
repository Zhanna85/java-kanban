public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        TaskManager manager = new TaskManager();
        Task newTask = new Task();
        newTask.setName(" Новая задача");
        newTask.setDescription("Описание задачи");
        newTask.setStatus(TaskStatus.NEW);
        manager.creatTask(newTask);

        Task newTask1 = new Task();
        newTask1.setName(" Новая задача1");
        newTask1.setDescription("Описание задачи1");
        newTask1.setStatus(TaskStatus.NEW);
        manager.creatTask(newTask1);

        System.out.println(manager.tasks);

        Epic newEpic = new Epic();
        newEpic.setStatus(TaskStatus.NEW);
        newEpic.setName("новый Эпик");
        newEpic.setDescription("создать позадачу");
        manager.creatEpic(newEpic);


        Subtask newSubtask = new Subtask();
        newSubtask.setEpicId(1);
        newSubtask.setStatus(TaskStatus.NEW);
        newSubtask.setName("Новая подзадача");
        newSubtask.setDescription("Описание подзадачи");
        manager.creatSubtask(newSubtask);

        System.out.println(manager.epics);
        System.out.println(manager.subtasks);

        System.out.println(manager.getListedOfAllTasks());

    }
}
