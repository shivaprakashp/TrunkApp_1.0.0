package com.opera.app.pojo.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opera.app.pojo.wallet.eventwallethistory.BookedEventHistory;

import java.util.List;

public class WalletDetails {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;
   /* @SerializedName("Events")
    @Expose
    private List<Event> events = null;*/
    @SerializedName("BookedEventHistory")
    @Expose
    private List<BookedEventHistory> mBookedEventHistories = null;
    /*@SerializedName("Restaurants")
    @Expose
    private List<Restaurant> restaurants = null;*/
    @SerializedName("GiftCard")
    @Expose
    private List<GiftCard> giftCard = null;
    @SerializedName("data")
    @Expose
    private List<Restaurant> restaurants = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /*public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }*/

    public List<BookedEventHistory> getmBookedEventHistories() {
        return mBookedEventHistories;
    }

    public void setmBookedEventHistories(List<BookedEventHistory> mBookedEventHistories) {
        this.mBookedEventHistories = mBookedEventHistories;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<GiftCard> getGiftCard() {
        return giftCard;
    }

    public void setGiftCard(List<GiftCard> giftCard) {
        this.giftCard = giftCard;
    }

}
