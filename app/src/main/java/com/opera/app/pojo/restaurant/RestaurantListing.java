package com.opera.app.pojo.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/9/2018.
 */

public class RestaurantListing {

    @SerializedName("mRestaurantImage")
    @Expose
    String mRestaurantImage;

    @SerializedName("mRestaurantName")
    @Expose
    String mRestaurantName;

    @SerializedName("mRestaurantPlace")
    @Expose
    String mRestaurantPlace;


    public RestaurantListing(String mRestaurantImage, String mRestaurantName, String mRestaurantPlace) {
        this.mRestaurantImage = mRestaurantImage;
        this.mRestaurantName = mRestaurantName;
        this.mRestaurantPlace = mRestaurantPlace;
    }

    public String getmRestaurantImage() {
        return mRestaurantImage;
    }

    public void setmRestaurantImage(String mRestaurantImage) {
        this.mRestaurantImage = mRestaurantImage;
    }

    public String getmRestaurantName() {
        return mRestaurantName;
    }

    public void setmRestaurantName(String mRestaurantName) {
        this.mRestaurantName = mRestaurantName;
    }

    public String getmRestaurantPlace() {
        return mRestaurantPlace;
    }

    public void setmRestaurantPlace(String mRestaurantPlace) {
        this.mRestaurantPlace = mRestaurantPlace;
    }
}
