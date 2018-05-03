package com.opera.app.pojo.restaurant.getmasterdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Meal_Periods
{
    @SerializedName("Meal_Period_ID")
    @Expose
    private String Meal_Period_ID;

    @SerializedName("Meal_Period_Name")
    @Expose
    private String Meal_Period_Name;

    public String getMeal_Period_ID ()
    {
        return Meal_Period_ID;
    }

    public void setMeal_Period_ID (String Meal_Period_ID)
    {
        this.Meal_Period_ID = Meal_Period_ID;
    }

    public String getMeal_Period_Name ()
    {
        return Meal_Period_Name;
    }

    public void setMeal_Period_Name (String Meal_Period_Name)
    {
        this.Meal_Period_Name = Meal_Period_Name;
    }

    public Meal_Periods(String meal_Period_ID, String meal_Period_Name) {
        Meal_Period_ID = meal_Period_ID;
        Meal_Period_Name = meal_Period_Name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Meal_Period_ID = "+Meal_Period_ID+", Meal_Period_Name = "+Meal_Period_Name+"]";
    }
}