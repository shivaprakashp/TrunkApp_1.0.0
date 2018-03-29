package com.opera.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.opera.app.activities.LanguageActivity;

import java.util.Locale;

/**
 * Created by 1000779 on 3/29/2018.
 */

public class LanguageManager {

    private static LanguageManager languageManager = null;

    public static String mLanguageEnglish = "en";

    public static String mLanguageArabic = "ar";

    public static String mSelectedLanguage = "SelectedLanguage";

    //restricted user to create instance
    private LanguageManager(){}

    public static LanguageManager createInstance(){

        //initialize if instance is null
        //or else return instance
        if ( languageManager == null ){
            languageManager = new LanguageManager();
        }

        return languageManager;
    }


    public void StoreInSharedPreference(Activity mActivity, String mKey, String mValue) {
        SharedPreferences mPrefs = mActivity.getSharedPreferences("OperaData", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(mKey, mValue);
        mEditor.commit();

    }

    public String GetSharedPreferences(Activity mActivity, String mKey, String mDefauleValue) {
        String mValue = "";
        SharedPreferences mPrefs = mActivity.getSharedPreferences("OperaData", Context.MODE_PRIVATE);
        mValue = mPrefs.getString(mKey, mDefauleValue);

        return mValue;
    }

    public void CommonLanguageFunction(Activity mActivity) {
        Locale locale = new Locale(GetSharedPreferences(mActivity, mSelectedLanguage, mLanguageEnglish));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        mActivity.getBaseContext().getResources().updateConfiguration(config,
                mActivity.getBaseContext().getResources().getDisplayMetrics());
    }

}
