package com.opera.app.dagger;

import com.opera.app.pojo.ResponseData;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.registration.Registration;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

/**
 * Created by 1000779 on 1/22/2018.
 */

public interface Api {

    @POST("login/")
    Call<LoginResponse> userLogin(@Header("Content-Type") String content,
                                  @Body PostLogin postLogin);

    @POST("Register/")
    Call<Registration> userRegistration(@Header("Content-Type") String content,
                                        @Body Registration registration);
}
