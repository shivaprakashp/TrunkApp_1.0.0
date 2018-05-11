package com.opera.app.pojo.events.eventdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/10/2018.
 */

public class EventDetails
{
    @SerializedName("PriceFrom")
    @Expose
    private String PriceFrom;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("EventGenres")
    @Expose
    private ArrayList<EventGenres> EventGenres;

    @SerializedName("BuyNowLink")
    @Expose
    private String BuyNowLink;

    @SerializedName("Image")
    @Expose
    private String Image;

    @SerializedName("InternalName")
    @Expose
    private String InternalName;

    @SerializedName("EndTime")
    @Expose
    private String EndTime;

    @SerializedName("StartTime")
    @Expose
    private String StartTime;

    @SerializedName("Video")
    @Expose
    private String Video;

    public String getPriceFrom ()
    {
        return PriceFrom;
    }

    public void setPriceFrom (String PriceFrom)
    {
        this.PriceFrom = PriceFrom;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public ArrayList<EventGenres> getEventGenres ()
    {
        return EventGenres;
    }

    public void setEventGenres (ArrayList<EventGenres> EventGenres)
    {
        this.EventGenres = EventGenres;
    }

    public String getBuyNowLink ()
    {
        return BuyNowLink;
    }

    public void setBuyNowLink (String BuyNowLink)
    {
        this.BuyNowLink = BuyNowLink;
    }

    public String getImage ()
    {
        return Image;
    }

    public void setImage (String Image)
    {
        this.Image = Image;
    }

    public String getInternalName ()
    {
        return InternalName;
    }

    public void setInternalName (String InternalName)
    {
        this.InternalName = InternalName;
    }

    public String getEndTime ()
    {
        return EndTime;
    }

    public void setEndTime (String EndTime)
    {
        this.EndTime = EndTime;
    }

    public String getStartTime ()
    {
        return StartTime;
    }

    public void setStartTime (String StartTime)
    {
        this.StartTime = StartTime;
    }

    public String getVideo ()
    {
        return Video;
    }

    public void setVideo (String Video)
    {
        this.Video = Video;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PriceFrom = "+PriceFrom+", Name = "+Name+", Description = "+Description+", EventGenres = "+EventGenres+", BuyNowLink = "+BuyNowLink+", Image = "+Image+", InternalName = "+InternalName+", EndTime = "+EndTime+", StartTime = "+StartTime+", Video = "+Video+"]";
    }
}


