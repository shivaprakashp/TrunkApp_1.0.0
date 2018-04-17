package com.opera.app.pojo.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opera.app.pojo.login.LoginResponseData;

/**
 * Created by 1000632 on 4/17/2018.
 */

public class GetSettingsPojo {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private GetSettingsDataPojo data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetSettingsDataPojo getData() {
        return data;
    }

    public void setData(GetSettingsDataPojo data) {
        this.data = data;
    }
}
