package com.opera.app.pojo.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GiftCard {

    @SerializedName("voucherId")
    @Expose
    private String voucherId;
    @SerializedName("voucherName")
    @Expose
    private String voucherName;
    @SerializedName("voucherDate")
    @Expose
    private String voucherDate;
    @SerializedName("voucherAmount")
    @Expose
    private String voucherAmount;
    @SerializedName("voucherRefId")
    @Expose
    private String voucherRefId;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherAmount() {
        return voucherAmount;
    }

    public void setVoucherAmount(String voucherAmount) {
        this.voucherAmount = voucherAmount;
    }

    public String getVoucherRefId() {
        return voucherRefId;
    }

    public void setVoucherRefId(String voucherRefId) {
        this.voucherRefId = voucherRefId;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

}