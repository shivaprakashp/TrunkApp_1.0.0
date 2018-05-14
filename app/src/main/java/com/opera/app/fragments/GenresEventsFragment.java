package com.opera.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.opera.app.R;
import com.opera.app.database.events.EventGenresDB;
import com.opera.app.listadapters.AdapterGenres;
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresEventsFragment extends BaseFragment {

    private Activity mActivity;

    private EventGenresDB mEventGenreDB;
    private ArrayList<GenreList> mGenreListingData = new ArrayList<>();
    private AdapterGenres mAdapterGenres;

    @BindView(R.id.spinnerSelectGenre)
    Spinner mSpinnerSelectGenre;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerGenre;

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
        View view = inflater.inflate(R.layout.fragment_genres_events, container, false);

        InitView(view);

        return view;
    }

    private void InitView(View view) {
        ButterKnife.bind(this, view);
        GetGenres();
    }

    private void GetGenres() {
        mEventGenreDB = new EventGenresDB(mActivity);
        mEventGenreDB.open();
        mGenreListingData = mEventGenreDB.fetchAllEvents();
        mEventGenreDB.close();

        mAdapterGenres = new AdapterGenres(mActivity, mGenreListingData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerGenre.setLayoutManager(mLayoutManager);
        mRecyclerGenre.setItemAnimator(new DefaultItemAnimator());
        mRecyclerGenre.setAdapter(mAdapterGenres);
    }
}
