package com.opera.app.controller;

import android.content.Context;

import com.opera.app.constants.AppConstants;
import com.opera.app.dagger.Api;
import com.opera.app.dataadapter.DataListener;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.RequestProperties;
import com.opera.app.pojo.contactUs.ContactUs;
import com.opera.app.pojo.login.ForgotPasswordPojo;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.profile.EditProfile;
import com.opera.app.pojo.profile.PostChangePassword;
import com.opera.app.pojo.registration.Registration;
import com.opera.app.pojo.restaurant.booktable.BookTableRequest;
import com.opera.app.pojo.restaurant.getmasterdetails.GetMasterDetailsRequestPojo;
import com.opera.app.pojo.restaurant.getmasterdetails.RestaurantMasterDetails;
import com.opera.app.pojo.settings.SetSettingsPojo;
import com.opera.app.preferences.SessionManager;

import retrofit2.Call;

/**
 * Created by 1000779 on 3/21/2018.
 */

public class MainController {

    private Context context;
    private String contentType;
    private SessionManager manager;
    private RequestProperties properties;

    public MainController(Context context) {
        this.context = context;
        contentType = "application/json";
        manager = new SessionManager(context);
        properties = new RequestProperties();
    }

    public void loginPost(TaskComplete taskComplete, Api api, PostLogin postLogin) {

        Call call = api.userLogin(contentType, postLogin);
        properties.setRequestKey(AppConstants.LOGIN.LOGIN);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void registerPost(TaskComplete taskComplete, Api api, Registration registration) {
        Call call = api.userRegistration(contentType, registration);
        properties.setRequestKey(AppConstants.REGISTER.REGISTER);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void editProfilePost(TaskComplete taskComplete, Api api, EditProfile editProfile) {
        Call call = api.userEditprofile(contentType, manager.getUserLoginData().getData().getToken(), editProfile);
        properties.setRequestKey(AppConstants.EDITPROFILE.EDITPROFILE);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void changePassword(TaskComplete taskComplete, Api api, PostChangePassword mPostChangePwd) {
        Call call = api.ChangePassword(contentType, manager.getUserLoginData().getData().getToken(), mPostChangePwd);
        properties.setRequestKey(AppConstants.CHANGEPASSWORD.CHANGEPASSWORD);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void forgotPassword(TaskComplete taskComplete, Api api, ForgotPasswordPojo mForgotPasswordPojo) {
        Call call = api.ForgotPassword(contentType, mForgotPasswordPojo);
        properties.setRequestKey(AppConstants.FORGOTPASSWORD.FORGOTPASSWORD);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void updateSettings(TaskComplete taskComplete, Api api, SetSettingsPojo mSettingsPojo) {
        Call call = api.UpdateSettings(contentType, manager.getUserLoginData().getData().getToken(), mSettingsPojo);
        properties.setRequestKey(AppConstants.SETUSERSETTINGS.SETUSERSETTINGS);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getUpdatedSettings(TaskComplete taskComplete, Api api) {
        Call call = api.GetUpdatedSettings(contentType, manager.getUserLoginData().getData().getToken());
        properties.setRequestKey(AppConstants.GETUSERSETTINGS.GETUSERSETTINGS);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getRestaurantListing(TaskComplete taskComplete, Api api) {
        Call call = api.GetRestaurantListing(contentType);          // need to add auth token
        properties.setRequestKey(AppConstants.GETRESTAURANTLISTING.GETRESTAURANTLISTING);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void fetchMasterDetails(TaskComplete taskComplete, Api api, GetMasterDetailsRequestPojo getMasterDetailsRequestPojo) {

        Call<RestaurantMasterDetails> call;
        if (manager.getUserLoginData() != null) {
            call = api.RestaurantsGetMasterDetails(contentType, manager.getUserLoginData().getData().getToken(), getMasterDetailsRequestPojo);
        } else {
            call = api.RestaurantsGetMasterDetails(contentType, "", getMasterDetailsRequestPojo);
        }
        properties.setRequestKey(AppConstants.GETMASTERDETAILS.GETMASTERDETAILS);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void reserveTable(TaskComplete taskComplete, Api api, BookTableRequest tableResponse){
        Call call = api.ReserveRestaurantSeat(contentType, tableResponse);          // need to add auth token
        properties.setRequestKey(AppConstants.BOOKATABLE.BOOKATABLE);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void contactUs(TaskComplete taskComplete, Api api, ContactUs contact) {
        Call call = api.contactUs(contentType, contact);
        properties.setRequestKey(AppConstants.CONTACTUS.CONTACTUS);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getSpecificRestaurant(TaskComplete taskComplete, Api api) {
        Call call = api.GetSpecificRestaurant(contentType, AppConstants.SEAN_CONOLLY_RESTAURANT_ID);          // need to add auth token
        properties.setRequestKey(AppConstants.GETSPECIFICRESTAURANT.GETSPECIFICRESTAURANT);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }


}
