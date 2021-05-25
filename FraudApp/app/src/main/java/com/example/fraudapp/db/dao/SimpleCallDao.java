package com.example.fraudapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fraudapp.db.entity.SimpleCallEntity;

import java.util.List;
// Database Queries interface through which we will interact with database
// these queries are specific for Simple Calls
@Dao
public interface SimpleCallDao {
    // get all data from database
    @Query("SELECT * FROM `simple call` ORDER BY id DESC")
    LiveData<List<SimpleCallEntity>> getAllNotes();
    // get specific data from database by id
    @Query("SELECT * FROM `simple call` WHERE id=:id")
    SimpleCallEntity getNoteById(int id);
    // get specific data from database
    @Query("SELECT * FROM `simple call` WHERE id=:id")
    LiveData<SimpleCallEntity> getNote(int id);
    // insert data into database
    @Insert
    long insert(SimpleCallEntity note);
    // update data into database
    @Update
    void update(SimpleCallEntity note);
    // delete specific data from database
    @Delete
    void delete(SimpleCallEntity note);
    // delete all data from database
    @Query("DELETE FROM `simple call`")
    void deleteAll();
}
