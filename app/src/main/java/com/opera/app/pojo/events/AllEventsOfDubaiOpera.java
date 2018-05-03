package com.opera.app.pojo.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/2/2018.
 */

public class AllEventsOfDubaiOpera
{
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("events")
    @Expose
    private ArrayList<events> events;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public ArrayList<events> getEvents ()
    {
        return events;
    }

    public void setEvents (ArrayList<events> events)
    {
        this.events = events;
    }

  /*  @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+", events = "+events+"]";
    }*/
}

