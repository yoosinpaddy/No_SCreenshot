package com.example.fraudapp.VoipSMS;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fraudapp.R;
import com.example.fraudapp.TinyDB;
import com.example.fraudapp.db.entity.VoipSMSEntity;
import com.example.fraudapp.utils.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
// voip sms activity to show list
public class VoipSmsActivity extends AppCompatActivity implements VoipSMSAdapter.VoipSMSAdapterListener {

    //TextView tvSMS;
    RecyclerView rvSMS;
VoipSMSListViewModel voipSMSListViewModel;
VoipSMSAdapter mAdapter;
    VoipSMSAdapter.VoipSMSAdapterListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voip_sms);
        getSupportActionBar().setTitle(getString(R.string.voip_sms));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        voipSMSListViewModel=new VoipSMSListViewModel(getApplication());
        listener=this;
rvSMS=findViewById(R.id.rv_sms);
rvSMS.setLayoutManager(new LinearLayoutManager(VoipSmsActivity.this));
        mAdapter = new VoipSMSAdapter(VoipSmsActivity.this, listener);
        rvSMS.setItemAnimator(new DefaultItemAnimator());
        rvSMS.addItemDecoration(new MyDividerItemDecoration(VoipSmsActivity.this, DividerItemDecoration.VERTICAL, 16));
        rvSMS.setAdapter(mAdapter);
        voipSMSListViewModel.getNotes().observe(VoipSmsActivity.this, new Observer<List<VoipSMSEntity>>() {
            @Override
            public void onChanged(@Nullable List<VoipSMSEntity> notes) {
                mAdapter.submitList(notes);
//                toggleEmptyNotes(notes.size());
            }
        });

        //tvSMS = findViewById(R.id.tv_sms);
       // tvSMS.setText(getSMSDetails());
//getSMSDetails();
    }

    StringBuffer getSMSDetails() {

        TinyDB tinyDB = new TinyDB(this);
        ArrayList<String> smsLogs = tinyDB.getListString("voip_sms_logs");
    //    ArrayList<VoipSMSEntity> smsLogs=new
        StringBuffer sb = new StringBuffer();
        sb.append("Voip SMS Log :");
        for (int i=0; i<smsLogs.size(); i++) {

            sb.append(smsLogs.get(i));
        }
        return sb;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(int voipSmsId, int position) {
        
    }

    @Override
    public void onLongClick(int voipSmsId, int position) {

    }
}