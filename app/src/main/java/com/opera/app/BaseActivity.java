package com.opera.app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.opera.app.constants.AppConstants;
import com.opera.app.customwidget.CustomToast;

import org.infobip.mobile.messaging.geo.MobileGeo;
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

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();

        activateGeoFenc();
    }

    protected void bindViews() {
        ButterKnife.bind(this);
        int prefMode = 0;
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
            if(response.errorBody() != null) {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                return jObjError.getString(AppConstants.MESSAGE);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void activateGeoFenc(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String permissions[] = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        MobileGeo.getInstance(this).activateGeofencing();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        MobileGeo.getInstance(this).activateGeofencing();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
