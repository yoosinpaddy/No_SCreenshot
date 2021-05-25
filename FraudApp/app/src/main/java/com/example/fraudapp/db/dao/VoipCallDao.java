package com.example.fraudapp.db.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fraudapp.db.entity.VoipCallEntity;

import java.util.List;


/**
 * Created by ravi on 05/02/18.
 */
// Database Queries interface through which we will interact with database
    // these queries are specific for Voip Calls
@Dao
public interface VoipCallDao {
// get all data from database
    @Query("SELECT * FROM notes ORDER BY id DESC")
    LiveData<List<VoipCallEntity>> getAllNotes();
// get specific data from database by id
    @Query("SELECT * FROM notes WHERE id=:id")
    VoipCallEntity getNoteById(int id);
// get specific data from database
    @Query("SELECT * FROM notes WHERE id=:id")
    LiveData<VoipCallEntity> getNote(int id);
// insert data into database
    @Insert
    long insert(VoipCallEntity note);
// update data into database
    @Update
    void update(VoipCallEntity note);
// delete specific data from database
    @Delete
    void delete(VoipCallEntity note);
// delete all data from database
    @Query("DELETE FROM notes")
    void deleteAll();
}
