package com.opera.app.listadapters;

/**
 * Created by 1000632 on 2/19/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CoverFlowAdapter extends BaseAdapter {

    private ArrayList<Events> mHighlightedEvents = new ArrayList<>(0);
    private Context mContext;
    private EventListingDB mEventListingDB;

    public CoverFlowAdapter(Context context, ArrayList<Events> mHighlightedEvents) {
        mContext = context;
        this.mHighlightedEvents = mHighlightedEvents;
        mEventListingDB = new EventListingDB(mContext);
    }

    @Override
    public int getCount() {
        return mHighlightedEvents.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = rowView.findViewById(R.id.label);
            viewHolder.image = rowView
                    .findViewById(R.id.image);
            viewHolder.frameParent = rowView.findViewById(R.id.frameParent);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        Picasso.with(mContext).load(mHighlightedEvents.get(position).getHighlightedImage()).fit()
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                        /*holder.progressImageLoader.setVisibility(View.GONE);*/
                    }

                    @Override
                    public void onError() {
                       /* holder.progressImageLoader.setVisibility(View.GONE);*/
                    }
                });

        holder.frameParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, EventDetailsActivity.class);
                in.putExtra("EventId", mHighlightedEvents.get(position).getEventId());
                in.putExtra("EventInternalName", mHighlightedEvents.get(position).getInternalName());

                //Taking this value from DB as we are not updating CarouselView for a reason
                mEventListingDB.open();
                if (mEventListingDB.IsFavouriteForSpecificEvent(mHighlightedEvents.get(position).getEventId())) {
                    in.putExtra("IsFavourite", "true");
                } else {
                    in.putExtra("IsFavourite", "false");
                }
                mEventListingDB.close();
                mContext.startActivity(in);
            }
        });

        return rowView;
    }


    static class ViewHolder {
        public TextView text;
        public FrameLayout frameParent;
        public ImageView image;
    }
}