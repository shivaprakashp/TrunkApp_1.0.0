package com.opera.app.pojo.restaurant.getmasterdetails;

/**
 * Created by 1000632 on 4/23/2018.
 */

public class SubmitSaveRestaurantReservationRequestPojo
{
    private String EncryptedString;

    private String RRO_Subject;

    private String Send_Email_To_Reservation;

    private Respak_Reservation Respak_Reservation;

    private Patron Patron;

    private String Save_Transaction;

    private String HPT_1;

    private String Email_Type;

    private String HPT_2;

    public SubmitSaveRestaurantReservationRequestPojo(String encryptedString, String RRO_Subject, String send_Email_To_Reservation, com.opera.app.pojo.restaurant.getmasterdetails.Respak_Reservation respak_Reservation, com.opera.app.pojo.restaurant.getmasterdetails.Patron patron, String save_Transaction, String HPT_1, String email_Type, String HPT_2) {
        EncryptedString = encryptedString;
        this.RRO_Subject = RRO_Subject;
        Send_Email_To_Reservation = send_Email_To_Reservation;
        Respak_Reservation = respak_Reservation;
        Patron = patron;
        Save_Transaction = save_Transaction;
        this.HPT_1 = HPT_1;
        Email_Type = email_Type;
        this.HPT_2 = HPT_2;
    }

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
        return "ClassPojo [EncryptedString = "+EncryptedString+", RRO_Subject = "+RRO_Subject+", Send_Email_To_Reservation = "+Send_Email_To_Reservation+", Respak_Reservation = "+Respak_Reservation+", Patron = "+Patron+", Save_Transaction = "+Save_Transaction+", HPT_1 = "+HPT_1+", Email_Type = "+Email_Type+", HPT_2 = "+HPT_2+"]";
    }
}
