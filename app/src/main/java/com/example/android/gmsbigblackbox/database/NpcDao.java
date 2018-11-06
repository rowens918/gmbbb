package com.example.android.gmsbigblackbox.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NpcDao {
    @Query("SELECT * FROM npcs ORDER BY id")
    LiveData<List<NpcCardEntry>> loadAllNpcs();

    @Insert
    void insertNpcCard(NpcCardEntry npcCard);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNpcCard(NpcCardEntry npcCard);

    @Delete
    void deleteNpcCard(NpcCardEntry npcCard);

    @Query("SELECT * FROM npcs WHERE id = :id")
    NpcCardEntry loadNpcById(int id);
}
