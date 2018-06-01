package com.opera.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.utils.LanguageManager;

import butterknife.BindView;
import butterknife.OnClick;

public class GiftCardActivity extends BaseActivity {

    private Activity mActivity;

    @BindView(R.id.toolbar_edit_profile)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.btnBuyTickets)
    Button btnBuyTickets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = GiftCardActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_gift_card);

        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_gift_cards));

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
    }
    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.btnBuyTickets})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuyTickets:

                break;
        }
    }
}
