package com.example.fraudapp.VoipSMS;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fraudapp.db.VoipSMSRepository;
import com.example.fraudapp.db.entity.VoipSMSEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class VoipSMSListViewModel extends AndroidViewModel {

    private VoipSMSRepository mRepository;
    private LiveData<List<VoipSMSEntity>> notes;

    public VoipSMSListViewModel(@NonNull Application application) {
        super(application);

        mRepository = new VoipSMSRepository(application);
    }

    public LiveData<List<VoipSMSEntity>> getNotes() {
        if (notes == null) {
            notes = mRepository.getAllNotes();
        }

        return notes;
    }

    public VoipSMSEntity getNote(int id) throws ExecutionException, InterruptedException {
        return mRepository.getNote(id);
    }

    public void insertNote(VoipSMSEntity note) {
        mRepository.insertNote(note);
    }

    public void updateNote(VoipSMSEntity note) {
        mRepository.updateNote(note);
    }

    public void deleteNote(VoipSMSEntity note) {
        mRepository.deleteNote(note);
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}
