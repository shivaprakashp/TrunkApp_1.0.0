package com.opera.app.fragments;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.activities.CommonWebViewActivity;
import com.opera.app.activities.OtherRestaurantsActivity;
import com.opera.app.activities.ReserveATableActivity;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.customwidget.ExpandableTextView;
import com.opera.app.dagger.Api;
import com.opera.app.database.restaurants.SeanRestOpeation;
import com.opera.app.dialogues.GuestDialog;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.restaurant.RestaurantListing;
import com.opera.app.pojo.restaurant.RestaurantsData;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;
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
    private SessionManager manager;
    private CustomToast customToast;

    @Inject
    Retrofit retrofit;

    @BindView(R.id.linearReadMore)
    LinearLayout mLinearReadMore;

    @BindView(R.id.txtShowmore)
    TextView txtShowmore;

    @BindView(R.id.ivShowmore)
    ImageView ivShowmore;

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

    private SeanRestOpeation restOpeation;
    private RestaurantsData data = null;

    public static DiningFragment newDiningFragment(RestaurantsData data){
        DiningFragment fragment = new DiningFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.GETRESTAURANTLISTING.GETRESTAURANTLISTING, data);
        fragment.setArguments(bundle);

        return fragment;
    }
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

        data = (RestaurantsData)getArguments().getSerializable(AppConstants.GETRESTAURANTLISTING.GETRESTAURANTLISTING);
        if (data!=null){
            setRestaurant(data);
        }else{
            GetSeanConollyDetails();
        }

        return view;
    }

    private void initData(View view) {
        ButterKnife.bind(this, view);
        ((MainApplication) mActivity.getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);
        manager = new SessionManager(mActivity);
        customToast = new CustomToast(mActivity);
        restOpeation = new SeanRestOpeation(mActivity);
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
                    ivShowmore.setScaleY(1);
                } else {
                    mExpandableTextView.expand();
                    txtShowmore.setText(R.string.read_less);
                    ivShowmore.setScaleY(-1);
                }
                break;
            case R.id.btnOtherRestaurants:
                openActivity(mActivity, OtherRestaurantsActivity.class);
                break;
            case R.id.mBtnReserveATable:
                if (Connections.isConnectionAlive(mActivity)) {
                    if (data.getRestId().equalsIgnoreCase(AppConstants.SEAN_CONOLLY_RESTAURANT_ID)){
                        openActivity(mActivity, ReserveATableActivity.class);
                    }else{
                        Intent in = new Intent(mActivity, CommonWebViewActivity.class);
                        in.putExtra("URL", data.getRestBookUrl());
                        in.putExtra("Header", data.getRestName());
                        mActivity.startActivity(in);
                    }
                } else {
                    Toast.makeText(mActivity, mActivity.getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            RestaurantListing mRestaurantPojo = (RestaurantListing) response.body();

            if (mRestaurantPojo.getStatus().equalsIgnoreCase("success")) {
                restOpeation.open();
                restOpeation.removeSeanConnolly();
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

    private void getSeanConnollyData(){
        setRestaurant(restOpeation.getSeanConnolly());
        restOpeation.close();

    }

    private void setRestaurant(RestaurantsData data){
        mLinearReadMore.setVisibility(View.VISIBLE);
        try {
            mTxtRestaurantName.setText(data.getRestName());
            mTxtRestaurantPlace.setText("at " + data.getRestPlace());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
