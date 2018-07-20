package com.opera.app.pojo.favouriteandsettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1000632 on 5/23/2018.
 */

public class FavouriteAndSettings
{
    @SerializedName("Favourite")
    @Expose
    private ArrayList<Favourite> Favourite;

    @SerializedName("Settings")
    @Expose
    private Settings Settings;

    @SerializedName("OrderHistory")
    @Expose
    private List<OrderHistory> orderHistory = null;

    public FavouriteAndSettings(ArrayList<Favourite> favourite) {
        Favourite = favourite;
    }
    public FavouriteAndSettings(Settings Settings) {
        this.Settings = Settings;
    }

    public ArrayList<Favourite> getFavourite ()
    {
        return Favourite;
    }

    public void setFavourite (ArrayList<Favourite> Favourite)
    {
        this.Favourite = Favourite;
    }

    public Settings getSettings ()
    {
        return Settings;
    }

    public void setSettings (Settings Settings)
    {
        this.Settings = Settings;
    }

    public List<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Favourite = "+Favourite+", Settings = "+Settings+
                ", orderHistory"+orderHistory+"]";
    }
}
