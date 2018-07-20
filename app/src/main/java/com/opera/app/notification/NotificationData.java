package com.opera.app.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
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

        long notificatioId = System.currentTimeMillis();

        Intent intent = new Intent(context, aClass); // Here pass your activity where you want to redirect.

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(aClass);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this.context.getApplicationContext());

        builder.setSmallIcon(R.drawable.ic_notification_icon);
        builder.setContentTitle(notifyTitle);
        builder.setVibrate(new long[]{100, 100, 100, 100});
        builder.setContentIntent(pendingIntent);
        builder.setContentText(notifyText).setPriority(Notification.PRIORITY_HIGH);
        builder.setDefaults(Notification.FLAG_AUTO_CANCEL);
        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Notification notification = builder.build();

        // O icone diferencia uma notificacao da outra.
        notificationManager.notify((int) notificatioId, notification);


    }

}
