package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 7/20/2018.
 */

public class ParentDataForBookedEventHistory {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("Data")
    @Expose
    private ArrayList<BookedEventHistory> Data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<BookedEventHistory> getData() {
        return Data;
    }

    public void setData(ArrayList<BookedEventHistory> data) {
        Data = data;
    }
}
