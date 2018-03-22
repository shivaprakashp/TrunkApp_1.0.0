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

public class LanguageActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent in;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        mActivity = LanguageActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnglish:

                in = new Intent(mActivity, PreLoginActivity.class);
                startActivity(in);

                break;

            case R.id.btnArabic:

                in = new Intent(mActivity, PreLoginActivity.class);
                startActivity(in);

                break;
        }
    }
}
