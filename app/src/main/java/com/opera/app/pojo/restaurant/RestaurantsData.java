package com.opera.app.pojo.restaurant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class RestaurantsData implements Serializable{

    @SerializedName("restId")
    @Expose
    public String restId;

    @SerializedName("restName")
    @Expose
    String restName;

    @SerializedName("restImage")
    @Expose
    String restImage;

    @SerializedName("restPlace")
    @Expose
    String restPlace;

    @SerializedName("restLocation")
    @Expose
    String restLocation;

    @SerializedName("restBookUrl")
    @Expose
    String restBookUrl;

    @SerializedName("restStatus")
    @Expose
    String restStatus;

    @SerializedName("restDetails")
    @Expose
    String restDetails;

    @SerializedName("openHour")
    @Expose
    String openHour;

    @SerializedName("phoneNumber")
    @Expose
    String phoneNumber;


    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestImage() {
        return restImage;
    }

    public void setRestImage(String restImage) {
        this.restImage = restImage;
    }

    public String getRestPlace() {
        return restPlace;
    }

    public void setRestPlace(String restPlace) {
        this.restPlace = restPlace;
    }

    public String getRestLocation() {
        return restLocation;
    }

    public void setRestLocation(String restLocation) {
        this.restLocation = restLocation;
    }

    public String getRestBookUrl() {
        return restBookUrl;
    }

    public void setRestBookUrl(String restBookUrl) {
        this.restBookUrl = restBookUrl;
    }

    public String getRestStatus() {
        return restStatus;
    }

    public void setRestStatus(String restStatus) {
        this.restStatus = restStatus;
    }

    public String getRestDetails() {
        return restDetails;
    }

    public void setRestDetails(String restDetails) {
        this.restDetails = restDetails;
    }

    public String getOpenHour() {
        return openHour;
    }

    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
