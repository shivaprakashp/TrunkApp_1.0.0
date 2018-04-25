package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/24/2018.
 */

public class Data
{
    @SerializedName("EncryptedString")
    @Expose
    private String EncryptedString;

    @SerializedName("RRO_Subject")
    @Expose
    private String RRO_Subject;

    @SerializedName("Session_ID")
    @Expose
    private String Session_ID;

    @SerializedName("Send_Email_To_Reservation")
    @Expose
    private String Send_Email_To_Reservation;

    @SerializedName("Respak_Reservation")
    @Expose
    private Respak_Reservation Respak_Reservation;

    @SerializedName("Patron")
    @Expose
    private Patron Patron;

    @SerializedName("Save_Transaction")
    @Expose
    private String Save_Transaction;

    @SerializedName("HPT_1")
    @Expose
    private String HPT_1;

    @SerializedName("Email_Type")
    @Expose
    private String Email_Type;

    @SerializedName("HPT_2")
    @Expose
    private String HPT_2;

    public String getEncryptedString ()
{
    return EncryptedString;
}

    public void setEncryptedString (String EncryptedString)
    {
        this.EncryptedString = EncryptedString;
    }

    public String getRRO_Subject ()
    {
        return RRO_Subject;
    }

    public void setRRO_Subject (String RRO_Subject)
    {
        this.RRO_Subject = RRO_Subject;
    }

    public String getSession_ID ()
    {
        return Session_ID;
    }

    public void setSession_ID (String Session_ID)
    {
        this.Session_ID = Session_ID;
    }

    public String getSend_Email_To_Reservation ()
    {
        return Send_Email_To_Reservation;
    }

    public void setSend_Email_To_Reservation (String Send_Email_To_Reservation)
    {
        this.Send_Email_To_Reservation = Send_Email_To_Reservation;
    }

    public Respak_Reservation getRespak_Reservation ()
    {
        return Respak_Reservation;
    }

    public void setRespak_Reservation (Respak_Reservation Respak_Reservation)
    {
        this.Respak_Reservation = Respak_Reservation;
    }

    public Patron getPatron ()
    {
        return Patron;
    }

    public void setPatron (Patron Patron)
    {
        this.Patron = Patron;
    }

    public String getSave_Transaction ()
    {
        return Save_Transaction;
    }

    public void setSave_Transaction (String Save_Transaction)
    {
        this.Save_Transaction = Save_Transaction;
    }

    public String getHPT_1 ()
    {
        return HPT_1;
    }

    public void setHPT_1 (String HPT_1)
    {
        this.HPT_1 = HPT_1;
    }

    public String getEmail_Type ()
    {
        return Email_Type;
    }

    public void setEmail_Type (String Email_Type)
    {
        this.Email_Type = Email_Type;
    }

    public String getHPT_2 ()
{
    return HPT_2;
}

    public void setHPT_2 (String HPT_2)
    {
        this.HPT_2 = HPT_2;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [EncryptedString = "+EncryptedString+", RRO_Subject = "+RRO_Subject+", Session_ID = "+Session_ID+", Send_Email_To_Reservation = "+Send_Email_To_Reservation+", Respak_Reservation = "+Respak_Reservation+", Patron = "+Patron+", Save_Transaction = "+Save_Transaction+", HPT_1 = "+HPT_1+", Email_Type = "+Email_Type+", HPT_2 = "+HPT_2+"]";
    }
}

