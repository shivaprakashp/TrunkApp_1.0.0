package com.opera.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.opera.app.BaseActivity;
import com.opera.app.R;

import butterknife.BindView;

/**
 * Created by 58001 on 23-03-2018.
 */

public class SettingsActivity extends BaseActivity {

    private Activity mActivity;

    @BindView(R.id.toolbar_setting)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        mActivity = SettingsActivity.this;

        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {

    }
}
