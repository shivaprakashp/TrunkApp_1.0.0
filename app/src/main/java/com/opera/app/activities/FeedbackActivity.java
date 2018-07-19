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
import com.opera.app.dagger.Api;
import com.opera.app.database.orders.OrderHistoryDB;
import com.opera.app.listadapters.FeedbackAdapter;
import com.opera.app.pojo.favouriteandsettings.OrderHistory;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 6/4/2018.
 */

public class FeedbackActivity extends BaseActivity {

    private Activity mActivity;

    @BindView(R.id.toolbarRecycler)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerFeedback;

    @Inject
    Retrofit retrofit;
    private Api api;
    TextViewWithFont txtToolbarName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        mActivity = FeedbackActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.common_recycler);

        initView();
        initToolbar();
        getFeedback();
    }

    private void initView() {
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_feedback));

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

    private void getFeedback() {

        OrderHistoryDB mOrderHistoryDB = new OrderHistoryDB(mActivity);
        mOrderHistoryDB.open();
        ArrayList<OrderHistory> arrFeedbackListing = mOrderHistoryDB.orderHistories();
        mOrderHistoryDB.close();

        FeedbackAdapter mAdapter = new FeedbackAdapter(mActivity, arrFeedbackListing);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerFeedback.setLayoutManager(mLayoutManager);
        mRecyclerFeedback.setItemAnimator(new DefaultItemAnimator());
        mRecyclerFeedback.setAdapter(mAdapter);

    }
}
