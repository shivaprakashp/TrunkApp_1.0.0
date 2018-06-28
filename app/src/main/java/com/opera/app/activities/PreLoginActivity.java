package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class PreLoginActivity extends BaseActivity {

    //instance of activity created
    private Activity mActivity;

    @BindView(R.id.btnCreateAccount)
    Button mButtonCreateAccount;

    @BindView(R.id.btnLogin)
    Button mButtonLogin;

    @BindView(R.id.textView_continue_as_guest)
    TextView mTvContinueAsGuest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = PreLoginActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_prelogin);

    }

    //used bindView method of onClick, it allocate memory at the run time
    @OnClick({R.id.btnCreateAccount, R.id.btnLogin, R.id.textView_continue_as_guest})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateAccount:
                openActivity(mActivity, RegisterActivity.class);
                break;

            case R.id.btnLogin:
                openActivity(mActivity, LoginActivity.class);
                break;

            case R.id.textView_continue_as_guest:
                openActivityWithClearPreviousActivities(mActivity, MainActivity.class);
                break;
        }
    }
}
