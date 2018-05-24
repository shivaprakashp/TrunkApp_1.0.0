package com.opera.app.pojo.favouriteandsettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 5/23/2018.
 */

public class Favourite
{
    @SerializedName("IsFavourite")
    @Expose
    private String IsFavourite;

    @SerializedName("FavouriteId")
    @Expose
    private String FavouriteId;

    public Favourite(String isFavourite, String favouriteId) {
        IsFavourite = isFavourite;
        FavouriteId = favouriteId;
    }

    public String getIsFavourite ()
    {
        return IsFavourite;
    }

    public void setIsFavourite (String IsFavourite)
    {
        this.IsFavourite = IsFavourite;
    }

    public String getFavouriteId ()
    {
        return FavouriteId;
    }

    public void setFavouriteId (String FavouriteId)
    {
        this.FavouriteId = FavouriteId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [IsFavourite = "+IsFavourite+", FavouriteId = "+FavouriteId+"]";
    }
}
