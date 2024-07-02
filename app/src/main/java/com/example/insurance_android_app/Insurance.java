package com.example.insurance_android_app;

import android.os.Build;

import java.time.LocalDateTime;

public class Insurance {
    private int id;
    private String name;
    private int userId;
    private LocalDateTime expiryDate;

    public Insurance() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.expiryDate = LocalDateTime.now().plusDays(365);
        }
    }

    public Insurance(int id, String name, int userId, int expiryD) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.expiryDate = LocalDateTime.now().plusDays(expiryD);
        }
    }

    public Insurance(int id, String name, int userId, String expiryD) {
        this.id = id;
        this.name = name;
        this.userId = userId;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            this.expiryDate = LocalDateTime.parse(expiryD);
//        }
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

