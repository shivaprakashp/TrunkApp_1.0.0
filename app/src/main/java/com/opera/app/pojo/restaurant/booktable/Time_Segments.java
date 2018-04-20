package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class Time_Segments {
    @SerializedName("Total_Available")
    @Expose
    private String Total_Available;

    @SerializedName("Meal_Period_ID")
    @Expose
    private String Meal_Period_ID;

    @SerializedName("Total_Booked")
    @Expose
    private String Total_Booked;

    @SerializedName("List_Type")
    @Expose
    private String List_Type;

    @SerializedName("Online_Capacity")
    @Expose
    private String Online_Capacity;

    @SerializedName("Online_Booked")
    @Expose
    private String Online_Booked;

    @SerializedName("Sitting")
    @Expose
    private String Sitting;

    @SerializedName("Total_Capacity")
    @Expose
    private String Total_Capacity;

    @SerializedName("Meal_Period_Time")
    @Expose
    private String Meal_Period_Time;

    public String getTotal_Available() {
        return Total_Available;
    }

    public void setTotal_Available(String Total_Available) {
        this.Total_Available = Total_Available;
    }

    public String getMeal_Period_ID() {
        return Meal_Period_ID;
    }

    public void setMeal_Period_ID(String Meal_Period_ID) {
        this.Meal_Period_ID = Meal_Period_ID;
    }

    public String getTotal_Booked() {
        return Total_Booked;
    }

    public void setTotal_Booked(String Total_Booked) {
        this.Total_Booked = Total_Booked;
    }

    public String getList_Type() {
        return List_Type;
    }

    public void setList_Type(String List_Type) {
        this.List_Type = List_Type;
    }

    public String getOnline_Capacity() {
        return Online_Capacity;
    }

    public void setOnline_Capacity(String Online_Capacity) {
        this.Online_Capacity = Online_Capacity;
    }

    public String getOnline_Booked() {
        return Online_Booked;
    }

    public void setOnline_Booked(String Online_Booked) {
        this.Online_Booked = Online_Booked;
    }

    public String getSitting() {
        return Sitting;
    }

    public void setSitting(String Sitting) {
        this.Sitting = Sitting;
    }

    public String getTotal_Capacity() {
        return Total_Capacity;
    }

    public void setTotal_Capacity(String Total_Capacity) {
        this.Total_Capacity = Total_Capacity;
    }

    public String getMeal_Period_Time() {
        return Meal_Period_Time;
    }

    public void setMeal_Period_Time(String Meal_Period_Time) {
        this.Meal_Period_Time = Meal_Period_Time;
    }

    @Override
    public String toString() {
        return "ClassPojo [Total_Available = " + Total_Available + ", Meal_Period_ID = " + Meal_Period_ID + ", Total_Booked = " + Total_Booked + ", List_Type = " + List_Type + ", Online_Capacity = " + Online_Capacity + ", Online_Booked = " + Online_Booked + ", Sitting = " + Sitting + ", Total_Capacity = " + Total_Capacity + ", Meal_Period_Time = " + Meal_Period_Time + "]";
    }
}


