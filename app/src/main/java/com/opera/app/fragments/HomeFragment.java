package com.opera.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.activities.MainActivity;
import com.opera.app.activities.PreLoginActivity;
import com.opera.app.activities.SearchEventActivity;
import com.opera.app.controller.MainController;
import com.opera.app.dagger.Api;
import com.opera.app.database.events.EventGenresDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.CoverFlowAdapter;
import com.opera.app.listadapters.WhatsOnPagerAdapter;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.events.eventlisiting.AllEvents;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends BaseFragment {

    private Activity mActivity;
    private CoverFlowAdapter mAdapter;
    private EventListingDB mEventListingDB;
    private EventGenresDB mEventGenresDB;
    private ArrayList<Events> mHighlightedEvents = new ArrayList<>();
    private ArrayList<Events> mWhatsEvents = new ArrayList<>();
    private SessionManager manager;

    private WhatsOnPagerAdapter mWhatsOnPagerAdapter;
    //    private AdapterEvent mAdapterEvent;
    private ArrayList<Events> mEventAllData = new ArrayList<>();
    private Api api;
    @Inject
    Retrofit retrofit;

    /*@BindView(R.id.recyclerList)
    RecyclerView mRecyclerEvents;*/

    @BindView(R.id.viewpagerWhatsOnShows)
    ViewPager mViewpagerWhatsOnShows;

    @BindView(R.id.coverflow)
    FeatureCoverFlow mCoverFlow;

    @BindView(R.id.btnSearch)
    Button mBtnSearch;

    @BindView(R.id.edtSearch)
    EditText mEdtSearch;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        InitView(view);
        GetCurrentEvents();
        return view;
    }

    private void GetCurrentEvents() {
        MainController controller = new MainController(mActivity);
        controller.getEventListing(taskComplete, api);
    }

    private void InitView(View view) {

        ButterKnife.bind(this, view);
        ((MainApplication) getActivity().getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);
        manager = new SessionManager(mActivity);

        mEventListingDB = new EventListingDB(mActivity);
        mEventGenresDB = new EventGenresDB(mActivity);
        //Highlighted events
        mAdapter = new CoverFlowAdapter(getActivity(), mHighlightedEvents);
        /*mCoverFlow.setAdapter(mAdapter);*/
        mViewpagerWhatsOnShows.setClipToPadding(false);
        mViewpagerWhatsOnShows.setPageMargin(20);
        //What's on events
        mWhatsOnPagerAdapter = new WhatsOnPagerAdapter(mActivity, mWhatsEvents,"HomePage");
        mViewpagerWhatsOnShows.setAdapter(mWhatsOnPagerAdapter);

        mViewpagerWhatsOnShows.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("onPageScrolled", position + "");
                if (position == 0) {
                    mViewpagerWhatsOnShows.setPadding(0, 0, 70, 0);
                } else if (mWhatsEvents.size() - 1 == position) {
                    mViewpagerWhatsOnShows.setPadding(70, 0, 0, 0);
                } else {
                    mViewpagerWhatsOnShows.setPadding(70, 0, 70, 0);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            AllEvents mEventDataPojo = (AllEvents) response.body();

            try {
                if (mEventDataPojo.getStatus().equalsIgnoreCase("success")) {
                    mEventListingDB.open();

                    //Calling this function for guest user to take his favourite events
                    if (!manager.isUserLoggedIn()) {
                        mEventAllData = new ArrayList<>();
                        mEventAllData = mEventListingDB.fetchEventsWithFavouriteForGuest();
                    }

                    mEventListingDB.deleteCompleteTable(EventListingDB.TABLE_EVENT_LISTING);
                    mEventListingDB.insertOtherEvents(mActivity, mEventDataPojo.getEvents(), mEventAllData);

                    mEventGenresDB.open();
                    mEventGenresDB.deleteCompleteTable(EventGenresDB.TABLE_GENRES_LISTING);
                    mEventGenresDB.insertEventsGenres(mEventDataPojo.getGenreList());

                    fetchDataFromDB();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("data", "error");
            try {
                mEventListingDB.open();
                fetchDataFromDB();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void fetchDataFromDB() {
        GetWhatsOnEvents();
        GetHighlightedEvents();
        mEventListingDB.close();
    }

    private void GetWhatsOnEvents() {
        mEventListingDB.open();
        mEventAllData = new ArrayList<>();
        mWhatsEvents = new ArrayList<>();
        mEventAllData = mEventListingDB.fetchAllEvents();
        for (int i = 0; i < mEventAllData.size(); i++) {

            //            if (mEventAllData.get(i).getEventIsWhatsOn().equalsIgnoreCase("true")) {
            mWhatsEvents.add(new Events(mEventAllData.get(i).getEventId(), mEventAllData.get(i).getName(), mEventAllData.get(i).getImage(), mEventAllData.get(i).getInternalName(), mEventAllData.get(i).getFrom(), mEventAllData.get(i).getTo(), mEventAllData.get(i).getDescription(), mEventAllData.get(i).isFavourite()));
//            }
        }

        if (mEventAllData.size() > 0) {
            mWhatsOnPagerAdapter.RefreshList(mWhatsEvents);
        }
    }

    private void GetHighlightedEvents() {
        mEventListingDB.open();
        mEventAllData = new ArrayList<>();
        mEventAllData = mEventListingDB.fetchAllEvents();
        for (int i = 0; i < mEventAllData.size(); i++) {
            if (mEventAllData.get(i).getHighlighted().equalsIgnoreCase("true")) {
                mHighlightedEvents.add(new Events(mEventAllData.get(i).getImage(), mEventAllData.get(i).getInternalName(), mEventAllData.get(i).getEventId(), mEventAllData.get(i).isFavourite()));
            }
        }
        if (mEventAllData.size() > 0) {
            mAdapter.notifyDataSetChanged();
            mCoverFlow.setAdapter(mAdapter);
            mCoverFlow.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.btnSearch})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                Intent in = new Intent(mActivity, SearchEventActivity.class);
                in.putExtra("SearchedData", mEdtSearch.getText().toString().trim());
                startActivity(in);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        GetWhatsOnEvents();
    }
}
