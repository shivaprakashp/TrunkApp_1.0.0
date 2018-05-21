package com.opera.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;

import com.opera.app.dagger.ApiComponent;
import com.opera.app.dagger.ApiModule;
import com.opera.app.dagger.AppModule;
import com.opera.app.dagger.DaggerApiComponent;
import com.opera.app.utils.OperaUtils;

/**
 * Created by 1000779 on 2/2/2018.
 */

public class MainApplication extends Application {

    private static Context context;
    //for font type defined
    private Typeface fontLight, fontMedium, fontRegular, fontBold;

    private ApiComponent apiComponent;
    @Override
    public void onCreate(){
        super.onCreate();

        context = getApplicationContext();
        setFont();
        initDagger();
        initNotification();
    }

    private void initNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.app_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
    }

    private void setFont(){
        fontLight = Typeface.createFromAsset(getAssets(), OperaUtils.FONT_MONSTERRAT_LIGHT);
        fontMedium = Typeface.createFromAsset(getAssets(), OperaUtils.FONT_MONSTERRAT_MEDIUM);
        fontRegular = Typeface.createFromAsset(getAssets(), OperaUtils.FONT_MONSTERRAT_REGULAR);
        fontBold = Typeface.createFromAsset(getAssets(), OperaUtils.FONT_MONSTERRAT_BOLD);

    }

    private void initDagger(){
        apiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(UrlUtils.baseUrl))
                .build();

    }

    public Typeface getFontLight() {
        return fontLight;
    }

    public Typeface getFontMedium() {
        return fontMedium;
    }

    public Typeface getFontRegular() {
        return fontRegular;
    }

    public Typeface getFontBold() {
        return fontBold;
    }

    public ApiComponent getNetComponent() {
        return apiComponent;
    }
}
