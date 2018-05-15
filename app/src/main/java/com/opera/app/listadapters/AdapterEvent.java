package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

import com.opera.app.R;
import com.opera.app.activities.CommonWebViewActivity;
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.listener.EventInterfaceTab;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/2/2018.
 */

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.MyViewHolder> {

    private ArrayList<Events> mEventListingData;
    private EventListingDB mEventListingDB;
    private Activity mActivity;
    Animation slide_in_left;
    Animation slide_out_left;
    private EventInterfaceTab listener=null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgEvent, imgFavourite, imgShare, imgInfo;
        public Button btnBuyTickets;
        public ProgressBar progressImageLoader;
        public LinearLayout linearHolder, linearParent;
        public TextView txtEventInfo, txtEventDate;

        public MyViewHolder(View view) {
            super(view);
            imgEvent = (ImageView) view.findViewById(R.id.imgEvent);
            btnBuyTickets = (Button) view.findViewById(R.id.btnBuyTickets);
            imgFavourite = (ImageView) view.findViewById(R.id.imgFavourite);
            imgShare = (ImageView) view.findViewById(R.id.imgShare);
            imgInfo = (ImageView) view.findViewById(R.id.imgInfo);
            linearHolder = (LinearLayout) view.findViewById(R.id.linearHolder);
            txtEventInfo = (TextView) view.findViewById(R.id.txtEventInfo);
            txtEventDate = (TextView) view.findViewById(R.id.txtEventDate);
            progressImageLoader = (ProgressBar) view.findViewById(R.id.progressImageLoader);
            linearParent = (LinearLayout) view.findViewById(R.id.linearParent);
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
    }

    //For Favourite and Genres
    public AdapterEvent(Activity mActivity, ArrayList<Events> mEventListingData,EventInterfaceTab listener) {
        this.mActivity = mActivity;
        this.mEventListingData = mEventListingData;

        slide_in_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_in_left);

        slide_out_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_out_left);
        mEventListingDB = new EventListingDB(mActivity);
        this.listener=listener;
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
            holder.imgFavourite.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.fav_selected));
        } else {
            holder.imgFavourite.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_favourite));
        }

        holder.txtEventDate.setText(mEventPojo.getFrom() + " to " + mEventPojo.getTo());
        holder.txtEventInfo.setText(Html.fromHtml(mEventPojo.getDescription()));
        Picasso.with(mActivity).load(mEventPojo.getImage())
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

        holder.imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventListingDB.open();
                if (mEventPojo.isFavourite().equalsIgnoreCase("true")) {
                    mEventPojo.setFavourite("false");
                    mEventListingDB.UpdateFavouriteData(mEventPojo.getEventId(), "false");
                } else {
                    mEventPojo.setFavourite("true");
                    mEventListingDB.UpdateFavouriteData(mEventPojo.getEventId(), "true");
                }
                mEventListingDB.close();
                notifyDataSetChanged();
                RefreshList(mEventListingData);

                if(listener!=null){
                    listener.onLikeProcessComplete();
                }
            }
        });
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check it out. Your message goes here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                mActivity.startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));*/
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
                Intent in = new Intent(mActivity, CommonWebViewActivity.class);
                in.putExtra("URL", mEventPojo.getBuyNowLink());
                in.putExtra("Header", mActivity.getResources().getString(R.string.buy_tickets));
                mActivity.startActivity(in);
            }
        });

        holder.linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mActivity, EventDetailsActivity.class);
                in.putExtra("EventId", mEventPojo.getEventId());
                in.putExtra("EventInternalName", mEventPojo.getInternalName());
                in.putExtra("IsFavourite", mEventPojo.isFavourite());
                mActivity.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventListingData.size();
    }
}
