package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 7/16/2018.
 */

public class Price
{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("sheetId")
    @Expose
    private String sheetId;

    @SerializedName("fees")
    @Expose
    private ArrayList<Fees> fees;

    @SerializedName("net")
    @Expose
    private String net;

    public String getId ()
{
    return id;
}

    public void setId (String id)
    {
        this.id = id;
    }

    public String getSheetId ()
    {
        return sheetId;
    }

    public void setSheetId (String sheetId)
    {
        this.sheetId = sheetId;
    }

    public ArrayList<Fees> getFees ()
    {
        return fees;
    }

    public void setFees (ArrayList<Fees> fees)
    {
        this.fees = fees;
    }

    public String getNet ()
    {
        return net;
    }

    public void setNet (String net)
    {
        this.net = net;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", sheetId = "+sheetId+", fees = "+fees+", net = "+net+"]";
    }
}
