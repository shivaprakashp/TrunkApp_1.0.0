package com.opera.app.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostLogin {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("pushRegistrationId")
    @Expose
    private String pushRegistrationId;

    public PostLogin(String email, String password, String pushRegistrationId){
        this.email = email;
        this.password = password;
        this.pushRegistrationId = pushRegistrationId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPushRegistrationId() {
        return pushRegistrationId;
    }
}
