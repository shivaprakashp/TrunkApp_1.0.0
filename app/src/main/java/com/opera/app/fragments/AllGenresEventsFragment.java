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
import com.opera.app.database.events.EventGenresDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.AdapterGenres;
import com.opera.app.listener.EventInterfaceTab;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllGenresEventsFragment extends BaseFragment implements EventInterfaceTab {

    private Activity mActivity;

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
    }

    private void fetchAllGenres() {
        ArrayList<GenreList> mFilteredNames = new ArrayList<>();

        EventGenresDB mEventGenresDetailsDB = new EventGenresDB(mActivity);
        mEventGenresDetailsDB.open();
        ArrayList<GenreList> mEventGenresListingData = mEventGenresDetailsDB.fetchGenresEvents();
        mEventGenresDetailsDB.close();

        EventListingDB mEventDetailsDB = new EventListingDB(mActivity);
        mEventDetailsDB.open();
        ArrayList<Events> mEventListingData = mEventDetailsDB.fetchAllEvents();
        mEventDetailsDB.close();

        for (int k = 0; k < mEventGenresListingData.size(); k++) {
            boolean flag = false;
            for (int i = 0; i < mEventListingData.size(); i++) {
                for (int j = 0; j < mEventListingData.get(i).getGenreList().size(); j++) {
                    if (mEventGenresListingData.get(k).getGenere().toLowerCase().contains(mEventListingData.get(i).getGenreList().get(j).getGenere().toLowerCase())) {
                        mFilteredNames.add(new GenreList(mEventGenresListingData.get(k).getInternalName(), mEventGenresListingData.get(k).getId(), mEventGenresListingData.get(k).getGenere(), mEventGenresListingData.get(k).getDescription(), mEventGenresListingData.get(k).getImage()));
                        flag = true;
                    }
                }
                if(flag){
                    break;
                }
            }
        }

        AdapterGenres mAdapterGenres = new AdapterGenres(mActivity, mFilteredNames);
        if (mEventGenresListingData.size() > 0) {
            mRecyclerEvents.setVisibility(View.VISIBLE);
            mtvMsg.setVisibility(View.GONE);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerEvents.setLayoutManager(mLayoutManager);
            mRecyclerEvents.setItemAnimator(new DefaultItemAnimator());
            mRecyclerEvents.setAdapter(mAdapterGenres);
        } else {
            mRecyclerEvents.setVisibility(View.GONE);
            mtvMsg.setVisibility(View.VISIBLE);
            mtvMsg.setText(getResources().getString(R.string.no_data));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAllGenres();
    }

    @Override
    public void onLikeProcessComplete() {

    }
}
