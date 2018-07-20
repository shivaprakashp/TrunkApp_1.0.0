package com.opera.app.dagger;

import com.opera.app.pojo.contactUs.ContactUs;
import com.opera.app.pojo.contactUs.ContactUsResponse;
import com.opera.app.pojo.events.eventdetails.GetEventDetails;
import com.opera.app.pojo.events.eventlisiting.AllEvents;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettings;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettingsResponseMain;
import com.opera.app.pojo.login.ForgotPasswordPojo;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.notifications.NotificationDetails;
import com.opera.app.pojo.profile.EditProfile;
import com.opera.app.pojo.profile.EditProfileResponse;
import com.opera.app.pojo.profile.PostChangePassword;
import com.opera.app.pojo.promotions.PromotionsPojo;
import com.opera.app.pojo.registration.Registration;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.pojo.restaurant.RestaurantListing;
import com.opera.app.pojo.restaurant.booktable.BookTableRequest;
import com.opera.app.pojo.restaurant.booktable.ReserveResponse;
import com.opera.app.pojo.restaurant.getmasterdetails.GetMasterDetailsRequestPojo;
import com.opera.app.pojo.restaurant.getmasterdetails.RestaurantMasterDetails;
import com.opera.app.pojo.ticketbooking.EventTicketBookingPojo;
import com.opera.app.pojo.ticketbooking.SuccessResponse;
import com.opera.app.pojo.wallet.WalletDetails;
import com.opera.app.pojo.wallet.eventwallethistory.BookedEventHistory;
import com.opera.app.pojo.wallet.eventwallethistory.ParentDataForBookedEventHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 1000779 on 1/22/2018.
 */

public interface Api {

    @POST("accounts/extended/login/")
    Call<LoginResponse> userLogin(@Header("Content-Type") String content,
                                  @Header("Accept-Language") String mLanguage,
                                  @Body PostLogin postLogin);

    @POST("accounts/extended/Register/")
    Call<RegistrationResponse> userRegistration(@Header("Content-Type") String content,@Body Registration registration);

    @POST("accounts/extended/editProfile/")
    Call<EditProfileResponse> userEditprofile(@Header("Content-Type") String content,
                                              @Header("Accept-Language") String mLanguage,
                                              @Header("X-Customer") String dtcmCustomerId,
                                              @Header("Authorization") String token,
                                              @Body EditProfile editProfile);

    @POST("accounts/extended/ChangePassword/")
    Call<RegistrationResponse> ChangePassword(@Header("Content-Type") String content,
                                              @Header("Accept-Language") String mLanguage,
                                              @Header("X-Customer") String dtcmCustomerId,
                                              @Header("Authorization") String token,
                                              @Body PostChangePassword mPostChangePassword);

    @POST("accounts/extended/ForgotPassword/")
    Call<RegistrationResponse> ForgotPassword(@Header("Content-Type") String content,
                                              @Header("Accept-Language") String mLanguage,
                                              @Body ForgotPasswordPojo mForgotPasswordPojo);

    @POST("accounts/extended/setUserSettings/")
    Call<RegistrationResponse> UpdateSettings(@Header("Content-Type") String content,
                                              @Header("Accept-Language") String mLanguage,
                                              @Header("Authorization") String token,
                                              @Body FavouriteAndSettings favouriteAndSettings);

    //@POST("accounts/extended/GetUserSettings/")
    @GET("http://www.mocky.io/v2/5b504f543600003c14dd0d58")
    //Call<FavouriteAndSettingsResponseMain> GetUpdatedSettings(@Header("Content-Type") String content, @Header("Authorization") String token);
    Call<FavouriteAndSettingsResponseMain> GetUpdatedSettings();

    @POST("restaurants/extended/GetRestaurantDetails/")
    Call<RestaurantListing> GetRestaurantListing(@Header("Content-Type") String content,
                                                 @Header("Accept-Language") String mLanguage);

    @POST("restaurants/extended/GetMasterDetails/")
    Call<RestaurantMasterDetails> RestaurantsGetMasterDetails(@Header("Content-Type") String content,
                                                              @Header("Accept-Language") String mLanguage,
                                                              @Header("Authorization") String token,
                                                              @Body GetMasterDetailsRequestPojo getMasterDetailsRequestPojo);

    @POST("contatcus/extended/SaveContact/")
    Call<ContactUsResponse> contactUs(@Header("Content-Type") String content,
                                      @Header("Accept-Language") String mLanguage,
                                      @Body ContactUs contactUs);

    @POST("restaurants/extended/BookTable/")
    Call<ReserveResponse> ReserveRestaurantSeat(@Header("Content-Type") String content,
                                                @Header("Accept-Language") String mLanguage,
                                                @Header("Authorization") String token,
                                                @Body BookTableRequest tableResponse);

    @POST("restaurants/extended/GetRestaurant/")
    Call<RestaurantListing> GetSpecificRestaurant(@Header("Content-Type") String content,
                                                  @Header("Accept-Language") String mLanguage,
                                                  @Query("restaurantId") String restaurantId);

    @POST("restaurants/extended/GetRestaurantById/")
    Call<RestaurantListing> GetSpecificRestaurantWithSiteCoreId(@Header("Content-Type") String content,
                                                                @Header("Accept-Language") String mLanguage,
                                                                @Query("id") String restaurantId);

    @POST("events/extended/GetEvents/")
    Call<AllEvents> GetEventListing(@Header("Accept-Language") String mLanguage);

    @POST("events/extended/GetEventById/")
    Call<GetEventDetails> GetEventDetails(@Header("Accept-Language") String mLanguage,
                                          @Query("itemId") String EventId);

    @POST("restaurants/extended/GetWalletDetails/")
    Call<WalletDetails> getWalletDetails(@Header("Content-Type") String content,
                                         @Header("Accept-Language") String mLanguage,
                                         @Header("Authorization") String token);

    @POST("promotion/extended/GetNotifications")
    Call<NotificationDetails> getNotificationDetails(@Header("Accept-Language") String mLanguage);

    @POST("promotion/extended/GetPromotions")
    Call<PromotionsPojo> getPromotionDetails(@Header("Accept-Language") String mLanguage);

    @POST("accounts/extended/setUserSettings/")
    Call<FavouriteAndSettingsResponseMain> MarkFavouriteForEvent(@Header("Content-Type") String content,
                                                                 @Header("Accept-Language") String mLanguage,
                                                                 @Header("Authorization") String token,
                                                                 @Body FavouriteAndSettings mFavouriteAndSettings);

    @POST("events/extended/GetEvents/")
    Call<AllEvents> GetDubaiOperaTour(@Header("Accept-Language") String mLanguage,
                                      @Query("type") String eventType);

    @POST("events/extended/GetEvents/")
    Call<AllEvents> GetGiftCard(@Header("Accept-Language") String mLanguage,
                                @Query("type") String eventType);

    @POST("accounts/extended/SaveOrder/")
    Call<SuccessResponse> SaveOrderAPI(@Header("Content-Type") String content,
                                       @Header("Accept-Language") String mLanguage,
                                       @Header("X-Customer") String dtcmCustomerId,
                                       @Header("Authorization") String mAuthorization,
                                       @Body EventTicketBookingPojo mCompleteData);

    @POST("accounts/extended/ViewOrdersHistory")
    Call<ParentDataForBookedEventHistory> getBookedEventDetails(@Header("Content-Type") String content, @Header("Accept-Language") String mLanguage
            , @Header("X-Customer") String mDtcmId);

}
