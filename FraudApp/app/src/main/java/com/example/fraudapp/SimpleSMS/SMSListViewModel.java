package com.example.fraudapp.SimpleSMS;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fraudapp.db.SMSRepository;
import com.example.fraudapp.db.entity.SMSEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class SMSListViewModel extends AndroidViewModel {

    private SMSRepository mRepository;
    private LiveData<List<SMSEntity>> notes;

    public SMSListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new SMSRepository(application);
    }

    public LiveData<List<SMSEntity>> getNotes() {
        if (notes == null) {
            notes = mRepository.getAllNotes();
        }

        return notes;
    }

    public SMSEntity getNote(int id) throws ExecutionException, InterruptedException {
        return mRepository.getNote(id);
    }

    public void insertNote(SMSEntity note) {
        mRepository.insertNote(note);
    }

    public void updateNote(SMSEntity note) {
        mRepository.updateNote(note);
    }

    public void deleteNote(SMSEntity note) {
        mRepository.deleteNote(note);
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}
