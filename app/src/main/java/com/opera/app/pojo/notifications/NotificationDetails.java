package com.opera.app.pojo.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationDetails {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("notificationType")
    @Expose
    private String notificationType;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("notification")
    @Expose
    private ArrayList<Notification> notification;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Notification> getNotification() {
        return notification;
    }

    public void setNotification(ArrayList<Notification> notification) {
        this.notification = notification;
    }
}
