package com.opera.app.constants;

public interface AppConstants {

    public static final String LOG_FEEDBACK_ALARM = "LOG_FEEDBACK_ALARM";
    String SEAN_CONOLLY_RESTAURANT_ID = "40";
    //static String SEAN_CONOLLY_RESTAURANT_ID = "{05427B33-F675-4045-B99F-97EE841E5EC0}";

    String SEAN_CONOLLY_R_STATUS = "3";
    String EVENT_TYPE_OPERA_TOUR = "DubaiTour";
    String EVENT_TYPE_GIFT_CARD = "GiftCard";
    String YOUTUBE_DEVELOPER_KEY = "AIzaSyBt5baizzFMEP--s1eu82goVw_7iRhDAbc";
    String EnglishLanguage = "en";
    String ArabicLanguage = "ar";
    String STATUS_SUCCESS = "success";
    String STATUS_ERROR = "error";

    //DTCM Keys
    static String DTCM_DOMAIN_NAME = "https://dubaioperaw-mobile-uat.etixdubai.com/";
    static String USER_SESSION_TOKEN = "tbfu1Q%2bYyxSMAdrfX5i73h3SyXodnpQdEIZaK09JpN1KeiqFt7mmijEWGbdoB%2fNtyEDy1XaqFQ7LO4aak6ufpg%3d%3d";
    static String DTCM_USER_AGENT_STRING = "^.*(iPhone(?!.*Safari))|(Kinder.+Android).*$";
//    static String DTCM_USER_AGENT_STRING = "Mozilla/5.0 (Linux; Android 4.1.1; HTC One X Build/JRO03C) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.58 Mobile Safari/537.31";

    static String APPLE_MUSIC_URL = "https://itunes.apple.com/us/curator/the-metropolitan-opera-house/1110087349?mt=1&app=music";

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

    interface SAVEORDER {
        String SAVEORDER = "SAVEORDER";
    }
}
