package com.opera.app.constants;

public interface AppConstants {

    String LOG_FEEDBACK_ALARM = "LOG_FEEDBACK_ALARM";
    String LOG_ALARM = "log_alarm";
    String SEAN_CONOLLY_RESTAURANT_ID = "40";
    //static String SEAN_CONOLLY_RESTAURANT_ID = "{05427B33-F675-4045-B99F-97EE841E5EC0}";

    String SEAN_CONOLLY_R_STATUS = "3";
    String EVENT_TYPE_OPERA_TOUR = "DubaiTour";
    String EVENT_TYPE_GIFT_CARD = "GiftCard";

    //Testing
//    String YOUTUBE_DEVELOPER_KEY = "AIzaSyBt5baizzFMEP--s1eu82goVw_7iRhDAbc";

    //Live
    String YOUTUBE_DEVELOPER_KEY = "AIzaSyBDP3_MA_E5lSZZ5XXHrDwP8bcGKQGOhVo";
    String EnglishLanguage = "en";
    String ArabicLanguage = "ar-AE";
    String STATUS_SUCCESS = "success";
    String MESSAGE = "message";

    //DTCM Keys
    String DTCM_DOMAIN_NAME = "https://dubaioperaw-mobile-uat.etixdubai.com/";
    //    static String DTCM_USER_AGENT_STRING = "iPhone 6";
//    String DTCM_USER_AGENT_STRING = "^.*(iPhone(?!.*Safari))|(Kinder.+Android).*$";
    String DTCM_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36 ^.*(iPhone(?!.*Safari))|(Kinder.+Android).*$";

    //static String DTCM_USER_AGENT_STRING = "Mozilla/5.0 (Linux; Android 4.1.1; HTC One X Build/JRO03C) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.58 Mobile Safari/537.31";
    //    static String DTCM_USER_AGENT_STRING = "iPhone";
//          static String DTCM_USER_AGENT_STRING = "Visit Dubai App";

    String APPLE_MUSIC_URL = "https://www.applemusic.com/dubaiopera";

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

    interface GETWALLETDETAIL {
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

    interface GETBOOKEDEVENTDETAILS {
        String GETBOOKEDEVENTDETAILS = "GETBOOKEDEVENTDETAILS";
    }
}
