package com.opera.app.pojo.ticketbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 7/3/2018.
 */

public class EventTicketBookingPojo
{
   /* @SerializedName("token")
    @Expose
    private String token;*/

    @SerializedName("tickets")
    @Expose
    private ArrayList<Tickets> tickets;

    /*public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }*/

    public ArrayList<Tickets> getTickets ()
    {
        return tickets;
    }

    public void setTickets (ArrayList<Tickets> tickets)
    {
        this.tickets = tickets;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tickets = "+tickets+"]";
    }
}

