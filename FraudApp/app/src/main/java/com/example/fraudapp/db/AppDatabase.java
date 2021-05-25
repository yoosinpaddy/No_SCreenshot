package com.example.fraudapp.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.fraudapp.db.converter.DateConverter;
import com.example.fraudapp.db.dao.VoipCallDao;
import com.example.fraudapp.db.dao.SMSDao;
import com.example.fraudapp.db.dao.SimpleCallDao;
import com.example.fraudapp.db.dao.VoipSMSDao;
import com.example.fraudapp.db.entity.VoipCallEntity;
import com.example.fraudapp.db.entity.SMSEntity;
import com.example.fraudapp.db.entity.SimpleCallEntity;
import com.example.fraudapp.db.entity.VoipSMSEntity;


/**
 * Created by ravi on 05/02/18.
 */

@Database(entities = {VoipCallEntity.class, SMSEntity.class, SimpleCallEntity.class, VoipSMSEntity.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract VoipCallDao voipCallDao();
    public abstract SMSDao smsDao();
    public abstract VoipSMSDao voipSmsDao();
    public abstract SimpleCallDao simpleCallDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "notes_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}