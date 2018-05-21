package com.opera.app.pojo.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("notifyTitle")
    @Expose
    private String notifyTitle;
    @SerializedName("notifyImage")
    @Expose
    private String notifyImage;
    @SerializedName("notifyDescribe")
    @Expose
    private String notifyDescribe;
    @SerializedName("priceFrom")
    @Expose
    private String priceFrom;

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyImage() {
        return notifyImage;
    }

    public void setNotifyImage(String notifyImage) {
        this.notifyImage = notifyImage;
    }

    public String getNotifyDescribe() {
        return notifyDescribe;
    }

    public void setNotifyDescribe(String notifyDescribe) {
        this.notifyDescribe = notifyDescribe;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }
}
