package com.opera.app.pojo.events.eventdetails;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class EventDetails {
    private String eventPerformType;

    private ArrayList<EventVenue> eventVenue;

    private String eventVideo;

    private String eventGenreType;

    private String eventId;

    private String eventTitle;

    private String eventImage;

    private ArrayList<FavouriteEvents> favouriteEvents;

    private String eventDetail;


    public String getEventPerformType() {
        return eventPerformType;
    }

    public void setEventPerformType(String eventPerformType) {
        this.eventPerformType = eventPerformType;
    }

    public ArrayList<EventVenue> getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(ArrayList<EventVenue> eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getEventVideo() {
        return eventVideo;
    }

    public void setEventVideo(String eventVideo) {
        this.eventVideo = eventVideo;
    }

    public String getEventGenreType() {
        return eventGenreType;
    }

    public void setEventGenreType(String eventGenreType) {
        this.eventGenreType = eventGenreType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public ArrayList<FavouriteEvents> getFavouriteEvents() {
        return favouriteEvents;
    }

    public void setFavouriteEvents(ArrayList<FavouriteEvents> favouriteEvents) {
        this.favouriteEvents = favouriteEvents;
    }

    public String getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.eventDetail = eventDetail;
    }

    @Override
    public String toString() {
        return "ClassPojo [eventPerformType = " + eventPerformType + ", eventVenue = " + eventVenue + ", eventVideo = " + eventVideo + ", eventGenreType = " + eventGenreType + ", eventId = " + eventId + ", eventTitle = " + eventTitle + ", eventImage = " + eventImage + ", favouriteEvents = " + favouriteEvents + ", eventDetail = " + eventDetail + "]";
    }
}
