package com.opera.app.pojo.promotions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by 1000632 on 6/11/2018.
 */

public class PromotionsPojo {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("promotions")
    @Expose
    private ArrayList<PromotionDetails> promotionsData;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<PromotionDetails> getPromotionsData() {
        return promotionsData;
    }

    public void setPromotionsData(ArrayList<PromotionDetails> promotionsData) {
        this.promotionsData = promotionsData;
    }
}
