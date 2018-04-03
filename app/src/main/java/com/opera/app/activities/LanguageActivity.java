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
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by 1000632 on 3/22/2018.
 */

public class LanguageActivity extends BaseActivity{

    private Activity mActivity;

    @BindView(R.id.btnEnglish)
    Button mButtonEnglish;

    @BindView(R.id.btnArabic)
    Button mButtonArabic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = LanguageActivity.this;

        if (!LanguageManager.createInstance().GetSharedPreferences(mActivity,
                LanguageManager.createInstance().mSelectedLanguage, "").equalsIgnoreCase("")) {
            openActivity(mActivity,PreLoginActivity.class);
            finish();
        } else {
            setContentView(R.layout.activity_language_selection);
        }
    }

    //used butterknife onClick
    @OnClick({R.id.btnEnglish, R.id.btnArabic})
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnEnglish:
                LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                        LanguageManager.createInstance().mSelectedLanguage,
                        LanguageManager.createInstance().mLanguageEnglish);
                openActivity(mActivity, PreLoginActivity.class);
                finish();
                break;

            case R.id.btnArabic:
                LanguageManager.createInstance().StoreInSharedPreference(mActivity,
                        LanguageManager.createInstance().mSelectedLanguage,
                        LanguageManager.createInstance().mLanguageArabic);
                openActivity(mActivity, PreLoginActivity.class);
                finish();
                break;
        }
    }
}
