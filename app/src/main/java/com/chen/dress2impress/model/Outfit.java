package com.chen.dress2impress.model;

import androidx.annotation.NonNull;

public class Outfit {
    @NonNull
    public String id;
    public String title;
    public String description;
    public String purchaseUrl;
    long lastUpdated;

    public Outfit() {
    }

    public Outfit(String id, String title, String description, String purchaseUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.purchaseUrl = purchaseUrl;
    }

    public void setId(@NonNull String id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) { this.description = description; }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }


    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }
}
