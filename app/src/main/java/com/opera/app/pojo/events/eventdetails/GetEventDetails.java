package com.opera.app.pojo.events.eventdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opera.app.pojo.events.eventlisiting.Events;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/7/2018.
 */
public class GetEventDetails
{
    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("EventDetails")
    @Expose
    private ArrayList<EventDetails> EventDetails;

    @SerializedName("Message")
    @Expose
    private String Message;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public ArrayList<EventDetails> getEventDetails ()
    {
        return EventDetails;
    }

    public void setEventDetails (ArrayList<EventDetails> EventDetails)
    {
        this.EventDetails = EventDetails;
    }

    public String getMessage ()
    {
        return Message;
    }

    public void setMessage (String Message)
    {
        this.Message = Message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", EventDetails = "+EventDetails+", Message = "+Message+"]";
    }
}

