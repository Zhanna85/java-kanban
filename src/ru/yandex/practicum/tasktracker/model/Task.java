package ru.yandex.practicum.tasktracker.model;

public class Task {
    private int uin; // Уникальный идентификационный номер задачи, по которому её можно будет найти.
    private TypesTasks type; // Тип задачи.
    private String name; // Название, кратко описывающее суть задачи (например, «Переезд»).
    private TaskStatus status; // Статус, отображающий её прогресс.
    private String description; // Описание, в котором раскрываются детали.

    public Task(int uin, TypesTasks type, String name, TaskStatus status, String description) {
        this.uin = uin;
        this.type = type;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public TypesTasks getType() {
        return type;
    }

    public void setType(TypesTasks type) {
        this.type = type;
    }

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

    public Integer getEpicId() {
        return null;
    }

    @Override
    public String toString() {
        return "Task{" +
                "uin=" + uin +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}