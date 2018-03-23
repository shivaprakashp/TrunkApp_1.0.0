package com.opera.app.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;

import butterknife.BindView;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private Intent in;
    private Activity mActivity;

    @BindView(R.id.btnCreateAccount)
    Button mButtonCreateAccount;

    @BindView(R.id.btnLogin)
    Button mButtonLogin;

    @BindView(R.id.textView_continue_as_guest)
    TextView mTvContinueAsGuest;

    @BindView(R.id.tvTerms)
    TextView mtvTerms;

    @BindView(R.id.tvPrivacyPolicy)
    TextView mtvPrivacyPolicy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initView();
    }

    private void initView() {
        mActivity = RegisterActivity.this;
        mButtonCreateAccount.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mTvContinueAsGuest.setOnClickListener(this);
        mtvTerms.setOnClickListener(this);
        mtvPrivacyPolicy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateAccount:

                in = new Intent(mActivity, MainActivity.class);
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

            case R.id.tvTerms: {
                final Dialog di = new Dialog(mActivity);
                di.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                di.setContentView(R.layout.dialog_terms_conditions);
                di.show();
            }
            break;

            case R.id.tvPrivacyPolicy: {
                final Dialog di = new Dialog(mActivity);
                di.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                di.setContentView(R.layout.dialog_privacy_policy);
                di.show();
            }
                break;


        }
    }
}
