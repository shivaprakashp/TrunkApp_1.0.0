package com.opera.app.pojo.restaurant.getmasterdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Meal_Period_Response
{
    @SerializedName("Result")
    @Expose
    private Result Result;

    @SerializedName("Meal_Periods")
    @Expose
    private ArrayList<Meal_Periods> Meal_Periods;

    @SerializedName("Advance_Reservation_Months")
    @Expose
    private String Advance_Reservation_Months;

    @SerializedName("Max_Party_Size")
    @Expose
    private String Max_Party_Size;

    public Result getResult ()
    {
        return Result;
    }

    public void setResult (Result Result)
    {
        this.Result = Result;
    }

    public ArrayList<com.opera.app.pojo.restaurant.getmasterdetails.Meal_Periods> getMeal_Periods() {
        return Meal_Periods;
    }

    public void setMeal_Periods(ArrayList<com.opera.app.pojo.restaurant.getmasterdetails.Meal_Periods> meal_Periods) {
        Meal_Periods = meal_Periods;
    }

    public String getAdvance_Reservation_Months ()
    {
        return Advance_Reservation_Months;
    }

    public void setAdvance_Reservation_Months (String Advance_Reservation_Months)
    {
        this.Advance_Reservation_Months = Advance_Reservation_Months;
    }

    public String getMax_Party_Size ()
    {
        return Max_Party_Size;
    }

    public void setMax_Party_Size (String Max_Party_Size)
    {
        this.Max_Party_Size = Max_Party_Size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Result = "+Result+", Meal_Periods = "+Meal_Periods+", Advance_Reservation_Months = "+Advance_Reservation_Months+", Max_Party_Size = "+Max_Party_Size+"]";
    }
}
