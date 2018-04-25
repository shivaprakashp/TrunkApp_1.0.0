package com.opera.app.pojo.restaurant.getmasterdetails;

/**
 * Created by 1000632 on 4/23/2018.
 */

public class Patron
{
    private String Phone;

    private String Organisation;

    private String Title;

    private String Address_2;

    private String State;

    private String Email;

    private String Address_1;

    private String PostCode;

    private String Suburb;

    private String Mobile;

    private String Position;

    private String Firstname;

    private String Lastname;

    private String Person_ID;

    public Patron(String phone, String organisation, String title, String address_2, String state, String email, String address_1, String postCode, String suburb, String mobile, String position, String firstname, String lastname, String person_ID) {
        Phone = phone;
        Organisation = organisation;
        Title = title;
        Address_2 = address_2;
        State = state;
        Email = email;
        Address_1 = address_1;
        PostCode = postCode;
        Suburb = suburb;
        Mobile = mobile;
        Position = position;
        Firstname = firstname;
        Lastname = lastname;
        Person_ID = person_ID;
    }

    public String getPhone ()
    {
        return Phone;
    }

    public void setPhone (String Phone)
    {
        this.Phone = Phone;
    }

    public String getOrganisation ()
    {
        return Organisation;
    }

    public void setOrganisation (String Organisation)
    {
        this.Organisation = Organisation;
    }

    public String getTitle ()
    {
        return Title;
    }

    public void setTitle (String Title)
    {
        this.Title = Title;
    }

    public String getAddress_2 ()
    {
        return Address_2;
    }

    public void setAddress_2 (String Address_2)
    {
        this.Address_2 = Address_2;
    }

    public String getState ()
    {
        return State;
    }

    public void setState (String State)
    {
        this.State = State;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getAddress_1 ()
    {
        return Address_1;
    }

    public void setAddress_1 (String Address_1)
    {
        this.Address_1 = Address_1;
    }

    public String getPostCode ()
    {
        return PostCode;
    }

    public void setPostCode (String PostCode)
    {
        this.PostCode = PostCode;
    }

    public String getSuburb ()
    {
        return Suburb;
    }

    public void setSuburb (String Suburb)
    {
        this.Suburb = Suburb;
    }

    public String getMobile ()
    {
        return Mobile;
    }

    public void setMobile (String Mobile)
    {
        this.Mobile = Mobile;
    }

    public String getPosition ()
    {
        return Position;
    }

    public void setPosition (String Position)
    {
        this.Position = Position;
    }

    public String getFirstname ()
    {
        return Firstname;
    }

    public void setFirstname (String Firstname)
    {
        this.Firstname = Firstname;
    }

    public String getLastname ()
    {
        return Lastname;
    }

    public void setLastname (String Lastname)
    {
        this.Lastname = Lastname;
    }

    public String getPerson_ID ()
    {
        return Person_ID;
    }

    public void setPerson_ID (String Person_ID)
    {
        this.Person_ID = Person_ID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Phone = "+Phone+", Organisation = "+Organisation+", Title = "+Title+", Address_2 = "+Address_2+", State = "+State+", Email = "+Email+", Address_1 = "+Address_1+", PostCode = "+PostCode+", Suburb = "+Suburb+", Mobile = "+Mobile+", Position = "+Position+", Firstname = "+Firstname+", Lastname = "+Lastname+", Person_ID = "+Person_ID+"]";
    }
}
