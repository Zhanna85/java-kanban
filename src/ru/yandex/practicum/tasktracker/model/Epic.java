package ru.yandex.practicum.tasktracker.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Epic extends Task {
    private final ArrayList<Integer> listIdSubtasks = new ArrayList<>(); // Список уин подзадач

    public Epic(int uin, TypesTasks type, String name, TaskStatus status, String description) {
        super(uin, type, name, status, description);
    }

    public ArrayList<Integer> getListIdSubtasks() {
        return listIdSubtasks;
    }

    public void removeListIdSubtask(Integer index) {
        listIdSubtasks.remove(index);
    }

    public void addListIdSubtasks(int idSubtask) {
        listIdSubtasks.add(idSubtask);
    }

    public void clearListIdSubtasks() {
        listIdSubtasks.clear();
    }

    @Override
    public String toString() { // нужен для информативного результата
        return "Epic{" +
                "uin=" + super.getUin() +
                ", type=" + super.getType() +
                ", name='" + super.getName() + '\'' +
                ", status=" + super.getStatus() +
                ", description='" + super.getDescription() + '\'' +
                ", listIdSubtasks=" + Arrays.asList(listIdSubtasks) +
                '}';
    }
}