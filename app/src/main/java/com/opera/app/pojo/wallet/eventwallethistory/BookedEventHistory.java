package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 7/16/2018.
 */

public class BookedEventHistory
{
    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("DateTime")
    @Expose
    private String dateTime;

    @SerializedName("payments")
    @Expose
    private Payments[] payments;

    @SerializedName("OrderEvent")
    @Expose
    private OrderEvents orderEvents;

    @SerializedName("OrderItems")
    @Expose
    private ArrayList<OrderItems> orderItems;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDateTime ()
    {
        return dateTime;
    }

    public void setDateTime (String dateTime)
    {
        this.dateTime = dateTime;
    }

    public Payments[] getPayments ()
    {
        return payments;
    }

    public void setPayments (Payments[] payments)
    {
        this.payments = payments;
    }

    public OrderEvents getOrderEvents ()
    {
        return orderEvents;
    }

    public void setOrderEvents (OrderEvents orderEvents)
    {
        this.orderEvents = orderEvents;
    }

    public ArrayList<OrderItems> getOrderItems ()
    {
        return orderItems;
    }

    public void setOrderItems (ArrayList<OrderItems> orderItems)
    {
        this.orderItems = orderItems;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", dateTime = "+dateTime+", payments = "+payments+", orderEvents = "+orderEvents+", orderItems = "+orderItems+"]";
    }
}