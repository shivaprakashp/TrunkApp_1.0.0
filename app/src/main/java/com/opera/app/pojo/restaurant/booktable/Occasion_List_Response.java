package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/20/2018.
 */
public class Occasion_List_Response
{
    @SerializedName("Occasion_List")
    @Expose
    private Occasion_List[] Occasion_List;

    @SerializedName("Result")
    @Expose
    private Result Result;

    public Occasion_List[] getOccasion_List ()
    {
        return Occasion_List;
    }

    public void setOccasion_List (Occasion_List[] Occasion_List)
    {
        this.Occasion_List = Occasion_List;
    }

    public Result getResult ()
    {
        return Result;
    }

    public void setResult (Result Result)
    {
        this.Result = Result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Occasion_List = "+Occasion_List+", Result = "+Result+"]";
    }
}
