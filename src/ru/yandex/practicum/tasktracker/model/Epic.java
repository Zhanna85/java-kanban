package ru.yandex.practicum.tasktracker.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Epic extends Task {
    private final ArrayList<Integer> listIdSubtasks = new ArrayList<>(); // Список уин подзадач

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
                "nameEpic='" + super.getName() + '\'' +
                ", EpicDescription='" + super.getDescription() + '\'' +
                ", uinEpic=" + super.getUin() +
                ", status=" + super.getStatus() +
                ", listIdSubtasks=" + Arrays.asList(listIdSubtasks) +
                '}';
    }
}