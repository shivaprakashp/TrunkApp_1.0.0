package com.opera.app;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.opera.app.customwidget.CustomToast;

import org.infobip.mobile.messaging.BroadcastParameter;
import org.infobip.mobile.messaging.Event;
import org.infobip.mobile.messaging.Message;
import org.infobip.mobile.messaging.MobileMessaging;
import org.infobip.mobile.messaging.NotificationSettings;
import org.infobip.mobile.messaging.api.data.MobileApiData;
import org.infobip.mobile.messaging.geo.MobileGeo;
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
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MobileMessaging mobileMessaging;
    private MessageStore messageStore;

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          //  String gcmRegistrationToken = intent.getStringExtra(BroadcastParameter.EXTRA_GCM_TOKEN);
           // Log.i("regId", gcmRegistrationToken);

        }
    };

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();

        initInfoBip();
        activateGeofencing();

        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(messageReceiver, new IntentFilter(Event.MESSAGE_RECEIVED.getKey()));
    }

    private void initInfoBip(){

        mobileMessaging = new MobileMessaging.Builder(getApplication()).withDisplayNotification(new NotificationSettings.Builder(this)
                .withMultipleNotifications()
                .withDefaultIcon(R.mipmap.ic_launcher)
                .build())
                .build();

       /* Log.i("regId", mobileMessaging.getPushRegistrationId());*/
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            activateGeofencing();
        }
    }

    private void activateGeofencing() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String permissions[] = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        MobileGeo.getInstance(this).activateGeofencing();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager
                .getInstance(this)
                .unregisterReceiver(messageReceiver);
    }
}
