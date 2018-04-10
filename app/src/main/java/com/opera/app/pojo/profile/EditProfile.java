package com.opera.app.pojo.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfile {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("interest")
    @Expose
    private String interest;

    @SerializedName("nationality")
    @Expose
    private String nationality;

    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;

    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("country")
    @Expose
    private String country;

    public EditProfile(String email, String firstName, String lastName, String phoneNumber, String interest, String nationality, String dateOfBirth, String mobileNumber, String city, String country) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.interest = interest;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
        this.city = city;
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getInterest() {
        return interest;
    }

    public String getNationality() {
        return nationality;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
/*public String getEmail(String email) {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getInterest() {
        return interest;
    }*/

}
