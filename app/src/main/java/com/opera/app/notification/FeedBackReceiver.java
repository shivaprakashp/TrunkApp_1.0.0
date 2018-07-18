package com.opera.app.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.opera.app.R;
import com.opera.app.activities.MainActivity;
import com.opera.app.constants.AppConstants;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class FeedBackReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getExtras() != null) {
            if (intent.getExtras().get(AppConstants.LOG_FEEDBACK_ALARM) != null) {
                OneTimeWorkRequest logWork = new OneTimeWorkRequest.Builder(LoggerWorker.class)
                        .build();
                WorkManager.getInstance().enqueue(logWork);

            }
        }
    }
}
