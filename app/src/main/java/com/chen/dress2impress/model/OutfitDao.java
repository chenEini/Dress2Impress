package com.chen.dress2impress.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OutfitDao {
    @Query("select * from Outfit")
    List<Outfit> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Outfit... outfits);

    @Delete
    void delete(Outfit outfit);
}
