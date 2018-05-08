package com.opera.app.dagger;

import com.opera.app.pojo.contactUs.ContactUs;
import com.opera.app.pojo.contactUs.ContactUsResponse;
import com.opera.app.pojo.events.eventdetails.GetEventDetails;
import com.opera.app.pojo.events.eventlisiting.AllEvents;
import com.opera.app.pojo.events.genreslisting.GenresEvents;
import com.opera.app.pojo.login.ForgotPasswordPojo;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.profile.EditProfile;
import com.opera.app.pojo.profile.EditProfileResponse;
import com.opera.app.pojo.profile.PostChangePassword;
import com.opera.app.pojo.registration.Registration;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.pojo.restaurant.RestaurantListing;
import com.opera.app.pojo.restaurant.booktable.BookTableRequest;
import com.opera.app.pojo.restaurant.booktable.ReserveResponse;
import com.opera.app.pojo.restaurant.getmasterdetails.GetMasterDetailsRequestPojo;
import com.opera.app.pojo.restaurant.getmasterdetails.RestaurantMasterDetails;
import com.opera.app.pojo.settings.GetSettingsPojo;
import com.opera.app.pojo.settings.SetSettingsPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 1000779 on 1/22/2018.
 */

public interface Api {

    @POST("accounts/extended/login/")
    Call<LoginResponse> userLogin(@Header("Content-Type") String content,
                                  @Body PostLogin postLogin);

    @POST("accounts/extended/Register/")
    Call<RegistrationResponse> userRegistration(@Header("Content-Type") String content,
                                                @Body Registration registration);

    @POST("accounts/extended/editProfile/")
    Call<EditProfileResponse> userEditprofile(@Header("Content-Type") String content, @Header("Authorization") String token,
                                              @Body EditProfile editProfile);

    @POST("accounts/extended/ChangePassword/")
    Call<RegistrationResponse> ChangePassword(@Header("Content-Type") String content, @Header("Authorization") String token,
                                              @Body PostChangePassword mPostChangePassword);

    @POST("accounts/extended/ForgotPassword/")
    Call<RegistrationResponse> ForgotPassword(@Header("Content-Type") String content,
                                              @Body ForgotPasswordPojo mForgotPasswordPojo);

    @POST("accounts/extended/setUserSettings/")
    Call<RegistrationResponse> UpdateSettings(@Header("Content-Type") String content, @Header("Authorization") String token,
                                              @Body SetSettingsPojo mSettingsPojo);

    @POST("accounts/extended/GetUserSettings/")
    Call<GetSettingsPojo> GetUpdatedSettings(@Header("Content-Type") String content, @Header("Authorization") String token);

    @POST("restaurants/extended/GetRestaurantDetails/")
    Call<RestaurantListing> GetRestaurantListing(@Header("Content-Type") String content);

    @POST("restaurants/extended/GetMasterDetails/")
    Call<RestaurantMasterDetails> RestaurantsGetMasterDetails(@Header("Content-Type") String content, @Header("Authorization") String token,
                                                              @Body GetMasterDetailsRequestPojo getMasterDetailsRequestPojo);

    @POST("contatcus/extended/SaveContact/")
    Call<ContactUsResponse> contactUs(@Header("Content-Type") String content,
                                      @Body ContactUs contactUs);

    @POST("restaurants/extended/BookTable/")
    Call<ReserveResponse> ReserveRestaurantSeat(@Header("Content-Type") String content,
                                                @Body BookTableRequest tableResponse);

    @POST("restaurants/extended/GetRestaurant/")
    Call<RestaurantListing> GetSpecificRestaurant(@Header("Content-Type") String content, @Query("restaurantId") String restaurantId);

    @POST("events/extended/GetEvents/")
    Call<AllEvents> GetEventListing();

    @POST("events/extended/GetEventByName/")
    Call<GetEventDetails> GetEventDetails(@Query("eventName") String eventName);

    @POST("events/extended/GetGeneres/")
    Call<GenresEvents> GetGenresListing();

}
