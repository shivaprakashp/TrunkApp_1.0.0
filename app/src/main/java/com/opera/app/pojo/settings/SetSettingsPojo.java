package com.opera.app.pojo.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/16/2018.
 */

public class SetSettingsPojo {

    @SerializedName("AllowNotification")
    @Expose
    private String AllowNotification;

    @SerializedName("AllowPromotion")
    @Expose
    private String AllowPromotion;

    @SerializedName("AllowFeedbackNotification")
    @Expose
    private String AllowFeedbackNotification;

    @SerializedName("WeeklyNewsLetters")
    @Expose
    private String WeeklyNewsLetters;

    @SerializedName("RemindersForBookedShow")
    @Expose
    private String RemindersForBookedShow;

    @SerializedName("Language")
    @Expose
    private String Language;

    public SetSettingsPojo(String allowNotification, String allowPromotion, String allowFeedbackNotification, String weeklyNewsletter, String remindersForBookedShow, String language) {
        AllowNotification = allowNotification;
        AllowPromotion = allowPromotion;
        AllowFeedbackNotification = allowFeedbackNotification;
        WeeklyNewsLetters = weeklyNewsletter;
        RemindersForBookedShow = remindersForBookedShow;
        Language=language;
    }

    public String getWeeklyNewsLetters() {
        return WeeklyNewsLetters;
    }

    public void setWeeklyNewsLetters(String weeklyNewsLetters) {
        WeeklyNewsLetters = weeklyNewsLetters;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
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

    public String getRemindersForBookedShow() {
        return RemindersForBookedShow;
    }

    public void setRemindersForBookedShow(String remindersForBookedShow) {
        RemindersForBookedShow = remindersForBookedShow;
    }
}
