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

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.ExpandableTextView;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.events.EventDetailsDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listadapters.AdapterEvent;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.events.eventdetails.EventDetails;
import com.opera.app.pojo.events.eventdetails.EventDetailsPojo;
import com.opera.app.pojo.events.eventdetails.EventPriceFrom;
import com.opera.app.pojo.events.eventdetails.EventVenue;
import com.opera.app.pojo.events.eventdetails.FavouriteEvents;
import com.opera.app.pojo.events.eventdetails.VenueDateTime;
import com.opera.app.pojo.events.events;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class EventDetailsActivity extends BaseActivity {

    private String EventId = "";
    private Activity mActivity;
    private Api api;
    private EventDetailsDB mEventDetailsDB;
    private ArrayList<EventDetails> mEventListingData = new ArrayList<>();
    private TextViewWithFont txtToolbarName;
    private AdapterEvent mAdapterEvent;

    @Inject
    Retrofit retrofit;

    @BindView(R.id.toolbar_setting)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.expandableTextView)
    ExpandableTextView mExpandableTextView;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerRestaurants;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        mActivity = EventDetailsActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_event_details);

        InitView();
        GetSpecificEventDetails();
    }

    private void InitView() {
        ((MainApplication) getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);
        mEventDetailsDB = new EventDetailsDB(mActivity);

        Intent in = getIntent();
        EventId = in.getStringExtra("EventId");

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_settings));

        //What's on events
//        mAdapterEvent = new AdapterEvent(mActivity, mEventListingData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerRestaurants.setLayoutManager(mLayoutManager);
        mRecyclerRestaurants.setItemAnimator(new DefaultItemAnimator());
        mRecyclerRestaurants.setAdapter(mAdapterEvent);
    }

    private void GetSpecificEventDetails() {
        MainController controller = new MainController(mActivity);
        controller.getEventDetails(taskComplete, api);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            EventDetailsPojo mEventDataPojo = (EventDetailsPojo) response.body();
            String eventPerformType, eventVideo, eventGenreType, eventId, eventTitle, eventImage, eventDetail;
            String venueImage, venueType, dateTime;
            String ticketId, ticketType, ticketPrice;
            String eventId_favouriteEvents, eventTitle_favouriteEvents, eventInfo_favouriteEvents, eventThumbImage_favouriteEvents;
            try {
                if (mEventDataPojo.getStatus().equalsIgnoreCase("success")) {
                    mEventDetailsDB.open();
                    mEventDetailsDB.deleteCompleteTable(EventDetailsDB.TABLE_EVENT_INNER_DETAILS);
                    mEventDetailsDB.insertIntoEventsDetails(mEventDataPojo.getEventDetails());
                    fetchDataFromDB();
                   /* for(EventDetails detailsPojo: mEventDataPojo.getEventDetails()){
                        eventPerformType = detailsPojo.getEventPerformType();
                        eventVideo = detailsPojo.getEventVideo();
                        eventGenreType = detailsPojo.getEventGenreType();
                        eventId = detailsPojo.getEventId();
                        eventTitle = detailsPojo.getEventTitle();
                        eventImage = detailsPojo.getEventImage();
                        eventDetail = detailsPojo.getEventDetail();

                        for(EventVenue eventVenue : detailsPojo.getEventVenue()){
                            venueImage = eventVenue.getVenueImage();
                            venueType = eventVenue.getVenueType();
                            for(VenueDateTime venueDateTime: eventVenue.getVenueDateTime()){
                                dateTime = venueDateTime.getDateTime();
                            }
                            for(EventPriceFrom eventPriceFrom: eventVenue.getEventPriceFrom()){
                                ticketId = eventPriceFrom.getTicketId();
                                ticketType = eventPriceFrom.getTicketType();
                                ticketPrice = eventPriceFrom.getTicketPrice();
                            }
                        }
                        for(FavouriteEvents favouriteEvents : detailsPojo.getFavouriteEvents()){
                            eventId_favouriteEvents = favouriteEvents.getEventId();
                            eventTitle_favouriteEvents = favouriteEvents.getEventTitle();
                            eventInfo_favouriteEvents = favouriteEvents.getEventInfo();
                            eventThumbImage_favouriteEvents = favouriteEvents.getEventThumbImage();
                        }
                    }*/

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
        }
    };

    private void fetchDataFromDB() {
        mEventListingData = mEventDetailsDB.fetchSpecificEventDetails();

        if (mEventListingData.size() > 0) {
            mExpandableTextView.setText(mEventListingData.get(0).getEventDetail());
            txtToolbarName.setText(mEventListingData.get(0).getEventTitle());
        }

    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
