package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 7/16/2018.
 */

public class OrderItems
{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("inventorySource")
    @Expose
    private InventorySource inventorySource;

    @SerializedName("orderLineItems")
    @Expose
    private ArrayList<OrderLineItems> orderLineItems;

    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    @SerializedName("fees")
    @Expose
    private Fees[] fees;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public InventorySource getInventorySource ()
    {
        return inventorySource;
    }

    public void setInventorySource (InventorySource inventorySource)
    {
        this.inventorySource = inventorySource;
    }

    public ArrayList<OrderLineItems> getOrderLineItems ()
    {
        return orderLineItems;
    }

    public void setOrderLineItems (ArrayList<OrderLineItems> orderLineItems)
    {
        this.orderLineItems = orderLineItems;
    }

    public String getDateTime ()
    {
        return dateTime;
    }

    public void setDateTime (String dateTime)
    {
        this.dateTime = dateTime;
    }

    public Fees[] getFees ()
    {
        return fees;
    }

    public void setFees (Fees[] fees)
    {
        this.fees = fees;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", inventorySource = "+inventorySource+", orderLineItems = "+orderLineItems+", dateTime = "+dateTime+", fees = "+fees+"]";
    }
}