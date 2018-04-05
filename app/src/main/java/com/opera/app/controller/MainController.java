package com.opera.app.controller;

import android.content.Context;

import com.opera.app.dagger.Api;
import com.opera.app.dataadapter.DataListener;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.PostLogin;

import retrofit2.Call;

/**
 * Created by 1000779 on 3/21/2018.
 */

public class MainController {

    private Context context;

    public MainController(Context context){
        this.context = context;
    }

    public void callData(TaskComplete complete, Api api){
       // Call call = api.getData();
        //DataListener listener = new DataListener(context, complete);
        //listener.dataLoad(call);

    }

    public void loginPost(TaskComplete taskComplete, Api api, PostLogin postLogin){
        Call call = api.userLogin("application/json", postLogin);
        DataListener listener = new DataListener(context, taskComplete);
        listener.dataLoad(call);
    }
}
