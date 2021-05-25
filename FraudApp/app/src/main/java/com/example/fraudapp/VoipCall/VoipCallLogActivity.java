package com.example.fraudapp.VoipCall;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fraudapp.R;
import com.example.fraudapp.TinyDB;
import com.example.fraudapp.db.entity.VoipCallEntity;
import com.example.fraudapp.utils.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
// activity to show data of voip calls to show users
public class VoipCallLogActivity extends AppCompatActivity implements VoipCallAdapter.NotesAdapterListener {

    TextView textView;
    VoipCallListViewModel voipCallListViewModel;
    private VoipCallAdapter mAdapter;
RecyclerView recyclerView;
VoipCallAdapter.NotesAdapterListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voip_call_log);

        voipCallListViewModel = ViewModelProviders.of(this).get(VoipCallListViewModel.class);
        textView = findViewById(R.id.textview_call);
        getSupportActionBar().setTitle(getString(R.string.voip_call_logs));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        listener=this;

       // getVoipCallDetails();
                mAdapter = new VoipCallAdapter(VoipCallLogActivity.this, listener);
                recyclerView = findViewById(R.id.recyclerview);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(VoipCallLogActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new MyDividerItemDecoration(VoipCallLogActivity.this, DividerItemDecoration.VERTICAL, 16));
                recyclerView.setAdapter(mAdapter);
                voipCallListViewModel.getNotes().observe(VoipCallLogActivity.this, new Observer<List<VoipCallEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<VoipCallEntity> notes) {
                        mAdapter.submitList(notes);
//                toggleEmptyNotes(notes.size());
                    }
                });



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getVoipCallDetails() {

        TinyDB tinyDB = new TinyDB(this);
        ArrayList<String> callLogs = tinyDB.getListString("voip_call_logs");
        StringBuffer sb = new StringBuffer();
        sb.append("Call Log :");
        for (int i=0; i<callLogs.size(); i++) {

            sb.append(callLogs.get(i));
        }
        //managedCursor.close();
        textView.setText(sb);
    }

    @Override
    public void onClick(int noteId, int position) {

    }

    @Override
    public void onLongClick(int noteId, int position) {

    }
}