package com.opera.app.pojo.events.eventdetails;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class EventDetailsPojo
{
    private String message;

    private String status;

    private ArrayList<EventDetails> eventDetails;

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

    public ArrayList<EventDetails> getEventDetails ()
    {
        return eventDetails;
    }

    public void setEventDetails (ArrayList<EventDetails> eventDetails)
    {
        this.eventDetails = eventDetails;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", status = "+status+", eventDetails = "+eventDetails+"]";
    }
}
