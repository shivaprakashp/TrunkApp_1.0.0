package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.SuccessDialogue;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.pojo.settings.GetSettingsPojo;
import com.opera.app.pojo.settings.SetSettingsPojo;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 58001 on 23-03-2018.
 */

public class SettingsActivity extends BaseActivity {

    private Activity mActivity;
    private SessionManager mSessionManager;
    private String mNotifSwitch = "", mPromoSwitch = "", mFeedbackNotifSwitch = "", mNewsletterSwitch = "", mBookedShowSwitch = "", mSelectedLanguage = "";
    private Api api;
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

    @BindView(R.id.btnSave)
    Button mBtnSave;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = SettingsActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.setting);

        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initView() {
        mSessionManager = new SessionManager(mActivity);
        ((MainApplication) getApplication()).getNetComponent().inject(SettingsActivity.this);
        api = retrofit.create(Api.class);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_settings));


        if (LanguageManager.createInstance().GetSharedPreferences(mActivity,
                LanguageManager.createInstance().mSelectedLanguage,
                LanguageManager.createInstance().mLanguageEnglish).
                equalsIgnoreCase(LanguageManager.createInstance().mLanguageEnglish)) {
            englishSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
            arabicSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
        } else {
            englishSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            arabicSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
        }

        if (mSessionManager.GetUserSettings()) {
            SetAlreadyUpdatedSettings();
        } else {
            GetUpdatedUserSettings();
        }
    }

    private void GetUpdatedUserSettings() {
        MainController controller = new MainController(SettingsActivity.this);
        controller.getUpdatedSettings(taskComplete, api);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.englishSwitch, R.id.arabicSwitch, R.id.tvLogout, R.id.btnSave})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.englishSwitch: {
                englishSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
                arabicSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                        LanguageManager.createInstance().mSelectedLanguage,
                        LanguageManager.createInstance().mLanguageEnglish);

                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            break;

            case R.id.arabicSwitch: {
                englishSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                arabicSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
                LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                        LanguageManager.createInstance().mSelectedLanguage,
                        LanguageManager.createInstance().mLanguageArabic);

                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            break;

            case R.id.tvLogout:
                break;

            case R.id.btnSave:
                if (Connections.isConnectionAlive(mActivity)) {
                    sendUpdatedSettings();
                } else {
                    Toast.makeText(mActivity, getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void sendUpdatedSettings() {
        mNotifSwitch = mNotificationSwitch.isChecked() ? "true" : "false";
        mPromoSwitch = mPromotionSwitch.isChecked() ? "true" : "false";
        mFeedbackNotifSwitch = mFeedbackSwitch.isChecked() ? "true" : "false";
        mNewsletterSwitch = mNewletterSwitch.isChecked() ? "true" : "false";
        mBookedShowSwitch = mReminderSwitch.isChecked() ? "true" : "false";

        mSelectedLanguage = LanguageManager.createInstance().GetSharedPreferences(mActivity, LanguageManager.createInstance().mSelectedLanguage, LanguageManager.createInstance().mLanguageEnglish);

        MainController controller = new MainController(SettingsActivity.this);
        controller.updateSettings(taskComplete, api, new SetSettingsPojo(mNotifSwitch, mPromoSwitch, mFeedbackNotifSwitch, mNewsletterSwitch, mBookedShowSwitch, mSelectedLanguage));
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            if (mRequestKey.equalsIgnoreCase(AppConstants.SETUSERSETTINGS.SETUSERSETTINGS)) {
                if (response.body() != null) {
                    RegistrationResponse mSettingsResponse = (RegistrationResponse) response.body();
                    if (mSettingsResponse.getStatus().equalsIgnoreCase("success")) {
                        SessionManager sessionManager = new SessionManager(mActivity);
                        sessionManager.UpdateUserSettings(mNotifSwitch, mPromoSwitch, mFeedbackNotifSwitch, mNewsletterSwitch, mBookedShowSwitch);

                        LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                                LanguageManager.createInstance().mSelectedLanguage,
                                mSelectedLanguage);

                        SuccessDialogue dialogue = new SuccessDialogue(mActivity, getResources().getString(R.string.successSettingsUpdation), getResources().getString(R.string.success_header), getResources().getString(R.string.ok), "");
                        dialogue.show();
                    } else {
                        try {
                            ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                            dialogue.show();
                        } catch (Exception e) {
                            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                } else if (response.errorBody() != null) {
                    try {
                        ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                        dialogue.show();
                    } catch (Exception e) {
                        Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            } else if (mRequestKey.equalsIgnoreCase(AppConstants.GETUSERSETTINGS.GETUSERSETTINGS)) {
                if (response.body() != null) {
                    GetSettingsPojo mSettingsResponse = (GetSettingsPojo) response.body();
                    if (mSettingsResponse.getStatus().equalsIgnoreCase("success")) {

                        SessionManager sessionManager = new SessionManager(mActivity);
                        sessionManager.UpdateUserSettings(mSettingsResponse.getData().getAllowNotification(), mSettingsResponse.getData().getAllowPromotion(),
                                mSettingsResponse.getData().getAllowFeedbackNotification(), mSettingsResponse.getData().getWeeklyNewsLetters(), mSettingsResponse.getData().getRemindersForBookedShow());

                        /*LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                                LanguageManager.createInstance().mSelectedLanguage,
                                mSettingsResponse.getData().getLanguage());*/
                        SetAlreadyUpdatedSettings();
                    } else {
                        try {
                            ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                            dialogue.show();
                        } catch (Exception e) {
                            Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                } else if (response.errorBody() != null) {
                    try {
                        ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                        dialogue.show();
                    } catch (Exception e) {
                        Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
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

        if (mSharedPreferences.getString(getString(R.string.NotificationSwitchValue), "false").equalsIgnoreCase("true")) {
            mNotificationSwitch.setChecked(true);
        } else {
            mNotificationSwitch.setChecked(false);
        }

        if (mSharedPreferences.getString(getString(R.string.PromotionSwitchValue), "false").equalsIgnoreCase("true")) {
            mPromotionSwitch.setChecked(true);
        } else {
            mPromotionSwitch.setChecked(false);
        }

        if (mSharedPreferences.getString(getString(R.string.FeedbackNotiSwitchValue), "false").equalsIgnoreCase("true")) {
            mFeedbackSwitch.setChecked(true);
        } else {
            mFeedbackSwitch.setChecked(false);
        }

        if (mSharedPreferences.getString(getString(R.string.NewsLetterSwitchValue), "false").equalsIgnoreCase("true")) {
            mNewletterSwitch.setChecked(true);
        } else {
            mNewletterSwitch.setChecked(false);
        }

        if (mSharedPreferences.getString(getString(R.string.BookedShowSwitchValue), "false").equalsIgnoreCase("true")) {
            mReminderSwitch.setChecked(true);
        } else {
            mReminderSwitch.setChecked(false);
        }
    }
}
