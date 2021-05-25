package com.example.fraudapp.VoipCall;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fraudapp.db.VoipCallRepository;
import com.example.fraudapp.db.entity.VoipCallEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class VoipCallListViewModel extends AndroidViewModel {

    private VoipCallRepository mRepository;
    private LiveData<List<VoipCallEntity>> notes;

    public VoipCallListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new VoipCallRepository(application);
    }

    public LiveData<List<VoipCallEntity>> getNotes() {
        if (notes == null) {
            notes = mRepository.getAllNotes();
        }

        return notes;
    }

    public VoipCallEntity getNote(int id) throws ExecutionException, InterruptedException {
        return mRepository.getNote(id);
    }

    public void insertNote(VoipCallEntity note) {
        mRepository.insertNote(note);
    }

    public void updateNote(VoipCallEntity note) {
        mRepository.updateNote(note);
    }

    public void deleteNote(VoipCallEntity note) {
        mRepository.deleteNote(note);
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}
