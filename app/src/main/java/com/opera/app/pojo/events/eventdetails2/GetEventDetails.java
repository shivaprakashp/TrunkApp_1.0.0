package com.opera.app.pojo.events.eventdetails2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 5/7/2018.
 */

public class GetEventDetails
{
    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("Event")
    @Expose
    private InnerEventDetails Event;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public String getMessage ()
    {
        return Message;
    }

    public void setMessage (String Message)
    {
        this.Message = Message;
    }

    public InnerEventDetails getEvent ()
    {
        return Event;
    }

    public void setEvent (InnerEventDetails Event)
    {
        this.Event = Event;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", Message = "+Message+", Event = "+Event+"]";
    }
}
