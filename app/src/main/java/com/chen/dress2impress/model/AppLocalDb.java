package com.chen.dress2impress.model;

import androidx.room.Room;

import com.chen.dress2impress.MyApplication;

public class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context, AppLocalDbRepository.class, "dress2impress.db")
                    .fallbackToDestructiveMigration().build();
}
