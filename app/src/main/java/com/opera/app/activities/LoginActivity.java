package com.opera.app.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.utils.LanguageManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class LoginActivity extends BaseActivity {

    private Activity mActivity;

    @BindView(R.id.tv_forgotPassword)
    TextView mTextForgotPwd;

    @BindView(R.id.btnRegister)
    Button mButtonRegister;

    @BindView(R.id.btnLogin)
    Button mButtonLogin;

    @BindView(R.id.textView_continue_as_guest)
    TextView mTextContinue_as_guest;

    @BindView(R.id.login_username)
    View login_username;

    @BindView(R.id.login_password)
    View login_password;

    @BindView(R.id.linearParent)
    LinearLayout mLinearParent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = LoginActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);

        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        //edittext
        EditTextWithFont username = (EditTextWithFont) login_username.findViewById(R.id.edt);
        username.setHint(getString(R.string.username));

        EditTextWithFont password = (EditTextWithFont) login_password.findViewById(R.id.edt);
        password.setHint(getString(R.string.password));

    }

    @OnClick({R.id.tv_forgotPassword,R.id.btnRegister,R.id.textView_continue_as_guest,R.id.btnLogin})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgotPassword:

                showDialog();

                break;

            case R.id.btnRegister:
                openActivity(mActivity, RegisterActivity.class);
                break;

            case R.id.textView_continue_as_guest:
                openActivity(mActivity, MainActivity.class);
                break;

            case R.id.btnLogin:
                openActivity(mActivity, MainActivity.class);
                break;

        }

    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        dialog.setContentView(R.layout.popup_forgotpassword);

        TextView tv_forgotPassword = (TextView) dialog.findViewById(R.id.tv_forgotPassword);
        EditText et_username = (EditText) dialog.findViewById(R.id.et_username);
        Button btnSend = (Button) dialog.findViewById(R.id.btnSend);

        dialog.show();

    }


}
