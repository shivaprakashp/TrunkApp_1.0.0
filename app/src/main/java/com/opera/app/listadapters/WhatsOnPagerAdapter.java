package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.activities.BuyTicketWebView;
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.controller.MainController;
import com.opera.app.dagger.Api;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.favouriteandsettings.Favourite;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettings;
import com.opera.app.pojo.favouriteandsettings.FavouriteAndSettingsResponseMain;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.OperaUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WhatsOnPagerAdapter extends PagerAdapter {

    private Activity mActivity;
    private String mFrom = "";
    private ArrayList<Events> mWhatsEvents;
    private EventListingDB mEventListingDB;
    Animation slide_in_left;
    Animation slide_out_left;
    private SessionManager sessionManager;
    private Api api;
    @Inject
    Retrofit retrofit;

    public WhatsOnPagerAdapter(Activity mActivity, ArrayList<Events> mWhatsEvents, String mFrom) {
        this.mActivity = mActivity;
        this.mWhatsEvents = mWhatsEvents;

        slide_in_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_in_left);

        slide_out_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_out_left);
        mEventListingDB = new EventListingDB(mActivity);
        this.mFrom = mFrom;
        ((MainApplication) mActivity.getApplication()).getNetComponent().inject(WhatsOnPagerAdapter.this);
        api = retrofit.create(Api.class);
        sessionManager = new SessionManager(mActivity);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        final Events eventObject = mWhatsEvents.get(position);
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.event_row, collection, false);

        TextView txtEventInfo = view.findViewById(R.id.txtEventInfo);
        TextView txtEventGenre = view.findViewById(R.id.txtEventGenre);
        TextView txtEventDate = view.findViewById(R.id.txtEventDate);
        final ProgressBar progressImageLoader = view.findViewById(R.id.progressImageLoader);
        ImageView imgEvent = view.findViewById(R.id.imgEvent);
        ImageView imgInfo = view.findViewById(R.id.imgInfo);
        final ImageView imgFavourite = view.findViewById(R.id.imgFavourite);
        ImageView imgShare = view.findViewById(R.id.imgShare);
        LinearLayout linearParent = view.findViewById(R.id.linearParent);

        final LinearLayout linearHolder = view.findViewById(R.id.linearHolder);
        Button btnBuyTickets = view.findViewById(R.id.btnBuyTickets);

        txtEventInfo.setText(Html.fromHtml(eventObject.getName()));
        txtEventDate.setText(OperaUtils.getDateInMonthFormat(eventObject.getFrom()) + " - " + OperaUtils.getDateInMonthFormat(eventObject.getTo()));

        String mAllGenres = "";
        for (int i = 0; i < eventObject.getGenreList().size(); i++) {
            if (mAllGenres.equalsIgnoreCase("")) {
                mAllGenres = eventObject.getGenreList().get(i).getGenere();
            } else {
                mAllGenres = mAllGenres + "," + eventObject.getGenreList().get(i).getGenere();
            }

        }
        if (mAllGenres.equalsIgnoreCase("")) {
            txtEventGenre.setVisibility(View.GONE);
        } else {
            txtEventGenre.setText(mAllGenres);
            txtEventGenre.setVisibility(View.VISIBLE);
        }

        if (eventObject.isFavourite().equalsIgnoreCase("true")) {
            imgFavourite.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_favourite_selected));
        } else {
            imgFavourite.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_favourite));
        }

//        String img = eventObject.getImage()+"?w=150&h=100&iar=1";
        Picasso.with(mActivity).load(eventObject.getWhatsOnImage()).fit()
                .into(imgEvent, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressImageLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressImageLoader.setVisibility(View.GONE);
                    }
                });

        if (eventObject.isInfoOpen()) {
            linearHolder.setVisibility(View.VISIBLE);
        } else {
            linearHolder.setVisibility(View.GONE);
        }

        linearHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventObject.isInfoOpen()) {
                    linearHolder.startAnimation(slide_out_left);
                    linearHolder.setVisibility(View.GONE);
                    eventObject.setInfoOpen(false);
                }
            }
        });

        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventObject.isInfoOpen()) {
                    linearHolder.startAnimation(slide_out_left);
                    linearHolder.setVisibility(View.GONE);
                    eventObject.setInfoOpen(false);
                } else {
                    linearHolder.startAnimation(slide_in_left);
                    linearHolder.setVisibility(View.VISIBLE);
                    eventObject.setInfoOpen(true);
                }
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eventObject.isInfoOpen()) {
                    OperaUtils.shareEventDetails(mActivity, eventObject.getSharedContentText());
                }
            }
        });

        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eventObject.isInfoOpen()) {
                    ArrayList<Favourite> mFavouriteMarked = new ArrayList<>();
                    mEventListingDB.open();
                    if (eventObject.isFavourite().equalsIgnoreCase("true")) {
                        eventObject.setFavourite("false");
                        mEventListingDB.UpdateFavouriteData(eventObject.getEventId(), "false");
                        mFavouriteMarked.add(new Favourite("false", eventObject.getEventId()));
                    } else {
                        eventObject.setFavourite("true");
                        mEventListingDB.UpdateFavouriteData(eventObject.getEventId(), "true");
                        mFavouriteMarked.add(new Favourite("true", eventObject.getEventId()));
                    }
                    mEventListingDB.close();
                    notifyDataSetChanged();
                    if (sessionManager.isUserLoggedIn()) {
                        UpdateFavouriteForLoggedInUser(mFavouriteMarked);
                    }
                }
            }
        });

        btnBuyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eventObject.isInfoOpen()) {
                    Intent in = new Intent(mActivity, BuyTicketWebView.class);
                    in.putExtra("URL", eventObject.getBuyNowLink());
                    in.putExtra("Header", mActivity.getResources().getString(R.string.buy_tickets));
                    mActivity.startActivity(in);
                }
            }
        });

        linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eventObject.isInfoOpen()) {
                    Intent in = new Intent(mActivity, EventDetailsActivity.class);
                    in.putExtra("EventId", eventObject.getEventId());
                    in.putExtra("EventInternalName", eventObject.getInternalName());
                    in.putExtra("IsFavourite", eventObject.isFavourite());
                    mActivity.startActivity(in);

                    if (!mFrom.equalsIgnoreCase("HomePage")) {
                        mActivity.finish();
                    }
                }
            }
        });

        view.setTag(eventObject);
        collection.addView(view);
        return view;
    }

    private void UpdateFavouriteForLoggedInUser(ArrayList<Favourite> mFavouriteMarked) {
        MainController controller = new MainController(mActivity);
        controller.updateSettingsAndFavourite(taskComplete, api, new FavouriteAndSettings(mFavouriteMarked));
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mWhatsEvents.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void RefreshList(ArrayList<Events> mWhatsEvents) {
        this.mWhatsEvents = mWhatsEvents;
        notifyDataSetChanged();
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        ModelObject customPagerEnum = ModelObject.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }*/

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            FavouriteAndSettingsResponseMain mFavouriteAndSettingsResponseMain = (FavouriteAndSettingsResponseMain) response.body();

            /*try {
                if (mFavouriteAndSettingsResponseMain.getStatus().equalsIgnoreCase("success")) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("data", "error");
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}