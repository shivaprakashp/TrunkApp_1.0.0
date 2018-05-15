package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.opera.app.listadapters.GenresDisplayAdapter;
import com.opera.app.listadapters.WhatsOnPagerAdapter;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.events.eventdetails.GetEventDetails;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.opera.app.preferences.SessionManager;
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

    private String IsFavourite = "false", EventInternalName = "", EventId = "", mEventBuyURL = "";
    private Activity mActivity;
    private Api api;
    private EventDetailsDB mEventDetailsDB;
    private EventListingDB mEventListingDB;
    private ArrayList<Events> mEventListingData = new ArrayList<>();
    private ArrayList<Events> mEventsWithSameGenres = new ArrayList<>();
    private ArrayList<GenreList> mGenresListing = new ArrayList<>();
    private TextViewWithFont txtToolbarName;
    //    private AdapterEvent mAdapterEvent;
    private WhatsOnPagerAdapter adapterFavGenres;
    private GenresDisplayAdapter mAdapter;
    private SessionManager manager;

    @Inject
    Retrofit retrofit;

    @BindView(R.id.expandableTextViewInfo)
    ExpandableTextView mExpandableTextView;

    @BindView(R.id.recyclerGenres)
    RecyclerView recyclerGenres;

    @BindView(R.id.viewpagerFavGenres)
    ViewPager mViewpagerFavGenres;

    @BindView(R.id.cover_image)
    ImageView mCover_image;

    @BindView(R.id.txtTicketPrice)
    TextView mTxtTicketPrice;

    /*@BindView(R.id.txtShowmore)
    TextView txtShowmore;*/

    @BindView(R.id.txtHeaderEventName)
    TextView txtHeaderEventName;

    @BindView(R.id.progressImageLoader)
    ProgressBar progressImageLoader;

    @BindView(R.id.imgBack)
    ImageView mImgBack;

    @BindView(R.id.btnBuyTickets)
    Button mBtnBuyTickets;

    @BindView(R.id.txtShowmore)
    TextView txtShowmore;

    @BindView(R.id.ivShowmore)
    ImageView ivShowmore;

    @BindView(R.id.imgFavourite)
    ImageView imgFavourite;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        mActivity = EventDetailsActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_event_details2);

        InitView();
        GetSpecificEventDetails();
    }

    private void InitView() {
        ((MainApplication) getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);
        mEventDetailsDB = new EventDetailsDB(mActivity);
        mEventListingDB = new EventListingDB(mActivity);
        manager = new SessionManager(mActivity);

        Intent in = getIntent();
        EventId = in.getStringExtra("EventId");
        EventInternalName = in.getStringExtra("EventInternalName");
        IsFavourite = in.getStringExtra("IsFavourite");

        mViewpagerFavGenres.setClipToPadding(false);
        mViewpagerFavGenres.setPageMargin(20);
        //What's on events
        adapterFavGenres = new WhatsOnPagerAdapter(mActivity, mEventsWithSameGenres);
        mViewpagerFavGenres.setAdapter(adapterFavGenres);

        mViewpagerFavGenres.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mViewpagerFavGenres.setPadding(0, 0, 70, 0);
                } else if (mEventsWithSameGenres.size() - 1 == position) {
                    mViewpagerFavGenres.setPadding(70, 0, 0, 0);
                } else {
                    mViewpagerFavGenres.setPadding(70, 0, 70, 0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });

        mAdapter = new GenresDisplayAdapter(mActivity, mGenresListing);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerGenres.setLayoutManager(mLayoutManager);
        recyclerGenres.setItemAnimator(new DefaultItemAnimator());
        recyclerGenres.setAdapter(mAdapter);

        if (IsFavourite.equalsIgnoreCase("true")) {
            imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.fav_selected));
        } else {
            imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite));
        }

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
        mEventsWithSameGenres = mEventListingDB.fetchEventsOfSpecificGenres(mEventListingData.get(0).getGenreList(), EventId);

        if (mEventsWithSameGenres.size() > 1) {
            mViewpagerFavGenres.setPadding(0, 0, 70, 0);
        }

        mGenresListing.addAll(mEventListingData.get(0).getGenreList());

        mEventBuyURL = mEventListingData.get(0).getBuyNowLink();

        if (mEventListingData.size() > 0) {
            Picasso.with(mActivity).load(mEventListingData.get(0).getImage()).fit().centerCrop()
                    .into(mCover_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressImageLoader.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressImageLoader.setVisibility(View.GONE);
                        }
                    });

            mExpandableTextView.setText(Html.fromHtml(mEventListingData.get(0).getDescription()));

            txtHeaderEventName.setText(mEventListingData.get(0).getName());
            mTxtTicketPrice.setText(mEventListingData.get(0).getPriceFrom());

            if (manager.isUserLoggedIn()) {
                if (mEventListingData.get(0).isFavourite().equalsIgnoreCase("true")) {
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.fav_selected));
                } else {
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite));
                }
            } else {

            }

            adapterFavGenres.RefreshList(mEventsWithSameGenres);
            mAdapter.RefreshList(mGenresListing);
        }

        mEventDetailsDB.close();
        mEventListingDB.close();
    }


    @OnClick({R.id.imgBack, R.id.btnBuyTickets, R.id.linearReadMore, R.id.imgFavourite})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                finish();
                break;

            case R.id.btnBuyTickets:
                Intent in = new Intent(mActivity, CommonWebViewActivity.class);
                in.putExtra("URL", mEventBuyURL);
                in.putExtra("Header", EventInternalName);
                mActivity.startActivity(in);
                break;
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
            case R.id.imgFavourite:
                mEventListingDB.open();
                if (IsFavourite.equalsIgnoreCase("true")) {
                    IsFavourite = "false";
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite));
                } else {
                    IsFavourite = "true";
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.fav_selected));
                }
                mEventListingDB.UpdateFavouriteData(EventId, IsFavourite);
                break;
        }
    }
}
