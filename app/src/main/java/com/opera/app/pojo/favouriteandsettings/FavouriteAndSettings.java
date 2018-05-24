package com.opera.app.pojo.favouriteandsettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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

    @Override
    public String toString()
    {
        return "ClassPojo [Favourite = "+Favourite+", Settings = "+Settings+"]";
    }
}
