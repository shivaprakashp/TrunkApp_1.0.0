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
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.opera.app.pojo.events.eventdetails.GetEventDetails;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.utils.LanguageManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class EventDetailsActivity extends BaseActivity {

    private String EventInternalName = "", EventId = "";
    private Activity mActivity;
    private Api api;
    private EventDetailsDB mEventDetailsDB;
    private EventListingDB mEventListingDB;
    private ArrayList<Events> mEventListingData = new ArrayList<>();
    private ArrayList<Events> mEventsWithSameGenres = new ArrayList<>();
    private TextViewWithFont txtToolbarName;
    private AdapterEvent mAdapterEvent;

    @Inject
    Retrofit retrofit;

    @BindView(R.id.toolbar_event_details)
    Toolbar mToolbar;

    @BindView(R.id.inc_set_toolbar)
    LinearLayout mLinearLayout;

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

    @BindView(R.id.txtShowmore)
    TextView txtShowmore;

    @BindView(R.id.ivShowmore)
    ImageView ivShowmore;

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

        mToolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        mLinearLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_settings));


        ((MainApplication) getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);
        mEventDetailsDB = new EventDetailsDB(mActivity);
        mEventListingDB = new EventListingDB(mActivity);

        Intent in = getIntent();
        EventId = in.getStringExtra("EventId");
        EventInternalName = in.getStringExtra("EventInternalName");

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);

        //What's on events
        mAdapterEvent = new AdapterEvent(mActivity, mEventsWithSameGenres);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerRestaurants.setLayoutManager(mLayoutManager);
        mRecyclerRestaurants.setItemAnimator(new DefaultItemAnimator());
        mRecyclerRestaurants.setAdapter(mAdapterEvent);

        //Expanded Textview
        mExpandableTextView.expand();
        txtShowmore.setText(R.string.read_less);
        ivShowmore.setScaleY(-1);
    }

    private void GetSpecificEventDetails() {
        MainController controller = new MainController(mActivity);
        controller.getEventDetails(taskComplete, api, EventId);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            GetEventDetails mEventDataPojo = (GetEventDetails) response.body();

            try {
                if (mEventDataPojo.getStatus().equalsIgnoreCase("success")) {
                    mEventDetailsDB.open();
                    mEventListingDB.open();
                    mEventDetailsDB.deleteCompleteTable(EventDetailsDB.TABLE_EVENT_DETAILS);
                    mEventDetailsDB.insertIntoEventsDetails(mEventDataPojo.getEvents());
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
        mEventsWithSameGenres = mEventListingDB.fetchEventsOfSpecificGenres(mEventListingData.get(0).getGenreList().getId());

        if (mEventListingData.size() > 0) {
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

            mExpandableTextView.setText(Html.fromHtml(mEventListingData.get(0).getDescription()));
            txtToolbarName.setText(mEventListingData.get(0).getName());
            mTxtTicketPrice.setText(mEventListingData.get(0).getPriceFrom());

            mAdapterEvent.RefreshList(mEventsWithSameGenres);
            mAdapterEvent.notifyDataSetChanged();
        }

        mEventDetailsDB.close();
        mEventListingDB.close();
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.linearReadMore})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.linearReadMore:
                if (mExpandableTextView.isExpanded()) {
                    mExpandableTextView.collapse();
                    txtShowmore.setText(R.string.read_more);
                    ivShowmore.setScaleY(1);
                } else {
                    mExpandableTextView.expand();
                    txtShowmore.setText(R.string.read_less);
                    ivShowmore.setScaleY(-1);
                }
                break;
        }
    }
}
