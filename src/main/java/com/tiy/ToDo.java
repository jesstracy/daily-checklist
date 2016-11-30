package com.tiy;

import java.time.LocalDateTime;

/**
 * Created by jessicatracy on 11/15/16.
 */
public class ToDo {
    private int id;
    private String description;
    private boolean isDone;
    private int userId;
    private String statusString;
    private ToDoStatus status;

    public ToDo() {
    }

    public ToDo(int id, String description, boolean isDone, int userId, ToDoStatus toDoStatus) {
        this.id = id;
        this.description = description;
        this.isDone = isDone;
        this.userId = userId;
        this.status = toDoStatus;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public ToDoStatus getStatus() {
        return status;
    }

    public void setStatus(ToDoStatus status) {
        this.status = status;
    }
}
