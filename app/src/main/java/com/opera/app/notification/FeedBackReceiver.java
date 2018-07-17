package com.opera.app.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.opera.app.R;
import com.opera.app.constants.AppConstants;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class FeedBackReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getExtras() != null) {
            if (intent.getExtras().get(AppConstants.LOG_FEEDBACK_ALARM) != null) {
                OneTimeWorkRequest logWork = new OneTimeWorkRequest.Builder(FeedBackWorker.class)
                        .build();
                WorkManager.getInstance().enqueue(logWork);
                notifyMe(context);
                Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void notifyMe(Context context){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context).
                        setSmallIcon(R.drawable.ic_notification_icon).
                        setContentTitle("Opera Title").setContentText("Opera Test").setOngoing(false)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.notify();
    }
}
