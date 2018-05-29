package com.opera.app.pojo.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("restId")
    @Expose
    private String restId;
    @SerializedName("RestaurantName")
    @Expose
    private String RestaurantName;
    @SerializedName("ReservationDate")
    @Expose
    private String ReservationDate;
    @SerializedName("MealPeriodId")
    @Expose
    private String MealPeriodId;
    @SerializedName("BookingDate")
    @Expose
    private String BookingDate;
    @SerializedName("PreferredTime")
    @Expose
    private String PreferredTime;
    @SerializedName("BookingReferenceNumber")
    @Expose
    private String BookingReferenceNumber;
    @SerializedName("SessionId")
    @Expose
    private String SessionId;
    @SerializedName("FullReservationID")
    @Expose
    private String FullReservationID;
    @SerializedName("PersonID")
    @Expose
    private String PersonID;

    public String getRestId() {
        return restId;
    }

    public void setRestId(String restId) {
        this.restId = restId;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getReservationDate() {
        return ReservationDate;
    }

    public void setReservationDate(String reservationDate) {
        ReservationDate = reservationDate;
    }

    public String getMealPeriodId() {
        return MealPeriodId;
    }

    public void setMealPeriodId(String mealPeriodId) {
        MealPeriodId = mealPeriodId;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getPreferredTime() {
        return PreferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        PreferredTime = preferredTime;
    }

    public String getBookingReferenceNumber() {
        return BookingReferenceNumber;
    }

    public void setBookingReferenceNumber(String bookingReferenceNumber) {
        BookingReferenceNumber = bookingReferenceNumber;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getFullReservationID() {
        return FullReservationID;
    }

    public void setFullReservationID(String fullReservationID) {
        FullReservationID = fullReservationID;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }
}