package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 7/16/2018.
 */


public class OrderEvents
{
    @SerializedName("EventId")
    @Expose
    private String eventId;

    @SerializedName("EventGenre")
    @Expose
    private ArrayList<EventGenre> arrEventGenre;

    @SerializedName("EventName")
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


    public ArrayList<EventGenre> getArrEventGenre() {
        return arrEventGenre;
    }

    public void setArrEventGenre(ArrayList<EventGenre> arrEventGenre) {
        this.arrEventGenre = arrEventGenre;
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
        return "ClassPojo [eventId = "+eventId+", eventGenre = "+arrEventGenre+", eventName = "+eventName+"]";
    }
}