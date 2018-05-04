package com.opera.app.pojo.events.eventdetails;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class FavouriteEvents
{
    private String eventId;

    private String eventTitle;

    private String eventInfo;

    private String eventThumbImage;



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

    public String getEventThumbImage ()
    {
        return eventThumbImage;
    }

    public void setEventThumbImage (String eventThumbImage)
    {
        this.eventThumbImage = eventThumbImage;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [eventId = "+eventId+", eventTitle = "+eventTitle+", eventInfo = "+eventInfo+", eventThumbImage = "+eventThumbImage+"]";
    }
}