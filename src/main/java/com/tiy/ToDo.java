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

    public ToDo(int id, String description, boolean isDone, int userId) {
        this.id = id;
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
}
