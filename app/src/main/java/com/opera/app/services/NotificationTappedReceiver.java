package com.opera.app.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.opera.app.activities.MainActivity;
import com.opera.app.activities.MyProfileActivity;
import com.opera.app.activities.NotificationActivity;
import com.opera.app.activities.PromotionsActivity;

import org.infobip.mobile.messaging.Message;

//on Tap notification Open the activity
public class NotificationTappedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //received data once notification reached
        Message message = Message.createFrom(intent.getExtras());
        //received from infobip
        //check custom payload has null value
        if (message.getCustomPayload()!=null){
            /*look out the notification type,
            * whether notification type is of promotion or geo notification*/
            if (message.getCustomPayload().opt("notifyType").toString().
                    equalsIgnoreCase("promotion")){
                //pass intent based on type of notification
               intent  = new Intent(context, PromotionsActivity.class);
            }else{
                intent = new Intent(context, NotificationActivity.class);
            }
        }else{
            intent = new Intent(context, MainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
