package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespakReservation {

    @SerializedName("Reservation_ID")
    @Expose
    private String reservationID;
    @SerializedName("Full_Reservation_ID")
    @Expose
    private String fullReservationID;
    @SerializedName("Patron_ID")
    @Expose
    private String patronID;
    @SerializedName("Area_Name")
    @Expose
    private String areaName;
    @SerializedName("Area_ID")
    @Expose
    private Integer areaID;
    @SerializedName("Meal_Period_ID")
    @Expose
    private String mealPeriodID;
    @SerializedName("Meal_Period_Name")
    @Expose
    private String mealPeriodName;
    @SerializedName("Reservation_Date")
    @Expose
    private String reservationDate;
    @SerializedName("Reservation_Time")
    @Expose
    private String reservationTime;
    @SerializedName("Party_Size")
    @Expose
    private Integer partySize;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("RStatus")
    @Expose
    private Integer rStatus;
    @SerializedName("Referral_ID")
    @Expose
    private String referralID;
    @SerializedName("Referral_Name")
    @Expose
    private String referralName;
    @SerializedName("Occasion_ID")
    @Expose
    private String occasionID;
    @SerializedName("Occasion_Name")
    @Expose
    private String occasionName;
    @SerializedName("Table_Position")
    @Expose
    private String tablePosition;
    @SerializedName("Promotion_Code")
    @Expose
    private String promotionCode;
    @SerializedName("Coupon_Code")
    @Expose
    private String couponCode;
    @SerializedName("Referrer_Code")
    @Expose
    private String referrerCode;
    @SerializedName("Source_Host")
    @Expose
    private String sourceHost;
    @SerializedName("Device_ID")
    @Expose
    private String deviceID;
    @SerializedName("UDF1")
    @Expose
    private String uDF1;
    @SerializedName("UDF2")
    @Expose
    private String uDF2;
    @SerializedName("UDF3")
    @Expose
    private String uDF3;
    @SerializedName("UDF4")
    @Expose
    private String uDF4;
    @SerializedName("UDF5")
    @Expose
    private String uDF5;

    public RespakReservation(String reservationDate,
                             String reservationTime, int partySize, String mealPeriodID) {
        reservationID = "sample string 1";
        fullReservationID = "sample string 2";
        patronID = "";
        areaName = "sample string 4";
        areaID = 40;
        this.mealPeriodID = mealPeriodID;
        mealPeriodName = "sample string 7";
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.partySize = partySize;
        notes = "sample string 11";
        rStatus = 3;
        referralID = "";
        referralName = "sample string 14";
        occasionID = "";
        occasionName = "sample string 16";
        tablePosition = "sample string 17";
        promotionCode = "sample string 18";
        couponCode = "sample string 19";
        referrerCode = "sample string 20";
        sourceHost = "sample string 21";
        deviceID = "sample string 22";
        uDF1 = "sample string 23";
        uDF2 = "sample string 24";
        uDF3 = "sample string 25";
        uDF4 = "sample string 26";
        uDF5 = "sample string 27";
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getFullReservationID() {
        return fullReservationID;
    }

    public void setFullReservationID(String fullReservationID) {
        this.fullReservationID = fullReservationID;
    }

    public String getPatronID() {
        return patronID;
    }

    public void setPatronID(String patronID) {
        this.patronID = patronID;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaID() {
        return areaID;
    }

    public void setAreaID(Integer areaID) {
        this.areaID = areaID;
    }

    public String getMealPeriodID() {
        return mealPeriodID;
    }

    public void setMealPeriodID(String mealPeriodID) {
        this.mealPeriodID = mealPeriodID;
    }

    public String getMealPeriodName() {
        return mealPeriodName;
    }

    public void setMealPeriodName(String mealPeriodName) {
        this.mealPeriodName = mealPeriodName;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Integer getPartySize() {
        return partySize;
    }

    public void setPartySize(Integer partySize) {
        this.partySize = partySize;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getRStatus() {
        return rStatus;
    }

    public void setRStatus(Integer rStatus) {
        this.rStatus = rStatus;
    }

    public String getReferralID() {
        return referralID;
    }

    public void setReferralID(String referralID) {
        this.referralID = referralID;
    }

    public String getReferralName() {
        return referralName;
    }

    public void setReferralName(String referralName) {
        this.referralName = referralName;
    }

    public String getOccasionID() {
        return occasionID;
    }

    public void setOccasionID(String occasionID) {
        this.occasionID = occasionID;
    }

    public String getOccasionName() {
        return occasionName;
    }

    public void setOccasionName(String occasionName) {
        this.occasionName = occasionName;
    }

    public String getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(String tablePosition) {
        this.tablePosition = tablePosition;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getReferrerCode() {
        return referrerCode;
    }

    public void setReferrerCode(String referrerCode) {
        this.referrerCode = referrerCode;
    }

    public String getSourceHost() {
        return sourceHost;
    }

    public void setSourceHost(String sourceHost) {
        this.sourceHost = sourceHost;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUDF1() {
        return uDF1;
    }

    public void setUDF1(String uDF1) {
        this.uDF1 = uDF1;
    }

    public String getUDF2() {
        return uDF2;
    }

    public void setUDF2(String uDF2) {
        this.uDF2 = uDF2;
    }

    public String getUDF3() {
        return uDF3;
    }

    public void setUDF3(String uDF3) {
        this.uDF3 = uDF3;
    }

    public String getUDF4() {
        return uDF4;
    }

    public void setUDF4(String uDF4) {
        this.uDF4 = uDF4;
    }

    public String getUDF5() {
        return uDF5;
    }

    public void setUDF5(String uDF5) {
        this.uDF5 = uDF5;
    }

}