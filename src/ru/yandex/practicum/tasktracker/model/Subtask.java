package ru.yandex.practicum.tasktracker.model;

public class Subtask extends Task {
    private Integer epicId;

    public Subtask(int uin, TypesTasks type, String name, TaskStatus status, String description, int epicId) {
        super(uin, type, name, status, description);
        this.epicId = epicId;
    }

    @Override
    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "uin=" + super.getUin() +
                ", type=" + super.getType() +
                ", name='" + super.getName() + '\'' +
                ", status=" + super.getStatus() +
                ", description='" + super.getDescription() + '\'' +
                ", epicId=" + epicId +
                '}';
    }
}