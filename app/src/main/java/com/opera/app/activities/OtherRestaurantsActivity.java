package com.opera.app.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.DBManager;
import com.opera.app.database.DatabaseHelper;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.listadapters.RestaurantAdapter;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.restaurant.RestaurantListing;
import com.opera.app.pojo.restaurant.restaurantsData;
import com.opera.app.pojo.settings.GetSettingsPojo;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 4/9/2018.
 */

public class OtherRestaurantsActivity extends BaseActivity {

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerRestaurants;

    @BindView(R.id.toolbarRecycler)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    private ArrayList<restaurantsData> mRestaurantListing = new ArrayList<>();
    private RestaurantAdapter mAdapter;
    private Activity mActivity;
    private Api api;
    private DBManager dbManager;
    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = OtherRestaurantsActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);

        setContentView(R.layout.common_recycler);

        initToolbar();
        initViews();
        if (Connections.isConnectionAlive(mActivity)) {
            GetRestauarantDetails();
        } else {
            FetchDataFromDB();
        }

    }

    private void GetRestauarantDetails() {
        MainController controller = new MainController(mActivity);
        controller.getRestaurantListing(taskComplete, api);
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

    private void initViews() {
        ((MainApplication) getApplication()).getNetComponent().inject(OtherRestaurantsActivity.this);
        api = retrofit.create(Api.class);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.restaurants));

        mAdapter = new RestaurantAdapter(mActivity, mRestaurantListing);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerRestaurants.setLayoutManager(mLayoutManager);
        mRecyclerRestaurants.setItemAnimator(new DefaultItemAnimator());
        mRecyclerRestaurants.setAdapter(mAdapter);

        dbManager = new DBManager(this);
        dbManager.open();
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            RestaurantListing mRestaurantPojo = (RestaurantListing) response.body();
            mRestaurantListing.addAll(mRestaurantPojo.getData());
            mAdapter.notifyDataSetChanged();

            dbManager.deleteCompleteTable(DatabaseHelper.TABLE_OTHER_RESTAURANTS);
            dbManager.insertOtherRestaurants(mRestaurantListing);
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            FetchDataFromDB();
        }
    };

    private void FetchDataFromDB() {
        mRestaurantListing = new ArrayList<>();
        Cursor cursor = dbManager.fetchOtherRestaurantDetails();
        if (cursor.moveToFirst()) {
            do {
                restaurantsData mRestaurantData = new restaurantsData();

                mRestaurantData.setRestId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_ID)));
                mRestaurantData.setRestName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_NAME)));
                mRestaurantData.setRestPlace(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_PLACE)));
                mRestaurantData.setRestDetails(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_DETAILS)));
                mRestaurantData.setRestImage(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_IMAGE_URL)));
                mRestaurantData.setRestBookUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_BOOKING_URL)));
                mRestaurantListing.add(mRestaurantData);
            } while (cursor.moveToNext());
        }
        mAdapter.RefreshList(mRestaurantListing);
        mAdapter.notifyDataSetChanged();
    }
}
