package com.example.fraudapp.db;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import com.example.fraudapp.db.dao.SimpleCallDao;
import com.example.fraudapp.db.entity.SimpleCallEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by ravi on 05/02/18.
 */
// simple call class used for interaction with db
    // in this class we used async task to get, set, delete and update
public class SimpleCallRepository {

    private SimpleCallDao mSimpleCallDao;
    private LiveData<List<SimpleCallEntity>> mAllNotes;

    public SimpleCallRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mSimpleCallDao = db.simpleCallDao();
        mAllNotes = mSimpleCallDao.getAllNotes();
    }

    public LiveData<List<SimpleCallEntity>> getAllNotes() {
        return mAllNotes;
    }

    public SimpleCallEntity getNote(int noteId) throws ExecutionException, InterruptedException {
        return new getNoteAsync(mSimpleCallDao).execute(noteId).get();
    }

    public void insertNote(SimpleCallEntity note) {
        new insertNotesAsync(mSimpleCallDao).execute(note);
    }

    public void updateNote(SimpleCallEntity note) {
        new updateNotesAsync(mSimpleCallDao).execute(note);
    }

    public void deleteNote(SimpleCallEntity note) {
        new deleteNotesAsync(mSimpleCallDao).execute(note);
    }

    public void deleteAllNotes() {
        new deleteAllNotesAsync(mSimpleCallDao).execute();
    }

    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the following error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

    private static class getNoteAsync extends AsyncTask<Integer, Void, SimpleCallEntity> {

        private SimpleCallDao mSimpleCallDaoAsync;

        getNoteAsync(SimpleCallDao animalDao) {
            mSimpleCallDaoAsync = animalDao;
        }

        @Override
        protected SimpleCallEntity doInBackground(Integer... ids) {
            return mSimpleCallDaoAsync.getNoteById(ids[0]);
        }
    }

    private static class insertNotesAsync extends AsyncTask<SimpleCallEntity, Void, Long> {

        private SimpleCallDao mSimpleCallDaoAsync;

        insertNotesAsync(SimpleCallDao SimpleCallDao) {
            mSimpleCallDaoAsync = SimpleCallDao;
        }

        @Override
        protected Long doInBackground(SimpleCallEntity... notes) {
            long id = mSimpleCallDaoAsync.insert(notes[0]);
            return id;
        }
    }

    private static class updateNotesAsync extends AsyncTask<SimpleCallEntity, Void, Void> {

        private SimpleCallDao mSimpleCallDaoAsync;

        updateNotesAsync(SimpleCallDao SimpleCallDao) {
            mSimpleCallDaoAsync = SimpleCallDao;
        }

        @Override
        protected Void doInBackground(SimpleCallEntity... notes) {
            mSimpleCallDaoAsync.update(notes[0]);
            return null;
        }
    }

    private static class deleteNotesAsync extends AsyncTask<SimpleCallEntity, Void, Void> {

        private SimpleCallDao mSimpleCallDaoAsync;

        deleteNotesAsync(SimpleCallDao SimpleCallDao) {
            mSimpleCallDaoAsync = SimpleCallDao;
        }

        @Override
        protected Void doInBackground(SimpleCallEntity... notes) {
            mSimpleCallDaoAsync.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllNotesAsync extends AsyncTask<SimpleCallEntity, Void, Void> {

        private SimpleCallDao mSimpleCallDaoAsync;

        deleteAllNotesAsync(SimpleCallDao SimpleCallDao) {
            mSimpleCallDaoAsync = SimpleCallDao;
        }

        @Override
        protected Void doInBackground(SimpleCallEntity... notes) {
            mSimpleCallDaoAsync.deleteAll();
            return null;
        }
    }
}
