package com.opera.app.pojo.promotions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 6/11/2018.
 */

public class PromotionDetails {

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("Title")
    @Expose
    private String Title;

    @SerializedName("Image")
    @Expose
    private String Image;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Price")
    @Expose
    private String Price;

    @SerializedName("ValidFrom")
    @Expose
    private String ValidFrom;

    @SerializedName("ValidTo")
    @Expose
    private String ValidTo;

    @SerializedName("DescriptionHtml")
    @Expose
    private String DescriptionHtml;

    @SerializedName("PromotionType")
    @Expose
    private String PromotionType;

    @SerializedName("PromotionItemId")
    @Expose
    private String PromotionItemId;

    public String getPromotionType() {
        return PromotionType;
    }

    public void setPromotionType(String promotionType) {
        PromotionType = promotionType;
    }

    public String getPromotionItemId() {
        return PromotionItemId;
    }

    public void setPromotionItemId(String promotionItemId) {
        PromotionItemId = promotionItemId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getValidFrom() {
        return ValidFrom;
    }

    public void setValidFrom(String validFrom) {
        ValidFrom = validFrom;
    }

    public String getValidTo() {
        return ValidTo;
    }

    public void setValidTo(String validTo) {
        ValidTo = validTo;
    }

    public String getDescriptionHtml() {
        return DescriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        DescriptionHtml = descriptionHtml;
    }
}
