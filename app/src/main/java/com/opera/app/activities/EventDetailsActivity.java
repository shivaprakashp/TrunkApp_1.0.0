package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.ExpandableTextView;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.events.EventDetailsDB;
import com.opera.app.listadapters.AdapterEvent;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.events.eventdetails.GetEventDetails;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.utils.LanguageManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

    private String EventInternalName = "";
    private Activity mActivity;
    private Api api;
    private EventDetailsDB mEventDetailsDB;
    private ArrayList<Events> mEventListingData = new ArrayList<>();
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

    @BindView(R.id.expandableTextViewInfo)
    ExpandableTextView mExpandableTextView;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerRestaurants;

    @BindView(R.id.cover_image)
    ImageView mCover_image;

    @BindView(R.id.txtTicketPrice)
    TextView mTxtTicketPrice;

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

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_settings));


        ((MainApplication) getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);
        mEventDetailsDB = new EventDetailsDB(mActivity);

        Intent in = getIntent();
        EventInternalName = in.getStringExtra("EventInternalName");

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_settings));

        //What's on events
        mAdapterEvent = new AdapterEvent(mActivity, mEventListingData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerRestaurants.setLayoutManager(mLayoutManager);
        mRecyclerRestaurants.setItemAnimator(new DefaultItemAnimator());
        mRecyclerRestaurants.setAdapter(mAdapterEvent);
    }

    private void GetSpecificEventDetails() {
        MainController controller = new MainController(mActivity);
        controller.getEventDetails(taskComplete, api, EventInternalName);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            GetEventDetails mEventDataPojo = (GetEventDetails) response.body();
            /*String eventPerformType, eventVideo, eventGenreType, eventId, eventTitle, eventImage, eventDetail;
            String venueImage, venueType, dateTime;
            String ticketId, ticketType, ticketPrice;
            String eventId_favouriteEvents, eventTitle_favouriteEvents, eventInfo_favouriteEvents, eventThumbImage_favouriteEvents;*/
            try {
                if (mEventDataPojo.getStatus().equalsIgnoreCase("success")) {
                    mEventDetailsDB.open();
                    mEventDetailsDB.deleteCompleteTable(EventDetailsDB.TABLE_EVENT_INNER_DETAILS);
//                    mEventDetailsDB.insertIntoEventsDetails(mEventDataPojo.getEvent());
                    fetchDataFromDB();

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
            mExpandableTextView.setText(Html.fromHtml(mEventListingData.get(0).getDescription()));
            txtToolbarName.setText(mEventListingData.get(0).getName());
//            mTxtTicketPrice.setText(mEventListingData.get(0).getPriceFrom());


            Picasso.with(mActivity).load(mEventListingData.get(0).getImage()).fit().centerCrop()
                    .into(mCover_image, new Callback() {
                        @Override
                        public void onSuccess() {
                        /*holder.progressImageLoader.setVisibility(View.GONE);*/
                        }

                        @Override
                        public void onError() {
                       /* holder.progressImageLoader.setVisibility(View.GONE);*/
                        }
                    });
            mAdapterEvent.RefreshList(mEventListingData);
            mAdapterEvent.notifyDataSetChanged();
        }
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
