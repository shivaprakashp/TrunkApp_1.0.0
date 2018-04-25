package com.opera.app.pojo.restaurant.getmasterdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Data
{
    @SerializedName("Result")
    @Expose
    private Result Result;

    @SerializedName("Control_Values_Response")
    @Expose
    private Control_Values_Response Control_Values_Response;

    @SerializedName("Time_Segment_Responses")
    @Expose
    private ArrayList<Time_Segment_Responses> Time_Segment_Responses;

    public Result getResult ()
    {
        return Result;
    }

    public void setResult (Result Result)
    {
        this.Result = Result;
    }

    public Control_Values_Response getControl_Values_Response ()
    {
        return Control_Values_Response;
    }

    public void setControl_Values_Response (Control_Values_Response Control_Values_Response)
    {
        this.Control_Values_Response = Control_Values_Response;
    }

    public ArrayList<Time_Segment_Responses> getTime_Segment_Responses ()
    {
        return Time_Segment_Responses;
    }

    public void setTime_Segment_Responses (ArrayList<Time_Segment_Responses> Time_Segment_Responses)
    {
        this.Time_Segment_Responses = Time_Segment_Responses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Result = "+Result+", Control_Values_Response = "+Control_Values_Response+", Time_Segment_Responses = "+Time_Segment_Responses+"]";
    }
}
