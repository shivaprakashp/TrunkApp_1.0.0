package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dialogues.GuestDialog;
import com.opera.app.pojo.restaurant.RestaurantsData;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 1000632 on 5/24/2018.
 */

public class RestaurantCompleteDetails extends BaseActivity {

    private RestaurantsData mRestaurantListingData;
    private Activity mActivity;
    private SessionManager manager;
    private CustomToast customToast;

    @BindView(R.id.expandableTextViewInfo)
    TextView mExpandableTextView;

    @BindView(R.id.btnOtherRestaurants)
    Button mBtnOtherRestaurants;

    @BindView(R.id.mTxtRestaurantName)
    TextView mTxtRestaurantName;

    @BindView(R.id.mTxtRestaurantPlace)
    TextView mTxtRestaurantPlace;

    @BindView(R.id.restaurantImage)
    ImageView mRestaurantImage;

    @BindView(R.id.progressImageLoader)
    ProgressBar mProgressImageLoader;

    @BindView(R.id.toolbar_setting)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = RestaurantCompleteDetails.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.restaurantcompletedetails);

        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initView() {
        mRestaurantListingData = (RestaurantsData) getIntent().getSerializableExtra(AppConstants.GETRESTAURANTLISTING.GETRESTAURANTLISTING);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        toolbar.setVisibility(View.VISIBLE);
        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(mRestaurantListingData.getRestName());

        manager = new SessionManager(mActivity);
        customToast = new CustomToast(mActivity);

        mBtnOtherRestaurants.setVisibility(View.GONE);
        mTxtRestaurantPlace.setText("at " + mRestaurantListingData.getRestPlace());
        mTxtRestaurantName.setText(mRestaurantListingData.getRestName());
        mExpandableTextView.setText(mRestaurantListingData.getRestDetails());
        Picasso.with(mActivity).load(mRestaurantListingData.getRestImage()).fit().centerCrop()
                .into(mRestaurantImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressImageLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        mProgressImageLoader.setVisibility(View.GONE);
                    }
                });
    }

    @OnClick({R.id.mBtnReserveATable})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtnReserveATable:
                if (Connections.isConnectionAlive(mActivity)) {
                    if (manager.isUserLoggedIn()) {
                        if (mRestaurantListingData.getRestId().equalsIgnoreCase(AppConstants.SEAN_CONOLLY_RESTAURANT_ID)) {
                            openActivity(mActivity, ReserveATableActivity.class);
                        } else {
                            Intent in = new Intent(mActivity, CommonWebViewActivity.class);
                            in.putExtra("URL", mRestaurantListingData.getRestBookUrl());
                            in.putExtra("Header", mRestaurantListingData.getRestName());
                            mActivity.startActivity(in);
                        }
                    } else {
                        GuestDialog dialog = new GuestDialog(mActivity, mActivity.getString(R.string.guest_title), mActivity.getString(R.string.guest_msg));
                        dialog.show();
                    }
                } else {
                    //Toast.makeText(mActivity, mActivity.getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                    customToast.showErrorToast(mActivity.getResources().getString(R.string.internet_error_msg));
                }
                break;
        }
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
