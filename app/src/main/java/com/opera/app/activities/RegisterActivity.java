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
import com.opera.app.customwidget.EditTextWithFont;

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

    @BindView(R.id.reg_edtEmail)
    View reg_edtEmail;

    @BindView(R.id.reg_edtPassword)
    View reg_edtPassword;

    @BindView(R.id.reg_edtRePass)
    View reg_edtRePass;

    @BindView(R.id.reg_edtFirstName)
    View reg_edtFirstName;

    @BindView(R.id.reg_edtLastName)
    View reg_edtLastName;

    @BindView(R.id.reg_edtDob)
    View reg_edtDob;

    @BindView(R.id.reg_edtMobile)
    View reg_edtMobile;

    @BindView(R.id.reg_edtCity)
    View reg_edtCity;

    @BindView(R.id.reg_edtState)
    View reg_edtState;

    @BindView(R.id.reg_edtCountry)
    View reg_edtCountry;

    @BindView(R.id.reg_edtNationality)
    View reg_edtNationality;

    /*@BindView(R.id.spinnerNationality)
    Spinner spinnerNationality;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initView();
    }

    private void initView() {
        mActivity = RegisterActivity.this;

        //button
        mButtonCreateAccount.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);

        //textview
        mTvContinueAsGuest.setOnClickListener(this);
        mtvTerms.setOnClickListener(this);
        mtvPrivacyPolicy.setOnClickListener(this);

        //edittext
        EditTextWithFont edtEmail = (EditTextWithFont) reg_edtEmail.findViewById(R.id.edt);
        edtEmail.setHint(getString(R.string.email));

        EditTextWithFont edtPassword = (EditTextWithFont) reg_edtPassword.findViewById(R.id.edt);
        edtPassword.setHint(getString(R.string.pass));

        EditTextWithFont edtRePass = (EditTextWithFont) reg_edtRePass.findViewById(R.id.edt);
        edtRePass.setHint(getString(R.string.re_pass));

        EditTextWithFont edtFirstName = (EditTextWithFont) reg_edtFirstName.findViewById(R.id.edt);
        edtFirstName.setHint(getString(R.string.firstname));

        EditTextWithFont edtLastName = (EditTextWithFont) reg_edtLastName.findViewById(R.id.edt);
        edtLastName.setHint(getString(R.string.lastname));

        EditTextWithFont edtDob = (EditTextWithFont) reg_edtDob.findViewById(R.id.edt);
        edtDob.setHint(getString(R.string.dob));

        EditTextWithFont edtMobile = (EditTextWithFont) reg_edtMobile.findViewById(R.id.edt);
        edtMobile.setHint(getString(R.string.mobile));

        EditTextWithFont edtCity = (EditTextWithFont) reg_edtCity.findViewById(R.id.edt);
        edtCity.setHint(getString(R.string.city));

        EditTextWithFont edtState = (EditTextWithFont) reg_edtState.findViewById(R.id.edt);
        edtState.setHint(getString(R.string.state));

        EditTextWithFont edtCountry = (EditTextWithFont) reg_edtCountry.findViewById(R.id.edt);
        edtCountry.setHint(getString(R.string.country));

        EditTextWithFont edtNationality = (EditTextWithFont) reg_edtNationality.findViewById(R.id.edt);
        edtNationality.setHint(getString(R.string.nationality));

        /*//spinner
        spinnerNationality.setOnClickListener(this);*/

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
