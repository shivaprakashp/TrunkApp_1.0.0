package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.dagger.Api;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listener.EventInterfaceTab;
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

/**
 * Created by 1000632 on 5/2/2018.
 */

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.MyViewHolder> {

    private ArrayList<Events> mEventListingData;
    private EventListingDB mEventListingDB;
    private Activity mActivity;
    Animation slide_in_left;
    Animation slide_out_left;
    private EventInterfaceTab listener = null;
    private SessionManager manager;
    private Api api;
    @Inject
    Retrofit retrofit;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgEvent, imgFavourite, imgShare, imgInfo;
        public Button btnBuyTickets;
        public ProgressBar progressImageLoader;
        public LinearLayout linearHolder, linearParent;
        public TextView txtEventInfo, txtEventGenre, txtEventDate;

        public MyViewHolder(View view) {
            super(view);
            imgEvent = view.findViewById(R.id.imgEvent);
            btnBuyTickets = view.findViewById(R.id.btnBuyTickets);
            imgFavourite = view.findViewById(R.id.imgFavourite);
            imgShare = view.findViewById(R.id.imgShare);
            imgInfo = view.findViewById(R.id.imgInfo);
            linearHolder = view.findViewById(R.id.linearHolder);
            txtEventInfo = view.findViewById(R.id.txtEventInfo);
            txtEventGenre = view.findViewById(R.id.txtEventGenre);
            txtEventDate = view.findViewById(R.id.txtEventDate);
            progressImageLoader = view.findViewById(R.id.progressImageLoader);
            linearParent = view.findViewById(R.id.linearParent);
        }
    }

    public AdapterEvent(Activity mActivity, ArrayList<Events> mEventListingData) {
        this.mActivity = mActivity;
        this.mEventListingData = mEventListingData;

        slide_in_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_in_left);

        slide_out_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_out_left);
        mEventListingDB = new EventListingDB(mActivity);

        ((MainApplication) mActivity.getApplication()).getNetComponent().inject(AdapterEvent.this);
        api = retrofit.create(Api.class);
        manager = new SessionManager(mActivity);
    }

    //For Favourite and Genres
    public AdapterEvent(Activity mActivity, ArrayList<Events> mEventListingData, EventInterfaceTab listener) {
        this.mActivity = mActivity;
        this.mEventListingData = mEventListingData;

        slide_in_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_in_left);

        slide_out_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_out_left);
        mEventListingDB = new EventListingDB(mActivity);
        this.listener = listener;
        ((MainApplication) mActivity.getApplication()).getNetComponent().inject(AdapterEvent.this);
        api = retrofit.create(Api.class);
        manager = new SessionManager(mActivity);
    }

    public void RefreshList(ArrayList<Events> mEventListingData) {
        this.mEventListingData = mEventListingData;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row_for_tabs, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Events mEventPojo = mEventListingData.get(position);

        if (mEventPojo.isFavourite().equalsIgnoreCase("true")) {
            holder.imgFavourite.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_favourite_selected));
        } else {
            holder.imgFavourite.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_favourite));
        }

        holder.txtEventDate.setText(new StringBuilder().append(OperaUtils.getDateInMonthFormat(mEventPojo.getFrom())).append(" - ").append(OperaUtils.getDateInMonthFormat(mEventPojo.getTo())).toString());
        holder.txtEventInfo.setText(Html.fromHtml(mEventPojo.getName()));

        StringBuilder mAllGenres = new StringBuilder();
        for (int i = 0; i < mEventPojo.getGenreList().size(); i++) {
            if (mAllGenres.toString().equalsIgnoreCase("")) {
                mAllGenres = new StringBuilder(mEventPojo.getGenreList().get(i).getGenere());
            } else {
                mAllGenres.append(",").append(mEventPojo.getGenreList().get(i).getGenere());
            }

        }
        if (mAllGenres.toString().equalsIgnoreCase("")) {
            holder.txtEventGenre.setVisibility(View.GONE);
        } else {
            holder.txtEventGenre.setText(mAllGenres.toString());
            holder.txtEventGenre.setVisibility(View.VISIBLE);
        }


        Picasso.with(mActivity).load(mEventPojo.getWhatsOnImage())
                .into(holder.imgEvent, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressImageLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressImageLoader.setVisibility(View.GONE);
                    }
                });

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEventPojo.isInfoOpen()) {
                    OperaUtils.shareEventDetails(mActivity, mEventPojo.getSharedContentText());
                }
            }
        });

        holder.linearHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEventPojo.isInfoOpen()) {
                    holder.linearHolder.startAnimation(slide_out_left);
                    holder.linearHolder.setVisibility(View.GONE);
                    mEventPojo.setInfoOpen(false);
                }
            }
        });
        holder.imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEventPojo.isInfoOpen()) {
                    holder.linearHolder.startAnimation(slide_out_left);
                    holder.linearHolder.setVisibility(View.GONE);
                    mEventPojo.setInfoOpen(false);
                } else {
                    holder.linearHolder.startAnimation(slide_in_left);
                    holder.linearHolder.setVisibility(View.VISIBLE);
                    mEventPojo.setInfoOpen(true);
                }
            }
        });

        holder.btnBuyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEventPojo.isInfoOpen()) {
                    /*Intent in = new Intent(mActivity, BuyTicketWebView.class);
                    in.putExtra("URL", mEventPojo.getBuyNowLink());
                    in.putExtra("Header", mActivity.getResources().getString(R.string.buy_tickets));
                    mActivity.startActivity(in);*/

                    OperaUtils.BuyTicketCommmonFunction(mActivity, mEventPojo.getBuyNowLink(), mActivity.getResources().getString(R.string.buy_tickets));
                }
            }
        });

        holder.linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEventPojo.isInfoOpen()) {
                    Intent in = new Intent(mActivity, EventDetailsActivity.class);
                    in.putExtra("EventId", mEventPojo.getEventId());
                    in.putExtra("EventInternalName", mEventPojo.getInternalName());
                    in.putExtra("IsFavourite", mEventPojo.isFavourite());
                    mActivity.startActivity(in);
                }
            }
        });

        holder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEventPojo.isInfoOpen()) {
                    mEventListingDB.open();
                    String IsFavourite;
                    if (mEventPojo.isFavourite().equalsIgnoreCase("true")) {
                        IsFavourite = "false";
                        mEventPojo.setFavourite(IsFavourite);
                        mEventListingDB.UpdateFavouriteData(mEventPojo.getEventId(), IsFavourite);
                    } else {
                        IsFavourite = "true";
                        mEventPojo.setFavourite(IsFavourite);
                        mEventListingDB.UpdateFavouriteData(mEventPojo.getEventId(), IsFavourite);
                    }
                    mEventListingDB.close();
                    notifyDataSetChanged();
                    RefreshList(mEventListingData);

                    if (manager.isUserLoggedIn()) {
                        UpdateFavouriteForLoggedInUser(IsFavourite, mEventPojo.getEventId());
                    }

                    if (listener != null) {
                        listener.onLikeProcessComplete();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventListingData.size();
    }

    private void UpdateFavouriteForLoggedInUser(String IsFavourite, String EventId) {
        ArrayList<Favourite> mFavouriteMarked = new ArrayList<>();
        mFavouriteMarked.add(new Favourite(IsFavourite, EventId));

        MainController controller = new MainController(mActivity);
        controller.updateSettingsAndFavourite(taskComplete, api, new FavouriteAndSettings(mFavouriteMarked));
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            FavouriteAndSettingsResponseMain mFavouriteAndSettingsResponseMain = (FavouriteAndSettingsResponseMain) response.body();
            if (mFavouriteAndSettingsResponseMain != null) {
                try {
                    if (mFavouriteAndSettingsResponseMain.getStatus().equalsIgnoreCase(AppConstants.STATUS_SUCCESS)) {
                        if (mFavouriteAndSettingsResponseMain.getData().getFavourite().size() > 0) {
                            mEventListingDB.open();
                            mEventListingDB.UpdateFavouriteData(mFavouriteAndSettingsResponseMain.getData().getFavourite().get(0).getFavouriteId(), mFavouriteAndSettingsResponseMain.getData().getFavourite().get(0).getIsFavourite());
                            mEventListingDB.close();

                            listener.onLikeProcessComplete();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("data", "error");
        }
    };
}
