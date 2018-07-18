package com.opera.app.pojo.ticketbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/13/2018.
 */

public class ViewHistoryResponsePojo {
//    private Data Data;

    @SerializedName("status")
    @Expose
    private String status;

    /*public Data getData ()
    {
        return Data;
    }

    public void setData (Data Data)
    {
        this.Data = Data;
    }*/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+"]";
    }
}
