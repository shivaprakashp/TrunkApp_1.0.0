package com.opera.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by 1000632 on 3/22/2018.
 */

public class LanguageActivity extends BaseActivity{

    //create activity instance used it as context of the activity
    private Activity mActivity;

    /*advantage of using BindView is
    * it creates views at the time of execution.
    * It acts as run time isntance*/
    @BindView(R.id.btnEnglish)
    Button mButtonEnglish;

    @BindView(R.id.btnArabic)
    Button mButtonArabic;

    /*Session manager is used to store language preference of user.
    * The selected lanugage used for further communication into the apps.*/
    private SessionManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //context of activity created
        mActivity = LanguageActivity.this;
        //instance of session has created
        manager = new SessionManager(mActivity);

        //call userSession
        userSessionLanguage();
        Log.e("json data",decodeURIComponent());
    }

    private void userSessionLanguage(){
        //LanguageManager class is used to creatInstance.
        //And store the selected language
        if (!LanguageManager.createInstance().GetSharedPreferences(mActivity,
                LanguageManager.createInstance().mSelectedLanguage, "").equalsIgnoreCase("")) {
            //validate wheteher user already login
            if (manager.isUserLoggedIn()){
                openActivity(mActivity, MainActivity.class);
            }else {
                openActivity(mActivity,PreLoginActivity.class);
            }
            finish();
        } else {
            setContentView(R.layout.activity_language_selection);
        }
    }

    //used butterknife onClick.
    //run time instance
    @OnClick({R.id.btnEnglish, R.id.btnArabic})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnEnglish:
                /*Set the language selection.
                * click on button to save the data.*/
                LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                        LanguageManager.createInstance().mSelectedLanguage,
                        LanguageManager.createInstance().mLanguageEnglish);
                openActivity(mActivity, PreLoginActivity.class);
                finish();
                break;

            case R.id.btnArabic:
                /*Set the language selection.
                 * click on button to save the data.*/
                LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                        LanguageManager.createInstance().mSelectedLanguage,
                        LanguageManager.createInstance().mLanguageArabic);
                openActivity(mActivity, PreLoginActivity.class);
                finish();
                break;
        }
    }

    public String decodeURIComponent() {
      String s="";
        String result = null;

        try {
            result = URLDecoder.decode("\"token\"%3a\"tbfu1Q%2bYyxSMAdrfX5i73h3SyXodnpQdEIZaK09JpN1KeiqFt7mmijEWGbdoB%2fNtyEDy1XaqFQ7LO4aak6ufpg%3d%3d\"%2c\"tickets\"%3a%5b%7b\"id\"%3a\"180712%2c709\"%2c\"show\"%3a%5b%7b\"code\"%3a\"ETES0000002PC\"%2c\"who\"%3a\"TEST+2+PAYMENT+CENTER\"%2c\"when\"%3a\"Mon+31+Dec+2018+7%3a00PM\"%2c\"where\"%3a\"Sheikh+Maktoum+Hall+-+Dubai+World+Trade+Centre\"%7d%5d%2c\"seatingInformation\"%3a%7b\"section\"%3a\"SGA\"%2c\"row\"%3a\"GA\"%2c\"seats\"%3a\"248-252\"%7d%7d%5d%7d", "UTF-8");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }
}
