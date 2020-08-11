package com.chen.dress2impress.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.outfit.OutfitDao;

@Database(entities = {Outfit.class}, version = 2)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract OutfitDao outfitDao();
}
