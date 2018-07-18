package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/16/2018.
 */

public class Customer
{
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("orgCustomerId")
    @Expose
    private String orgCustomerId;

    @SerializedName("aFile")
    @Expose
    private String aFile;

    @SerializedName("account")
    @Expose
    private String account;

    public String getId ()
{
    return id;
}

    public void setId (String id)
    {
        this.id = id;
    }

    public String getOrgCustomerId ()
{
    return orgCustomerId;
}

    public void setOrgCustomerId (String orgCustomerId)
    {
        this.orgCustomerId = orgCustomerId;
    }

    public String getAFile ()
    {
        return aFile;
    }

    public void setAFile (String aFile)
    {
        this.aFile = aFile;
    }

    public String getAccount ()
    {
        return account;
    }

    public void setAccount (String account)
    {
        this.account = account;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", orgCustomerId = "+orgCustomerId+", aFile = "+aFile+", account = "+account+"]";
    }
}