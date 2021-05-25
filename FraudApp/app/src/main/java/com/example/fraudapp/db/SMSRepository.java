package com.example.fraudapp.db;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import com.example.fraudapp.db.dao.SMSDao;
import com.example.fraudapp.db.entity.SMSEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by ravi on 05/02/18.
 */

// simple sms class used for interaction with db
// in this class we used async task to get, set, delete and update
public class SMSRepository {

    private SMSDao mSMSDao;
    private LiveData<List<SMSEntity>> mAllNotes;

    public SMSRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mSMSDao = db.smsDao();
        mAllNotes = mSMSDao.getAllNotes();
    }

    public LiveData<List<SMSEntity>> getAllNotes() {
        return mAllNotes;
    }

    public SMSEntity getNote(int noteId) throws ExecutionException, InterruptedException {
        return new getNoteAsync(mSMSDao).execute(noteId).get();
    }

    public void insertNote(SMSEntity note) {
        new insertNotesAsync(mSMSDao).execute(note);
    }

    public void updateNote(SMSEntity note) {
        new updateNotesAsync(mSMSDao).execute(note);
    }

    public void deleteNote(SMSEntity note) {
        new deleteNotesAsync(mSMSDao).execute(note);
    }

    public void deleteAllNotes() {
        new deleteAllNotesAsync(mSMSDao).execute();
    }

    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the following error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

    private static class getNoteAsync extends AsyncTask<Integer, Void, SMSEntity> {

        private SMSDao mSMSDaoAsync;

        getNoteAsync(SMSDao animalDao) {
            mSMSDaoAsync = animalDao;
        }

        @Override
        protected SMSEntity doInBackground(Integer... ids) {
            return mSMSDaoAsync.getNoteById(ids[0]);
        }
    }

    private static class insertNotesAsync extends AsyncTask<SMSEntity, Void, Long> {

        private SMSDao mSMSDaoAsync;

        insertNotesAsync(SMSDao SMSDao) {
            mSMSDaoAsync = SMSDao;
        }

        @Override
        protected Long doInBackground(SMSEntity... notes) {
            long id = mSMSDaoAsync.insert(notes[0]);
            return id;
        }
    }

    private static class updateNotesAsync extends AsyncTask<SMSEntity, Void, Void> {

        private SMSDao mSMSDaoAsync;

        updateNotesAsync(SMSDao SMSDao) {
            mSMSDaoAsync = SMSDao;
        }

        @Override
        protected Void doInBackground(SMSEntity... notes) {
            mSMSDaoAsync.update(notes[0]);
            return null;
        }
    }

    private static class deleteNotesAsync extends AsyncTask<SMSEntity, Void, Void> {

        private SMSDao mSMSDaoAsync;

        deleteNotesAsync(SMSDao SMSDao) {
            mSMSDaoAsync = SMSDao;
        }

        @Override
        protected Void doInBackground(SMSEntity... notes) {
            mSMSDaoAsync.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllNotesAsync extends AsyncTask<SMSEntity, Void, Void> {

        private SMSDao mSMSDaoAsync;

        deleteAllNotesAsync(SMSDao SMSDao) {
            mSMSDaoAsync = SMSDao;
        }

        @Override
        protected Void doInBackground(SMSEntity... notes) {
            mSMSDaoAsync.deleteAll();
            return null;
        }
    }
}
