package com.tiy;

/**
 * Created by jessicatracy on 11/15/16.
 */
public class ToDo {
    private int id;
    private String description;
    private boolean isDone;
    private User user;

    public ToDo() {
    }

    public ToDo(String description, boolean isDone, User user) {
        this.description = description;
        this.isDone = isDone;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
