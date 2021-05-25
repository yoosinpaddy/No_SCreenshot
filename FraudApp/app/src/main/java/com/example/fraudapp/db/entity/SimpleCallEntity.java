package com.example.fraudapp.db.entity;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Created by ravi on 05/02/18.
 */
// table for simple calls other are same as in note entity
@Entity(tableName = "simple call")
public class SimpleCallEntity {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "call")
    String note;

    @ColumnInfo(name = "timestamp")
    Date timestamp;

    public SimpleCallEntity(@NonNull String note) {
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
