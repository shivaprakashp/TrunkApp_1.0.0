package com.opera.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.opera.app.customwidget.CustomToast;

import org.infobip.mobile.messaging.Event;
import org.infobip.mobile.messaging.Message;
import org.infobip.mobile.messaging.MobileMessaging;
import org.infobip.mobile.messaging.storage.MessageStore;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * Created by 1000779 on 3/22/2018.
 */

public class BaseActivity extends AppCompatActivity {

    public SharedPreferences mSharedPreferences;
    public CustomToast customToast;
    private int prefMode = 0;

    private MobileMessaging mobileMessaging;
    private MessageStore messageStore;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();

        mobileMessaging = MobileMessaging.getInstance(this);
        messageStore = mobileMessaging.getMessageStore();

    }

    protected void bindViews() {
        ButterKnife.bind(this);
        mSharedPreferences=getSharedPreferences(getResources().getString(R.string.prefName), prefMode);
        customToast = new CustomToast(BaseActivity.this);
    }

    public void openActivity(Activity activity, Class<?> startClass) {
        Intent intent = new Intent(activity, startClass);
        startActivity(intent);
        //finish();
    }

    public void openActivityWithClearPreviousActivities(Activity activity, Class<?> startClass) {
        Intent intent = new Intent(activity, startClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public String jsonResponse(Response response) {

        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            return jObjError.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
