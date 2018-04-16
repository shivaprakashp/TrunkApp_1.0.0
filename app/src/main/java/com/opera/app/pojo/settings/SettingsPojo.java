package com.opera.app.pojo.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/16/2018.
 */

public class SettingsPojo {

    @SerializedName("AllowNotification")
    @Expose
    private String AllowNotification;

    @SerializedName("AllowPromotion")
    @Expose
    private String AllowPromotion;

    @SerializedName("AllowFeedbackNotification")
    @Expose
    private String AllowFeedbackNotification;

    @SerializedName("WeeklyNewsletter")
    @Expose
    private String WeeklyNewsletter;

    @SerializedName("RemindersForBookedShow")
    @Expose
    private String RemindersForBookedShow;

    public SettingsPojo(String allowNotification, String allowPromotion, String allowFeedbackNotification, String weeklyNewsletter, String remindersForBookedShow) {
        AllowNotification = allowNotification;
        AllowPromotion = allowPromotion;
        AllowFeedbackNotification = allowFeedbackNotification;
        WeeklyNewsletter = weeklyNewsletter;
        RemindersForBookedShow = remindersForBookedShow;
    }

    public String getAllowNotification() {
        return AllowNotification;
    }

    public void setAllowNotification(String allowNotification) {
        AllowNotification = allowNotification;
    }

    public String getAllowPromotion() {
        return AllowPromotion;
    }

    public void setAllowPromotion(String allowPromotion) {
        AllowPromotion = allowPromotion;
    }

    public String getAllowFeedbackNotification() {
        return AllowFeedbackNotification;
    }

    public void setAllowFeedbackNotification(String allowFeedbackNotification) {
        AllowFeedbackNotification = allowFeedbackNotification;
    }

    public String getWeeklyNewsletter() {
        return WeeklyNewsletter;
    }

    public void setWeeklyNewsletter(String weeklyNewsletter) {
        WeeklyNewsletter = weeklyNewsletter;
    }

    public String getRemindersForBookedShow() {
        return RemindersForBookedShow;
    }

    public void setRemindersForBookedShow(String remindersForBookedShow) {
        RemindersForBookedShow = remindersForBookedShow;
    }
}
