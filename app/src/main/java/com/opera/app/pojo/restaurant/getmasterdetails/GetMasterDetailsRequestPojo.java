package com.opera.app.pojo.restaurant.getmasterdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class GetMasterDetailsRequestPojo {

    @SerializedName("RestaurantId")
    @Expose
    private String RestaurantId;

    @SerializedName("ReservationDate")
    @Expose
    private String ReservationDate;

    public GetMasterDetailsRequestPojo(String restaurantId, String reservationDate) {
        RestaurantId = restaurantId;
        ReservationDate = reservationDate;
    }
}
