package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/20/2018.
 */

public class EventGenre
{
    @SerializedName("Genere")
    @Expose
    private String Genere;

    public String getGenere ()
    {
        return Genere;
    }

    public void setGenere (String Genere)
    {
        this.Genere = Genere;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Genere = "+Genere+"]";
    }
}