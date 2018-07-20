package com.opera.app.pojo.wallet.eventwallethistory;

/**
 * Created by 1000632 on 7/20/2018.
 */

public class EventGenre
{
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