package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.restaurants.SeanRestOpeation;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.restaurant.RestaurantListing;
import com.opera.app.pojo.restaurant.RestaurantsData;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 5/24/2018.
 */

public class RestaurantCompleteDetails extends BaseActivity {

    //    private RestaurantsData mRestaurantListingData;
    private Activity mActivity;
    private SeanRestOpeation restOpeation;
    private String RestaurantIdSiteCore = "";
    private String RestaurantId = "";
    private String mRestaurantEmail = "";
    private String mRestaurantPhone = "";
    private String mRestaurantLatitude = "";
    private String mRestaurantLongitude = "";
    private String mRestaurantName = "";

    private Api api;
    @Inject
    Retrofit retrofit;

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

    @BindView(R.id.mTxtRestaurantNumber)
    TextView mTxtRestaurantNumber;

    @BindView(R.id.mTxtRestaurantEmail)
    TextView mTxtRestaurantEmail;

    @BindView(R.id.mLinRestaurantNumber)
    LinearLayout mLinRestaurantNumber;

    @BindView(R.id.mLinRestaurantEmail)
    LinearLayout mLinRestaurantEmail;

    @BindView(R.id.mTxtRestaurantOpeningHours)
    TextView mTxtRestaurantOpeningHours;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = RestaurantCompleteDetails.this;
        //For Language activity_setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_restaurant_details);

        initToolbar();
        initView();
        GetSpecificRestaurantDetails();

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initView() {
        ((MainApplication) mActivity.getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);
        restOpeation = new SeanRestOpeation(mActivity);

//        mRestaurantListingData = (RestaurantsData) getIntent().getSerializableExtra(AppConstants.GETRESTAURANTLISTING.GETRESTAURANTLISTING);
        Intent in = getIntent();
        RestaurantIdSiteCore = in.getStringExtra("RestaurantIdSiteCore");
        RestaurantId = in.getStringExtra("RestaurantId");
        String from = in.getStringExtra("from");

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        toolbar.setVisibility(View.VISIBLE);
        TextViewWithFont txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getResources().getString(R.string.dining));

        customToast = new CustomToast(mActivity);

       /* mTxtRestaurantNumber.setText(Html.fromHtml("<u>"+ mRestaurantListingData.getPhoneNumber()+"</u>"));
        mTxtRestaurantEmail.setText(Html.fromHtml("<u>"+ mRestaurantListingData.getEmail()+"</u>"));*/

        mBtnOtherRestaurants.setVisibility(View.GONE);

        /*mTxtRestaurantPlace.setText("at " + mRestaurantListingData.getRestPlace());
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
                });*/
    }

    private void GetSpecificRestaurantDetails() {
        MainController controller = new MainController(mActivity);

//        if(from!=null && !from.equalsIgnoreCase("Restaurant")){
        controller.getSpecificRestaurantBySiteCoreId(taskComplete, api, RestaurantIdSiteCore);
        /*}else{
            controller.getSpecificRestaurant(taskComplete, api, RestaurantId);
        }*/
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            RestaurantListing mRestaurantPojo = (RestaurantListing) response.body();

            if (mRestaurantPojo != null && mRestaurantPojo.getStatus().equalsIgnoreCase(AppConstants.STATUS_SUCCESS)) {
                restOpeation.open();
                restOpeation.removeSeanConnolly(RestaurantId);
                restOpeation.addSeanConnollyData(mRestaurantPojo.getData().get(0));
                getSeanConnollyData();
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("Error", call.toString());
            restOpeation.open();
            getSeanConnollyData();
        }
    };

    @OnClick({R.id.mBtnLocationMap, R.id.mLinRestaurantNumber, R.id.mLinRestaurantEmail})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtnLocationMap:

                String uriBegin = "geo:" + mRestaurantLatitude + "," + mRestaurantLongitude;
                String query = mRestaurantLatitude + "," + mRestaurantLongitude + "(" + mRestaurantName + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);

                break;

            case R.id.mLinRestaurantNumber:

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mRestaurantPhone));
                mActivity.startActivity(intent);

                break;

            case R.id.mLinRestaurantEmail:

                String[] TO = {mRestaurantEmail};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                //emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Queries");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Please send more details about the restaurant.");
                try {
                    mActivity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }

                break;
        }
    }

    private void getSeanConnollyData() {
        setRestaurant(restOpeation.getSeanConnolly(RestaurantIdSiteCore));
        restOpeation.close();
    }

    private void setRestaurant(RestaurantsData data) {
        //mLinearReadMore.setVisibility(View.VISIBLE);
        try {
            if (data.getPhoneNumber() != null) {
                mTxtRestaurantNumber.setText(Html.fromHtml("<u>" + data.getPhoneNumber() + "</u>"));
            }

            if (data.getEmail() != null) {
                mTxtRestaurantEmail.setText(Html.fromHtml("<u>" + data.getEmail() + "</u>"));
            }

            if (data.getRestPlace() != null) {
                mTxtRestaurantPlace.setText(new StringBuilder().append("at ").append(data.getRestLocation()).toString());
            }

            if (data.getRestPlace() != null) {
                mTxtRestaurantOpeningHours.setText(data.getOpenHour());
            }
            mRestaurantName = data.getRestName();
            mRestaurantEmail = data.getEmail();
            mRestaurantPhone = data.getPhoneNumber();
            mRestaurantLatitude = data.getRestLatitude();
            mRestaurantLongitude = data.getRestLongitude();

            mTxtRestaurantName.setText(data.getRestName());
            mExpandableTextView.setText(data.getRestDetails());
            Picasso.with(mActivity).load(data.getRestImage()).fit().centerCrop()
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

            //Calling Google analytics
            OperaUtils.SendGoogleAnalyticsEvent("Dining - Other Restaurants - " + mRestaurantName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
