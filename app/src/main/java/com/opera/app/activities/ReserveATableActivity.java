package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 1000632 on 4/5/2018.
 */

public class ReserveATableActivity extends BaseActivity {

    private Activity mActivity;

    @BindView(R.id.toolbarReserveATable)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.reserve_edtWindowTable)
    View reserve_edtWindowTable;

    @BindView(R.id.reserve_edtFulName)
    View reserve_edtFulName;

    @BindView(R.id.reserve_edtFulNo)
    View reserve_edtFulNo;

    @BindView(R.id.reserve_edtEmail)
    View reserve_edtEmail;

    @BindView(R.id.imageMinus)
    ImageView mImageMinus;

    @BindView(R.id.imagePlus)
    ImageView mImagePlus;

    @BindView(R.id.txtNumberOfGuests)
    TextView mTxtNumberOfGuests;

    @BindView(R.id.spinnerMealPeriod)
    Spinner mSpinnerMealPeriod;

    @BindView(R.id.spinnerSelectTime)
    Spinner mSpinnerSelectTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = ReserveATableActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_reserve_table);

        initToolbar();

        initView();
    }

    private void initView() {
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.reserve_a_table));

        EditTextWithFont edtWindowTable = (EditTextWithFont) reserve_edtWindowTable.findViewById(R.id.edt);
        edtWindowTable.setHint(getString(R.string.window_table));

        EditTextWithFont edtFulName = (EditTextWithFont) reserve_edtFulName.findViewById(R.id.edt);
        edtFulName.setHint(getString(R.string.full_name1));

        EditTextWithFont edtFulNo = (EditTextWithFont) reserve_edtFulNo.findViewById(R.id.edt);
        edtFulNo.setHint(getString(R.string.telephone_no));

        EditTextWithFont edtEmail = (EditTextWithFont) reserve_edtEmail.findViewById(R.id.edt);
        edtEmail.setHint(getString(R.string.email3));

        String[] arrMealPeriod = {getResources().getString(R.string.select_meal_priod)};

        ArrayAdapter<String> adapterMealPeriod = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrMealPeriod);
        mSpinnerMealPeriod.setAdapter(adapterMealPeriod);

        String[] arrSelectTime = {getResources().getString(R.string.select_time)};

        ArrayAdapter<String> adapterSelectTime = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrSelectTime);
        mSpinnerSelectTime.setAdapter(adapterSelectTime);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.imagePlus, R.id.imageMinus})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imagePlus:
                int valuePlus = Integer.parseInt(mTxtNumberOfGuests.getText().toString());
                valuePlus++;
                mTxtNumberOfGuests.setText(valuePlus + "");
                break;

            case R.id.imageMinus:
                if (!mTxtNumberOfGuests.getText().toString().equalsIgnoreCase("0")) {
                    int valueMinus = Integer.parseInt(mTxtNumberOfGuests.getText().toString());
                    valueMinus--;
                    mTxtNumberOfGuests.setText(valueMinus + "");
                }

                break;

        }
    }
}
