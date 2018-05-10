package com.opera.app.pojo.events.genreslisting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GenresEvents {
    @SerializedName("Status")
    @Expose
    private String Status;

    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("Genres")
    @Expose
    private ArrayList<com.opera.app.pojo.events.genreslisting.Genres> Genres;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<com.opera.app.pojo.events.genreslisting.Genres> getGenres() {
        return Genres;
    }

    public void setGenres(ArrayList<com.opera.app.pojo.events.genreslisting.Genres> genres) {
        Genres = genres;
    }

    @Override
    public String toString() {
        return "ClassPojo{" +
                "Status='" + Status + '\'' +
                ", Message='" + Message + '\'' +
                ", Genres=" + Genres +
                '}';
    }
}
