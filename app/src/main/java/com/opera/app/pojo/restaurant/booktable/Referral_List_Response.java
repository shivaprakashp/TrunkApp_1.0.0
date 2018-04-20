package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Referral_List_Response
{
    @SerializedName("Result")
    @Expose
    private Result Result;

    @SerializedName("Referral_List")
    @Expose
    private Referral_List[] Referral_List;

    public Result getResult ()
    {
        return Result;
    }

    public void setResult (Result Result)
    {
        this.Result = Result;
    }

    public Referral_List[] getReferral_List ()
    {
        return Referral_List;
    }

    public void setReferral_List (Referral_List[] Referral_List)
    {
        this.Referral_List = Referral_List;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Result = "+Result+", Referral_List = "+Referral_List+"]";
    }
}
