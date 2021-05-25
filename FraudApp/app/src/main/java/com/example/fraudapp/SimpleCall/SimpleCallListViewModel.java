package com.example.fraudapp.SimpleCall;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fraudapp.db.SimpleCallRepository;
import com.example.fraudapp.db.entity.SimpleCallEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class SimpleCallListViewModel extends AndroidViewModel {

    private SimpleCallRepository mRepository;
    private LiveData<List<SimpleCallEntity>> notes;

    public SimpleCallListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new SimpleCallRepository(application);
    }

    public LiveData<List<SimpleCallEntity>> getNotes() {
        if (notes == null) {
            notes = mRepository.getAllNotes();
        }

        return notes;
    }

    public SimpleCallEntity getNote(int id) throws ExecutionException, InterruptedException {
        return mRepository.getNote(id);
    }

    public void insertNote(SimpleCallEntity note) {
        mRepository.insertNote(note);
    }

    public void updateNote(SimpleCallEntity note) {
        mRepository.updateNote(note);
    }

    public void deleteNote(SimpleCallEntity note) {
        mRepository.deleteNote(note);
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}
