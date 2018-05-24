package com.opera.app.pojo.favouriteandsettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 5/23/2018.
 */

public class Settings {
    @SerializedName("RemindersForBookedShow")
    @Expose
    private String RemindersForBookedShow;

    @SerializedName("AllowPromotion")
    @Expose
    private String AllowPromotion;

    @SerializedName("Language")
    @Expose
    private String Language;

    @SerializedName("WeeklyNewsLetters")
    @Expose
    private String WeeklyNewsLetters;

    @SerializedName("AllowNotification")
    @Expose
    private String AllowNotification;

    @SerializedName("AllowFeedbackNotification")
    @Expose
    private String AllowFeedbackNotification;

    public Settings(String remindersForBookedShow, String allowPromotion, String language, String weeklyNewsLetters, String allowNotification, String allowFeedbackNotification) {
        RemindersForBookedShow = remindersForBookedShow;
        AllowPromotion = allowPromotion;
        Language = language;
        WeeklyNewsLetters = weeklyNewsLetters;
        AllowNotification = allowNotification;
        AllowFeedbackNotification = allowFeedbackNotification;
    }

    public String getRemindersForBookedShow() {
        return RemindersForBookedShow;
    }

    public void setRemindersForBookedShow(String RemindersForBookedShow) {
        this.RemindersForBookedShow = RemindersForBookedShow;
    }

    public String getAllowPromotion() {
        return AllowPromotion;
    }

    public void setAllowPromotion(String AllowPromotion) {
        this.AllowPromotion = AllowPromotion;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getWeeklyNewsLetters() {
        return WeeklyNewsLetters;
    }

    public void setWeeklyNewsLetters(String WeeklyNewsLetters) {
        this.WeeklyNewsLetters = WeeklyNewsLetters;
    }

    public String getAllowNotification() {
        return AllowNotification;
    }

    public void setAllowNotification(String AllowNotification) {
        this.AllowNotification = AllowNotification;
    }

    public String getAllowFeedbackNotification() {
        return AllowFeedbackNotification;
    }

    public void setAllowFeedbackNotification(String AllowFeedbackNotification) {
        this.AllowFeedbackNotification = AllowFeedbackNotification;
    }

    @Override
    public String toString() {
        return "ClassPojo [RemindersForBookedShow = " + RemindersForBookedShow + ", AllowPromotion = " + AllowPromotion + ", Language = " + Language + ", WeeklyNewsLetters = " + WeeklyNewsLetters + ", AllowNotification = " + AllowNotification + ", AllowFeedbackNotification = " + AllowFeedbackNotification + "]";
    }
}

