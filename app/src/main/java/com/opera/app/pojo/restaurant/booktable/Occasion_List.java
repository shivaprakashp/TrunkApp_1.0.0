package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Occasion_List
{
    @SerializedName("Occasion_Name")
    @Expose
    private String Occasion_Name;

    @SerializedName("Occasion_ID")
    @Expose
    private String Occasion_ID;

    public String getOccasion_Name ()
    {
        return Occasion_Name;
    }

    public void setOccasion_Name (String Occasion_Name)
    {
        this.Occasion_Name = Occasion_Name;
    }

    public String getOccasion_ID ()
    {
        return Occasion_ID;
    }

    public void setOccasion_ID (String Occasion_ID)
    {
        this.Occasion_ID = Occasion_ID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Occasion_Name = "+Occasion_Name+", Occasion_ID = "+Occasion_ID+"]";
    }
}

