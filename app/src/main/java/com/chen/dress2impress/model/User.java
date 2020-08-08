package com.chen.dress2impress.model;

import androidx.annotation.NonNull;

public class User {
    @NonNull
    public String id;
    public String name;
    public String email;
    public String imgUrl;
    long lastUpdated;

    public User() {
    }

    public User(String id, String name, String email, String imgUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImgUrl() {
        return imgUrl;
    }


    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }
}
