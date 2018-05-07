package com.opera.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.activities.SearchEventActivity;
import com.opera.app.controller.MainController;
import com.opera.app.dagger.Api;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.AdapterEvent;
import com.opera.app.listadapters.CoverFlowAdapter;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.events.AllEventsOfDubaiOpera;
import com.opera.app.pojo.events.events;
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
    private ArrayList<events> mHighlightedEvents = new ArrayList<>();

    private AdapterEvent mAdapterEvent;
    private ArrayList<events> mEventListingData = new ArrayList<>();
    private Api api;
    @Inject
    Retrofit retrofit;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerRestaurants;

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
        View view = inflater.inflate(R.layout.fragment_home_2, container, false);

        //InitView(view);

        //GetCurrentEvents();

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

        mEventListingDB = new EventListingDB(mActivity);
        //Highlighted events
        mAdapter = new CoverFlowAdapter(getActivity(), mHighlightedEvents);
        /*mCoverFlow.setAdapter(mAdapter);*/

        //What's on events
        mAdapterEvent = new AdapterEvent(mActivity, mEventListingData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerRestaurants.setLayoutManager(mLayoutManager);
        mRecyclerRestaurants.setItemAnimator(new DefaultItemAnimator());
        mRecyclerRestaurants.setAdapter(mAdapterEvent);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            AllEventsOfDubaiOpera mEventDataPojo = (AllEventsOfDubaiOpera) response.body();
            try {
                if (mEventDataPojo.getStatus().equalsIgnoreCase("success")) {
                    mEventListingDB.open();
                    mEventListingDB.deleteCompleteTable(EventListingDB.TABLE_EVENT_DETAILS);
                    mEventListingDB.insertOtherEvents(mEventDataPojo.getEvents());
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
        mEventListingData = mEventListingDB.fetchAllEvents();
        mAdapterEvent.RefreshList(mEventListingData);
        mEventListingDB.close();
        mAdapterEvent.notifyDataSetChanged();

        for (int i = 0; i < mEventListingData.size(); i++) {
            if (mEventListingData.get(i).getEventIsHighlighted().equalsIgnoreCase("true")) {
                mHighlightedEvents.add(new events(mEventListingData.get(i).getEventDate(), "http://61.12.113.197:8199/-/media/Habitat/Images/Restaurants/SC-HR-5.jpg"));
            }

        }
        if (mEventListingData.size() > 0) {
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
}
