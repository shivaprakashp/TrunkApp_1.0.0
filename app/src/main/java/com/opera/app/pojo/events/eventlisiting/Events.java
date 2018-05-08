package com.opera.app.pojo.events.eventlisiting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 5/7/2018.
 */

public class Events
{

    @SerializedName("EventId")
    @Expose
    private String EventId;

    @SerializedName("PriceFrom")
    @Expose
    private String PriceFrom;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Active")
    @Expose
    private String Active;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("BuyNowLink")
    @Expose
    private String BuyNowLink;

    @SerializedName("To")
    @Expose
    private String To;

    @SerializedName("Image")
    @Expose
    private String Image;

    @SerializedName("InternalName")
    @Expose
    private String InternalName;

    @SerializedName("EndTime")
    @Expose
    private String EndTime;

    @SerializedName("WhatsOn")
    @Expose
    private String WhatsOn;

    @SerializedName("StartTime")
    @Expose
    private String StartTime;

    @SerializedName("Video")
    @Expose
    private String Video;

    @SerializedName("From")
    @Expose
    private String From;

    boolean IsInfoOpen=false;

    public Events() {
    }

    public Events(String from,String image,String InternalName) {
        From = from;
        Image = image;
        this.InternalName=InternalName;
    }

    public Events(String Name,String From,String Description,String Image) {
        this.Name = Name;
        this.From = From;
        this.Description=Description;
        this.Image=Image;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public boolean isInfoOpen() {
        return IsInfoOpen;
    }

    public void setInfoOpen(boolean infoOpen) {
        IsInfoOpen = infoOpen;
    }

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

    public String getActive ()
    {
        return Active;
    }

    public void setActive (String Active)
    {
        this.Active = Active;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getBuyNowLink ()
    {
        return BuyNowLink;
    }

    public void setBuyNowLink (String BuyNowLink)
    {
        this.BuyNowLink = BuyNowLink;
    }

    public String getTo ()
    {
        return To;
    }

    public void setTo (String To)
    {
        this.To = To;
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

    public String getWhatsOn ()
    {
        return WhatsOn;
    }

    public void setWhatsOn (String WhatsOn)
    {
        this.WhatsOn = WhatsOn;
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

    public String getFrom ()
    {
        return From;
    }

    public void setFrom (String From)
    {
        this.From = From;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PriceFrom = "+PriceFrom+", Name = "+Name+", Active = "+Active+", Description = "+Description+", BuyNowLink = "+BuyNowLink+", To = "+To+", Image = "+Image+", InternalName = "+InternalName+", EndTime = "+EndTime+", WhatsOn = "+WhatsOn+", StartTime = "+StartTime+", Video = "+Video+", From = "+From+"]";
    }
}