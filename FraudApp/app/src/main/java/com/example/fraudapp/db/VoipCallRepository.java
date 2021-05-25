package com.example.fraudapp.db;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import com.example.fraudapp.db.dao.VoipCallDao;
import com.example.fraudapp.db.entity.VoipCallEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by ravi on 05/02/18.
 */

public class VoipCallRepository {

    private VoipCallDao mVoipCallDao;
    private LiveData<List<VoipCallEntity>> mAllNotes;

    public VoipCallRepository(Application application) {
        // init db here
        AppDatabase db = AppDatabase.getDatabase(application);
        // init get voip call database access
        mVoipCallDao = db.voipCallDao();
        //get all calls data from db
        mAllNotes = mVoipCallDao.getAllNotes();
    }

    public LiveData<List<VoipCallEntity>> getAllNotes() {
        //send data
        return mAllNotes;
    }

    public VoipCallEntity getNote(int noteId) throws ExecutionException, InterruptedException {
        // get not against id
        return new getNoteAsync(mVoipCallDao).execute(noteId).get();
    }

    public void insertNote(VoipCallEntity note) {
        // insert data into db
        new insertNotesAsync(mVoipCallDao).execute(note);
    }

    public void updateNote(VoipCallEntity note) {
        new updateNotesAsync(mVoipCallDao).execute(note);
    }

    public void deleteNote(VoipCallEntity note) {
        new deleteNotesAsync(mVoipCallDao).execute(note);
    }

    public void deleteAllNotes() {
        new deleteAllNotesAsync(mVoipCallDao).execute();
    }

    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the following error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */
// async task to manage database interaction and not harm to ui when data will send or get from database or
        // any other process
    private static class getNoteAsync extends AsyncTask<Integer, Void, VoipCallEntity> {
// voip call db instance
        private VoipCallDao mVoipCallDaoAsync;

        getNoteAsync(VoipCallDao animalDao) {
            mVoipCallDaoAsync = animalDao;
        }

        @Override
        protected VoipCallEntity doInBackground(Integer... ids) {
            // get voip call db instance
            return mVoipCallDaoAsync.getNoteById(ids[0]);
        }
    }
// async for insertion
    private static class insertNotesAsync extends AsyncTask<VoipCallEntity, Void, Long> {

        private VoipCallDao mVoipCallDaoAsync;

        insertNotesAsync(VoipCallDao VoipCallDao) {
            mVoipCallDaoAsync = VoipCallDao;
        }

        @Override
        protected Long doInBackground(VoipCallEntity... notes) {
            long id = mVoipCallDaoAsync.insert(notes[0]);
            return id;
        }
    }
// async for update
    private static class updateNotesAsync extends AsyncTask<VoipCallEntity, Void, Void> {

        private VoipCallDao mVoipCallDaoAsync;

        updateNotesAsync(VoipCallDao VoipCallDao) {
            mVoipCallDaoAsync = VoipCallDao;
        }

        @Override
        protected Void doInBackground(VoipCallEntity... notes) {
            mVoipCallDaoAsync.update(notes[0]);
            return null;
        }
    }
// async for deletion
    private static class deleteNotesAsync extends AsyncTask<VoipCallEntity, Void, Void> {

        private VoipCallDao mVoipCallDaoAsync;

        deleteNotesAsync(VoipCallDao VoipCallDao) {
            mVoipCallDaoAsync = VoipCallDao;
        }

        @Override
        protected Void doInBackground(VoipCallEntity... notes) {
            mVoipCallDaoAsync.delete(notes[0]);
            return null;
        }
    }
// delete all data from db
    private static class deleteAllNotesAsync extends AsyncTask<VoipCallEntity, Void, Void> {

        private VoipCallDao mVoipCallDaoAsync;

        deleteAllNotesAsync(VoipCallDao VoipCallDao) {
            mVoipCallDaoAsync = VoipCallDao;
        }

        @Override
        protected Void doInBackground(VoipCallEntity... notes) {
            mVoipCallDaoAsync.deleteAll();
            return null;
        }
    }
}
