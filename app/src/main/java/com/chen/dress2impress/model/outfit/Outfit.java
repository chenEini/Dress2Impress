package com.chen.dress2impress.model.outfit;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Outfit implements Serializable {
    @PrimaryKey
    @NonNull
    public String id = "";
    public String ownerId;
    public String ownerName;
    public String title;
    public String imageUrl;
    public String description;
    long lastUpdated;

    public Outfit() {
    }

    public Outfit(String ownerId, String ownerName, String title, String imageUrl, String description) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Outfit(String id, String ownerId, String ownerName, String title, String imageUrl, String description) {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }
}
