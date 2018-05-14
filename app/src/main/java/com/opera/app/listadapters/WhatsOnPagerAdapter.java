package com.opera.app.listadapters;

/**
 * Created by 1000632 on 5/11/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
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
import com.opera.app.database.events.EventDetailsDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WhatsOnPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Events> mWhatsEvents;
    private EventListingDB mEventListingDB;
    Animation slide_in_left;
    Animation slide_out_left;

    public WhatsOnPagerAdapter(Context context, ArrayList<Events> mWhatsEvents) {
        mContext = context;
        this.mWhatsEvents = mWhatsEvents;

        slide_in_left = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_slide_in_left);

        slide_out_left = AnimationUtils.loadAnimation(mContext,
                R.anim.anim_slide_out_left);
        mEventListingDB = new EventListingDB(mContext);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        final Events eventObject = mWhatsEvents.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.event_row, collection, false);

        TextView txtEventInfo = (TextView) view.findViewById(R.id.txtEventInfo);
        TextView txtEventDate = (TextView) view.findViewById(R.id.txtEventDate);
        final ProgressBar progressImageLoader = (ProgressBar) view.findViewById(R.id.progressImageLoader);
        ImageView imgEvent = (ImageView) view.findViewById(R.id.imgEvent);
        ImageView imgInfo = (ImageView) view.findViewById(R.id.imgInfo);
        ImageView imgFavourite = (ImageView) view.findViewById(R.id.imgFavourite);

        final LinearLayout linearHolder = (LinearLayout) view.findViewById(R.id.linearHolder);
        Button btnBuyTickets = (Button) view.findViewById(R.id.btnBuyTickets);

        txtEventInfo.setText(Html.fromHtml(eventObject.getDescription()));
        txtEventDate.setText(eventObject.getFrom() + " to " + eventObject.getTo());

        if (eventObject.isFavourite().equalsIgnoreCase("true")) {
            imgFavourite.setImageDrawable(mContext.getResources().getDrawable(R.drawable.fav_selected));
        } else {
            imgFavourite.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_favourite));
        }

        Picasso.with(mContext).load(eventObject.getImage()).fit().centerCrop()
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

        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventListingDB.open();
                if (eventObject.isFavourite().equalsIgnoreCase("true")) {
                    eventObject.setFavourite("false");
                    mEventListingDB.UpdateFavouriteData(eventObject.getEventId(), "false");
                } else {
                    eventObject.setFavourite("true");
                    mEventListingDB.UpdateFavouriteData(eventObject.getEventId(), "true");
                }
                mEventListingDB.close();
                notifyDataSetChanged();
            }
        });

        btnBuyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, CommonWebViewActivity.class);
                in.putExtra("URL", eventObject.getBuyNowLink());
                in.putExtra("Header", mContext.getResources().getString(R.string.buy_tickets));
                mContext.startActivity(in);
            }
        });

        view.setTag(eventObject);
        collection.addView(view);
        return view;
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

}