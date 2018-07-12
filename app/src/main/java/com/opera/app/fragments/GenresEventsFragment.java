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
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.database.events.EventGenresDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.AdapterEvent;
import com.opera.app.listener.EventInterfaceTab;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresEventsFragment extends BaseFragment implements EventInterfaceTab {

    private Activity mActivity;
    private ArrayList<GenreList> mGenreListingData = new ArrayList<>();
    private EventInterfaceTab listenerGenres;

    @BindView(R.id.spinnerSelectGenre)
    Spinner mSpinnerSelectGenre;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerGenre;

    @BindView(R.id.tv_msg)
    TextView mtvMsg;

    String selectedGenre = null;

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
        View view = inflater.inflate(R.layout.fragment_events_genres, container, false);

        InitView(view);

        return view;
    }

    private void InitView(View view) {
        ButterKnife.bind(this, view);
        listenerGenres= this;
    }

    private void initSpinnervalues() {
        EventGenresDB mEventGenreDB = new EventGenresDB(mActivity);
        mEventGenreDB.open();
        List<String> labels = mEventGenreDB.getAllGenresLabels();
        mEventGenreDB.close();

        ArrayAdapter<String> adapterMealPeriod = new ArrayAdapter<String>(mActivity, R.layout.custom_dark_spinner, labels);
        mSpinnerSelectGenre.setAdapter(adapterMealPeriod);
    }

    private void Onclicks() {
        mSpinnerSelectGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGenre = parent.getItemAtPosition(position).toString();
                ApplyGenreSearch(selectedGenre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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
        AdapterEvent mAdapterEvent = new AdapterEvent(mActivity, mFilteredNames, listenerGenres);
        if (mFilteredNames.size() > 0) {
            mRecyclerGenre.setVisibility(View.VISIBLE);
            mtvMsg.setVisibility(View.GONE);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
    @Override
    public void onLikeProcessComplete() {
        if(mSpinnerSelectGenre.getSelectedItem()!=null)
        ApplyGenreSearch(mSpinnerSelectGenre.getSelectedItem().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        initSpinnervalues();
        Onclicks();

    }
}
