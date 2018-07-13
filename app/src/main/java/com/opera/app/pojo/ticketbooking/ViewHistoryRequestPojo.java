package com.opera.app.pojo.ticketbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/13/2018.
 */

public class ViewHistoryRequestPojo {

    @SerializedName("OrderId")
    @Expose
    private String OrderId;

    public ViewHistoryRequestPojo(String orderId) {
        OrderId = orderId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}
