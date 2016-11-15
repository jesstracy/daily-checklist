package com.tiy;

/**
 * Created by jessicatracy on 11/15/16.
 */
public class LoginRegReturnContainer {
    private User user;
    private String errorMessage;

    public LoginRegReturnContainer() {
    }

    public LoginRegReturnContainer(User user, String errorMessage) {
        this.user = user;
        this.errorMessage = errorMessage;
    }

    //Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
