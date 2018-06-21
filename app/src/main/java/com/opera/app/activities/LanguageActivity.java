package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
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
}
