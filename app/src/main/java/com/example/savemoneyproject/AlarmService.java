package com.example.savemoneyproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import java.util.Date;

public class AlarmService extends Service {
    public int anHour;
    PendingIntent pi;
    AlarmManager alarmManager;

    public AlarmService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            AlertDialog.Builder builder = new AlertDialog.Builder(AlarmService.this);
            builder.setTitle("提醒");
            builder.setMessage("该补水啦");
            builder.setCancelable(false);

            final AlertDialog dialog = builder.create();
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("LongRunningService", "executed at " + new Date().
//                        toString());
//                 mHandler.sendEmptyMessage(1);
//            }
//        }).start();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = intent.getIntExtra("Time",2);
        anHour = time * 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AlarmReceiver.class);
        pi = PendingIntent.getBroadcast(this, 0, i, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        alarmManager.cancel(pi);
    }
}