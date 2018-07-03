package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/24/2018.
 */

public class BookTableRequest
{
    @SerializedName("Patron")
    @Expose
    private Patron patron;
    @SerializedName("Respak_Reservation")
    @Expose
    private RespakReservation respakReservation;
    @SerializedName("Save_Transaction")
    @Expose
    private Boolean saveTransaction;
    @SerializedName("Send_Email_To_Reservation")
    @Expose
    private Boolean sendEmailToReservation;
    @SerializedName("Email_Type")
    @Expose
    private String emailType;
    @SerializedName("RRO_Subject")
    @Expose
    private String rROSubject;
    @SerializedName("HPT_1")
    @Expose
    private String hPT1;
    @SerializedName("HPT_2")
    @Expose
    private String hPT2;
    @SerializedName("EncryptedString")
    @Expose
    private String encryptedString;

    public BookTableRequest(Patron patron, RespakReservation respakReservation){
        this.patron = patron;
        this.respakReservation = respakReservation;
        saveTransaction = true;
        sendEmailToReservation = true;
        emailType = "sample String";
        rROSubject = "sample String";
        hPT1 = "bjRWU1RxeGlVZUhrZkpjei9pb0MvcVEvcURPbmR6cWdHb08rUEhSWVdHRWFQMGxLZVBFeXdocG15VzlxeTVGdXZueVQ1bVhwclFpYzNUU0pGYndjdSs5OWM3R3Q3bnJ5NkVTZUh4RjVPbmd1eUhIMjRqS3BqRC9jbnE5VExtL0wveTVlTEF6ZHFrOS9xUFVLNTZua3dPVXVuc1dBZHZZM3VGeGFYTmFpOUc0PQ==";
        hPT2 = "";
        encryptedString = "sample String";

    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public RespakReservation getRespakReservation() {
        return respakReservation;
    }

    public void setRespakReservation(RespakReservation respakReservation) {
        this.respakReservation = respakReservation;
    }

    public Boolean getSaveTransaction() {
        return saveTransaction;
    }

    public void setSaveTransaction(Boolean saveTransaction) {
        this.saveTransaction = saveTransaction;
    }

    public Boolean getSendEmailToReservation() {
        return sendEmailToReservation;
    }

    public void setSendEmailToReservation(Boolean sendEmailToReservation) {
        this.sendEmailToReservation = sendEmailToReservation;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getRROSubject() {
        return rROSubject;
    }

    public void setRROSubject(String rROSubject) {
        this.rROSubject = rROSubject;
    }

    public String getHPT1() {
        return hPT1;
    }

    public void setHPT1(String hPT1) {
        this.hPT1 = hPT1;
    }

    public String getHPT2() {
        return hPT2;
    }

    public void setHPT2(String hPT2) {
        this.hPT2 = hPT2;
    }

    public String getEncryptedString() {
        return encryptedString;
    }

    public void setEncryptedString(String encryptedString) {
        this.encryptedString = encryptedString;
    }

}