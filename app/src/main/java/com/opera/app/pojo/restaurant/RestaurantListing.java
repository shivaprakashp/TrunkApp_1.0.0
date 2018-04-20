package com.opera.app.pojo.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opera.app.pojo.login.LoginResponseData;

import java.util.ArrayList;

/**
 * Created by 1000632 on 4/9/2018.
 */

public class RestaurantListing {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("restaurants")
    @Expose
    private ArrayList<restaurantsData> data;

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

    public ArrayList<restaurantsData> getData() {
        return data;
    }

    public void setData(ArrayList<restaurantsData> data) {
        this.data = data;
    }
}
