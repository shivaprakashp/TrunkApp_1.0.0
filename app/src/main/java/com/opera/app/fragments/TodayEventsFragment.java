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
import com.opera.app.utils.OperaUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayEventsFragment extends BaseFragment implements EventInterfaceTab {

    private Activity mActivity;
    private EventListingDB mEventDetailsDB;
    private ArrayList<Events> mEventListingData = new ArrayList<>();
    private AdapterEvent mAdapterEvent;
    private EventInterfaceTab listenerToday;
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
        listenerToday=(TodayEventsFragment)this;

        mEventDetailsDB = new EventListingDB(mActivity);
        mEventDetailsDB.open();
        mEventListingData = mEventDetailsDB.fetchAllEvents();
        mEventDetailsDB.close();

        FetchTodayEvents(OperaUtils.getCurrentDate());
    }

    private void FetchTodayEvents(String ToDay) {
        ArrayList<Events> mFilteredEvents = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(ToDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy");
        String finalDate = timeFormat.format(myDate);

        for (int i = 0; i < mEventListingData.size(); i++) {

                String Fromdate = mEventListingData.get(i).getFrom();
                String ToDate = mEventListingData.get(i).getTo();
                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                Date date = null;
                Date fromdate = null;
                Date todate = null;
                try {
                    date = format.parse(finalDate);
                    fromdate = format.parse(Fromdate);
                    todate = format.parse(ToDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            if (date.before(todate) && date.after(fromdate) || date.equals(todate) || date.equals(fromdate)) {
                mFilteredEvents.add(new Events(mEventListingData.get(i).getEventId(),mEventListingData.get(i).getName(), mEventListingData.get(i).getImage(), mEventListingData.get(i).getInternalName(), mEventListingData.get(i).getFrom(), mEventListingData.get(i).getTo(), mEventListingData.get(i).getDescription(), mEventListingData.get(i).isFavourite()));
            }
        }

        mAdapterEvent = new AdapterEvent(mActivity, mFilteredEvents);
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
            mtvMsg.setText(getResources().getString(R.string.no_today_data));
        }
    }

    @Override
    public void onLikeProcessComplete() {
        FetchTodayEvents(OperaUtils.getCurrentDate());
    }
}
