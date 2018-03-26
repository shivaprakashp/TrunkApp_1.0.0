package com.opera.app.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import android.view.View;

import java.util.Locale;

/**
 * Created by 1000779 on 2/3/2018.
 */

public class OperaUtils {

    public static String FONT_MONSTERRAT_LIGHT = "Montserrat-Light.ttf";

    public static String FONT_MONSTERRAT_MEDIUM = "Montserrat-Medium.ttf";

    public static String FONT_MONSTERRAT_REGULAR = "Montserrat-Regular.ttf";

    public static String FONT_MONSTERRAT_BOLD = "Montserrat-Bold.ttf";

    public static String mSelectedLanguage = "SelectedLanguage";

    public static String mLanguageEnglish = "en";

    public static String mLanguageArabic = "ar";

    public void StoreInSharedPreference(Activity mActivity, String mKey, String mValue) {
        SharedPreferences mPrefs = mActivity.getSharedPreferences("OperaData", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(mKey, mValue);
        mEditor.commit();

        Log.e("test", "");
    }

    public String GetSharedPreferences(Activity mActivity, String mKey, String mDefauleValue) {
        String mValue = "";
        SharedPreferences mPrefs = mActivity.getSharedPreferences("OperaData", Context.MODE_PRIVATE);
        mValue = mPrefs.getString(mKey, mDefauleValue);

        return mValue;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void forceRTLIfSupported(Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mActivity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
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
