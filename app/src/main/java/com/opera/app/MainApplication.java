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

import org.infobip.mobile.messaging.MobileMessaging;
import org.infobip.mobile.messaging.NotificationSettings;
import org.infobip.mobile.messaging.storage.SQLiteMessageStore;

/**
 * Created by 1000779 on 2/2/2018.
 */

public class MainApplication extends Application {

    private static Context context;
    //for font type defined
    private Typeface fontLight, fontMedium, fontRegular, fontBold;

    private ApiComponent apiComponent;

    private static MobileMessaging mobileMessaging = null;

    @Override
    public void onCreate(){
        super.onCreate();

        context = getApplicationContext();

        setFont();
        initDagger();
        initInfoBip();

    }

    private void initInfoBip(){
        mobileMessaging = new MobileMessaging.Builder(this)
                .withMessageStore(SQLiteMessageStore.class)
                .withDisplayNotification(new NotificationSettings.Builder(this)
                        .withMultipleNotifications()
                        .withDefaultIcon(R.drawable.ic_state_icon)
                        .build())
                .build();
    }

    //return static instance of Infobip MobileMessaging
    public static MobileMessaging getMobileMessaging() {

        return mobileMessaging;
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
