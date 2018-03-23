package com.opera.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by 1000779 on 2/3/2018.
 */

public class OperaUtils {

    public static String FONT_MONSTERRAT_LIGHT = "Montserrat-Light.ttf";

    public static String FONT_MONSTERRAT_MEDIUM = "Montserrat-Medium.ttf";

    public static String FONT_MONSTERRAT_REGULAR = "Montserrat-Regular.ttf";

    public static String FONT_MONSTERRAT_BOLD = "Montserrat-Bold.ttf";

    public static String mSelectedLanguage = "";

    public static String mLanguageEnglish = "English";

    public static String mLanguageArabic = "Arabic";

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

}
