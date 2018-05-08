package com.opera.app.pojo.events.genreslisting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genres {

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("InternalName")
    @Expose
    private String InternalName;

    @SerializedName("Genere")
    @Expose
    private String Genere;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Image")
    @Expose
    private String Image;

    public Genres() {
    }

    public Genres(String image) {
        Image = image;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getInternalName() {
        return InternalName;
    }

    public void setInternalName(String internalName) {
        InternalName = internalName;
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

    @Override
    public String toString() {
        return "ClassPojo{" +
                "Id='" + Id + '\'' +
                ", InternalName='" + InternalName + '\'' +
                ", Genere='" + Genere + '\'' +
                ", Description='" + Description + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }
}
