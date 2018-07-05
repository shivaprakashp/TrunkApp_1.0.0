package com.opera.app.pojo.events.eventlisiting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 5/9/2018.
 */

public class GenreList {

    @SerializedName("InternalName")
    @Expose
    private String InternalName;

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("Genere")
    @Expose
    private String Genere;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Image")
    @Expose
    private String Image;

    public GenreList() {

    }

    public GenreList(String internalName, String id, String genere, String description, String image) {
        InternalName = internalName;
        Id = id;
        Genere = genere;
        Description = description;
        Image = image;
    }

    public String getInternalName() {
        return InternalName;
    }

    public void setInternalName(String internalName) {
        InternalName = internalName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getGenere() {
        return Genere;
    }

    public void setGenere(String genere) {
        Genere = genere;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}