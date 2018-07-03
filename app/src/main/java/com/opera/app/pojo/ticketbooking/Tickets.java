package com.opera.app.pojo.ticketbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 7/3/2018.
 */

public class Tickets
{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("seatingInformation")
    @Expose
    private SeatingInformation seatingInformation;

    @SerializedName("show")
    @Expose
    private ArrayList<Show> show;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public SeatingInformation getSeatingInformation ()
    {
        return seatingInformation;
    }

    public void setSeatingInformation (SeatingInformation seatingInformation)
    {
        this.seatingInformation = seatingInformation;
    }

    public ArrayList<Show> getShow ()
    {
        return show;
    }

    public void setShow (ArrayList<Show> show)
    {
        this.show = show;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", seatingInformation = "+seatingInformation+", show = "+show+"]";
    }
}

