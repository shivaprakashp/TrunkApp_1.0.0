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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by 1000632 on 3/22/2018.
 */

public class LanguageActivity extends BaseActivity {

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

    }

    private void userSessionLanguage() {
        //LanguageManager class is used to creatInstance.
        //And store the selected language
        if (!LanguageManager.createInstance().GetSharedPreferences(mActivity,
                LanguageManager.createInstance().mSelectedLanguage, "").equalsIgnoreCase("")) {
            //validate wheteher user already login
            if (manager.isUserLoggedIn()) {
                openActivity(mActivity, MainActivity.class);
            } else {
                openActivity(mActivity, PreLoginActivity.class);
            }
            finish();
        } else {
            setContentView(R.layout.activity_language_selection);
        }
    }

    //used butterknife onClick.
    //run time instance
    @OnClick({R.id.btnEnglish, R.id.btnArabic})
    public void onClick(View v) {
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
        String s = "";
        String result;

        try {
            result = URLDecoder.decode("%7b%22token%22%3a%22PfD1GdRtDyYwmNTOGoWftoWfjnQ9CbO2Xc2p%2bpXY75vfL8dPz21kUktXhOCoSR0eMop5NtOKF5IjSlrSErS2dg%3d%3d%22%2c%22tickets%22%3a%5b%7b%22id%22%3a%22180720%2c1844%22%2c%22show%22%3a%5b%7b%22code%22%3a%22ETES0000002PC%22%2c%22who%22%3a%22TEST+2+PAYMENT+CENTER%22%2c%22when%22%3a%22Mon+31+Dec+2018+7%3a00PM%22%2c%22where%22%3a%22Sheikh+Maktoum+Hall+-+Dubai+World+Trade+Centre%22%7d%5d%2c%22seatingInformation%22%3a%7b%22section%22%3a%22SGA%22%2c%22row%22%3a%22GA%22%2c%22seats%22%3a%22387%22%7d%7d%5d%7d", "UTF-8");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }
}
