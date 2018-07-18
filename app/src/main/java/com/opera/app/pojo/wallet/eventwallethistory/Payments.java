package com.opera.app.pojo.wallet.eventwallethistory;

/**
 * Created by 1000632 on 7/16/2018.
 */

public class Payments
{
    private String id;

    private InventorySource inventorySource;

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

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", inventorySource = "+inventorySource+"]";
    }
}
