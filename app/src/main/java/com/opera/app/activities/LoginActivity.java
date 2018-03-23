package com.opera.app.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.utils.OperaUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Intent in;
    private Activity mActivity;
    private OperaUtils mOperaUtils = new OperaUtils();

    @BindView(R.id.tv_forgotPassword)
    TextView mTextForgotPwd;

    @BindView(R.id.btnRegister)
    Button mButtonRegister;

    @BindView(R.id.btnLogin)
    Button mButtonLogin;

    @BindView(R.id.textView_continue_as_guest)
    TextView mTextContinue_as_guest;

    @BindView(R.id.editUsername)
    EditText mEditTextUsername;

    @BindView(R.id.editPassword)
    EditText mEditTextPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = LoginActivity.this;
        if (mOperaUtils.GetSharedPreferences(mActivity, mOperaUtils.mSelectedLanguage, mOperaUtils.mLanguageEnglish).equalsIgnoreCase(mOperaUtils.mLanguageArabic))

        {
            forceRTLIfSupported();
        }

        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mTextForgotPwd.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mTextContinue_as_guest.setOnClickListener(this);

        if (mOperaUtils.GetSharedPreferences(mActivity, mOperaUtils.mSelectedLanguage, mOperaUtils.mLanguageEnglish).equalsIgnoreCase(mOperaUtils.mLanguageArabic)) {
            mButtonLogin.setText(getResources().getString(R.string.login_arabic));
            mButtonRegister.setText(getResources().getString(R.string.dont_have_an_account_arabic));
            mTextContinue_as_guest.setText(getResources().getString(R.string.continue_as_guest_arabic));
            mTextForgotPwd.setText(getResources().getString(R.string.forgot_password_arabic));
            mEditTextUsername.setHint(getResources().getString(R.string.username_arabic));
            mEditTextPassword.setHint(getResources().getString(R.string.password_arabic));
            mEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgotPassword:

                showDialog();

                break;

            case R.id.btnRegister:

                in = new Intent(mActivity, RegisterActivity.class);
                startActivity(in);

                break;

            case R.id.textView_continue_as_guest:

                in = new Intent(mActivity, MainActivity.class);
                startActivity(in);

                break;

            case R.id.btnLogin:

                in = new Intent(mActivity, MainActivity.class);
                startActivity(in);

                break;

        }

    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.popup_forgotpassword);

        TextView tv_forgotPassword = (TextView) dialog.findViewById(R.id.tv_forgotPassword);
        EditText et_username = (EditText) dialog.findViewById(R.id.et_username);
        Button btnSend = (Button) dialog.findViewById(R.id.btnSend);

        if (mOperaUtils.GetSharedPreferences(mActivity, mOperaUtils.mSelectedLanguage, mOperaUtils.mLanguageEnglish).equalsIgnoreCase(mOperaUtils.mLanguageArabic)) {
            tv_forgotPassword.setText(getResources().getString(R.string.forgot_password_arabic));
            et_username.setHint(getResources().getString(R.string.username_arabic));
            btnSend.setText(getResources().getString(R.string.send_arabic));
        }

        dialog.show();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }
}
