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
import com.opera.app.utils.OperaUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class PreLoginActivity extends BaseActivity implements View.OnClickListener {

    private Intent in;
    private Activity mActivity;
    private OperaUtils mOperaUtils = new OperaUtils();

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
        mOperaUtils.CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_prelogin);

        initView();
    }

    private void initView() {

        mButtonCreateAccount.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mTvContinueAsGuest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateAccount:

                in = new Intent(mActivity, RegisterActivity.class);
                startActivity(in);

                break;

            case R.id.btnLogin:

                in = new Intent(mActivity, LoginActivity.class);
                startActivity(in);

                break;

            case R.id.textView_continue_as_guest:

                in = new Intent(mActivity, MainActivity.class);
                startActivity(in);

                break;
        }
    }
}
