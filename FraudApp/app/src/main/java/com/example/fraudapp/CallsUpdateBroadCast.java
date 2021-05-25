package com.example.fraudapp;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.fraudapp.SimpleCall.SimpleCallAdapter;
import com.example.fraudapp.SimpleCall.SimpleCallListViewModel;
import com.example.fraudapp.SimpleCall.SimpleCallLogActivity;
import com.example.fraudapp.VoipCall.VoipCallLogActivity;
import com.example.fraudapp.db.entity.SMSEntity;
import com.example.fraudapp.db.entity.SimpleCallEntity;
import com.example.fraudapp.utils.MyDividerItemDecoration;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.util.Date;
import java.util.List;

import static android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

public class CallsUpdateBroadCast extends BroadcastReceiver {
    String number;
    @Override
    public void onReceive(Context context, Intent intent) {
      /*  if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            number = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
            Toast.makeText(context, "ITs not WOrkinG", Toast.LENGTH_SHORT).show();
        }
        else {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
             number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            Toast.makeText(context, "ITs WOrkinG", Toast.LENGTH_SHORT).show();
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }
            onCallStateChanged(context,state);
        }
*/
       /* if (intent.getAction().equals(intent.getExtras().getString(TelephonyManager.EXTRA_STATE))) {
            Intent intentService=new Intent(context,ReadService.class);
            intentService.putExtra("key","CALL");
            context.startService(intentService);
        }*/ /*if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Intent intentService=new Intent(context,ReadService.class);
            intentService.putExtra("key","SMS");
            context.startService(intentService);
        }*/
       /* else{
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)||stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)||stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Intent intentService=new Intent(context,ReadService.class);
                intentService.putExtra("key","CALL");
                context.startService(intentService);
            }


        }*/
    }

    int lastState;
    Date callStartTime;
    String callType;
    public void onCallStateChanged(Context context, int state) {
        if(lastState == state){
            //No change, debounce extras
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                callType="INCOMING";
                callStartTime = new Date();
                number = number;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    //isIncoming = false;
                    callType="OUTGOING";
                    callStartTime = new Date();

                }
                else
                {
                   // isIncoming = true;
                    callType="INCOMING";
                    callStartTime = new Date();

                }

                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                    //Ring but no pickup-  a miss
                    callType="MISSED";

                }
                break;
        }
        lastState = state;
        StringBuffer sb = new StringBuffer();
        sb.append("\nPhone Number:--- " + number + " \nCall Type:--- "
                + lastState + " \nCall Date:--- " + callStartTime
                + " \nCall duration in sec :--- " + "0"
                + " \nWifi Or not :--- " + (-1)
                + " \nCountry ISO :--- " + TelephonyManager.EXTRA_NETWORK_COUNTRY);
        sb.append("\n----------------------------------");
       /* SimpleCallListViewModel simpleCallListViewModel=new SimpleCallListViewModel((Application) context);
        simpleCallListViewModel.insertNote(new SimpleCallEntity(sb.toString()));*/

    }

}
