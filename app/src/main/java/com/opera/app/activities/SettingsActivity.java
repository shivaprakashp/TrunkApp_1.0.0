package com.opera.app.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.orders.OrderHistoryDB;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.LogoutDialog;
import com.opera.app.dialogues.PermissionDialogue;
import com.opera.app.listener.TaskComplete;
import com.opera.app.notification.ShowReminderReceiver;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettingsResponseMain;
import com.opera.app.pojo.favouriteandsettings.OrderHistory;
import com.opera.app.preferences.SessionManager;
import com.opera.app.services.SettingsService;
import com.opera.app.services.UpdateSettingsInterface;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import org.infobip.mobile.messaging.CustomUserDataValue;
import org.infobip.mobile.messaging.UserData;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 58001 on 23-03-2018.
 */

public class SettingsActivity extends BaseActivity implements  UpdateSettingsInterface{

    private Activity mActivity;
    private SessionManager mSessionManager;
    private SettingsService mSettingsService = new SettingsService();
    private UpdateSettingsInterface mUpdateSettingsInterface;
    private String mNotifSwitch = "",
            mPromoSwitch = "",
            mFeedbackNotifSwitch = "",
            mNewsletterSwitch = "",
            mBookedShowSwitch = "",
            mNewLanguage = "";
    private Api api;
    SharedPreferences sp;

    @Inject
    Retrofit retrofit;

    @BindView(R.id.englishSwitch)
    TextView englishSwitch;

    @BindView(R.id.arabicSwitch)
    TextView arabicSwitch;

    @BindView(R.id.tvLogout)
    TextView tvLogout;

    @BindView(R.id.toolbar_setting)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.notificationSwitch)
    Switch mNotificationSwitch;

    @BindView(R.id.promotionSwitch)
    Switch mPromotionSwitch;

    @BindView(R.id.feedbackSwitch)
    Switch mFeedbackSwitch;

    @BindView(R.id.newletterSwitch)
    Switch mNewletterSwitch;

    @BindView(R.id.reminderSwitch)
    Switch mReminderSwitch;

    @BindView(R.id.linearLogout)
    LinearLayout mLinearLogout;

    private UserData userData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = SettingsActivity.this;

        //For Language activity_setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_setting);

        initToolbar();
        initView();
        SwitchEvents();
    }

    private void SwitchEvents() {
        mNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (checkAccessPermission()){
                        userData.setCustomUserDataElement("notificationSwitch", new CustomUserDataValue("true"));
                    }else{
                        PermissionDialogue dialogue = new PermissionDialogue(mActivity);
                        dialogue.show();
                    }

                }else{
                    userData.setCustomUserDataElement("notificationSwitch", new CustomUserDataValue("false"));
                }

                mPromoSwitch = mPromotionSwitch.isChecked() ? "true" : "false";

                userData.setCustomUserDataElement("promotionSwitch", new CustomUserDataValue(mPromoSwitch));

                ((MainApplication)getApplication()).getMobileMessaging().getInstance(SettingsActivity.this).syncUserData(userData);
            }
        });

        mPromotionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    userData.setCustomUserDataElement("promotionSwitch", new CustomUserDataValue("true"));
                }else{
                    userData.setCustomUserDataElement("promotionSwitch", new CustomUserDataValue("false"));
                }

                mNotifSwitch = mNotificationSwitch.isChecked() ? "true" : "false";

                userData.setCustomUserDataElement("notificationSwitch", new CustomUserDataValue(mNotifSwitch));

                ((MainApplication)getApplication()).getMobileMessaging().getInstance(SettingsActivity.this).syncUserData(userData);
            }
        });

        mReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                OrderHistoryDB orderHistoryDB = new OrderHistoryDB(mActivity);

                ComponentName component = new ComponentName(mActivity, ShowReminderReceiver.class);
                if (isChecked){
                    try{
                        //Enable
                        mActivity.getPackageManager().setComponentEnabledSetting(component,
                                PackageManager.COMPONENT_ENABLED_STATE_ENABLED , PackageManager.DONT_KILL_APP);

                        //set dabase data
                        orderHistoryDB.open();
                        if (orderHistoryDB.orderHistories() != null ){

                            MainApplication.alarmManager = new AlarmManager[orderHistoryDB.orderHistories().size()];
                            MainApplication.arrayList = new ArrayList<>();

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            calendar.clear();

                            for (int i = 0 ; i < orderHistoryDB.orderHistories().size() ; i++){
                                OrderHistory history = orderHistoryDB.orderHistories().get(i);

                                if (history.getStartTime()!=null){
                                   // String[] startTime = history.getStartTime().split("T");

                                    OperaUtils utils = new OperaUtils();
                                    calendar = utils.splitISODateFormat(history.getStartTime());

                                    calendar.set(calendar.get(Calendar.YEAR),
                                            (calendar.get(Calendar.MONTH)+1),
                                            calendar.get(Calendar.DATE),
                                            calendar.get(Calendar.HOUR),
                                            calendar.get(Calendar.MINUTE));

                                    //log alarm
                                    Intent intentLog = new Intent(mActivity, ShowReminderReceiver.class);
                                    intentLog.putExtra(AppConstants.LOG_ALARM, AppConstants.LOG_ALARM);

                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                            mActivity, i, intentLog, 0);

                                    MainApplication.alarmManager[i] = (AlarmManager) getSystemService(ALARM_SERVICE);

                                    MainApplication.alarmManager[i].set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                            pendingIntent);

                                    MainApplication.arrayList.add(pendingIntent);
                                }/*

                                String[] dateTime = history.getDateTime().split("T");
                                String[] dateYearMonth = dateTime[0].split("-");

                                String endTimeAmPm = history.getStartTime().split(" ")[1];
                                String startTimeHr = history.getStartTime().split(":")[0];

                                String startTime;
                                if (startTimeHr.equalsIgnoreCase("00") ||
                                        startTimeHr.equalsIgnoreCase("0")){
                                    startTime = "12";
                                }else {
                                    startTime = String.valueOf(Integer.parseInt(startTimeHr) - 1);
                                }

                                String startTimeMM = history.getStartTime().split(":")[1].split(" ")[0];


                                calendar.set(Integer.valueOf(dateYearMonth[0]),
                                        Integer.valueOf(dateYearMonth[1]),
                                        Integer.valueOf(dateYearMonth[2]),
                                        Integer.valueOf(startTime),
                                        Integer.valueOf(startTimeMM));

              */                  /*calendar.set(2018,
                                        06,
                                        17,
                                        17,
                                        55);*/

                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        orderHistoryDB.close();
                    }
                }else{
                    //remove notification

                    //Disable
                    mActivity.getPackageManager().
                            setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED ,
                                    PackageManager.DONT_KILL_APP);
//Enable
                }
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initView() {
        mUpdateSettingsInterface= (UpdateSettingsInterface) this;
        mSessionManager = new SessionManager(mActivity);
        ((MainApplication) getApplication()).getNetComponent().inject(SettingsActivity.this);
        api = retrofit.create(Api.class);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_settings));

        //infobip user data update
        userData = new UserData();
        if (mSessionManager.isUserLoggedIn()) {
            if (mSessionManager.GetUserSettings()) {
                SetAlreadyUpdatedSettings();
            } else {
                GetUpdatedUserSettings();
            }
        } else {
            //Guest user
            mLinearLogout.setVisibility(View.GONE);

            sp = getSharedPreferences("guest_switchs", MODE_PRIVATE);
            if (sp.getString("notificationSwitch", "true").equals("true")) {
                mNotificationSwitch.setChecked(true);
                userData.setCustomUserDataElement("notificationSwitch", new CustomUserDataValue("true"));
            } else {
                mNotificationSwitch.setChecked(false);
                userData.setCustomUserDataElement("notificationSwitch", new CustomUserDataValue("false"));
            }

            if (sp.getString("promotionSwitch", "true").equals("true")) {
                mPromotionSwitch.setChecked(true);
                userData.setCustomUserDataElement("promotionSwitch", new CustomUserDataValue("true"));
            } else {
                mPromotionSwitch.setChecked(false);
                userData.setCustomUserDataElement("promotionSwitch", new CustomUserDataValue("false"));
            }

            mFeedbackSwitch.setClickable(false);
            mFeedbackSwitch.setChecked(false);
            mNewletterSwitch.setClickable(false);
            mNewletterSwitch.setChecked(false);
            mReminderSwitch.setClickable(false);
            mReminderSwitch.setChecked(false);

            SetLanguageForPage();

//            getMobileMessaging().getInstance(SettingsActivity.this).syncUserData(userData);
        }
    }

    private boolean checkAccessPermission()
    {
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int res = mActivity.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private void GetUpdatedUserSettings() {
        MainController controller = new MainController(mActivity);
        controller.getUpdatedSettings(taskComplete, api);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mSessionManager.isUserLoggedIn()) {
            StartServiceUpdateSettings(getResources().getString(R.string.OnBackPressed));
        }
    }

    private void StartServiceUpdateSettings(String mFrom) {
        if (Connections.isConnectionAlive(mActivity)) {
            mNotifSwitch = mNotificationSwitch.isChecked() ? "true" : "false";
            mPromoSwitch = mPromotionSwitch.isChecked() ? "true" : "false";
            mFeedbackNotifSwitch = mFeedbackSwitch.isChecked() ? "true" : "false";
            mNewsletterSwitch = mNewletterSwitch.isChecked() ? "true" : "false";
            mBookedShowSwitch = mReminderSwitch.isChecked() ? "true" : "false";

            String languageType="";
            if (LanguageManager.createInstance().
                    GetSharedPreferences(mActivity, LanguageManager.createInstance().mSelectedLanguage, "").
                    equalsIgnoreCase(LanguageManager.mLanguageEnglish)) {
                languageType = AppConstants.EnglishLanguage;
            } else {
                languageType = AppConstants.ArabicLanguage;
            }

            mSettingsService.StartServiceFunction(mActivity, mNotifSwitch, mPromoSwitch, mFeedbackNotifSwitch, mNewsletterSwitch, mBookedShowSwitch, mNewLanguage, mFrom,userData,languageType);
        } else {
            //Toast.makeText(mActivity, getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
            customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
        }
    }

    @OnClick({R.id.englishSwitch, R.id.arabicSwitch, R.id.tvLogout, R.id.linearLogout, R.id.notificationSwitch, R.id.promotionSwitch})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.englishSwitch: {
                if (mSessionManager.isUserLoggedIn()) {
                    englishSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
                    arabicSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                    mNewLanguage = LanguageManager.createInstance().mLanguageEnglish;

                    StartServiceUpdateSettings(getResources().getString(R.string.OnLanguageChange));
                    /*sendUpdatedSettings();*/
                } else {
                    RedirectToHome(LanguageManager.createInstance().mLanguageEnglish);
                }
            }
            break;

            case R.id.arabicSwitch: {
                if (mSessionManager.isUserLoggedIn()) {
                    englishSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                    arabicSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
                    mNewLanguage = LanguageManager.createInstance().mLanguageArabic;

                    StartServiceUpdateSettings(getResources().getString(R.string.OnLanguageChange));
                    /*sendUpdatedSettings();*/
                } else {
                    RedirectToHome(LanguageManager.createInstance().mLanguageArabic);
                }
            }
            break;

            case R.id.linearLogout:
                LogoutDialog dialog = new LogoutDialog(mActivity, getString(R.string.logout_header), getString(R.string.logout_msg), getString(R.string.ok),mUpdateSettingsInterface);
                dialog.show();
                /*StartServiceUpdateSettings(getResources().getString(R.string.logout));*/
                /* mSessionManager.logoutUser();*/
                break;

            case R.id.tvLogout:
                LogoutDialog dialog1 = new LogoutDialog(mActivity, getString(R.string.logout_header), getString(R.string.logout_msg), getString(R.string.ok),mUpdateSettingsInterface);
                dialog1.show();

                /*StartServiceUpdateSettings(getResources().getString(R.string.logout));
                mSessionManager.logoutUser();*/
                break;
            case R.id.notificationSwitch:
                if (!mSessionManager.isUserLoggedIn()) {
                        mNotifSwitch = mNotificationSwitch.isChecked() ? "true" : "false";
                        sp.edit().putString("notificationSwitch", mNotifSwitch).apply();
                        userData.setCustomUserDataElement("notificationSwitch",
                                new CustomUserDataValue(mNotifSwitch));
                        ((MainApplication)getApplication()).getMobileMessaging().getInstance(SettingsActivity.this).syncUserData(userData);
                }
                break;
            case R.id.promotionSwitch:
                if (!mSessionManager.isUserLoggedIn()) {
                    mPromoSwitch = mPromotionSwitch.isChecked() ? "true" : "false";
                    sp.edit().putString("promotionSwitch", mPromoSwitch).apply();
                    userData.setCustomUserDataElement("promotionSwitch",
                            new CustomUserDataValue(mPromoSwitch));
                    ((MainApplication)getApplication()).getMobileMessaging().getInstance(SettingsActivity.this).syncUserData(userData);
                }
                break;

            case R.id.reminderSwitch:

                if (mReminderSwitch.isChecked()){

                }
                break;
        }
    }

    private void RedirectToHome(String mSelectedLanguage) {
        LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                LanguageManager.createInstance().mSelectedLanguage,
                mSelectedLanguage);

        openActivityWithClearPreviousActivities(mActivity, PreLoginActivity.class);

    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            if (mRequestKey.equalsIgnoreCase(AppConstants.GETUSERSETTINGS.GETUSERSETTINGS)) {
                if (response.body() != null) {
                    FavouriteAndSettingsResponseMain mSettingsResponse = (FavouriteAndSettingsResponseMain) response.body();
                    if (mSettingsResponse.getStatus().equalsIgnoreCase(AppConstants.STATUS_SUCCESS)) {

                        SessionManager sessionManager = new SessionManager(mActivity);
                        sessionManager.UpdateUserSettings(mSettingsResponse.getData().getSettings().getAllowNotification(), mSettingsResponse.getData().getSettings().getAllowPromotion(),
                                mSettingsResponse.getData().getSettings().getAllowFeedbackNotification(), mSettingsResponse.getData().getSettings().getWeeklyNewsLetters(), mSettingsResponse.getData().getSettings().getRemindersForBookedShow());

                        /*LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                                LanguageManager.createInstance().mSelectedLanguage,
                                mSettingsResponse.getData().getLanguage());*/
                        SetAlreadyUpdatedSettings();
                    } else {
                        try {
                            ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                            dialogue.show();
                        } catch (Exception e) {
                            //Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                            customToast.showErrorToast(e.getMessage());
                        }
                    }

                } else if (response.errorBody() != null) {
                    try {
                        ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                        dialogue.show();
                    } catch (Exception e) {
                        //Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                        customToast.showErrorToast(e.getMessage());
                    }
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("Error", call.toString());
        }
    };

    private void SetAlreadyUpdatedSettings() {
        if (mSharedPreferences.getString(getString(R.string.NotificationSwitchValue), "true").
                equalsIgnoreCase("true")) {
            if (checkAccessPermission()){
                mNotificationSwitch.setChecked(true);
                userData.setCustomUserDataElement("notificationSwitch", new CustomUserDataValue("true"));
            }else {
                mNotificationSwitch.setChecked(false);
                userData.setCustomUserDataElement("notificationSwitch", new CustomUserDataValue("false"));
            }

        } else {
            mNotificationSwitch.setChecked(false);
            userData.setCustomUserDataElement("notificationSwitch", new CustomUserDataValue("false"));
        }

        if (mSharedPreferences.getString(getString(R.string.PromotionSwitchValue), "true").
                equalsIgnoreCase("true")) {
            mPromotionSwitch.setChecked(true);
            userData.setCustomUserDataElement("promotionSwitch", new CustomUserDataValue("true"));
        } else {
            mPromotionSwitch.setChecked(false);
            userData.setCustomUserDataElement("promotionSwitch", new CustomUserDataValue("false"));
        }

        if (mSharedPreferences.getString(getString(R.string.FeedbackNotiSwitchValue), "true").equalsIgnoreCase("true")) {
            mFeedbackSwitch.setChecked(true);
        } else {
            mFeedbackSwitch.setChecked(false);
        }

        if (mSharedPreferences.getString(getString(R.string.NewsLetterSwitchValue), "true").equalsIgnoreCase("true")) {
            mNewletterSwitch.setChecked(true);
        } else {
            mNewletterSwitch.setChecked(false);
        }

        if (mSharedPreferences.getString(getString(R.string.BookedShowSwitchValue), "true").equalsIgnoreCase("true")) {
            mReminderSwitch.setChecked(true);
        } else {
            mReminderSwitch.setChecked(false);
        }
        SetLanguageForPage();
        ((MainApplication)getApplication()).getMobileMessaging().getInstance(SettingsActivity.this).syncUserData(userData);
    }

    private void SetLanguageForPage() {
        if (LanguageManager.createInstance().GetSharedPreferences(mActivity,
                LanguageManager.createInstance().mSelectedLanguage,
                LanguageManager.createInstance().mLanguageEnglish).
                equalsIgnoreCase(LanguageManager.createInstance().mLanguageEnglish)) {
            englishSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
            arabicSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            mNewLanguage = LanguageManager.createInstance().mLanguageEnglish;
        } else {
            englishSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            arabicSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
            mNewLanguage = LanguageManager.createInstance().mLanguageArabic;
        }
    }

    @Override
    public void UpdateSettingsPage() {
        StartServiceUpdateSettings(mActivity.getResources().getString(R.string.logout));
    }
}
