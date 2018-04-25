package com.opera.app.pojo.restaurant.getmasterdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Result
{
    @SerializedName("Result_Message")
    @Expose
    private String Result_Message;

    @SerializedName("Result_Code")
    @Expose
    private String Result_Code;

    public String getResult_Message ()
    {
        return Result_Message;
    }

    public void setResult_Message (String Result_Message)
    {
        this.Result_Message = Result_Message;
    }

    public String getResult_Code ()
    {
        return Result_Code;
    }

    public void setResult_Code (String Result_Code)
    {
        this.Result_Code = Result_Code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Result_Message = "+Result_Message+", Result_Code = "+Result_Code+"]";
    }
}
