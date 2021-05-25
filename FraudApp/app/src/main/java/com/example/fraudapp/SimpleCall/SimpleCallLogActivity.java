package com.example.fraudapp.SimpleCall;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.Settings;
import android.provider.Telephony;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fraudapp.CallsUpdateBroadCast;
import com.example.fraudapp.R;
import com.example.fraudapp.SimpleSMS.SMSListViewModel;
import com.example.fraudapp.SimpleSMS.SimpleSmsActivity;
import com.example.fraudapp.TinyDB;
import com.example.fraudapp.VoipCall.VoipCallLogActivity;
import com.example.fraudapp.VoipSMS.VoipSmsActivity;
import com.example.fraudapp.db.entity.SMSEntity;
import com.example.fraudapp.db.entity.SimpleCallEntity;
import com.example.fraudapp.utils.MyDividerItemDecoration;
import com.github.ag.floatingactionmenu.OptionsFabLayout;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

//import android.app.Fragment;

// main activity in which calls data will visible and fab buttons will visible to get more tasks in app
// here we get data about simple calls and message for first time when app will install
public class SimpleCallLogActivity extends AppCompatActivity implements SimpleCallAdapter.SimpleCallAdapterListener {

    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    TextView textView = null;
    OptionsFabLayout optionsFabLayout;
    RecyclerView rvMainSimpleCall;
    String SIMPLE_CALL="SIMPLE_CALL";
    String SIMPL_SMS="SIMPLE_SMS";
    SMSListViewModel smsListViewModel;
    TinyDB tinyDB;
    SimpleCallAdapter mAdapter;
    SimpleCallAdapter.SimpleCallAdapterListener listener;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tinyDB=new TinyDB(this);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.setTitle("Fetching");
rvMainSimpleCall=findViewById(R.id.rv_main_simple_call);
smsListViewModel=new SMSListViewModel(getApplication());
listener=this;
rvMainSimpleCall.setLayoutManager(new LinearLayoutManager(SimpleCallLogActivity.this));

        PermissionX.init(this)
                .permissions(Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.WRITE_CALL_LOG, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_SMS)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                            dialog.show();
                            textView = findViewById(R.id.textview_call);
                            setOptionsFabLayout();
                            if(tinyDB.isKeyExist(SIMPLE_CALL)){
                                if(tinyDB.isNew(SIMPLE_CALL)){
                                    System.out.println("-----------------key is New-------------");
                                   /* Intent intent=new Intent(SimpleCallLogActivity.this, CallsUpdateBroadCast.class);
                                    startActivity(intent);*/
                                    getCallDetails();
                                    getSMSDetails();
                                }else{
                                    mAdapter = new SimpleCallAdapter(SimpleCallLogActivity.this, listener);
                                    rvMainSimpleCall.setItemAnimator(new DefaultItemAnimator());
                                    rvMainSimpleCall.addItemDecoration(new MyDividerItemDecoration(SimpleCallLogActivity.this, DividerItemDecoration.VERTICAL, 16));
                                    rvMainSimpleCall.setAdapter(mAdapter);
                                    callListViewModel.getNotes().observe(SimpleCallLogActivity.this, new Observer<List<SimpleCallEntity>>() {
                                        @Override
                                        public void onChanged(@Nullable List<SimpleCallEntity> notes) {
                                            mAdapter.submitList(notes);
//                toggleEmptyNotes(notes.size());
                                        }
                                    });
                                    dialog.dismiss();

                                }
                            }else{
                                tinyDB.setNew(SIMPLE_CALL,true);
                                getCallDetails();
                                getSMSDetails();
                            }

                            if (!isNotificationServiceEnabled())
                                startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                            findViewById(R.id.btn_voip_call_logs).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    startActivity(new Intent(SimpleCallLogActivity.this, VoipCallLogActivity.class));
                                }
                            });
                        } else {
                            Toast.makeText(SimpleCallLogActivity.this, "The following permissions are denied：" + deniedList, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    void setOptionsFabLayout() {

        optionsFabLayout = (OptionsFabLayout) findViewById(R.id.fab_menu);
        //Set main fab clicklistener.
        optionsFabLayout.setMainFabOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (optionsFabLayout.isOptionsMenuOpened()) {

                    optionsFabLayout.closeOptionsMenu();
                }
            }
        });

        //Set mini fabs clicklisteners.
        optionsFabLayout.setMiniFabSelectedListener(new OptionsFabLayout.OnMiniFabSelectedListener() {
            @Override
            public void onMiniFabSelected(MenuItem fabItem) {
                switch (fabItem.getItemId()) {
                    case R.id.fab_voip_callogs:
                        startActivity(new Intent(SimpleCallLogActivity.this, VoipCallLogActivity.class));
                        break;
                    case R.id.fab_voip_sms:
                        startActivity(new Intent(SimpleCallLogActivity.this, VoipSmsActivity.class));
                        break;
                    case R.id.fab_simple_sms:
                        startActivity(new Intent(SimpleCallLogActivity.this, SimpleSmsActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }
  void getSMSDetails() {

        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        String msgData = "";

       // sb.append("SMS Log :");

        int number = cursor.getColumnIndex(Telephony.Sms.SERVICE_CENTER);
        int name = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
        int body = cursor.getColumnIndex(Telephony.Sms.BODY);
        int date = cursor.getColumnIndex(Telephony.Sms.DATE);

        while (cursor.moveToNext()) {
            StringBuffer sb = new StringBuffer();
            String phNumber = cursor.getString(number);
            String smsName = cursor.getString(name);
            Date callDayTime = new Date(Long.valueOf(date));
            String smsBody = cursor.getString(body);

            sb.append("\nService Center:--- " + phNumber + " \nName:--- "
                    + smsName + " \nDate :--- " + callDayTime
                    + " \nBody :--- " + smsBody);
            sb.append("\n----------------------------------");
            smsListViewModel.insertNote(new SMSEntity(sb.toString()));
        }


    }
    private boolean isNotificationServiceEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (String name: names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }List<Integer> q;
        return false;
    }
SimpleCallListViewModel callListViewModel;
    private void getCallDetails() {
       callListViewModel=new SimpleCallListViewModel(getApplication());
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int wifi = managedCursor.getColumnIndex(String.valueOf(CallLog.Calls.FEATURES_WIFI));
        int iso = managedCursor.getColumnIndex(CallLog.Calls.COUNTRY_ISO);
       // sb.append("Call Log :");
        while (managedCursor.moveToNext()) {
            StringBuffer sb = new StringBuffer();
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String countryIso = managedCursor.getString(iso);
            //String featuresWifi = managedCursor.getString(wifi);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                    + dir + " \nCall Date:--- " + callDayTime
                    + " \nCall duration in sec :--- " + callDuration
                    + " \nWifi Or not :--- " + wifi
                    + " \nCountry ISO :--- " + countryIso);
            sb.append("\n----------------------------------");
           // System.out.println("--------------------"+sb);
            callListViewModel.insertNote(new SimpleCallEntity(sb.toString()));

        }
        tinyDB.setNew(SIMPLE_CALL,false);
        mAdapter = new SimpleCallAdapter(SimpleCallLogActivity.this, listener);
        rvMainSimpleCall.setItemAnimator(new DefaultItemAnimator());
        rvMainSimpleCall.addItemDecoration(new MyDividerItemDecoration(SimpleCallLogActivity.this, DividerItemDecoration.VERTICAL, 16));
        rvMainSimpleCall.setAdapter(mAdapter);
        callListViewModel.getNotes().observe(SimpleCallLogActivity.this, new Observer<List<SimpleCallEntity>>() {
            @Override
            public void onChanged(@Nullable List<SimpleCallEntity> notes) {
                mAdapter.submitList(notes);
                System.out.println("-------------------------------------------------"+notes.size());
//                toggleEmptyNotes(notes.size());
            }
        });
        dialog.dismiss();

        //managedCursor.close();
       // textView.setText(sb);
    }

    @Override
    public void onClick(int simpleCallId, int position) {

    }

    @Override
    public void onLongClick(int simpleCallId, int position) {

    }
}