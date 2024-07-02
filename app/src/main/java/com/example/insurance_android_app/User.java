package com.example.insurance_android_app;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String deviceId;


    public User() {}

    public User(int id, String username, String password, String email, String deviceId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.deviceId = deviceId;
    }

    public int getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}