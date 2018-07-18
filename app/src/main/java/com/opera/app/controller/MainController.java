package com.opera.app.controller;

import android.app.Activity;

import com.opera.app.constants.AppConstants;
import com.opera.app.dagger.Api;
import com.opera.app.dataadapter.DataListener;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.RequestProperties;
import com.opera.app.pojo.contactUs.ContactUs;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettings;
import com.opera.app.pojo.login.ForgotPasswordPojo;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.profile.EditProfile;
import com.opera.app.pojo.profile.PostChangePassword;
import com.opera.app.pojo.registration.Registration;
import com.opera.app.pojo.restaurant.booktable.BookTableRequest;
import com.opera.app.pojo.restaurant.getmasterdetails.GetMasterDetailsRequestPojo;
import com.opera.app.pojo.restaurant.getmasterdetails.RestaurantMasterDetails;
import com.opera.app.pojo.ticketbooking.EventTicketBookingPojo;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;

import retrofit2.Call;

/**
 * Created by 1000779 on 3/21/2018.
 */

public class MainController {
    private Activity mActivity;
    private String contentType;
    private String languageType;
    private SessionManager manager;
    private RequestProperties properties;

    public MainController(Activity mActivity) {
        this.mActivity = mActivity;
        contentType = "application/json";
        manager = new SessionManager(mActivity);
        properties = new RequestProperties();

        if (LanguageManager.createInstance().
                GetSharedPreferences(mActivity, LanguageManager.createInstance().mSelectedLanguage, "").
                equalsIgnoreCase(LanguageManager.mLanguageEnglish)) {
            languageType = AppConstants.EnglishLanguage;
        } else {
            languageType = AppConstants.ArabicLanguage;
        }
    }

    public void loginPost(TaskComplete taskComplete, Api api, PostLogin postLogin) {

        Call call = api.userLogin(contentType, languageType, postLogin);
        properties.setRequestKey(AppConstants.LOGIN.LOGIN);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void registerPost(TaskComplete taskComplete, Api api, Registration registration) {
        Call call = api.userRegistration(contentType,  registration);
        properties.setRequestKey(AppConstants.REGISTER.REGISTER);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void editProfilePost(TaskComplete taskComplete, Api api, EditProfile editProfile) {
        Call call = api.userEditprofile(contentType, languageType, manager.getUserLoginData().getData().getDtcmCustomerId(), manager.getUserLoginData().getData().getToken(), editProfile);
        properties.setRequestKey(AppConstants.EDITPROFILE.EDITPROFILE);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void changePassword(TaskComplete taskComplete, Api api, PostChangePassword mPostChangePwd) {
        Call call = api.ChangePassword(contentType, languageType, manager.getUserLoginData().getData().getDtcmCustomerId(), manager.getUserLoginData().getData().getToken(), mPostChangePwd);
        properties.setRequestKey(AppConstants.CHANGEPASSWORD.CHANGEPASSWORD);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void forgotPassword(TaskComplete taskComplete, Api api, ForgotPasswordPojo mForgotPasswordPojo) {
        Call call = api.ForgotPassword(contentType, languageType, mForgotPasswordPojo);
        properties.setRequestKey(AppConstants.FORGOTPASSWORD.FORGOTPASSWORD);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    /*public void updateSettings(TaskComplete taskComplete, Api api, Settings mSettingsPojo) {
        Call call = api.UpdateSettings(contentType, manager.getUserLoginData().getData().getToken(), mSettingsPojo);
        properties.setRequestKey(AppConstants.SETUSERSETTINGS.SETUSERSETTINGS);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }*/

    public void getUpdatedSettings(TaskComplete taskComplete, Api api) {
        //Call call = api.GetUpdatedSettings(contentType, manager.getUserLoginData().getData().getToken());
        Call call = api.GetUpdatedSettings();
        properties.setRequestKey(AppConstants.GETUSERSETTINGS.GETUSERSETTINGS);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getRestaurantListing(TaskComplete taskComplete, Api api) {
        Call call = api.GetRestaurantListing(contentType, languageType);          // need to add auth token
        properties.setRequestKey(AppConstants.GETRESTAURANTLISTING.GETRESTAURANTLISTING);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void fetchMasterDetails(TaskComplete taskComplete, Api api, GetMasterDetailsRequestPojo getMasterDetailsRequestPojo) {

        Call<RestaurantMasterDetails> call;
        if (manager.getUserLoginData() != null) {
            call = api.RestaurantsGetMasterDetails(contentType, languageType, manager.getUserLoginData().getData().getToken(), getMasterDetailsRequestPojo);
        } else {
            call = api.RestaurantsGetMasterDetails(contentType, languageType, "", getMasterDetailsRequestPojo);
        }
        properties.setRequestKey(AppConstants.GETMASTERDETAILS.GETMASTERDETAILS);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void reserveTable(TaskComplete taskComplete, Api api, BookTableRequest tableResponse) {
        Call call = api.ReserveRestaurantSeat(contentType, languageType, manager.getUserLoginData().getData().getToken(), tableResponse);          // need to add auth token
        properties.setRequestKey(AppConstants.BOOKATABLE.BOOKATABLE);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void contactUs(TaskComplete taskComplete, Api api, ContactUs contact) {
        Call call = api.contactUs(contentType, languageType, contact);
        properties.setRequestKey(AppConstants.CONTACTUS.CONTACTUS);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getSpecificRestaurant(TaskComplete taskComplete, Api api,String mRestaurantId) {
        Call call = api.GetSpecificRestaurant(contentType, languageType, mRestaurantId);          // need to add auth token
        properties.setRequestKey(AppConstants.GETSPECIFICRESTAURANT.GETSPECIFICRESTAURANT);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getSpecificRestaurantBySiteCoreId(TaskComplete taskComplete, Api api,String mRestaurantId) {
        Call call = api.GetSpecificRestaurantWithSiteCoreId(contentType, languageType, mRestaurantId);          // need to add auth token
        properties.setRequestKey(AppConstants.GETSPECIFICRESTAURANT.GETSPECIFICRESTAURANT);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getEventListing(TaskComplete taskComplete, Api api) {
        Call call = api.GetEventListing(languageType);          // need to add auth token
        properties.setRequestKey(AppConstants.GETEVENTLISTING.GETEVENTLISTING);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getEventDetails(TaskComplete taskComplete, Api api, String EventId) {
        Call call = api.GetEventDetails(languageType, EventId);
        properties.setRequestKey(AppConstants.GETEVENTDETAILS.GETEVENTDETAILS);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getWalletDetails(TaskComplete taskComplete, Api api){
        Call call = api.getWalletDetails(contentType, languageType, manager.getUserLoginData().getData().getToken());
        properties.setRequestKey(AppConstants.GETWALLETDETAIL.GETWALLETDETAIL);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

     public void getNotificationsDetails(TaskComplete taskComplete, Api api) {
        Call call = api.getNotificationDetails(languageType);
        properties.setRequestKey(AppConstants.GETNOTIFICATIONDETAILS.GETNOTIFICATIONDETAILS);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getPromotionDetails(TaskComplete taskComplete, Api api) {
        Call call = api.getPromotionDetails(languageType);
        properties.setRequestKey(AppConstants.GETPROMOTIONDETAILS.GETPROMOTIONDETAILS);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getFeedbackDetails(TaskComplete taskComplete, Api api) {
        Call call = api.getFeedbackDetails(languageType);
        properties.setRequestKey(AppConstants.GETFEEDBACKDETAILS.GETFEEDBACKDETAILS);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void updateSettingsAndFavourite(TaskComplete taskComplete, Api api, FavouriteAndSettings mFavouriteAndSettings) {
        Call call = api.MarkFavouriteForEvent(contentType, languageType, manager.getUserLoginData().getData().getToken(), mFavouriteAndSettings);
        properties.setRequestKey(AppConstants.MARKFAVOURITEFOREVENT.MARKFAVOURITEFOREVENT);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }


    public void getDubaiOperaTourDetails(TaskComplete taskComplete, Api api) {
        Call call = api.GetDubaiOperaTour(languageType, AppConstants.EVENT_TYPE_OPERA_TOUR);
        properties.setRequestKey(AppConstants.GETDUBAIOPERATOUR.GETDUBAIOPERATOUR);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getGiftCardDetails(TaskComplete taskComplete, Api api) {
        Call call = api.GetGiftCard(languageType, AppConstants.EVENT_TYPE_GIFT_CARD);
        properties.setRequestKey(AppConstants.GETGIFTCARD.GETGIFTCARD);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void SaveOrderAPI(TaskComplete taskComplete, Api api,EventTicketBookingPojo mCompleteData) {
        Call call = api.SaveOrderAPI(contentType, AppConstants.EnglishLanguage, manager.getUserLoginData().getData().getDtcmCustomerId(),
                manager.getUserLoginData().getData().getToken(), mCompleteData);
        properties.setRequestKey(AppConstants.SAVEORDER.SAVEORDER);
        DataListener listener = new DataListener(mActivity, taskComplete, properties);
        listener.dataLoad(call);
    }

    public void getBookedEventDetails(TaskComplete taskComplete, Api api){
        Call call = api.getBookedEventDetails(contentType, manager.getUserLoginData().getData().getToken());
        properties.setRequestKey(AppConstants.GETBOOKEDEVENTDETAILS.GETBOOKEDEVENTDETAILS);
        DataListener listener = new DataListener(context, taskComplete, properties);
        listener.dataLoad(call);
    }
}
