package com.example.fraudapp;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.fraudapp.SimpleSMS.SMSListViewModel;
import com.example.fraudapp.VoipCall.VoipCallListViewModel;
import com.example.fraudapp.VoipSMS.VoipSMSListViewModel;
import com.example.fraudapp.db.entity.VoipCallEntity;
import com.example.fraudapp.db.entity.VoipSMSEntity;

import java.util.ArrayList;
import java.util.Date;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = "NotificationListener";
    private static final String WA_PACKAGE = "com.whatsapp";
    private static final String VIBER_PACKAGE = "com.viber.voip";
    private static final String WAB_PACKAGE = "com.whatsapp.w4b";
    TinyDB tinyDB;
    SMSListViewModel smsListViewModel;
    VoipSMSListViewModel voipSMSListViewModel;
    private VoipCallListViewModel viewModel;

    @Override
    public void onListenerConnected() {
        Log.i(TAG, "Notification Listener connected");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        tinyDB = new TinyDB(this);
        viewModel = new VoipCallListViewModel(getApplication());
        smsListViewModel=new SMSListViewModel(getApplication());
        voipSMSListViewModel=new VoipSMSListViewModel(getApplication());

        if (sbn.getPackageName().equals(WA_PACKAGE) || sbn.getPackageName().equals(WAB_PACKAGE)) {

            final String WA_INCOMING_VOICE = "Incoming voice call";
            final String WA_OUTGOING_VOICE = "Outgoing voice call";
            final String WA_MISSED_VOICE = "missed call";

            final String WA_INCOMING_VIDEO = "Incoming video call";
            final String WA_OUTGOING_VIDEO = "Outgoing video call";
            final String WA_MISSED_VIDEO = "missed call";

            final String WA_WEB = "WhatsApp Web";
            final String WA_NEW_MESSAGES = "new messages";

            Notification notification = sbn.getNotification();
            Bundle bundle = notification.extras;

            String pkgname = sbn.getPackageName();
            String appname;
            if (pkgname.equals(WA_PACKAGE)) {
                appname = "WhatsApp";
            }
            else appname = "WhatsApp Business";
            String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
            String message= bundle.getString(NotificationCompat.EXTRA_TEXT);

//            Log.e(TAG, "PKG NAME: " + pkgname);
//            Log.e(TAG, "EXTRA_TITLE: " + from);
//            Log.e(TAG, "EXTRA_TEXT: " + message);

            ArrayList<String> callLogs = tinyDB.getListString("voip_call_logs");
            StringBuffer sb = new StringBuffer();

           // ArrayList<String> smsLogs = tinyDB.getListString("voip_sms_logs");
            StringBuffer smssb = new StringBuffer();
    if (message.trim().contains(WA_MISSED_VOICE)) {
        sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                + message + " \nCall Date:--- " + new Date()
                + " \nCall duration in sec :--- " + "null"
                + " \nApp Name :--- " + appname
                + " \nCountry ISO :--- " + "null");
        sb.append("\n----------------------------------");
//                if (!callLogs.contains(sb.toString()))
        viewModel.insertNote(new VoipCallEntity(sb.toString()));
//                    callLogs.add(sb.toString());
    }
    else if (message.trim().contains(WA_INCOMING_VOICE)) {
        sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                + message + " \nCall Date:--- " + new Date()
                + " \nCall duration in sec :--- " + "null"
                + " \nApp Name :--- " + appname
                + " \nCountry ISO :--- " + "null");
        sb.append("\n----------------------------------");
//                if (!callLogs.contains(sb.toString()))
//                    callLogs.add(sb.toString());
        viewModel.insertNote(new VoipCallEntity(sb.toString()));
    }
    else if (message.trim().contains(WA_OUTGOING_VOICE)) {
        sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                + message + " \nCall Date:--- " + new Date()
                + " \nCall duration in sec :--- " + "null"
                + " \nApp Name :--- " + appname
                + " \nCountry ISO :--- " + "null");
        sb.append("\n----------------------------------");
//                if (!callLogs.contains(sb.toString()))
//                    callLogs.add(sb.toString());
        viewModel.insertNote(new VoipCallEntity(sb.toString()));
    }
    else if (message.trim().contains(WA_MISSED_VIDEO)) {
        sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                + message + " \nCall Date:--- " + new Date()
                + " \nCall duration in sec :--- " + "null"
                + " \nApp Name :--- " + appname
                + " \nCountry ISO :--- " + "null");
        sb.append("\n----------------------------------");
//                if (!callLogs.contains(sb.toString()))
//                    callLogs.add(sb.toString());
        viewModel.insertNote(new VoipCallEntity(sb.toString()));
    }
    else if (message.trim().contains(WA_INCOMING_VIDEO)) {
        sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                + message + " \nCall Date:--- " + new Date()
                + " \nCall duration in sec :--- " + "null"
                + " \nApp Name :--- " + appname
                + " \nCountry ISO :--- " + "null");
        sb.append("\n----------------------------------");
//                if (!callLogs.contains(sb.toString()))
//                    callLogs.add(sb.toString());
        viewModel.insertNote(new VoipCallEntity(sb.toString()));
    }
    else if (message.trim().contains(WA_OUTGOING_VIDEO)) {
        sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                + message + " \nCall Date:--- " + new Date()
                + " \nCall duration in sec :--- " + "null"
                + " \nApp Name :--- " + appname
                + " \nCountry ISO :--- " + "null");
        sb.append("\n----------------------------------");
//                if (!callLogs.contains(sb.toString()))
//                    callLogs.add(sb.toString());
        viewModel.insertNote(new VoipCallEntity(sb.toString()));
    }
    else if (!message.trim().contains(WA_NEW_MESSAGES) && !message.trim().contains(WA_WEB)) {

        smssb.append("\nApp Name :--- " + appname + " \nName:--- "
                + from + " \nDate :--- " + new Date()
                + " \nBody :--- " + message);
        smssb.append("\n----------------------------------");

              /*  if (!smsLogs.contains(smssb.toString()))
                    smsLogs.add(smssb.toString());*/
        //   tinyDB.putListString("voip_sms_logs", smsLogs);
        voipSMSListViewModel.insertNote(new VoipSMSEntity(smssb.toString()));
    }

            if (!callLogs.contains(sb.toString()))
                callLogs.add(sb.toString());
            tinyDB.putListString("voip_call_logs", callLogs);
        }
        else if (sbn.getPackageName().equals(VIBER_PACKAGE)) {

            final String VIBER_INCOMING = "Incoming Viber call";
            final String VIBER_OUTGOING = "Outgoing Viber call";
            final String VIBER_MISSED = "Missed Viber call";
            final String MISSED_CALL="Missed call";
            final String VIBER_CALL_PROGRESS = "Call in progress";

            Notification notification = sbn.getNotification();
            Bundle bundle = notification.extras;

            String pkgname = sbn.getPackageName();
            String appname = "Viber";

            String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
            String message = "null";
            try {

                message = "" + bundle.get(NotificationCompat.EXTRA_TEXT);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            /*Log.e(TAG, "PKG NAME: " + pkgname);
            Log.e(TAG, "EXTRA_TITLE: " + from);
            Log.e(TAG, "EXTRA_TEXT: " + message);*/

            ArrayList<String> callLogs = tinyDB.getListString("voip_call_logs");
            StringBuffer sb = new StringBuffer();

          //  ArrayList<String> smsLogs = tinyDB.getListString("voip_sms_logs");
            StringBuffer smssb = new StringBuffer();
if(!message.equals("null")){
            if (message.trim().equals(VIBER_MISSED) || message.trim().contains(MISSED_CALL)) {
                sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                        + message + " \nCall Date:--- " + new Date()
                        + " \nCall duration in sec :--- " + "Missed Call"
                        + " \nApp Name :--- " + appname
                        + " \nCountry ISO :--- " + "null");
                sb.append("\n----------------------------------");
//                if (!callLogs.contains(sb.toString()))
//                callLogs.add(sb.toString());
                viewModel.insertNote(new VoipCallEntity(sb.toString()));
            }
            else if (message.trim().equals(VIBER_INCOMING)) {
                sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                        + message + " \nCall Date:--- " + new Date()
                        + " \nCall duration in sec :--- " + "Missed Call"
                        + " \nApp Name :--- " + appname
                        + " \nCountry ISO :--- " + "null");
                sb.append("\n----------------------------------");
                viewModel.insertNote(new VoipCallEntity(sb.toString()));
              /*  if (!callLogs.contains(sb.toString()))
                callLogs.add(sb.toString());*/
            }
            else if (message.trim().equals(VIBER_OUTGOING)) {
                sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                        + message + " \nCall Date:--- " + new Date()
                        + " \nCall duration in sec :--- " + "Missed Call"
                        + " \nApp Name :--- " + appname
                        + " \nCountry ISO :--- " + "null");
                sb.append("\n----------------------------------");
//                if (!callLogs.contains(sb.toString()))
//                callLogs.add(sb.toString());
                viewModel.insertNote(new VoipCallEntity(sb.toString()));
            }
            else if (message.contains(VIBER_CALL_PROGRESS)) {

                String lastLog = callLogs.get(callLogs.size()-1);
                Log.e("LastLog", lastLog);
                if (lastLog.trim().contains(VIBER_INCOMING)) {

                    sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                            + VIBER_INCOMING + " \nCall Date:--- " + new Date()
                            + " \nCall duration in sec :--- " + message
                            + " \nApp Name :--- " + appname
                            + " \nCountry ISO :--- " + "null");
                    sb.append("\n----------------------------------");
                    viewModel.insertNote(new VoipCallEntity(sb.toString()));
                   // callLogs.set(callLogs.size()-1, sb.toString());
                }
                else if (lastLog.trim().contains(VIBER_OUTGOING)) {

                    sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                            + VIBER_OUTGOING + " \nCall Date:--- " + new Date()
                            + " \nCall duration in sec :--- " + message
                            + " \nApp Name :--- " + appname
                            + " \nCountry ISO :--- " + "null");
                    sb.append("\n----------------------------------");
//                    callLogs.set(callLogs.size()-1, sb.toString());
                    viewModel.insertNote(new VoipCallEntity(sb.toString()));
                }

            }
            else {
                if(!message.trim().contains("Reconnecting")||!message.trim().contains("Return to Viber")){

                smssb.append("\nApp Name :--- " + appname + " \nName:--- "
                        + from + " \nDate :--- " + new Date()
                        + " \nBody :--- " + message);
                smssb.append("\n----------------------------------");

              /*  if (!smsLogs.contains(smssb.toString()))
                    smsLogs.add(smssb.toString());*/
              //  tinyDB.putListString("voip_sms_logs", smsLogs);
                voipSMSListViewModel.insertNote(new VoipSMSEntity(smssb.toString()));
                }
            }
}
            /*else {

                sb.append("\nPhone Number/Name:--- " + from + " \nCall Type:--- "
                        + message + " \nCall Date:--- " + new Date()
                        + " \nCall duration in sec :--- " + "null"
                        + " \nApp Name :--- " + appname
                        + " \nCountry ISO :--- " + "null");
                sb.append("\n----------------------------------");
                if (!callLogs.contains(sb.toString()))
                callLogs.add(sb.toString());
            }*/

           // tinyDB.putListString("voip_call_logs", callLogs);
            Log.e(TAG, "Call Log: " + sb);
        }



        /*Log.e(TAG, "EXTRA_BIG_TEXT: " + message2);
        Log.e(TAG, "EXTRA_INFO_TEXT: " + message3);
        Log.e(TAG, "EXTRA_PROGRESS: " + message4);
        Log.e(TAG, "EXTRA_MESSAGES: " + message5);
        Log.e(TAG, "EXTRA_SUB_TEXT: " + message6);
        Log.e(TAG, "EXTRA_SUMMARY_TEXT: " + message7);
        Log.e(TAG, "EXTRA_TITLE_BIG: " + message8);*/
    }
}