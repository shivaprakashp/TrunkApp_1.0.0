package com.opera.app.pojo.events.eventlisiting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 5/9/2018.
 */
public class EventGenres
{
    @SerializedName("genreName")
    @Expose
    private String genreName;

    @SerializedName("genreId")
    @Expose
    private String genreId;

    public String getGenreName ()
    {
        return genreName;
    }

    public void setGenreName (String genreName)
    {
        this.genreName = genreName;
    }

    public String getGenreId ()
    {
        return genreId;
    }

    public void setGenreId (String genreId)
    {
        this.genreId = genreId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [genreName = "+genreName+", genreId = "+genreId+"]";
    }
}
