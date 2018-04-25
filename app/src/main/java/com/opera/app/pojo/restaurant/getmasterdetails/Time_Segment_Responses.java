package com.opera.app.pojo.restaurant.getmasterdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Time_Segment_Responses
{
    @SerializedName("Result")
    @Expose
    private Result Result;

    @SerializedName("Time_Segments")
    @Expose
    private ArrayList<Time_Segments> Time_Segments;

    @SerializedName("Party_Size")
    @Expose
    private String Party_Size;

    public Result getResult ()
    {
        return Result;
    }

    public void setResult (Result Result)
    {
        this.Result = Result;
    }

    public ArrayList<com.opera.app.pojo.restaurant.getmasterdetails.Time_Segments> getTime_Segments() {
        return Time_Segments;
    }

    public void setTime_Segments(ArrayList<com.opera.app.pojo.restaurant.getmasterdetails.Time_Segments> time_Segments) {
        Time_Segments = time_Segments;
    }

    public String getParty_Size ()
    {
        return Party_Size;
    }

    public void setParty_Size (String Party_Size)
    {
        this.Party_Size = Party_Size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Result = "+Result+", Time_Segments = "+Time_Segments+", Party_Size = "+Party_Size+"]";
    }
}
