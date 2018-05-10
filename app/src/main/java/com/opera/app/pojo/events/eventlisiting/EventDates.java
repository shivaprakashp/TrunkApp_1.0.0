package com.opera.app.pojo.events.eventlisiting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/9/2018.
 */
public class EventDates
{
    @SerializedName("eventDate")
    @Expose
    private String eventDate;

    @SerializedName("eventTime")
    @Expose
    private ArrayList<EventTime> eventTime;

    public String getEventDate ()
    {
        return eventDate;
    }

    public void setEventDate (String eventDate)
    {
        this.eventDate = eventDate;
    }

    public ArrayList<EventTime> getEventTime ()
    {
        return eventTime;
    }

    public void setEventTime (ArrayList<EventTime> eventTime)
    {
        this.eventTime = eventTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [eventDate = "+eventDate+", eventTime = "+eventTime+"]";
    }
}