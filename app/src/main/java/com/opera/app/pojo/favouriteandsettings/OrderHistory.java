package com.opera.app.pojo.favouriteandsettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistory {

    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("MobileDescription")
    @Expose
    private String mobileDescription;
    @SerializedName("feedBackUrl")
    @Expose
    private String feedBackUrl;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;

    public OrderHistory() {
    }

    public OrderHistory(String dateTime, String orderId, String eventId, String eventName, String mobileDescription, String feedBackUrl, String startTime, String endTime) {
        this.dateTime = dateTime;
        this.orderId = orderId;
        this.eventId = eventId;
        this.eventName = eventName;
        this.mobileDescription = mobileDescription;
        this.feedBackUrl = feedBackUrl;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMobileDescription() {
        return mobileDescription;
    }

    public void setMobileDescription(String mobileDescription) {
        this.mobileDescription = mobileDescription;
    }

    public String getFeedBackUrl() {
        return feedBackUrl;
    }

    public void setFeedBackUrl(String feedBackUrl) {
        this.feedBackUrl = feedBackUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
