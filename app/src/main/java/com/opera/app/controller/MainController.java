package com.opera.app.controller;

import android.app.Activity;
import android.content.Context;

import com.opera.app.dagger.Api;
import com.opera.app.dataadapter.DataListener;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.ForgotPasswordPojo;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.profile.EditProfile;
import com.opera.app.pojo.profile.PostChangePassword;
import com.opera.app.pojo.registration.Registration;
import com.opera.app.preferences.SessionManager;

import retrofit2.Call;

/**
 * Created by 1000779 on 3/21/2018.
 */

public class MainController {

    private Context context;
    private String contentType;
    private SessionManager manager;

    public MainController(Context context) {
        this.context = context;
        contentType = "application/json";
        manager = new SessionManager(context);
    }

    public void loginPost(TaskComplete taskComplete, Api api, PostLogin postLogin,String mRequestKey) {
        Call call = api.userLogin(contentType, postLogin);
        DataListener listener = new DataListener(context, taskComplete);
        listener.dataLoad(call,mRequestKey);
    }

    public void registerPost(TaskComplete taskComplete, Api api, Registration registration,String mRequestKey) {
        Call call = api.userRegistration(contentType, registration);
        DataListener listener = new DataListener(context, taskComplete);
        listener.dataLoad(call,mRequestKey);
    }

    public void editProfilePost(Activity mActivity, TaskComplete taskComplete, Api api, EditProfile editProfile,String mRequestKey) {
        Call call = api.userEditprofile(contentType, manager.getUserLoginData().getData().getToken(), editProfile);
        DataListener listener = new DataListener(context, taskComplete);
        listener.dataLoad(call,mRequestKey);
    }

    public void changePassword(TaskComplete taskComplete, Api api, PostChangePassword mPostChangePwd,String mRequestKey) {
        Call call = api.ChangePassword(contentType, manager.getUserLoginData().getData().getToken(), mPostChangePwd);
        DataListener listener = new DataListener(context, taskComplete);
        listener.dataLoad(call,mRequestKey);
    }

    public void forgotPassword(TaskComplete taskComplete, Api api, ForgotPasswordPojo mForgotPasswordPojo,String mRequestKey) {
        Call call = api.ForgotPassword(contentType, mForgotPasswordPojo);
        DataListener listener = new DataListener(context, taskComplete);
        listener.dataLoad(call,mRequestKey);
    }
}
