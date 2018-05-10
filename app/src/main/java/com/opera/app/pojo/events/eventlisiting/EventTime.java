package com.opera.app.pojo.events.eventlisiting;

/**
 * Created by 1000632 on 5/9/2018.
 */

public class EventTime
{
    private String startTime;

    private String endTime;

    public String getStartTime ()
    {
        return startTime;
    }

    public void setStartTime (String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime ()
    {
        return endTime;
    }

    public void setEndTime (String endTime)
    {
        this.endTime = endTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [startTime = "+startTime+", endTime = "+endTime+"]";
    }
}

