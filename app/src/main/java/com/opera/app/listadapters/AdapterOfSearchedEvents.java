package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/3/2018.
 */

public class AdapterOfSearchedEvents extends RecyclerView.Adapter<AdapterOfSearchedEvents.MyViewHolder> {

    private Activity mActivity;
    private ArrayList<Events> mEventListingData;

    public AdapterOfSearchedEvents(Activity mActivity, ArrayList<Events> mEventListingData) {
        this.mActivity = mActivity;
        this.mEventListingData = mEventListingData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row_for_search, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Events mEventPojo = mEventListingData.get(position);

        holder.txtEventName.setText(mEventPojo.getName());
        holder.txtEventDate.setText(mEventPojo.getFrom() + " to " + mEventPojo.getTo());
        holder.txtEventInfo.setText(Html.fromHtml(mEventPojo.getMobileDescription()));

        Picasso.with(mActivity).load(mEventPojo.getImage()).fit().centerCrop()
                .into(holder.imgEvent, new Callback() {
                    @Override
                    public void onSuccess() {
                        /*holder.progressImageLoader.setVisibility(View.GONE);*/
                    }

                    @Override
                    public void onError() {
                       /* holder.progressImageLoader.setVisibility(View.GONE);*/
                    }
                });

        holder.linearSearchParent.setOnClickListener(new View.OnClickListener() {
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

    public void filterList(ArrayList<Events> mEventListingData) {
        this.mEventListingData = mEventListingData;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgEvent;
        public Button btnBuyTickets;
        public LinearLayout linearSearchParent;
        public TextView txtEventName, txtEventInfo, txtEventDate;

        public MyViewHolder(View view) {
            super(view);
            imgEvent = (ImageView) view.findViewById(R.id.imgEvent);
            btnBuyTickets = (Button) view.findViewById(R.id.btnBuyTickets);
            txtEventName = (TextView) view.findViewById(R.id.txtEventName);
            txtEventInfo = (TextView) view.findViewById(R.id.txtEventInfo);
            txtEventDate = (TextView) view.findViewById(R.id.txtEventDate);
            linearSearchParent=(LinearLayout)view.findViewById(R.id.linearSearchParent);
            ;
        }
    }
}


