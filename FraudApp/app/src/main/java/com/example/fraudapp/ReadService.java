package com.example.fraudapp;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;

import androidx.annotation.Nullable;

import com.example.fraudapp.SimpleCall.SimpleCallListViewModel;
import com.example.fraudapp.SimpleSMS.SMSListViewModel;
import com.example.fraudapp.db.entity.SMSEntity;
import com.example.fraudapp.db.entity.SimpleCallEntity;

import java.security.Provider;
import java.util.Date;

public class ReadService extends Service {
    SMSListViewModel smsListViewModel;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
      /*  String key=intent.getStringExtra("key");
        if(key.equals("CALL")){
            getCallDetails();
        }else if(key.equals("SMS")){
            getSMSDetails();
        }*/
        return null;
    }
    SimpleCallListViewModel callListViewModel;
    private void getCallDetails() {
        callListViewModel=new SimpleCallListViewModel(getApplication());
        @SuppressLint("MissingPermission") Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int wifi = managedCursor.getColumnIndex(String.valueOf(CallLog.Calls.FEATURES_WIFI));
        int iso = managedCursor.getColumnIndex(CallLog.Calls.COUNTRY_ISO);
        // sb.append("Call Log :");
        String phNumber="";
        String callType="";
        String callDate="";
        Date callDayTime=new Date();
        String callDuration="";
        String countryIso="";
        StringBuffer sb = new StringBuffer();
        String dir = null;
        while (managedCursor.moveToNext()) {

            phNumber = managedCursor.getString(number);
            callType = managedCursor.getString(type);
            callDate = managedCursor.getString(date);
            callDayTime = new Date(Long.valueOf(callDate));
            callDuration = managedCursor.getString(duration);
            countryIso = managedCursor.getString(iso);
            //String featuresWifi = managedCursor.getString(wifi);

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

        }
        sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                + dir + " \nCall Date:--- " + callDayTime
                + " \nCall duration in sec :--- " + callDuration
                + " \nWifi Or not :--- " + wifi
                + " \nCountry ISO :--- " + countryIso);
        sb.append("\n----------------------------------");
        // System.out.println("--------------------"+sb);
        callListViewModel.insertNote(new SimpleCallEntity(sb.toString()));



        //managedCursor.close();
        // textView.setText(sb);
    }
    void getSMSDetails() {
        smsListViewModel=new SMSListViewModel(getApplication());
        Cursor cursor =getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        String msgData = "";

        // sb.append("SMS Log :");

        int number = cursor.getColumnIndex(Telephony.Sms.SERVICE_CENTER);
        int name = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
        int body = cursor.getColumnIndex(Telephony.Sms.BODY);
        int date = cursor.getColumnIndex(Telephony.Sms.DATE);
        StringBuffer sb = new StringBuffer();
        String phNumber = "";
        String smsName = "";
        Date callDayTime = new Date(Long.valueOf(date));
        String smsBody = "";
        while (cursor.moveToNext()) {

             phNumber = cursor.getString(number);
             smsName = cursor.getString(name);
           callDayTime = new Date(Long.valueOf(date));
            smsBody = cursor.getString(body);


        }
        sb.append("\nService Center:--- " + phNumber + " \nName:--- "
                + smsName + " \nDate :--- " + callDayTime
                + " \nBody :--- " + smsBody);
        sb.append("\n----------------------------------");
        smsListViewModel.insertNote(new SMSEntity(sb.toString()));

    }
}
