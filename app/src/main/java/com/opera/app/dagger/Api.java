package com.opera.app.dagger;

import com.opera.app.pojo.contactUs.ContactUs;
import com.opera.app.pojo.contactUs.ContactUsResponse;
import com.opera.app.pojo.events.eventdetails.GetEventDetails;
import com.opera.app.pojo.events.eventlisiting.AllEvents;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettings;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettingsResponseMain;
import com.opera.app.pojo.feedback.FeedbackResponseParent;
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
import com.opera.app.pojo.wallet.eventwallethistory.BookedEventHistory;
import com.opera.app.pojo.wallet.WalletDetails;

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
                                              @Body FavouriteAndSettings favouriteAndSettings);

    @POST("accounts/extended/GetUserSettings/")
    Call<FavouriteAndSettingsResponseMain> GetUpdatedSettings(@Header("Content-Type") String content, @Header("Authorization") String token);

    @POST("restaurants/extended/GetRestaurantDetails/")
    Call<RestaurantListing> GetRestaurantListing(@Header("Content-Type") String content);

    @POST("restaurants/extended/GetMasterDetails/")
    Call<RestaurantMasterDetails> RestaurantsGetMasterDetails(@Header("Content-Type") String content, @Header("Authorization") String token,
                                                              @Body GetMasterDetailsRequestPojo getMasterDetailsRequestPojo);

    @POST("contatcus/extended/SaveContact/")
    Call<ContactUsResponse> contactUs(@Header("Content-Type") String content,
                                      @Body ContactUs contactUs);

    @POST("restaurants/extended/BookTable/")
    Call<ReserveResponse> ReserveRestaurantSeat(@Header("Content-Type") String content, @Header("Authorization") String token,
                                                @Body BookTableRequest tableResponse);

    @POST("restaurants/extended/GetRestaurant/")
    Call<RestaurantListing> GetSpecificRestaurant(@Header("Content-Type") String content, @Query("restaurantId") String restaurantId);

    @POST("restaurants/extended/GetRestaurantById/")
    Call<RestaurantListing> GetSpecificRestaurantWithSiteCoreId(@Header("Content-Type") String content, @Query("id") String restaurantId);

    @POST("events/extended/GetEvents/")
    Call<AllEvents> GetEventListing();

    @POST("events/extended/GetEventById/")
    Call<GetEventDetails> GetEventDetails(@Query("itemId") String EventId);

    @POST("restaurants/extended/GetWalletDetails/")
    Call<WalletDetails> getWalletDetails(@Header("Content-Type") String content, @Header("Authorization") String token);

    @POST("promotion/extended/GetNotifications")
    Call<NotificationDetails> getNotificationDetails();

    @POST("promotion/extended/GetPromotions")
    Call<PromotionsPojo> getPromotionDetails();

    @POST("accounts/extended/setUserSettings/")
    Call<FavouriteAndSettingsResponseMain> MarkFavouriteForEvent(@Header("Content-Type") String content, @Header("Authorization") String token,
                                                                 @Body FavouriteAndSettings mFavouriteAndSettings);

    @POST("events/extended/GetEvents/")
    Call<AllEvents> GetDubaiOperaTour(@Query("type") String eventType);

    @POST("events/extended/GetEvents/")
    Call<AllEvents> GetGiftCard(@Query("type") String eventType);

    @POST("accounts/extended/SaveOrder/")
    Call<SuccessResponse> SaveOrderAPI(@Header("Content-Type") String content, @Header("Accept-Language") String mLanguage,
                                       @Header("X-Customer") String dtcmCustomerId, @Header("Authorization") String mAuthorization,
                                       @Body EventTicketBookingPojo mCompleteData);

    @GET("http://www.mocky.io/v2/5b0269dc3000007400cee0ff")
    Call<FeedbackResponseParent> getFeedbackDetails();

    @GET("http://www.mocky.io/v2/5b4c544d3100006300a7df5a%20")
    Call<List<BookedEventHistory>> getBookedEventDetails(@Header("Content-Type") String content, @Header("Authorization") String token);



}
