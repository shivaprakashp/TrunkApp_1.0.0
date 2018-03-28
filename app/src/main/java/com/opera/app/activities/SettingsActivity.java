package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.utils.OperaUtils;

import butterknife.BindView;

/**
 * Created by 58001 on 23-03-2018.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private Activity mActivity;
    private OperaUtils mOperaUtils = new OperaUtils();

    @BindView(R.id.englishSwitch)
    TextView englishSwitch;

    @BindView(R.id.arabicSwitch)
    TextView arabicSwitch;

    @BindView(R.id.tvLogout)
    TextView tvLogout;

    @BindView(R.id.toolbar_setting)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = SettingsActivity.this;

        //For Language setting
        mOperaUtils.CommonLanguageFunction(mActivity);
        setContentView(R.layout.setting);

        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initView() {
        //textview
        englishSwitch.setOnClickListener(this);
        arabicSwitch.setOnClickListener(this);
        tvLogout.setOnClickListener(this);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        if (mOperaUtils.GetSharedPreferences(mActivity, mOperaUtils.mSelectedLanguage, mOperaUtils.mLanguageEnglish).equalsIgnoreCase(mOperaUtils.mLanguageEnglish)) {
            englishSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
            arabicSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
        } else {
            englishSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
            arabicSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
        }
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.englishSwitch: {
                englishSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
                arabicSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                mOperaUtils.StoreInSharedPreference(mActivity, mOperaUtils.mSelectedLanguage, mOperaUtils.mLanguageEnglish);

                Intent in = new Intent(mActivity, MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                /*//For Language setting
                mOperaUtils.CommonLanguageFunction(mActivity);
                setContentView(R.layout.setting);*/
            }
            break;

            case R.id.arabicSwitch: {
                englishSwitch.setBackgroundColor(getResources().getColor(R.color.dark_gray));
                arabicSwitch.setBackgroundColor(getResources().getColor(R.color.colorBurgendy));
                mOperaUtils.StoreInSharedPreference(mActivity, mOperaUtils.mSelectedLanguage, mOperaUtils.mLanguageArabic);

                Intent in = new Intent(mActivity, MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                /*//For Language setting
                mOperaUtils.CommonLanguageFunction(mActivity);
                setContentView(R.layout.setting);*/
            }
            break;

            case R.id.tvLogout:
                break;
        }
    }
}
