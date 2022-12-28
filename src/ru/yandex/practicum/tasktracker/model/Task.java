package ru.yandex.practicum.tasktracker.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected int uin; // Уникальный идентификационный номер задачи, по которому её можно будет найти.
    protected TypesTasks type; // Тип задачи.
    protected String name; // Название, кратко описывающее суть задачи (например, «Переезд»).
    protected TaskStatus status; // Статус, отображающий её прогресс.
    protected String description; // Описание, в котором раскрываются детали.
    protected int duration; // Продолжительность задачи, оценка того, сколько времени она займёт в минутах (число).
    protected LocalDateTime startTime; // Дата и время, когда предполагается приступить к выполнению задачи.

    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public Task(int uin, TypesTasks type, String name, TaskStatus status, String description
            , int duration, LocalDateTime startTime) {
        this.uin = uin;
        this.type = type;
        this.name = name;
        this.status = status;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
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

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    //метод рассчитывает время завершения задачи, которое рассчитывается исходя из startTime и duration
    public LocalDateTime getEndTime() {
        if (startTime != null) {
            return startTime.plusMinutes(duration);
        }
        return null;
    }

    @Override
    public String toString() {
       return "Task{" +
               "uin=" + uin +
               ", type=" + type +
               ", name='" + name + '\'' +
               ", status=" + status +
               ", description='" + description +
               ", duration=" + duration + '\'' +
               ", startTime=" + ((startTime == null) ? "null" : startTime.format(formatter)) +
               ", endTime=" + ((getEndTime() == null) ? "null" : getEndTime().format(formatter)) +
               '}';
    }
}