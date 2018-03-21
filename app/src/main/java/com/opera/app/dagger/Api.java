package com.opera.app.dagger;

import com.opera.app.pojo.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 1000779 on 1/22/2018.
 */

public interface Api {

    @GET("contacts/")
    Call<ResponseData> getData();

}
