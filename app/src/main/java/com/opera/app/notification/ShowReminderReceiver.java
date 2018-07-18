package com.opera.app.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.opera.app.constants.AppConstants;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class ShowReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getExtras() != null) {
            if (intent.getExtras().get(AppConstants.LOG_ALARM) != null) {
                OneTimeWorkRequest logWork = new OneTimeWorkRequest.Builder(LoggerWorker.class)
                        .build();
                WorkManager.getInstance().enqueue(logWork);

            }
        }
    }
}
