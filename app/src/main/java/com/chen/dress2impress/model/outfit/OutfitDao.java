package com.chen.dress2impress.model.outfit;

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
    LiveData<List<Outfit>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Outfit... outfits);

    @Delete
    void delete(Outfit outfit);
}
