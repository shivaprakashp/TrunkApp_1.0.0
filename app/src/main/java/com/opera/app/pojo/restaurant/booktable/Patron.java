package com.opera.app.pojo.restaurant.booktable;

/**
 * Created by 1000632 on 4/24/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patron {

    @SerializedName("Person_ID")
    @Expose
    private String personID;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Firstname")
    @Expose
    private String firstname;
    @SerializedName("Lastname")
    @Expose
    private String lastname;
    @SerializedName("Address_1")
    @Expose
    private String address1;
    @SerializedName("Address_2")
    @Expose
    private String address2;
    @SerializedName("Suburb")
    @Expose
    private String suburb;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("PostCode")
    @Expose
    private String postCode;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Organisation")
    @Expose
    private String organisation;
    @SerializedName("Position")
    @Expose
    private String position;

    public Patron(String firstname, String lastname, String mobile, String email, String title){
        personID = "";
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        address1 = "sample string";
        address2 = "sample string";
        suburb = "sample string";
        state = "sample string";
        postCode = "sample string";
        phone = "sample string";
        this.mobile = mobile;
        this.email = email;
        organisation = "sample string";
        position = "sample string";
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}