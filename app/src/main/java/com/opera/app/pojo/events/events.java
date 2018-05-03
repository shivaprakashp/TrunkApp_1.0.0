package com.opera.app.pojo.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/2/2018.
 */

public class events
{
    @SerializedName("genreType")
    @Expose
    private ArrayList<GenreType> genreType;

    @SerializedName("eventDate")
    @Expose
    private String eventDate;

    @SerializedName("eventIsHighlighted")
    @Expose
    private String eventIsHighlighted;

    @SerializedName("eventKey")
    @Expose
    private ArrayList<EventKey> eventKey;

    @SerializedName("eventId")
    @Expose
    private String eventId;

    @SerializedName("eventTitle")
    @Expose
    private String eventTitle;

    @SerializedName("eventInfo")
    @Expose
    private String eventInfo;

    @SerializedName("eventTime")
    @Expose
    private ArrayList<EventTime> eventTime;

    @SerializedName("eventIsWhatsOn")
    @Expose
    private String eventIsWhatsOn;

    @SerializedName("eventThumbImage")
    @Expose
    private String eventThumbImage;

    private boolean IsInfoOpen=false;

    public events() {
    }

    public events(String eventDate, String eventThumbImage) {
        this.eventDate = eventDate;
        this.eventThumbImage = eventThumbImage;
    }

    public boolean isInfoOpen() {
        return IsInfoOpen;
    }

    public void setInfoOpen(boolean infoOpen) {
        IsInfoOpen = infoOpen;
    }

    public ArrayList<GenreType> getGenreType ()
    {
        return genreType;
    }

    public void setGenreType (ArrayList<GenreType> genreType)
    {
        this.genreType = genreType;
    }

    public String getEventDate ()
    {
        return eventDate;
    }

    public void setEventDate (String eventDate)
    {
        this.eventDate = eventDate;
    }

    public String getEventIsHighlighted ()
    {
        return eventIsHighlighted;
    }

    public void setEventIsHighlighted (String eventIsHighlighted)
    {
        this.eventIsHighlighted = eventIsHighlighted;
    }

    public ArrayList<EventKey> getEventKey ()
    {
        return eventKey;
    }

    public void setEventKey (ArrayList<EventKey> eventKey)
    {
        this.eventKey = eventKey;
    }

    public String getEventId ()
    {
        return eventId;
    }

    public void setEventId (String eventId)
    {
        this.eventId = eventId;
    }

    public String getEventTitle ()
    {
        return eventTitle;
    }

    public void setEventTitle (String eventTitle)
    {
        this.eventTitle = eventTitle;
    }

    public String getEventInfo ()
    {
        return eventInfo;
    }

    public void setEventInfo (String eventInfo)
    {
        this.eventInfo = eventInfo;
    }

    public ArrayList<EventTime> getEventTime ()
    {
        return eventTime;
    }

    public void setEventTime (ArrayList<EventTime> eventTime)
    {
        this.eventTime = eventTime;
    }

    public String getEventIsWhatsOn ()
    {
        return eventIsWhatsOn;
    }

    public void setEventIsWhatsOn (String eventIsWhatsOn)
    {
        this.eventIsWhatsOn = eventIsWhatsOn;
    }

    public String getEventThumbImage ()
    {
        return eventThumbImage;
    }

    public void setEventThumbImage (String eventThumbImage)
    {
        this.eventThumbImage = eventThumbImage;
    }

  /*  @Override
    public String toString()
    {
        return "ClassPojo [genreType = "+genreType+", eventDate = "+eventDate+", eventIsHighlighted = "+eventIsHighlighted+", eventKey = "+eventKey+", eventId = "+eventId+", eventTitle = "+eventTitle+", eventInfo = "+eventInfo+", eventTime = "+eventTime+", eventIsWhatsOn = "+eventIsWhatsOn+", eventThumbImage = "+eventThumbImage+"]";
    }*/
}
