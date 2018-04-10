package com.opera.app.controller;

import android.app.Activity;
import android.content.Context;

import com.opera.app.dagger.Api;
import com.opera.app.dataadapter.DataListener;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.profile.EditProfile;
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

    public MainController(Context context){
        this.context = context;
        contentType = "application/json";
    }

    public void loginPost(TaskComplete taskComplete, Api api, PostLogin postLogin){
        Call call = api.userLogin(contentType, postLogin);
        DataListener listener = new DataListener(context, taskComplete);
        listener.dataLoad(call);
    }

    public void registerPost(TaskComplete taskComplete, Api api, Registration registration){
        Call call = api.userRegistration(contentType, registration);
        DataListener listener = new DataListener(context, taskComplete);
        listener.dataLoad(call);
    }

    public void editProfilePost(Activity mActivity, TaskComplete taskComplete, Api api, EditProfile editProfile){
        manager = new SessionManager(mActivity);
        Call call = api.userEditprofile(contentType, manager.getUserLoginData().getData().getToken() , editProfile);
        DataListener listener = new DataListener(context, taskComplete);
        listener.dataLoad(call);
    }
}
