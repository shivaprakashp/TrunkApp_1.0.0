package com.opera.app.services;

import android.app.Activity;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.activities.MainActivity;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettings;
import com.opera.app.pojo.favouriteandsettings.Settings;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.pojo.settings.SetSettingsPojo;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 4/18/2018.
 */

public class SettingsService extends IntentService {

    private String mNotifSwitch = "", mPromoSwitch = "", mFeedbackNotifSwitch = "", mNewsletterSwitch = "", mBookedShowSwitch = "", SelecteLanguage = "";
    private String contentType = "application/json";
    static String mFrom = "";
    private Api api;
    private CustomToast customToast;
    @Inject
    Retrofit retrofit;
    public static Activity mActivity;
    static SessionManager mSessionManager;
    private static ProgressDialog mProgressDialog;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SettingsService() {
        super("SettingsService");
    }

    public void StartServiceFunction(Activity activity, String mNotifSwitch, String mPromoSwitch, String mFeedbackNotifSwitch, String mNewsletterSwitch, String mBookedShowSwitch, String mNewLanguage, String From) {
        mActivity = activity;
        Intent i = new Intent(mActivity, SettingsService.class);
        i.putExtra("NotificationSwitch", mNotifSwitch);
        i.putExtra("PromotionSwitch", mPromoSwitch);
        i.putExtra("FeedbackNotificationSwitch", mFeedbackNotifSwitch);
        i.putExtra("NewsletterSwitch", mNewsletterSwitch);
        i.putExtra("BookedShowSwitch", mBookedShowSwitch);
        i.putExtra("SelecteLanguage", mNewLanguage);
        mActivity.startService(i);
        mSessionManager = new SessionManager(mActivity);
        customToast = new CustomToast(mActivity);
        this.mFrom = From;

        if (!mFrom.equalsIgnoreCase(mActivity.getResources().getString(R.string.OnBackPressed))) {
            try {
                mProgressDialog = new ProgressDialog(mActivity);
                mProgressDialog.setMessage(mActivity.getResources().getString(R.string.loading));
                mProgressDialog.show();
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ((MainApplication) getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);

        mNotifSwitch = intent.getStringExtra("NotificationSwitch");
        mPromoSwitch = intent.getStringExtra("PromotionSwitch");
        mFeedbackNotifSwitch = intent.getStringExtra("FeedbackNotificationSwitch");
        mNewsletterSwitch = intent.getStringExtra("NewsletterSwitch");
        mBookedShowSwitch = intent.getStringExtra("BookedShowSwitch");
        SelecteLanguage = intent.getStringExtra("SelecteLanguage");

        if (Connections.isConnectionAlive(mActivity)) {
            sendUpdatedSettings();
        } else {
            //Toast.makeText(mActivity, mActivity.getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
            customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
        }
    }

    private void sendUpdatedSettings() {

        Settings mSettings=new Settings(mBookedShowSwitch,mPromoSwitch,SelecteLanguage,mNewsletterSwitch,mNotifSwitch, mFeedbackNotifSwitch);
        Call call = api.UpdateSettings(contentType, mSessionManager.getUserLoginData().getData().getToken(), new FavouriteAndSettings(mSettings));
        dataLoad(call);
    }

    public void dataLoad(Call call) {

        try {
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    try {
                        mProgressDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (response.body() != null) {
                        RegistrationResponse mSettingsResponse = (RegistrationResponse) response.body();
                        if (mSettingsResponse.getStatus().equalsIgnoreCase("success")) {
                            SessionManager sessionManager = new SessionManager(mActivity);
                            sessionManager.UpdateUserSettings(mNotifSwitch, mPromoSwitch, mFeedbackNotifSwitch, mNewsletterSwitch, mBookedShowSwitch);
                            if (mFrom.equalsIgnoreCase(getResources().getString(R.string.OnLanguageChange))) {

                                LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                                        LanguageManager.createInstance().mSelectedLanguage,
                                        SelecteLanguage);

                                Intent intent = new Intent(mActivity, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                mActivity.finish();
                            } else if (mFrom.equalsIgnoreCase(getResources().getString(R.string.logout))) {
                                mSessionManager.logoutUser();
                            }
                        }
                    } else {
                        if (mFrom.equalsIgnoreCase(getResources().getString(R.string.OnLanguageChange)) || mFrom.equalsIgnoreCase(getResources().getString(R.string.logout))) {
                            ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                            dialogue.show();
                        }
                    }

                    stopService();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    try {
                        mProgressDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stopService();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            stopService();
        }

    }

    private void stopService() {
        Intent i = new Intent(mActivity, SettingsService.class);
        mActivity.stopService(i);
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
