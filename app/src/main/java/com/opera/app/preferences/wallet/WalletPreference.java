package com.opera.app.preferences.wallet;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.pojo.wallet.WalletDetails;

public class WalletPreference {

    private static SharedPreferences walletPref;
    private static String IS_DATA_EXIST = "IsDataExist";
    private SharedPreferences.Editor editor;
    Context context;
    int prefMode = 0;
    private Gson gson;
    BaseActivity mBaseActivity;

    public WalletPreference(Context context){
        this.context = context;
        walletPref = context.getSharedPreferences(context.getString(R.string.walletTitle), prefMode);
        editor = walletPref.edit();
        mBaseActivity = (BaseActivity) context;
        gson = new Gson();
    }

    // check whether data available or not
    public boolean isWalletData() {
        return walletPref.getBoolean(IS_DATA_EXIST, false);
    }

    //set json response in preference
    public void setWalletData(WalletDetails walletData){
        editor.putBoolean(IS_DATA_EXIST, false);
        editor.putString(context.getString(R.string.prefWalletData), gson.toJson(walletData));
        editor.commit();
    }

    //get wallet data
    public WalletDetails getWalletData() {
        String userData = walletPref.getString(context.getString(R.string.prefWalletData), "");
        return gson.fromJson(userData, WalletDetails.class);
    }

    //clear login session
    public void deleteWalletData() {
        //clear all data from shared preference
        editor.clear();
        editor.commit();
    }
}
