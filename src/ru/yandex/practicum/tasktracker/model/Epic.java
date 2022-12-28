package ru.yandex.practicum.tasktracker.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Epic extends Task {
    private LocalDateTime endTime; // дата и время завершения эпика (расчетное).
    private final ArrayList<Integer> listIdSubtasks = new ArrayList<>(); // Список уин подзадач

    public Epic(int uin, TypesTasks type, String name, TaskStatus status, String description
            , int duration, LocalDateTime startTime) {
        super(uin, type, name, status, description, duration, startTime);
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

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() { // нужен для информативного результата
        return  "Epic{" +
                "uin=" + uin +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", startTime=" + ((startTime == null) ? "null" : startTime.format(formatter)) +
                ", endTime=" + ((endTime == null) ? "null" : endTime.format(formatter)) +
                ", listIdSubtasks=" + Arrays.asList(listIdSubtasks) +
                '}';
    }
}