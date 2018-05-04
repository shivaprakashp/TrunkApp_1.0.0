package com.opera.app.pojo.events.eventdetails;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class VenueDateTime
{
    private String dateTime;

    public String getDateTime ()
    {
        return dateTime;
    }

    public void setDateTime (String dateTime)
    {
        this.dateTime = dateTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dateTime = "+dateTime+"]";
    }
}
