package com.example.fraudapp.SimpleSMS;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fraudapp.R;
import com.example.fraudapp.db.entity.SMSEntity;
import com.example.fraudapp.utils.MyDividerItemDecoration;

import java.util.List;
// activity to show simple sms to user
public class SimpleSmsActivity extends AppCompatActivity implements SMSAdapter.SMSAdapterListener {

    //TextView tvSMS;
RecyclerView rvSimpleSms;
SMSAdapter mAdapter;
    SMSAdapter.SMSAdapterListener listener;
    SMSListViewModel smsListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_sms);
smsListViewModel=new SMSListViewModel(getApplication());
        getSupportActionBar().setTitle(getString(R.string.simple_sms));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        rvSimpleSms=findViewById(R.id.rv_simple_sms);
        listener=this;
        rvSimpleSms.setLayoutManager(new LinearLayoutManager(SimpleSmsActivity.this));
        
        mAdapter = new SMSAdapter(SimpleSmsActivity.this, listener);
        rvSimpleSms.setItemAnimator(new DefaultItemAnimator());
        rvSimpleSms.addItemDecoration(new MyDividerItemDecoration(SimpleSmsActivity.this, DividerItemDecoration.VERTICAL, 16));
        rvSimpleSms.setAdapter(mAdapter);
        smsListViewModel.getNotes().observe(SimpleSmsActivity.this, new Observer<List<SMSEntity>>() {
            @Override
            public void onChanged(@Nullable List<SMSEntity> notes) {
                mAdapter.submitList(notes);
//                toggleEmptyNotes(notes.size());
            }
        });
/*
        tvSMS = findViewById(R.id.tv_sms);
        tvSMS.setText(getSMSDetails());*/
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onClick(int SmsId, int position) {
        
    }

    @Override
    public void onLongClick(int SmsId, int position) {

    }
}