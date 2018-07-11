package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.events.EventDetailsDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.dialogues.GuestDialog;
import com.opera.app.listadapters.GenresDisplayAdapter;
import com.opera.app.listadapters.WhatsOnPagerAdapter;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.events.eventdetails.GetEventDetails;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.opera.app.pojo.favouriteandsettings.Favourite;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettings;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettingsResponseMain;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;
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

    private String IsFavourite = "false",
            EventInternalName = "",
            EventId = "",
            mEventBuyURL = "",
            mEventDescription = "",
            mEventImage = "",
            mEventYoutubeVideo = "",
            mEventURL = "",
            mAppleMusicURL = "";
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
    private Intent in;

    @Inject
    Retrofit retrofit;


    @BindView(R.id.btnBuyTickets2)
    TextView btnBuyTickets2;

    @BindView(R.id.expandableTextViewInfo)
    TextView mExpandableTextView;

    @BindView(R.id.recyclerGenres)
    RecyclerView recyclerGenres;

    @BindView(R.id.viewpagerFavGenres)
    ViewPager mViewpagerFavGenres;

    @BindView(R.id.cover_image)
    ImageView mCover_image;

    @BindView(R.id.txtTicketPrice)
    TextView mTxtTicketPrice;

    @BindView(R.id.txtYouMightAlsoLike)
    TextView txtYouMightAlsoLike;

    @BindView(R.id.txtHeaderEventName)
    TextView txtHeaderEventName;

    @BindView(R.id.progressImageLoader)
    ProgressBar progressImageLoader;

    @BindView(R.id.imgBack)
    ImageView mImgBack;

    @BindView(R.id.btnBuyTickets)
    Button mBtnBuyTickets;

    @BindView(R.id.imgFavourite)
    ImageView imgFavourite;

    @BindView(R.id.linearPlay)
    LinearLayout mLinearPlay;

    @BindView(R.id.linearYouMightLike)
    LinearLayout linearYouMightLike;

    @BindView(R.id.linearBottomNestedInside)
    LinearLayout linearBottomNestedInside;

    @BindView(R.id.linearShare)
    RelativeLayout linearShare;

    @BindView(R.id.relativeOpenAppleMusic)
    RelativeLayout relativeOpenAppleMusic;

    @BindView(R.id.img_plan_visit)
    ImageView mImgPlanVisit;

    @BindView(R.id.img_wallet)
    ImageView mImgWallet;

    @BindView(R.id.img_profile)
    ImageView mImgProfile;

    @BindView(R.id.nestedScrollTxt)
    NestedScrollView nestedScrollTxt;

    @BindView(R.id.nestedParent)
    ScrollView nestedParent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        mActivity = EventDetailsActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_event_details);

        InitView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetSpecificEventDetails();
    }

    private void InitView() {
        ((MainApplication) getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);
        mEventDetailsDB = new EventDetailsDB(mActivity);
        mEventListingDB = new EventListingDB(mActivity);
        manager = new SessionManager(mActivity);

        try {
            Intent in = getIntent();
            EventId = in.getStringExtra("EventId");
            IsFavourite = in.getStringExtra("IsFavourite");
        } catch (Exception e) {
            e.printStackTrace();
        }


        mViewpagerFavGenres.setClipToPadding(false);
        mViewpagerFavGenres.setPageMargin(20);
        mViewpagerFavGenres.setPadding(40, 0, 40, 0);

        //What's on events
        adapterFavGenres = new WhatsOnPagerAdapter(mActivity, mEventsWithSameGenres, "");
        mViewpagerFavGenres.setAdapter(adapterFavGenres);
        mViewpagerFavGenres.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
              /*  if (position == 0) {
                    mViewpagerFavGenres.setPadding(0, 0, 70, 0);
                } else if (mEventsWithSameGenres.size() - 1 == position) {
                    mViewpagerFavGenres.setPadding(70, 0, 0, 0);
                } else {
                    mViewpagerFavGenres.setPadding(70, 0, 70, 0);
                }*/
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
            imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite_selected));
        } else {
            imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite));
        }

        try {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(mActivity, R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GetSpecificEventDetails() {
        MainController controller = new MainController(mActivity);
        controller.getEventDetails(taskComplete, api, EventId);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            if (mRequestKey.equalsIgnoreCase(AppConstants.MARKFAVOURITEFOREVENT.MARKFAVOURITEFOREVENT)) {
                FavouriteAndSettingsResponseMain mFavouriteAndSettingsResponseMain = (FavouriteAndSettingsResponseMain) response.body();
                /*try {
                    if (mFavouriteAndSettingsResponseMain.getStatus().equalsIgnoreCase("success")) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            } else {
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
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            mEventDetailsDB.open();
            mEventListingDB.open();
            fetchDataFromDB();
        }
    };

    private void fetchDataFromDB() {
        mEventsWithSameGenres = new ArrayList<>();
        mGenresListing = new ArrayList<>();

        mEventListingData = mEventDetailsDB.fetchSpecificEventDetails(EventId);

        if (mEventListingData.size() > 0) {


            mEventsWithSameGenres = mEventListingDB.fetchEventsOfSpecificGenres(mEventListingData.get(0).getGenreList(), EventId);

            mGenresListing.addAll(mEventListingData.get(0).getGenreList());

            mEventBuyURL = mEventListingData.get(0).getBuyNowLink();
            mEventDescription = mEventListingData.get(0).getDescription();
            mEventImage = mEventListingData.get(0).getImage();
            mEventYoutubeVideo = mEventListingData.get(0).getVideo();
            mEventURL = mEventListingData.get(0).getSharedContentText();
            EventInternalName = mEventListingData.get(0).getInternalName();
            IsFavourite = mEventListingData.get(0).isFavourite();
            mAppleMusicURL = mEventListingData.get(0).getAppleUrl();

            if (manager.isUserLoggedIn()) {
                if (IsFavourite.equalsIgnoreCase("true")) {
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite_selected));
                } else {
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite));
                }
            }

            if (mEventYoutubeVideo.equalsIgnoreCase("")) {
                mLinearPlay.setVisibility(View.GONE);
            }

            if (mAppleMusicURL.equalsIgnoreCase("")) {
                relativeOpenAppleMusic.setVisibility(View.GONE);
            }

            if (mEventListingData.size() > 0) {
                Picasso.with(mActivity).load(mEventListingData.get(0).getEventDetailImage()).fit()
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


                txtHeaderEventName.setText(mEventListingData.get(0).getName());
                mTxtTicketPrice.setText(mEventListingData.get(0).getPriceFrom());

           /* if (manager.isUserLoggedIn()) {
                if (mEventListingData.get(0).isFavourite().equalsIgnoreCase("true")) {
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite_selected));
                } else {
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite));
                }
            } else {

            }*/

                if (mEventsWithSameGenres.size() > 0) {
                    adapterFavGenres.RefreshList(mEventsWithSameGenres);
                    mAdapter.RefreshList(mGenresListing);
                    txtYouMightAlsoLike.setVisibility(View.VISIBLE);
                    btnBuyTickets2.setVisibility(View.GONE);
                    mBtnBuyTickets.setVisibility(View.VISIBLE);
                } else {
                    txtYouMightAlsoLike.setVisibility(View.GONE);
                    linearYouMightLike.setBackgroundColor(getResources().getColor(R.color.black));
                    linearYouMightLike.setVisibility(View.GONE);
                    recyclerGenres.setVisibility(View.GONE);

                    linearBottomNestedInside.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams lp = nestedScrollTxt.getLayoutParams();
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    nestedScrollTxt.requestLayout();

                    /*nestedParent.setScrollingEnabled(false);
                    Log.e("IsScrollable", nestedParent.isScrollable() + "");*/


                /*RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                relativeParams.setMargins(0, 0, 0, 50);
                nestedScrollTxt.setLayoutParams(relativeParams);*/

                /*ViewGroup.LayoutParams linearParams = new ViewGroup.LayoutParams(
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
//                linearParams.setMargins(0, 80, 0, 0);
                nestedScrollTxt.setLayoutParams(linearParams);
                nestedScrollTxt.requestLayout();*/

                    mBtnBuyTickets.setVisibility(View.GONE);
                    btnBuyTickets2.setVisibility(View.VISIBLE);

                /*nestedScrollTxt.setLayoutParams
                        (new ViewGroup.MarginLayoutParams
                                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));*/
//                nestedScrollTxt.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
                }

            }

            mEventDetailsDB.close();
            mEventListingDB.close();
            mExpandableTextView.setText(Html.fromHtml(mEventListingData.get(0).getMobileDescription()));
        }
    }


    @OnClick({R.id.imgBack, R.id.btnBuyTickets, R.id.btnBuyTickets2,R.id.imgFavourite, R.id.linearPlay, R.id.linearShare, R.id.img_plan_visit, R.id.img_wallet, R.id.img_profile, R.id.relativeOpenAppleMusic})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                finish();
                break;

            case R.id.btnBuyTickets:
                in = new Intent(mActivity, BuyTicketWebView.class);
                in.putExtra("URL", mEventBuyURL);
                in.putExtra("Header", EventInternalName);
                mActivity.startActivity(in);
                break;

            case R.id.btnBuyTickets2:
                in = new Intent(mActivity, BuyTicketWebView.class);
                in.putExtra("URL", mEventBuyURL);
                in.putExtra("Header", EventInternalName);
                mActivity.startActivity(in);
                break;

            case R.id.imgFavourite:
                mEventListingDB.open();
                if (IsFavourite.equalsIgnoreCase("true")) {
                    IsFavourite = "false";
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite));
                } else {
                    IsFavourite = "true";
                    imgFavourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favourite_selected));
                }
                mEventListingDB.UpdateFavouriteData(EventId, IsFavourite);
                if (manager.isUserLoggedIn()) {
                    UpdateFavouriteForLoggedInUser(IsFavourite);
                }
                break;
            case R.id.linearPlay:
                in = new Intent(mActivity, OpenYoutubeVideo.class);
                in.putExtra("YoutubeVideo", mEventYoutubeVideo);
                startActivity(in);
                break;

            case R.id.linearShare:
                OperaUtils.shareEventDetails(mActivity, mEventURL);
                break;

            case R.id.img_plan_visit:
                openActivity(mActivity, CalendarActivity.class);
                break;
            case R.id.img_wallet: {
                if (manager.isUserLoggedIn()) {
                    openActivity(mActivity, WalletActivity.class);
                } else {
                    GuestDialog dialog = new GuestDialog(mActivity, mActivity.getString(R.string.guest_title), mActivity.getString(R.string.guest_msg));
                    dialog.show();
                }
            }
            break;

            case R.id.img_profile: {
                if (manager.isUserLoggedIn()) {
                    openActivity(mActivity, MyProfileActivity.class);
                } else {
                    GuestDialog dialog = new GuestDialog(mActivity, mActivity.getString(R.string.guest_title), mActivity.getString(R.string.guest_msg));
                    dialog.show();
                }
            }
            break;

            case R.id.relativeOpenAppleMusic:
                Intent in = new Intent(mActivity, CommonWebViewActivity.class);
                in.putExtra("URL", mAppleMusicURL);
                in.putExtra("Header", "Apple Music");
                startActivity(in);
                break;
        }
    }

    private void UpdateFavouriteForLoggedInUser(String IsFavourite) {
        ArrayList<Favourite> mFavouriteMarked = new ArrayList<>();
        mFavouriteMarked.add(new Favourite(IsFavourite, EventId));

        MainController controller = new MainController(mActivity);
        controller.updateSettingsAndFavourite(taskComplete, api, new FavouriteAndSettings(mFavouriteMarked));
    }
}
