package com.opera.app.pojo.wallet.eventwallethistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 7/16/2018.
 */

public class Fees
{
    @SerializedName("typeName")
    @Expose
    private String typeName;

    @SerializedName("total")
    @Expose
    private String total;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("sheetId")
    @Expose
    private String sheetId;

    @SerializedName("financeCode")
    @Expose
    private String financeCode;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("inside")
    @Expose
    private String inside;

    @SerializedName("bucket")
    @Expose
    private String bucket;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("type")
    @Expose
    private String type;

    public String getTypeName ()
    {
        return typeName;
    }

    public void setTypeName (String typeName)
    {
        this.typeName = typeName;
    }

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getId ()
{
    return id;
}

    public void setId (String id)
    {
        this.id = id;
    }

    public String getSheetId ()
    {
        return sheetId;
    }

    public void setSheetId (String sheetId)
    {
        this.sheetId = sheetId;
    }

    public String getFinanceCode ()
{
    return financeCode;
}

    public void setFinanceCode (String financeCode)
    {
        this.financeCode = financeCode;
    }

    public String getName ()
{
    return name;
}

    public void setName (String name)
    {
        this.name = name;
    }

    public String getInside ()
    {
        return inside;
    }

    public void setInside (String inside)
    {
        this.inside = inside;
    }

    public String getBucket ()
{
    return bucket;
}

    public void setBucket (String bucket)
    {
        this.bucket = bucket;
    }

    public String getCode ()
{
    return code;
}

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [typeName = "+typeName+", total = "+total+", id = "+id+", sheetId = "+sheetId+", financeCode = "+financeCode+", name = "+name+", inside = "+inside+", bucket = "+bucket+", code = "+code+", type = "+type+"]";
    }
}
