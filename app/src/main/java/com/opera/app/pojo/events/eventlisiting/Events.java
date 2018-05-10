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

    @SerializedName("eventIsHighlighted")
    @Expose
    private String eventIsHighlighted;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("EventGenres")
    @Expose
    private ArrayList<EventGenres> EventGenres;

    @SerializedName("Image")
    @Expose
    private String Image;

    @SerializedName("InternalName")
    @Expose
    private String InternalName;

    @SerializedName("EventId")
    @Expose
    private String EventId;

    @SerializedName("EventDates")
    @Expose
    private ArrayList<EventDates> EventDates;

    @SerializedName("eventIsWhatsOn")
    @Expose
    private String eventIsWhatsOn;

    boolean IsInfoOpen = false;
    private String StartDate;
    private String EndDate;

    public boolean isInfoOpen() {
        return IsInfoOpen;
    }

    public void setInfoOpen(boolean infoOpen) {
        IsInfoOpen = infoOpen;
    }


    public Events() {
    }

    public Events(String image, String internalName) {
        Image = image;
        InternalName = internalName;
    }

    public Events(String Name,String image, String internalName, String startDate, String endDate,String Description) {
        this.Name = Name;
        Image = image;
        InternalName = internalName;
        StartDate = startDate;
        EndDate = endDate;
        this.Description = Description;
    }



    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String Active) {
        this.Active = Active;
    }

    public String getEventIsHighlighted() {
        return eventIsHighlighted;
    }

    public void setEventIsHighlighted(String eventIsHighlighted) {
        this.eventIsHighlighted = eventIsHighlighted;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public ArrayList<EventGenres> getEventGenres() {
        return EventGenres;
    }

    public void setEventGenres(ArrayList<EventGenres> EventGenres) {
        this.EventGenres = EventGenres;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getInternalName() {
        return InternalName;
    }

    public void setInternalName(String InternalName) {
        this.InternalName = InternalName;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String EventId) {
        this.EventId = EventId;
    }

    public ArrayList<EventDates> getEventDates() {
        return EventDates;
    }

    public void setEventDates(ArrayList<EventDates> EventDates) {
        this.EventDates = EventDates;
    }

    public String getEventIsWhatsOn() {
        return eventIsWhatsOn;
    }

    public void setEventIsWhatsOn(String eventIsWhatsOn) {
        this.eventIsWhatsOn = eventIsWhatsOn;
    }

    @Override
    public String toString() {
        return "ClassPojo [Name = " + Name + ", Active = " + Active + ", eventIsHighlighted = " + eventIsHighlighted + ", Description = " + Description + ", EventGenres = " + EventGenres + ", Image = " + Image + ", InternalName = " + InternalName + ", EventId = " + EventId + ", EventDates = " + EventDates + ", eventIsWhatsOn = " + eventIsWhatsOn + "]";
    }
}

