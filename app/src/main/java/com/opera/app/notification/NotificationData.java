package com.opera.app.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;

import com.opera.app.R;

public class NotificationData {

    private Context context;
    private Class aClass;

    public NotificationData(Context context, Class aClass){
        this.context = context;
        this.aClass = aClass;
    }

    public void notifyData(String notifyTitle, String notifyText){

        NotificationManager notificationManager =
                (NotificationManager) this.context
                        .getSystemService(this.context.NOTIFICATION_SERVICE);

        PendingIntent pendingIntent =
                PendingIntent.getActivity
                        (
                                context.getApplicationContext(),
                                0,
                                new Intent
                                        (
                                                context.getApplicationContext(),
                                                this.aClass
                                        ),
                                0
                        );

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this.context.getApplicationContext());

        builder.setSmallIcon(R.drawable.ic_notification_icon);
        builder.setContentTitle(notifyTitle);
        builder.setVibrate(new long[]{100, 100, 100, 100});
        builder.setContentIntent(pendingIntent);
        builder.setContentText(notifyText);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Notification notification = builder.build();

        // O icone diferencia uma notificacao da outra.
        notificationManager.notify(1, notification);


    }

}
