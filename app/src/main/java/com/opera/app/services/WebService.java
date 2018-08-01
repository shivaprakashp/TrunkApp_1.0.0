package com.opera.app.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.activities.MainActivity;
import com.opera.app.activities.PreLoginActivity;
import com.opera.app.constants.AppConstants;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.LogoutDialog;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettings;
import com.opera.app.pojo.favouriteandsettings.Settings;
import com.opera.app.pojo.registration.RegistrationResponse;
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
 * Created by 1000632 on 7/30/2018.
 */

public class WebService {
    private String mBookedShowSwitch, mPromoSwitch, SelecteLanguage, mNewsletterSwitch, mNotifSwitch, mFeedbackNotifSwitch;
    private final Context mContext;
    private ProgressDialog mProgressDialog;
    private String mFrom, mUserToken;
    private CustomToast customToast;
    SessionManager sessionManager;
    //    SessionManager mSessionManager;
    private Api api;
    @Inject
    Retrofit retrofit;

    public WebService(Context ctx, String mBookedShowSwitch, String mPromoSwitch, String SelecteLanguage, String mNewsletterSwitch, String mNotifSwitch, String mFeedbackNotifSwitch, String mFrom, String mUserToken, String languageType) {
        //The only context that is safe to keep without tracking its lifetime
        //is application context. Activity context and Service context can expire
        //and we do not want to keep reference to them and prevent
        //GC from recycling the memory.
        this.mFrom = mFrom;
        this.mBookedShowSwitch = mBookedShowSwitch;
        this.mPromoSwitch = mPromoSwitch;
        this.SelecteLanguage = SelecteLanguage;
        this.mNewsletterSwitch = mNewsletterSwitch;
        this.mNotifSwitch = mNotifSwitch;
        this.mFeedbackNotifSwitch = mFeedbackNotifSwitch;
        this.mUserToken = mUserToken;

        mContext = ctx.getApplicationContext();
        mProgressDialog = new ProgressDialog(mContext);
//        mSessionManager = new SessionManager(mContext);
        customToast = new CustomToast(mContext);
        ((MainApplication) mContext.getApplicationContext()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);

        if (Connections.isConnectionAlive(mContext)) {
            sendUpdatedSettings(mBookedShowSwitch, mPromoSwitch, SelecteLanguage, mNewsletterSwitch, mNotifSwitch, mFeedbackNotifSwitch, languageType);
        } else {
            //Toast.makeText(mActivity, mActivity.getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
            customToast.showErrorToast(mContext.getResources().getString(R.string.internet_error_msg));
        }
    }

    private void sendUpdatedSettings(String mBookedShowSwitch, String mPromoSwitch, String SelecteLanguage, String mNewsletterSwitch, String mNotifSwitch, String mFeedbackNotifSwitch, String languageType) {
        Settings mSettings = new Settings(mBookedShowSwitch, mPromoSwitch, SelecteLanguage, mNewsletterSwitch, mNotifSwitch, mFeedbackNotifSwitch);
        String contentType = "application/json";
        Call call = api.UpdateSettings(contentType, languageType, mUserToken, new FavouriteAndSettings(mSettings));
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
                        if (mSettingsResponse.getStatus().equalsIgnoreCase(AppConstants.STATUS_SUCCESS)) {
//                            mApplication.getMobileMessaging().getInstance(mActivity).syncUserData(userData);

                            sessionManager = new SessionManager(mContext, "SettingService");
                            sessionManager.UpdateUserSettings(mNotifSwitch, mPromoSwitch, mFeedbackNotifSwitch, mNewsletterSwitch, mBookedShowSwitch);
                            if (mFrom.equalsIgnoreCase(mContext.getResources().getString(R.string.OnLanguageChange))) {

                                LanguageManager.createInstance().StoreInSharedPreference(mContext,
                                        LanguageManager.createInstance().mSelectedLanguage,
                                        SelecteLanguage);

                                Intent intent = new Intent(mContext, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mContext.startActivity(intent);
//                                mContext.finish();
                            } else if (mFrom.equalsIgnoreCase(mContext.getResources().getString(R.string.logout))) {
                                //mSessionManager.logoutUser(mActivity);
                                /*LogoutDialog dialog = new LogoutDialog((Activity) mContext, mContext.getString(R.string.logout_header), mContext.getString(R.string.logout_msg), mContext.getString(R.string.ok));
                                dialog.show();*/
                                sessionManager.logoutUser(mContext);

                                Intent intent = new Intent(mContext, PreLoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mContext.startActivity(intent);
                            }
                        }
                    } else {
                        if (mFrom.equalsIgnoreCase(mContext.getResources().getString(R.string.OnLanguageChange)) || mFrom.equalsIgnoreCase(mContext.getResources().getString(R.string.logout))) {
                            ErrorDialogue dialogue = new ErrorDialogue((Activity) mContext, jsonResponse(response));
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
        Intent i = new Intent(mContext, SettingsService.class);
        mContext.stopService(i);
    }

    public String jsonResponse(Response response) {

        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            return jObjError.getString(AppConstants.MESSAGE);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}