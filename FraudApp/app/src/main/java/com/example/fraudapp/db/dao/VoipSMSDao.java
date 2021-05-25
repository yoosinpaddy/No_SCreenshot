package com.example.fraudapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fraudapp.db.entity.VoipSMSEntity;

import java.util.List;
// Database Queries interface through which we will interact with database
// these queries are specific for Voip sms
@Dao
public interface VoipSMSDao {
    // get all data from database
    @Query("SELECT * FROM `sms notes` ORDER BY id DESC")
    LiveData<List<VoipSMSEntity>> getAllNotes();
    // get specific data from database by id
    @Query("SELECT * FROM `sms notes` WHERE id=:id")
    VoipSMSEntity getNoteById(int id);
    // get specific data from database
    @Query("SELECT * FROM `sms notes` WHERE id=:id")
    LiveData<VoipSMSEntity> getNote(int id);
    // insert data into database
    @Insert
    long insert(VoipSMSEntity note);
    // update data into database
    @Update
    void update(VoipSMSEntity note);
    // delete specific data from database
    @Delete
    void delete(VoipSMSEntity note);
    // delete all data from database
    @Query("DELETE FROM `sms notes`")
    void deleteAll();
}
