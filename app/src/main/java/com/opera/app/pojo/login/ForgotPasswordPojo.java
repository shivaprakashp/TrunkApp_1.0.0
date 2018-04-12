package com.opera.app.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/12/2018.
 */

public class ForgotPasswordPojo {

    @SerializedName("email")
    @Expose
    private String email;

    public ForgotPasswordPojo(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
