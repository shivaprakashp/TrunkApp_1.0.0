package com.opera.app.notification;

import android.content.Context;
import android.media.RingtoneManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.opera.app.R;

import androidx.work.Worker;

public class FeedBackWorker extends Worker {

    private static final String TAG = FeedBackWorker.class.getSimpleName();
    private Context context;

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Logging...");
        try {
            notifyMe();
            return Worker.Result.SUCCESS;
        } catch (Throwable throwable) {
            return Worker.Result.FAILURE;
        }
    }


    private void notifyMe(){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext()).
                        setSmallIcon(R.drawable.ic_notification_icon).
                        setContentTitle("Opera Title").setContentText("Opera Test").setOngoing(false)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.notifyAll();
    }
}
