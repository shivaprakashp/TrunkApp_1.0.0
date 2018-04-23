package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Control_Values_Response
{
    @SerializedName("Result")
    @Expose
    private Result Result;

    @SerializedName("Occasion_List_Response")
    @Expose
    private Occasion_List_Response Occasion_List_Response;

    @SerializedName("Referral_List_Response")
    @Expose
    private Referral_List_Response Referral_List_Response;

    @SerializedName("Meal_Period_Response")
    @Expose
    private Meal_Period_Response Meal_Period_Response;

    public Result getResult ()
    {
        return Result;
    }

    public void setResult (Result Result)
    {
        this.Result = Result;
    }

    public Occasion_List_Response getOccasion_List_Response ()
    {
        return Occasion_List_Response;
    }

    public void setOccasion_List_Response (Occasion_List_Response Occasion_List_Response)
    {
        this.Occasion_List_Response = Occasion_List_Response;
    }

    public Referral_List_Response getReferral_List_Response ()
    {
        return Referral_List_Response;
    }

    public void setReferral_List_Response (Referral_List_Response Referral_List_Response)
    {
        this.Referral_List_Response = Referral_List_Response;
    }

    public Meal_Period_Response getMeal_Period_Response ()
    {
        return Meal_Period_Response;
    }

    public void setMeal_Period_Response (Meal_Period_Response Meal_Period_Response)
    {
        this.Meal_Period_Response = Meal_Period_Response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Result = "+Result+", Occasion_List_Response = "+Occasion_List_Response+", Referral_List_Response = "+Referral_List_Response+", Meal_Period_Response = "+Meal_Period_Response+"]";
    }
}
