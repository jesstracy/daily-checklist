package com.tiy;

/**
 * Created by jessicatracy on 11/15/16.
 */
public class ToDo {
    private int id;
    private String description;
    private boolean isDone;
    private int userId;

    public ToDo() {
    }

    public ToDo(String description, boolean isDone, int userId) {
        this.description = description;
        this.isDone = isDone;
        this.userId = userId;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
