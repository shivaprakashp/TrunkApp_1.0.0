package com.opera.app.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Intent in;
    private Activity mActivity;

    @BindView(R.id.tv_forgotPassword)
    TextView mTextForgotPwd;

    @BindView(R.id.btnRegister)
    Button mButtonRegister;

    @BindView(R.id.btnLogin)
    Button mButtonLogin;

    @BindView(R.id.textView_continue_as_guest)
    TextView mTextContinue_as_guest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mActivity = LoginActivity.this;

        mTextForgotPwd.setOnClickListener(this);
        mButtonRegister.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
        mTextContinue_as_guest.setOnClickListener(this);
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
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.popup_forgotpassword);

//        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);

        dialog.show();

    }
}
