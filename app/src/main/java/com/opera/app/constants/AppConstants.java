package com.opera.app.constants;

public interface AppConstants {

    static String SEAN_CONOLLY_RESTAURANT_ID = "40";
//    static String SEAN_CONOLLY_RESTAURANT_ID = "{05427B33-F675-4045-B99F-97EE841E5EC0}";

    static String SEAN_CONOLLY_R_STATUS = "3";
    static String EVENT_TYPE_OPERA_TOUR = "DubaiTour";
    static String EVENT_TYPE_GIFT_CARD = "GiftCard";
    static String YOUTUBE_DEVELOPER_KEY = "AIzaSyBt5baizzFMEP--s1eu82goVw_7iRhDAbc";

    static String DTCM_DOMAIN_NAME = "https://dubaioperaw-mobile-uat.etixdubai.com/";
//    static String USER_SESSION_TOKEN = "n2f12rdyrlKP7S5JC365nUF%2f32Txp8QYQkrCctM7IQS4bcVfOrDZxfXyzG1jLzGqVWwKk1UY%2ffKtuneNszAWYg%3d%3d";
    static String USER_SESSION_TOKEN = "bbWBDZJFJwgJf0Gy8IiJvLiOIkJ1bJezXKcYJVAwsggi1Briy8QrPaC%2bSGWzSM3k2%2bIqLYtRMp5gxiMPURKL0A%3d%3d";
    static String DTCM_SHOW_URL = "https://dubaioperaw-mobile-uat.etixdubai.com/shows/show.aspx?sh=TEST2PC";
    static String DTCM_USER_AGENT_STRING = "^.*(iPhone(?!.*Safari))|(Kinder.+Android).*$";

    interface LOGIN {
        String LOGIN = "LOGIN";
    }

    interface FORGOTPASSWORD {
        String FORGOTPASSWORD = "FORGOTPASSWORD";
    }

    interface REGISTER {
        String REGISTER = "REGISTER";
    }

    interface EDITPROFILE {
        String EDITPROFILE = "EDITPROFILE";
    }

    interface CHANGEPASSWORD {
        String CHANGEPASSWORD = "CHANGEPASSWORD";
    }

    interface SETUSERSETTINGS {
        String SETUSERSETTINGS = "SETUSERSETTINGS";
    }

    interface GETUSERSETTINGS {
        String GETUSERSETTINGS = "GETUSERSETTINGS";
    }

    interface GETRESTAURANTLISTING {
        String GETRESTAURANTLISTING = "GETRESTAURANTLISTING";
    }

    interface GETMASTERDETAILS {
        String GETMASTERDETAILS = "GETMASTERDETAILS";
    }

    interface BOOKATABLE {
        String BOOKATABLE = "BOOKATABLE";
    }

    interface CONTACTUS {
        String CONTACTUS = "CONTACTUS";
    }

    interface GETSPECIFICRESTAURANT {
        String GETSPECIFICRESTAURANT = "GETSPECIFICRESTAURANT";
    }

    interface GETEVENTLISTING {
        String GETEVENTLISTING = "GETEVENTLISTING";
    }

    interface GETEVENTDETAILS {
        String GETEVENTDETAILS = "GETEVENTDETAILS";
    }

    interface GETWALLETDETAIL{
        String GETWALLETDETAIL = "GETWALLETDETAIL";
    }

    interface GETNOTIFICATIONDETAILS {
        String GETNOTIFICATIONDETAILS = "GETNOTIFICATIONDETAILS";
    }

    interface GETPROMOTIONDETAILS {
        String GETPROMOTIONDETAILS = "GETPROMOTIONDETAILS";
    }

    interface GETFEEDBACKDETAILS {
        String GETFEEDBACKDETAILS = "GETFEEDBACKDETAILS";
    }

    interface MARKFAVOURITEFOREVENT {
        String MARKFAVOURITEFOREVENT = "MARKFAVOURITEFOREVENT";
    }

    interface GETDUBAIOPERATOUR {
        String GETDUBAIOPERATOUR = "GETDUBAIOPERATOUR";
    }

    interface GETGIFTCARD {
        String GETGIFTCARD = "GETGIFTCARD";
    }
}
