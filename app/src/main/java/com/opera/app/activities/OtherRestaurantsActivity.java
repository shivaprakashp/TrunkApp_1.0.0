package com.opera.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.restaurants.DatabaseHelper;
import com.opera.app.listadapters.RestaurantAdapter;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.restaurant.RestaurantListing;
import com.opera.app.pojo.restaurant.RestaurantsData;
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

    @BindView(R.id.imgSearch)
    View imgSearch;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.linearSearch)
    View viewSearch;

    @BindView(R.id.edtSearch)
    EditTextWithFont edtSearch;

    @BindView(R.id.btnSearch)
    View btnSearch;

    private ArrayList<RestaurantsData> mRestaurantListing = new ArrayList<>();
    private RestaurantAdapter mAdapter;
    private Activity mActivity;
    private Api api;
    private DatabaseHelper dbManager;
    @Inject
    Retrofit retrofit;

   /* public static OtherRestaurantsActivity getInstance() {
        return (OtherRestaurantsActivity) mActivity;
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = OtherRestaurantsActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);

        setContentView(R.layout.common_recycler);

        initToolbar();
        initViews();
        GetRestauarantDetails();

    }

    private void GetRestauarantDetails() {
        if (Connections.isConnectionAlive(mActivity)) {
            MainController controller = new MainController(mActivity);
            controller.getRestaurantListing(taskComplete, api);
        } else {
            customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
            try {
                fetchDataFromDB();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    private View.OnClickListener layoutSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewSearch.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener searchList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mAdapter.getFilter().filter(edtSearch.getText().toString());
        }
    };

    private void initViews() {
        ((MainApplication) getApplication()).getNetComponent().inject(OtherRestaurantsActivity.this);
        api = retrofit.create(Api.class);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        imgSearch.findViewById(R.id.imgSearch).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
        imgSearch.findViewById(R.id.imgSearch).setOnClickListener(layoutSearch);
        btnSearch.findViewById(R.id.btnSearch).setOnClickListener(searchList);
        edtSearch.setHint(R.string.rest_search);

        TextViewWithFont txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.restaurants));

        mAdapter = new RestaurantAdapter(mActivity, mRestaurantListing);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerRestaurants.setLayoutManager(mLayoutManager);
        mRecyclerRestaurants.setItemAnimator(new DefaultItemAnimator());
        mRecyclerRestaurants.setAdapter(mAdapter);

        dbManager = new DatabaseHelper(mActivity);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            RestaurantListing mRestaurantPojo = (RestaurantListing) response.body();
            if (response.body() != null) {
                try {

                    if (mRestaurantPojo.getStatus().equalsIgnoreCase(AppConstants.STATUS_SUCCESS)) {
                        dbManager.open();
                        dbManager.deleteCompleteTable(DatabaseHelper.TABLE_OTHER_RESTAURANTS);
                        dbManager.insertOtherRestaurants(mRestaurantPojo.getData());
                        fetchDataFromDB();
                    }
                } catch (Exception e) {
                    Log.e("Message", e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            dbManager.open();
            fetchDataFromDB();
        }
    };

    private void fetchDataFromDB() {
        mAdapter.RefreshList(dbManager.fetchOtherRestaurantDetails());
        dbManager.close();
        mAdapter.notifyDataSetChanged();
    }

    public void diningDetails(RestaurantsData data) {
        Intent intent = new Intent(mActivity, MainActivity.class);
        intent.putExtra(AppConstants.GETRESTAURANTLISTING.GETRESTAURANTLISTING, data);
        startActivity(intent);
        mActivity.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mActivity != null) {
            mActivity = null;
        }
    }
}
