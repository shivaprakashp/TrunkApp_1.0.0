package com.opera.app.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponseData {

    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Token")
    @Expose
    private String token;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
