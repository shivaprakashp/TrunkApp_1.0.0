package com.opera.app.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.opera.app.activities.MyProfileActivity;
import com.opera.app.activities.PromotionsActivity;

import org.infobip.mobile.messaging.Message;

public class NotificationTappedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Message message = Message.createFrom(intent.getExtras());
        Log.i("message", message.toString());
        Intent i = new Intent(context, PromotionsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
