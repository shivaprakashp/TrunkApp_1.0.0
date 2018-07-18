package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/16/2018.
 */


public class OrderEvents
{
    @SerializedName("eventId")
    @Expose
    private String eventId;

    @SerializedName("eventGenre")
    @Expose
    private String eventGenre;

    @SerializedName("eventName")
    @Expose
    private String eventName;

    public String getEventId ()
    {
        return eventId;
    }

    public void setEventId (String eventId)
    {
        this.eventId = eventId;
    }

    public String getEventGenre ()
    {
        return eventGenre;
    }

    public void setEventGenre (String eventGenre)
    {
        this.eventGenre = eventGenre;
    }

    public String getEventName ()
    {
        return eventName;
    }

    public void setEventName (String eventName)
    {
        this.eventName = eventName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [eventId = "+eventId+", eventGenre = "+eventGenre+", eventName = "+eventName+"]";
    }
}