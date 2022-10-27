package ru.yandex.practicum.tasktracker.model;

public class Task {
    private String name; // Название, кратко описывающее суть задачи (например, «Переезд»).
    private String description; // Описание, в котором раскрываются детали.
    private int uin; // Уникальный идентификационный номер задачи, по которому её можно будет найти.
    private TaskStatus status; // Статус, отображающий её прогресс.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUin() {
        return uin;
    }

    public void setUin(int uin) {
        this.uin = uin;
    }

    @Override
    public String toString() {
        return "Task{" +
                "nameTask='" + name + '\'' +
                ", taskDescription='" + description + '\'' +
                ", uinTask=" + uin +
                ", status=" + status +
                '}';
    }
}