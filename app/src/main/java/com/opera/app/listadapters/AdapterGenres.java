package com.opera.app.listadapters;

import android.app.Activity;
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
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterGenres extends RecyclerView.Adapter<AdapterGenres.MyViewHolder> {

    private ArrayList<GenreList> mEventListingData;
    private Activity mActivity;
    Animation slide_in_left;
    Animation slide_out_left;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgEvent, imgFavourite, imgShare, imgInfo;
        public Button btnBuyTickets;
        public ProgressBar progressImageLoader;
        public LinearLayout linearHolder;
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
        }
    }

    public AdapterGenres(Activity mActivity, ArrayList<GenreList> mEventListingData) {
        this.mActivity = mActivity;
        this.mEventListingData = mEventListingData;

        slide_in_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_in_left);

        slide_out_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_out_left);
    }

    public void RefreshList(ArrayList<GenreList> mEventListingData) {
        this.mEventListingData = mEventListingData;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GenreList mEventPojo = mEventListingData.get(position);

        //holder.txtEventDate.setText(mEventPojo.getFrom() + " to " + mEventPojo.getTo());
        holder.txtEventInfo.setText(Html.fromHtml(mEventPojo.getDescription()));
        Picasso.with(mActivity).load(mEventPojo.getImage()).fit().centerCrop()
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

            }
        });
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    }

    @Override
    public int getItemCount() {
        return mEventListingData.size();
    }
}
