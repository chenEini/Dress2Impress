package com.chen.dress2impress.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.chen.dress2impress.MyApplication;

@Database(entities = {Outfit.class}, version = 9)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract OutfitDao outfitDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context, AppLocalDbRepository.class, "dress2impress.db")
                    .fallbackToDestructiveMigration().build();
}
