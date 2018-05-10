package com.opera.app.pojo.events.eventlisiting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/7/2018.
 */

public class Events {
    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Active")
    @Expose
    private String Active;

    @SerializedName("Highlighted")
    @Expose
    private String Highlighted;

    @SerializedName("Description")
    @Expose
    private String Description;

    /*@SerializedName("EventGenres")
    @Expose
    private ArrayList<EventGenres> EventGenres;*/

    @SerializedName("Image")
    @Expose
    private String Image;

    @SerializedName("InternalName")
    @Expose
    private String InternalName;

    @SerializedName("EventId")
    @Expose
    private String EventId;

    @SerializedName("EventTime")
    @Expose
    private ArrayList<EventTime> EventTime;

    @SerializedName("WhatsOn")
    @Expose
    private String WhatsOn;

    @SerializedName("BuyNowLink")
    @Expose
    private String BuyNowLink;

    @SerializedName("From")
    @Expose
    private String From;

    @SerializedName("To")
    @Expose
    private String To;

    @SerializedName("PriceFrom")
    @Expose
    private String PriceFrom;

    @SerializedName("Video")
    @Expose
    private String Video;

    @SerializedName("StartTime")
    @Expose
    private String StartTime;

    @SerializedName("EndTime")
    @Expose
    private String EndTime;

    @SerializedName("Genre")
    @Expose
    private GenreList Genre;

    boolean IsInfoOpen = false;

    public boolean isInfoOpen() {
        return IsInfoOpen;
    }

    public void setInfoOpen(boolean infoOpen) {
        IsInfoOpen = infoOpen;
    }


    public Events() {
    }

    public Events(String image, String internalName,String EventId) {
        Image = image;
        InternalName = internalName;
        this.EventId=EventId;
    }

    public Events(String Name, String image, String internalName, String startDate, String endDate, String Description) {
        this.Name = Name;
        Image = image;
        InternalName = internalName;
        From = startDate;
        To = endDate;
        this.Description = Description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getHighlighted() {
        return Highlighted;
    }

    public void setHighlighted(String highlighted) {
        Highlighted = highlighted;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

   /* public ArrayList<com.opera.app.pojo.events.eventlisiting.EventGenres> getEventGenres() {
        return EventGenres;
    }

    public void setEventGenres(ArrayList<EventGenres> eventGenres) {
        EventGenres = eventGenres;
    }*/

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getInternalName() {
        return InternalName;
    }

    public void setInternalName(String internalName) {
        InternalName = internalName;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public ArrayList<EventTime> getEventTime() {
        return EventTime;
    }

    public void setEventTime(ArrayList<EventTime> eventTime) {
        EventTime = eventTime;
    }

    public String getWhatsOn() {
        return WhatsOn;
    }

    public void setWhatsOn(String whatsOn) {
        WhatsOn = whatsOn;
    }

    public String getBuyNowLink() {
        return BuyNowLink;
    }

    public void setBuyNowLink(String buyNowLink) {
        BuyNowLink = buyNowLink;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getPriceFrom() {
        return PriceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        PriceFrom = priceFrom;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public GenreList getGenreList() {
        return Genre;
    }

    public void setGenreList(GenreList genreList) {
        Genre = genreList;
    }
}

