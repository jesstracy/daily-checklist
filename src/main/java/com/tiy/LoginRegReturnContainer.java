package com.tiy;

/**
 * Created by jessicatracy on 11/15/16.
 */
public class LoginRegReturnContainer {
    private int userId;
    private String errorMessage;

    public LoginRegReturnContainer() {
    }

    public LoginRegReturnContainer(int userId, String errorMessage) {
        this.userId = userId;
        this.errorMessage = errorMessage;
    }

    //Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
