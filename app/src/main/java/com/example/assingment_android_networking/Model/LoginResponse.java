package com.example.assingment_android_networking.Model;

public class LoginResponse {

    private String message;

    public String getMessage() {
        return message;
    }
    private int id_user;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
