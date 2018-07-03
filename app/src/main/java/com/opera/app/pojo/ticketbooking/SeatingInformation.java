package com.opera.app.pojo.ticketbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/3/2018.
 */

public class SeatingInformation
{
    @SerializedName("seats")
    @Expose
    private String seats;

    @SerializedName("row")
    @Expose
    private String row;

    @SerializedName("section")
    @Expose
    private String section;

    public String getSeats ()
    {
        return seats;
    }

    public void setSeats (String seats)
    {
        this.seats = seats;
    }

    public String getRow ()
    {
        return row;
    }

    public void setRow (String row)
    {
        this.row = row;
    }

    public String getSection ()
    {
        return section;
    }

    public void setSection (String section)
    {
        this.section = section;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [seats = "+seats+", row = "+row+", section = "+section+"]";
    }
}
