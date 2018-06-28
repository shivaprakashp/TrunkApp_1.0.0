package com.opera.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.AdapterEvent;
import com.opera.app.listener.EventInterfaceTab;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesEventsFragment extends BaseFragment implements EventInterfaceTab {

    private Activity mActivity;
    private EventListingDB mEventDetailsDB;
    private ArrayList<Events> mEventListingData = new ArrayList<>();
    private AdapterEvent mAdapterEvent;
    ArrayList<Events> mFilteredEvents = null;
    private EventInterfaceTab listenerFavourite;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerEvents;

    @BindView(R.id.tv_msg)
    TextView mtvMsg;


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
        View view = inflater.inflate(R.layout.fragment_events_tab, container, false);

        InitView(view);
        return view;
    }

    private void InitView(View view) {
        ButterKnife.bind(this, view);
        listenerFavourite=(FavouritesEventsFragment)this;
        mEventDetailsDB = new EventListingDB(mActivity);

    }

    private void FetchFavouriteEvents() {
        mEventDetailsDB.open();
        mEventListingData=new ArrayList<>();
        mEventListingData = mEventDetailsDB.fetchAllEvents();
        mEventDetailsDB.close();
        mFilteredEvents = new ArrayList<>();
        for (int i = 0; i < mEventListingData.size(); i++) {
            if (mEventListingData.get(i).isFavourite().equalsIgnoreCase("true")) {
                mFilteredEvents.add(new Events(mEventListingData.get(i).getEventId(),mEventListingData.get(i).getName(), mEventListingData.get(i).getImage(), mEventListingData.get(i).getInternalName(), mEventListingData.get(i).getFrom(), mEventListingData.get(i).getTo(), mEventListingData.get(i).getMobileDescription(), mEventListingData.get(i).isFavourite(), mEventListingData.get(i).getEventUrl(), mEventListingData.get(i).getGenreList(), mEventListingData.get(i).getBuyNowLink(), mEventListingData.get(i).getSharedContentText(), mEventListingData.get(i).getWhatsOnImage(), mEventListingData.get(i).getHighlightedImage()));
            }
        }
        mAdapterEvent = new AdapterEvent(mActivity, mFilteredEvents,listenerFavourite);
        if (mFilteredEvents.size() > 0) {
            mRecyclerEvents.setVisibility(View.VISIBLE);
            mtvMsg.setVisibility(View.GONE);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerEvents.setLayoutManager(mLayoutManager);
            mRecyclerEvents.setItemAnimator(new DefaultItemAnimator());
            mRecyclerEvents.setAdapter(mAdapterEvent);
        } else {
            mRecyclerEvents.setVisibility(View.GONE);
            mtvMsg.setVisibility(View.VISIBLE);
            mtvMsg.setText(getResources().getString(R.string.no_favo_data));
        }
    }

    @Override
    public void onLikeProcessComplete() {
        FetchFavouriteEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
        FetchFavouriteEvents();
    }
}
