package ru.yandex.practicum.tasktracker.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Epic extends Task {
    private ArrayList<Integer> listIdSubtasks = new ArrayList<>(); // Список уин подзадач

    public ArrayList<Integer> getListIdSubtasks() {
        return listIdSubtasks;
    }

    public void removeListIdSubtasks(Integer index) {
        this.listIdSubtasks.remove(index);
    }

    public void addListIdSubtasks(int idSubtask) {
        this.listIdSubtasks.add(idSubtask);
    }

    public void clearListIdSubtasks() {
        this.listIdSubtasks.clear();
    }

    @Override
    public String toString() { // нужен для информативного результата
        return "ru.yandex.practicum.tasktracker.model.Epic{" +
                "nameEpic='" + super.getName() + '\'' +
                ", EpicDescription='" + super.getDescription() + '\'' +
                ", uinEpic=" + super.getUin() +
                ", status=" + super.getStatus() +
                ", listIdSubtasks=" + Arrays.asList(listIdSubtasks) +
                '}';
    }
}