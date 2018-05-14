package com.opera.app.pojo.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("restId")
    @Expose
    private String restId;
    @SerializedName("restName")
    @Expose
    private String restName;
    @SerializedName("reserveDate")
    @Expose
    private String reserveDate;
    @SerializedName("mealPeriod")
    @Expose
    private String mealPeriod;
    @SerializedName("preferDate")
    @Expose
    private String preferDate;
    @SerializedName("prefferTime")
    @Expose
    private String prefferTime;
    @SerializedName("referenceNo")
    @Expose
    private String referenceNo;

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

    public String getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(String reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getMealPeriod() {
        return mealPeriod;
    }

    public void setMealPeriod(String mealPeriod) {
        this.mealPeriod = mealPeriod;
    }

    public String getPreferDate() {
        return preferDate;
    }

    public void setPreferDate(String preferDate) {
        this.preferDate = preferDate;
    }

    public String getPrefferTime() {
        return prefferTime;
    }

    public void setPrefferTime(String prefferTime) {
        this.prefferTime = prefferTime;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

}