package com.example.fraudapp.db;

import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import com.example.fraudapp.db.dao.VoipSMSDao;
import com.example.fraudapp.db.entity.VoipSMSEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by ravi on 05/02/18.
 */
// voip Sms class used for interaction with db
// in this class we used async task to get, set, delete and update
public class VoipSMSRepository {

    private VoipSMSDao mVoipSMSDao;
    private LiveData<List<VoipSMSEntity>> mAllNotes;

    public VoipSMSRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mVoipSMSDao = db.voipSmsDao();
        mAllNotes = mVoipSMSDao.getAllNotes();
    }

    public LiveData<List<VoipSMSEntity>> getAllNotes() {
        return mAllNotes;
    }

    public VoipSMSEntity getNote(int noteId) throws ExecutionException, InterruptedException {
        return new getNoteAsync(mVoipSMSDao).execute(noteId).get();
    }

    public void insertNote(VoipSMSEntity note) {
        new insertNotesAsync(mVoipSMSDao).execute(note);
    }

    public void updateNote(VoipSMSEntity note) {
        new updateNotesAsync(mVoipSMSDao).execute(note);
    }

    public void deleteNote(VoipSMSEntity note) {
        new deleteNotesAsync(mVoipSMSDao).execute(note);
    }

    public void deleteAllNotes() {
        new deleteAllNotesAsync(mVoipSMSDao).execute();
    }

    /**
     * NOTE: all write operations should be done in background thread,
     * otherwise the following error will be thrown
     * `java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.`
     */

    private static class getNoteAsync extends AsyncTask<Integer, Void, VoipSMSEntity> {

        private VoipSMSDao mVoipSMSDaoAsync;

        getNoteAsync(VoipSMSDao animalDao) {
            mVoipSMSDaoAsync = animalDao;
        }

        @Override
        protected VoipSMSEntity doInBackground(Integer... ids) {
            return mVoipSMSDaoAsync.getNoteById(ids[0]);
        }
    }

    private static class insertNotesAsync extends AsyncTask<VoipSMSEntity, Void, Long> {

        private VoipSMSDao mVoipSMSDaoAsync;

        insertNotesAsync(VoipSMSDao VoipSMSDao) {
            mVoipSMSDaoAsync = VoipSMSDao;
        }

        @Override
        protected Long doInBackground(VoipSMSEntity... notes) {
            long id = mVoipSMSDaoAsync.insert(notes[0]);
            return id;
        }
    }

    private static class updateNotesAsync extends AsyncTask<VoipSMSEntity, Void, Void> {

        private VoipSMSDao mVoipSMSDaoAsync;

        updateNotesAsync(VoipSMSDao VoipSMSDao) {
            mVoipSMSDaoAsync = VoipSMSDao;
        }

        @Override
        protected Void doInBackground(VoipSMSEntity... notes) {
            mVoipSMSDaoAsync.update(notes[0]);
            return null;
        }
    }

    private static class deleteNotesAsync extends AsyncTask<VoipSMSEntity, Void, Void> {

        private VoipSMSDao mVoipSMSDaoAsync;

        deleteNotesAsync(VoipSMSDao VoipSMSDao) {
            mVoipSMSDaoAsync = VoipSMSDao;
        }

        @Override
        protected Void doInBackground(VoipSMSEntity... notes) {
            mVoipSMSDaoAsync.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllNotesAsync extends AsyncTask<VoipSMSEntity, Void, Void> {

        private VoipSMSDao mVoipSMSDaoAsync;

        deleteAllNotesAsync(VoipSMSDao VoipSMSDao) {
            mVoipSMSDaoAsync = VoipSMSDao;
        }

        @Override
        protected Void doInBackground(VoipSMSEntity... notes) {
            mVoipSMSDaoAsync.deleteAll();
            return null;
        }
    }
}
