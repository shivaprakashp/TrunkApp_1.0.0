package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Referral_List
{
    @SerializedName("Referral_Name")
    @Expose
    private String Referral_Name;

    @SerializedName("Referral_ID")
    @Expose
    private String Referral_ID;

    public String getReferral_Name ()
    {
        return Referral_Name;
    }

    public void setReferral_Name (String Referral_Name)
    {
        this.Referral_Name = Referral_Name;
    }

    public String getReferral_ID ()
    {
        return Referral_ID;
    }

    public void setReferral_ID (String Referral_ID)
    {
        this.Referral_ID = Referral_ID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Referral_Name = "+Referral_Name+", Referral_ID = "+Referral_ID+"]";
    }
}