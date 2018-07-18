package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/16/2018.
 */

public class OrderLineItems {
    @SerializedName("priceTypeName")
    @Expose
    private String priceTypeName;

    @SerializedName("priceTypeCode")
    @Expose
    private String priceTypeCode;

    @SerializedName("concession")
    @Expose
    private String concession;

    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    @SerializedName("priceCategoryId")
    @Expose
    private String priceCategoryId;

    @SerializedName("performanceCode")
    @Expose
    private String performanceCode;

    @SerializedName("barcode")
    @Expose
    private String barcode;

    @SerializedName("customer")
    @Expose
    private Customer customer;

    @SerializedName("seller")
    @Expose
    private String seller;

    @SerializedName("offerCode")
    @Expose
    private String offerCode;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("priceCategoryCode")
    @Expose
    private String priceCategoryCode;

    @SerializedName("price")
    @Expose
    private Price price;

    @SerializedName("seat")
    @Expose
    private Seat seat;

    @SerializedName("qualifierCode")
    @Expose
    private String qualifierCode;

    @SerializedName("priceCategoryName")
    @Expose
    private String priceCategoryName;

    @SerializedName("priceTypeId")
    @Expose
    private String priceTypeId;

    @SerializedName("entitlement")
    @Expose
    private String entitlement;

    @SerializedName("channel")
    @Expose
    private String channel;

    public String getPriceTypeName() {
        return priceTypeName;
    }

    public void setPriceTypeName(String priceTypeName) {
        this.priceTypeName = priceTypeName;
    }

    public String getPriceTypeCode() {
        return priceTypeCode;
    }

    public void setPriceTypeCode(String priceTypeCode) {
        this.priceTypeCode = priceTypeCode;
    }

    public String getConcession() {
        return concession;
    }

    public void setConcession(String concession) {
        this.concession = concession;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPriceCategoryId() {
        return priceCategoryId;
    }

    public void setPriceCategoryId(String priceCategoryId) {
        this.priceCategoryId = priceCategoryId;
    }

    public String getPerformanceCode() {
        return performanceCode;
    }

    public void setPerformanceCode(String performanceCode) {
        this.performanceCode = performanceCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriceCategoryCode() {
        return priceCategoryCode;
    }

    public void setPriceCategoryCode(String priceCategoryCode) {
        this.priceCategoryCode = priceCategoryCode;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getQualifierCode() {
        return qualifierCode;
    }

    public void setQualifierCode(String qualifierCode) {
        this.qualifierCode = qualifierCode;
    }

    public String getPriceCategoryName() {
        return priceCategoryName;
    }

    public void setPriceCategoryName(String priceCategoryName) {
        this.priceCategoryName = priceCategoryName;
    }

    public String getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(String priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public String getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(String entitlement) {
        this.entitlement = entitlement;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "ClassPojo [priceTypeName = " + priceTypeName + ", priceTypeCode = " + priceTypeCode + ", concession = " + concession + ", dateTime = " + dateTime + ", priceCategoryId = " + priceCategoryId + ", performanceCode = " + performanceCode + ", barcode = " + barcode + ", customer = " + customer + ", seller = " + seller + ", offerCode = " + offerCode + ", id = " + id + ", priceCategoryCode = " + priceCategoryCode + ", price = " + price + ", seat = " + seat + ", qualifierCode = " + qualifierCode + ", priceCategoryName = " + priceCategoryName + ", priceTypeId = " + priceTypeId + ", entitlement = " + entitlement + ", channel = " + channel + "]";
    }
}

