package com.opera.app.listadapters;

/**
 * Created by 1000632 on 2/19/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.pojo.events.events;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CoverFlowAdapter extends BaseAdapter {

    private ArrayList<events> mHighlightedEvents = new ArrayList<>(0);
    private Context mContext;

    public CoverFlowAdapter(Context context, ArrayList<events> mHighlightedEvents) {
        mContext = context;
        this.mHighlightedEvents = mHighlightedEvents;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.image);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        Picasso.with(mContext).load(mHighlightedEvents.get(position).getEventThumbImage()).fit().centerCrop()
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


        return rowView;
    }


    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }
}