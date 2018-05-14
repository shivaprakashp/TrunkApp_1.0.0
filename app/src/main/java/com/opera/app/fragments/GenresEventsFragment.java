package com.opera.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.opera.app.R;
import com.opera.app.database.events.EventGenresDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.AdapterEvent;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresEventsFragment extends BaseFragment {

    private Activity mActivity;
    private EventListingDB mEventDetailsDB;
    private EventGenresDB mEventGenreDB;
    private ArrayList<GenreList> mGenreListingData = new ArrayList<>();
    private ArrayList<Events> mEventListingData = new ArrayList<>();
    private AdapterEvent mAdapterEvent;
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
        Onclicks();
        initSpinnervalues();
    }

    private void initSpinnervalues() {

        mEventGenreDB = new EventGenresDB(mActivity);
        mEventGenreDB.open();
        mGenreListingData = mEventGenreDB.fetchAllEvents();
        List<String> labels = mEventGenreDB.getAllGenresLabels();
        mEventGenreDB.close();

        ArrayAdapter<String> adapterMealPeriod = new ArrayAdapter<String>(mActivity, R.layout.custom_dark_spinner, labels);
        mSpinnerSelectGenre.setAdapter(adapterMealPeriod);
    }

    private void Onclicks() {
        mSpinnerSelectGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ApplyGenreSearch(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void ApplyGenreSearch(String mSearchedTxt) {
        ArrayList<Events> mFilteredNames = new ArrayList<>();

        mEventDetailsDB = new EventListingDB(mActivity);
        mEventDetailsDB.open();
        mEventListingData = mEventDetailsDB.fetchAllEvents();
        mEventDetailsDB.close();

        for (int i = 0; i < mEventListingData.size(); i++) {
            for (int j = 0; j < mEventListingData.get(i).getGenreList().size(); j++) {
                if (mEventListingData.get(j).getGenreList().get(j).getGenere().toLowerCase().contains(mSearchedTxt.toLowerCase())) {
                    mFilteredNames.add(new Events(mEventListingData.get(i).getName(), mEventListingData.get(i).getImage(), mEventListingData.get(i).getInternalName(), mEventListingData.get(i).getFrom(), mEventListingData.get(i).getTo(), mEventListingData.get(i).getDescription()));
                }
            }
        }
        mAdapterEvent = new AdapterEvent(mActivity, mFilteredNames);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerGenre.setLayoutManager(mLayoutManager);
        mRecyclerGenre.setItemAnimator(new DefaultItemAnimator());
        mRecyclerGenre.setAdapter(mAdapterEvent);
    }

}
