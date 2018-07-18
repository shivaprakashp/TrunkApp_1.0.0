package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/16/2018.
 */

public class Seat
{
    @SerializedName("rzStr")
    @Expose
    private String rzStr;

    @SerializedName("seats")
    @Expose
    private String seats;

    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("row")
    @Expose
    private String row;

    public String getRzStr ()
    {
        return rzStr;
    }

    public void setRzStr (String rzStr)
    {
        this.rzStr = rzStr;
    }

    public String getSeats ()
    {
        return seats;
    }

    public void setSeats (String seats)
    {
        this.seats = seats;
    }

    public String getSection ()
    {
        return section;
    }

    public void setSection (String section)
    {
        this.section = section;
    }

    public String getRow ()
    {
        return row;
    }

    public void setRow (String row)
    {
        this.row = row;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rzStr = "+rzStr+", seats = "+seats+", section = "+section+", row = "+row+"]";
    }
}
