package com.opera.app.pojo.events.eventlisiting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/7/2018.
 */

public class AllEvents
{

    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("Events")
    @Expose
    private ArrayList<Events> Events;

    @SerializedName("GenreList")
    @Expose
    private ArrayList<GenreList> GenreList;

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

    public ArrayList<Events> getEvents ()
    {
        return Events;
    }

    public void setEvents (ArrayList<Events> Events)
    {
        this.Events = Events;
    }

    public ArrayList<GenreList> getGenreList ()
    {
        return GenreList;
    }

    public void setGenreList (ArrayList<GenreList> GenreList)
    {
        this.GenreList = GenreList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", Message = "+Message+", Events = "+Events+", GenreList = "+GenreList+"]";
    }
}
