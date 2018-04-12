package com.opera.app.dagger;

import com.opera.app.pojo.profile.EditProfile;
import com.opera.app.pojo.profile.EditProfileResponse;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.profile.PostChangePassword;
import com.opera.app.pojo.registration.Registration;
import com.opera.app.pojo.registration.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by 1000779 on 1/22/2018.
 */

public interface Api {

    @POST("login/")
    Call<LoginResponse> userLogin(@Header("Content-Type") String content,
                                  @Body PostLogin postLogin);

    @POST("Register/")
    Call<RegistrationResponse> userRegistration(@Header("Content-Type") String content,
                                                @Body Registration registration);

    @POST("editProfile/")
    Call<EditProfileResponse> userEditprofile(@Header("Content-Type") String content, @Header("Authorization") String token,
                                                        @Body EditProfile editProfile);

    @POST("ChangePassword/")
    Call<EditProfileResponse> ChangePassword(@Header("Content-Type") String content, @Header("Authorization") String token,
                                              @Body PostChangePassword mPostChangePassword);
}
