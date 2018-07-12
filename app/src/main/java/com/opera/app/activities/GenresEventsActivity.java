package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.database.events.EventGenresDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.AdapterEvent;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class GenresEventsActivity extends BaseActivity {

    private Activity mActivity;
    private String SelectedGenre = "";
    private EventGenresDB mEventGenreDB;
    private ArrayList<GenreList> mGenreListingData = new ArrayList<>();

    @BindView(R.id.toolbar_genres)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerGenre;

    @BindView(R.id.tv_msg)
    TextView mtvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        mActivity = GenresEventsActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_genres);

        InitView();
        initToolbar();
    }

    private void InitView() {
        try {
            Intent in = getIntent();
            SelectedGenre = in.getStringExtra("SelectedGenre");
            ApplyGenreSearch(SelectedGenre);

            //Calling Google analytics
            OperaUtils.SendGoogleAnalyticsEvent("Genre - "+SelectedGenre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        TextViewWithFont txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(SelectedGenre);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
    }
    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void ApplyGenreSearch(String mSearchedTxt) {
        ArrayList<Events> mFilteredNames = new ArrayList<>();

        EventListingDB mEventDetailsDB = new EventListingDB(mActivity);
        mEventDetailsDB.open();
        ArrayList<Events> mEventListingData = mEventDetailsDB.fetchAllEvents();
        mEventDetailsDB.close();

        for (int i = 0; i < mEventListingData.size(); i++) {
            for (int j = 0; j < mEventListingData.get(i).getGenreList().size(); j++) {
                if (mEventListingData.get(i).getGenreList().get(j).getGenere().toLowerCase().contains(mSearchedTxt.toLowerCase())) {
                    mFilteredNames.add(new Events(mEventListingData.get(i).getEventId(), mEventListingData.get(i).getName(), mEventListingData.get(i).getImage(), mEventListingData.get(i).getInternalName(), mEventListingData.get(i).getFrom(), mEventListingData.get(i).getTo(), mEventListingData.get(i).getMobileDescription(), mEventListingData.get(i).isFavourite(), mEventListingData.get(i).getEventUrl(), mEventListingData.get(i).getGenreList(), mEventListingData.get(i).getBuyNowLink(), mEventListingData.get(i).getSharedContentText(), mEventListingData.get(i).getWhatsOnImage(), mEventListingData.get(i).getHighlightedImage()));
                }
            }
        }
        AdapterEvent mAdapterEvent = new AdapterEvent(mActivity, mFilteredNames);
        if (mFilteredNames.size() > 0) {
            mRecyclerGenre.setVisibility(View.VISIBLE);
            mtvMsg.setVisibility(View.GONE);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
            mRecyclerGenre.setLayoutManager(mLayoutManager);
            mRecyclerGenre.setItemAnimator(new DefaultItemAnimator());
            mRecyclerGenre.setAdapter(mAdapterEvent);
        }
        else {
            mRecyclerGenre.setVisibility(View.GONE);
            mtvMsg.setVisibility(View.VISIBLE);
            mtvMsg.setText(getResources().getString(R.string.no_data));
        }
    }
}
