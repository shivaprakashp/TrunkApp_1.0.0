package com.opera.app.preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.opera.app.R;
import com.opera.app.activities.PreLoginActivity;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.profile.EditProfileResponse;

public class SessionManager {

    private static SharedPreferences loginPref;
    private static String IS_USER_LOGIN = "IsUserLoggedIn";
    private SharedPreferences.Editor editor;
    Context context;
    int prefMode = 0 ;

    private Gson gson;

    public SessionManager(Context context){
        this.context = context;
        loginPref = context.getSharedPreferences(context.getString(R.string.prefName), prefMode);
        editor = loginPref.edit();

        gson = new Gson();
    }

    // Get Login State
    public boolean isUserLoggedIn(){
        return loginPref.getBoolean(IS_USER_LOGIN, false);
    }

    /*Create login session*/
    public void createLoginSession(LoginResponse loginResponse){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(context.getString(R.string.prefUserData), gson.toJson(loginResponse));
        editor.commit();
    }

    /*Create edit profile session*/
    public void createEditProfileSession(EditProfileResponse editProfileResponse){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(context.getString(R.string.prefUserData), gson.toJson(editProfileResponse));
        editor.commit();
    }

    //get stored user login data
    public LoginResponse getUserLoginData(){
        String userData = loginPref.getString(context.getString(R.string.prefUserData), "");
        return gson.fromJson(userData, LoginResponse.class);
    }

    //clear login session
    public void logoutUser(){
        //clear all data from shared preference
        editor.clear();
        editor.commit();

        //call Intent to redirect to particular page
        Intent intent = new Intent(context, PreLoginActivity.class);
        //clear all the top activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //start new activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
