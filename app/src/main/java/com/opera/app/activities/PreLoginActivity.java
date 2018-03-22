package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.opera.app.R;

import butterknife.BindView;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class PreLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent in;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prelogin);
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
