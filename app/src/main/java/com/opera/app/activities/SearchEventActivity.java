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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.AdapterOfSearchedEvents;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 1000632 on 5/3/2018.
 */

public class SearchEventActivity extends BaseActivity {

    private String SearchedData = "";
    private AdapterOfSearchedEvents adapterSearchedEvents;
    private ArrayList<Events> mEventListingData = new ArrayList<>();
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

    @BindView(R.id.btnSearch)
    Button mBtnSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        mActivity = SearchEventActivity.this;
        //For Language activity_setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_search_event);

        initToolbar();
        initView();

        OperaUtils.SendGoogleAnalyticsEvent(getResources().getString(R.string.analytics_search_event));
    }


    private void initView() {
        try {
            Intent in = getIntent();
            SearchedData = in.getStringExtra("SearchedData");
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventListingDB mEventDetailsDB = new EventListingDB(mActivity);
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
                ApplyFilter(s.toString());
            }
        });
        mEdtSearchedData.setText(SearchedData);
    }

    private void ApplyFilter(String s) {
        if (!s.equalsIgnoreCase("")) {
            filter(s);
            mTxtSearch.setVisibility(View.VISIBLE);
        } else {
            mTxtSearch.setVisibility(View.GONE);
            adapterSearchedEvents.filterList(mEventListingData);
        }
    }

    private void filter(String mSearchedTxt) {
        ArrayList<Events> mFilteredNames = new ArrayList<>();
//        String mEventStartDate = "", mEventEndDate = "";

        for (int i = 0; i < mEventListingData.size(); i++) {
            if (mEventListingData.get(i).getName().toLowerCase().contains(mSearchedTxt.toLowerCase())) {

              /*  for (int j = 0; j < mEventListingData.get(i).getEventDates().size(); j++) {
                    if (j == 0) {
                        mEventStartDate = mEventListingData.get(i).getEventDates().get(j).getEventDate();
                    }

                    if (j == mEventListingData.get(i).getEventDates().size() - 1) {
                        mEventEndDate = mEventListingData.get(i).getEventDates().get(j).getEventDate();
                    }
                }*/

                mFilteredNames.add(new Events(mEventListingData.get(i).getEventId(), mEventListingData.get(i).getName(), mEventListingData.get(i).getImage(), mEventListingData.get(i).getInternalName(), mEventListingData.get(i).getFrom(), mEventListingData.get(i).getTo(), mEventListingData.get(i).getMobileDescription(), mEventListingData.get(i).isFavourite(), mEventListingData.get(i).getEventUrl(), mEventListingData.get(i).getGenreList(), mEventListingData.get(i).getBuyNowLink(), mEventListingData.get(i).getSharedContentText(), mEventListingData.get(i).getWhatsOnImage(), mEventListingData.get(i).getHighlightedImage(),"",""));
            }
        }

        mTxtSearch.setText(new StringBuilder().append("You searched for ").append(mSearchedTxt).append(" ,found ").append(mFilteredNames.size()).append(" results").toString());
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

        TextViewWithFont txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.search));
    }

    @OnClick({R.id.btnSearch})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                ApplyFilter(mEdtSearchedData.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        OperaUtils.closeSoftKeyboard(mActivity);
    }
}
