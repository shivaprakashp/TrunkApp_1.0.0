package com.opera.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.listadapters.RestaurantAdapter;
import com.opera.app.pojo.restaurant.RestaurantListing;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import butterknife.BindView;

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

    private ArrayList<RestaurantListing> mRestaurantListing = new ArrayList<>();
    private RestaurantAdapter mAdapter;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = OtherRestaurantsActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);

        setContentView(R.layout.common_recycler);

        initToolbar();
        initViews();

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
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.restaurants));

        mAdapter = new RestaurantAdapter(mRestaurantListing);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerRestaurants.setLayoutManager(mLayoutManager);
        mRecyclerRestaurants.setItemAnimator(new DefaultItemAnimator());
        mRecyclerRestaurants.setAdapter(mAdapter);

        GetAllRestaurants();
    }

    private void GetAllRestaurants() {
        RestaurantListing mListRestaurant = new RestaurantListing("", "Armani/Hashi", "Armani Hotel Dubai");
        mRestaurantListing.add(mListRestaurant);

        mListRestaurant = new RestaurantListing("", "Armani/Amal", "Dubai Opera");
        mRestaurantListing.add(mListRestaurant);
        mAdapter.notifyDataSetChanged();
    }
}
