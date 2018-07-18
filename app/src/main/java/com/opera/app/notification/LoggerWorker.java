package com.opera.app.notification;

import android.support.annotation.NonNull;
import android.util.Log;

import com.opera.app.R;
import com.opera.app.activities.MainActivity;

import androidx.work.Worker;


public class LoggerWorker extends Worker {

    private static final String TAG = LoggerWorker.class.getSimpleName();

    @NonNull
    @Override
    public Worker.Result doWork() {
        try {
            Log.d(TAG, "Logging...");
            NotificationData data = new NotificationData(getApplicationContext(), MainActivity.class);
            data.notifyData("Opera Reminder","Reminder for the show");
            return Worker.Result.SUCCESS;
        } catch (Throwable throwable) {
            return Worker.Result.FAILURE;
        }
    }

}