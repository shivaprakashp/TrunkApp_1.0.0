package com.opera.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.activities.EditProfileActivity;
import com.opera.app.activities.MainActivity;
import com.opera.app.activities.OtherRestaurantsActivity;
import com.opera.app.activities.ReserveATableActivity;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.ExpandableTextView;
import com.opera.app.dagger.Api;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.restaurant.RestaurantListing;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DiningFragment extends BaseFragment {

    private Activity mActivity;
    private Intent in;
    private Api api;
    @Inject
    Retrofit retrofit;

    @BindView(R.id.linearReadMore)
    LinearLayout mLinearReadMore;

    @BindView(R.id.txtShowmore)
    TextView txtShowmore;

    @BindView(R.id.expandableTextView)
    ExpandableTextView mExpandableTextView;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mActivity = getActivity();
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        View view = inflater.inflate(R.layout.fragment_dining2, container, false);

        initData(view);

        GetSeanConollyDetails();

        return view;
    }

    private void initData(View view) {

        ButterKnife.bind(this, view);
        ((MainApplication) mActivity.getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);
    }

    private void GetSeanConollyDetails() {
        MainController controller = new MainController(mActivity);
        controller.getSpecificRestaurant(taskComplete, api);
    }

    @OnClick({R.id.linearReadMore, R.id.btnOtherRestaurants, R.id.mBtnReserveATable})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearReadMore:
                if (mExpandableTextView.isExpanded()) {
                    mExpandableTextView.collapse();
                    txtShowmore.setText(R.string.read_more);
                } else {
                    mExpandableTextView.expand();
                    txtShowmore.setText(R.string.read_less);
                }
                break;
            case R.id.btnOtherRestaurants:
                openActivity(mActivity, OtherRestaurantsActivity.class);
                break;
            case R.id.mBtnReserveATable:
                openActivity(mActivity, ReserveATableActivity.class);
                break;


        }
    }


    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            RestaurantListing mRestaurantPojo = (RestaurantListing) response.body();

            if (mRestaurantPojo.getStatus().equalsIgnoreCase("success")) {
                mLinearReadMore.setVisibility(View.VISIBLE);
                try {
                    mTxtRestaurantName.setText(mRestaurantPojo.getData().get(0).getRestName());
                    mTxtRestaurantPlace.setText("at " + mRestaurantPojo.getData().get(0).getRestPlace());
                    mExpandableTextView.setText("at " + mRestaurantPojo.getData().get(0).getRestDetails());

                    Picasso.with(mActivity).load(mRestaurantPojo.getData().get(0).getRestImage()).fit().centerCrop()
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("Error", call.toString());
        }
    };


}
