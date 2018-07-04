package com.opera.app.pojo.ticketbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/3/2018.
 */

public class Show
{
    @SerializedName("when")
    @Expose
    private String when;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("where")
    @Expose
    private String where;

    @SerializedName("who")
    @Expose
    private String who;

    public String getWhen ()
    {
        return when;
    }

    public void setWhen (String when)
    {
        this.when = when;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getWhere ()
    {
        return where;
    }

    public void setWhere (String where)
    {
        this.where = where;
    }

    public String getWho ()
    {
        return who;
    }

    public void setWho (String who)
    {
        this.who = who;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [when = "+when+", code = "+code+", where = "+where+", who = "+who+"]";
    }
}
