package com.opera.app.pojo.restaurant.booktable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 1000632 on 4/24/2018.
 */

public class Data
{
    @SerializedName("Session_ID")
    @Expose
    private String sessionID;
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
    private Object hPT1;
    @SerializedName("HPT_2")
    @Expose
    private Object hPT2;
    @SerializedName("EncryptedString")
    @Expose
    private Object encryptedString;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
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

    public Object getHPT1() {
        return hPT1;
    }

    public void setHPT1(Object hPT1) {
        this.hPT1 = hPT1;
    }

    public Object getHPT2() {
        return hPT2;
    }

    public void setHPT2(Object hPT2) {
        this.hPT2 = hPT2;
    }

    public Object getEncryptedString() {
        return encryptedString;
    }

    public void setEncryptedString(Object encryptedString) {
        this.encryptedString = encryptedString;
    }

}