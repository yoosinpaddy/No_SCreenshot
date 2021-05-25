package com.example.fraudapp.db.entity;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Created by ravi on 05/02/18.
 */
// this is the model class for voip calls through this class whe will store data
    // here is the name of table in db
@Entity(tableName = "notes")
public class VoipCallEntity {
    // here is the primary key which will always remain same
    @PrimaryKey(autoGenerate = true)
    int id;
// this the string of data
    @ColumnInfo(name = "note")
    String note;
// here date and time will save
    @ColumnInfo(name = "timestamp")
    Date timestamp;
// constructor
    public VoipCallEntity(@NonNull String note) {
        this.note = note;

        // TODO - not sure about this
        this.timestamp = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
