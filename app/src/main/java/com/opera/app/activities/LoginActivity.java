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

import com.opera.app.R;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent in;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mActivity = LoginActivity.this;
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
