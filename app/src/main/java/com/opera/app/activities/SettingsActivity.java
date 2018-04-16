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
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.pojo.settings.SettingsPojo;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

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
                }else {
                    Toast.makeText(mActivity, getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void sendUpdatedSettings() {
        String mNotifSwitch=mNotificationSwitch.isChecked()?"true":"false";
        String mPromoSwitch=mPromotionSwitch.isChecked()?"true":"false";
        String mFeedbackNotifSwitch=mFeedbackSwitch.isChecked()?"true":"false";
        String mNewsletterSwitch=mNewletterSwitch.isChecked()?"true":"false";
        String mBookedShowSwitch=mReminderSwitch.isChecked()?"true":"false";

        MainController controller = new MainController(SettingsActivity.this);
        controller.updateSettings(taskComplete, api, new SettingsPojo(mNotifSwitch,mPromoSwitch,mFeedbackNotifSwitch,mNewsletterSwitch,mBookedShowSwitch));
    }
    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {


        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("Error", call.toString());
        }
    };
}
