package ru.yandex.practicum.tasktracker.model;

import java.time.LocalDateTime;

public class Subtask extends Task {
    private Integer epicId;

    public Subtask(int uin, TypesTasks type, String name, TaskStatus status, String description, int duration, LocalDateTime startTime, Integer epicId) {
        super(uin, type, name, status, description, duration, startTime);
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
        return  "Subtask{" +
                "uin=" + uin +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description +
                ", duration=" + duration + '\'' +
                ", startTime=" + ((startTime == null) ? "null" : startTime.format(formatter)) +
                ", endTime=" + ((getEndTime() == null) ? "null" : getEndTime().format(formatter)) +
                ", epicId=" + epicId +
                '}';
    }
}