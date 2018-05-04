package com.opera.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.app.R;
import com.opera.app.database.events.EventDetailsDB;
import com.opera.app.listadapters.AdapterEvent;
import com.opera.app.utils.LanguageManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllEventsFragment extends BaseFragment {

    private Activity mActivity;
    private EventDetailsDB mEventDetailsDB;

    private AdapterEvent mAdapterEvent;
    //private ArrayList<events> mEventListingData = new ArrayList<>();

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerEvents;
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
        View view = inflater.inflate(R.layout.fragment_all_events, container, false);


        InitView(view);
        return view;
    }

    private void InitView(View view) {
        ButterKnife.bind(this, view);

        mEventDetailsDB = new EventDetailsDB(mActivity);

        /*ArrayList<events> mEventListingData = mEventDetailsDB.fetchAllEvents();

        mEventDetailsDB.close();
        mAdapterEvent.notifyDataSetChanged();

        mAdapterEvent = new AdapterEvent(mActivity, mEventListingData);*/
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerEvents.setLayoutManager(mLayoutManager);
        mRecyclerEvents.setItemAnimator(new DefaultItemAnimator());
        mRecyclerEvents.setAdapter(mAdapterEvent);
    }
}
