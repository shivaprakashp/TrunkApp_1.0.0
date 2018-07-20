package com.opera.app.pojo.events.eventlisiting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 5/9/2018.
 */

public class EventTime
{
    @SerializedName("eventPerfCode")
    @Expose
    private String eventPerfCode;

    @SerializedName("FromTime")
    @Expose
    private String FromTime;

    @SerializedName("ToTime")
    @Expose
    private String ToTime;

    public String getEventPerfCode() {
        return eventPerfCode;
    }

    public void setEventPerfCode(String eventPerfCode) {
        this.eventPerfCode = eventPerfCode;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }
}

