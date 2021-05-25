package com.example.fraudapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fraudapp.db.entity.SMSEntity;

import java.util.List;
// Database Queries interface through which we will interact with database
// these queries are specific for simple sms
@Dao
public interface SMSDao {
    // get all data from database
    @Query("SELECT * FROM `simple sms notes` ORDER BY id DESC")
    LiveData<List<SMSEntity>> getAllNotes();
    // get specific data from database by id
    @Query("SELECT * FROM `simple sms notes` WHERE id=:id")
    SMSEntity getNoteById(int id);
    // get specific data from database
    @Query("SELECT * FROM `simple sms notes` WHERE id=:id")
    LiveData<SMSEntity> getNote(int id);
    // insert data into database
    @Insert
    long insert(SMSEntity note);
    // update data into database
    @Update
    void update(SMSEntity note);
    // delete specific data from database
    @Delete
    void delete(SMSEntity note);
    // delete all data from database
    @Query("DELETE FROM `simple sms notes`")
    void deleteAll();
}
