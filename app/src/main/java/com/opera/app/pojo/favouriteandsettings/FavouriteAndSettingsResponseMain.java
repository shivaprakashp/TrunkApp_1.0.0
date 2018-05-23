package com.opera.app.pojo.favouriteandsettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opera.app.pojo.login.LoginResponseData;

/**
 * Created by 1000632 on 5/23/2018.
 */

public class FavouriteAndSettingsResponseMain {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private FavouriteAndSettings data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FavouriteAndSettings getData() {
        return data;
    }

    public void setData(FavouriteAndSettings data) {
        this.data = data;
    }
}
