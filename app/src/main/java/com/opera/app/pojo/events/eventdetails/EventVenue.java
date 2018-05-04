package com.opera.app.pojo.events.eventdetails;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class EventVenue
{
    private String venueImage;

    private VenueDateTime[] venueDateTime;

    private String venueType;

    private EventPriceFrom[] eventPriceFrom;




    public String getVenueImage ()
    {
        return venueImage;
    }

    public void setVenueImage (String venueImage)
    {
        this.venueImage = venueImage;
    }

    public VenueDateTime[] getVenueDateTime ()
    {
        return venueDateTime;
    }

    public void setVenueDateTime (VenueDateTime[] venueDateTime)
    {
        this.venueDateTime = venueDateTime;
    }

    public String getVenueType ()
    {
        return venueType;
    }

    public void setVenueType (String venueType)
    {
        this.venueType = venueType;
    }

    public EventPriceFrom[] getEventPriceFrom ()
    {
        return eventPriceFrom;
    }

    public void setEventPriceFrom (EventPriceFrom[] eventPriceFrom)
    {
        this.eventPriceFrom = eventPriceFrom;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [venueImage = "+venueImage+", venueDateTime = "+venueDateTime+", venueType = "+venueType+", eventPriceFrom = "+eventPriceFrom+"]";
    }
}