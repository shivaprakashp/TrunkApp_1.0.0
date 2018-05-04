package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.database.events.EventDetailsDB;
import com.opera.app.listadapters.AdapterOfSearchedEvents;
import com.opera.app.pojo.events.events;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 1000632 on 5/3/2018.
 */

public class SearchEventActivity extends BaseActivity {

    private String SearchedData = "";
    private AdapterOfSearchedEvents adapterSearchedEvents;
    private ArrayList<events> mEventListingData = new ArrayList<>();
    private EventDetailsDB mEventDetailsDB;
    private Activity mActivity;

    @BindView(R.id.edtSearchedData)
    EditText mEdtSearchedData;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerList;

    @BindView(R.id.txtSearch)
    TextView mTxtSearch;

    @BindView(R.id.toolbar_search)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        mActivity = SearchEventActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_search_event);

        initToolbar();
        initView();
    }


    private void initView() {
        Intent in = getIntent();
        SearchedData = in.getStringExtra("SearchedData");

        mEventDetailsDB = new EventDetailsDB(mActivity);
        mEventDetailsDB.open();
        mEventListingData = mEventDetailsDB.fetchAllEvents();
        mEventDetailsDB.close();

        adapterSearchedEvents = new AdapterOfSearchedEvents(mActivity, mEventListingData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerList.setLayoutManager(mLayoutManager);
        mRecyclerList.setItemAnimator(new DefaultItemAnimator());
        mRecyclerList.setAdapter(adapterSearchedEvents);

        mEdtSearchedData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equalsIgnoreCase("")) {
                    filter(s.toString());
                    mTxtSearch.setVisibility(View.VISIBLE);
                } else {
                    mTxtSearch.setVisibility(View.GONE);
                    adapterSearchedEvents.filterList(mEventListingData);
                }
            }
        });
        mEdtSearchedData.setText(SearchedData);
    }

    private void filter(String mSearchedTxt) {
        ArrayList<events> mFilteredNames = new ArrayList<>();

        for (int i = 0; i < mEventListingData.size(); i++) {
            if (mEventListingData.get(i).getEventTitle().toLowerCase().contains(mSearchedTxt.toLowerCase())) {
                mFilteredNames.addAll(mEventListingData);
            }
        }
        mTxtSearch.setText("You searched for " + mSearchedTxt + " ,found " + mFilteredNames.size() + " results");
        adapterSearchedEvents.filterList(mFilteredNames);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void initToolbar() {
        setSupportActionBar(toolbar);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.search));
    }
}
