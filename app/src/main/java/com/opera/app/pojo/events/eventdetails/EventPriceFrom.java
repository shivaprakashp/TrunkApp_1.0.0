package com.opera.app.pojo.events.eventdetails;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class EventPriceFrom
{
    private String ticketId;

    private String ticketType;

    private String ticketPrice;


    public String getTicketId ()
    {
        return ticketId;
    }

    public void setTicketId (String ticketId)
    {
        this.ticketId = ticketId;
    }

    public String getTicketType ()
    {
        return ticketType;
    }

    public void setTicketType (String ticketType)
    {
        this.ticketType = ticketType;
    }

    public String getTicketPrice ()
    {
        return ticketPrice;
    }

    public void setTicketPrice (String ticketPrice)
    {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ticketId = "+ticketId+", ticketType = "+ticketType+", ticketPrice = "+ticketPrice+"]";
    }
}
