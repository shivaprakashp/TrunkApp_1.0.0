package com.opera.app.pojo.settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/17/2018.
 */

public class GetSettingsDataPojo {

    @SerializedName("Settings")
    @Expose
    private SettingsPojo Settingsdata;

    public SettingsPojo getSettingsdata() {
        return Settingsdata;
    }

    public void setSettingsdata(SettingsPojo settingsdata) {
        Settingsdata = settingsdata;
    }
}
