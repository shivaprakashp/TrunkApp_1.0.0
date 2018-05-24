package com.opera.app.listadapters;

/**
 * Created by 1000632 on 5/11/2018.
 */

import android.app.Activity;
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
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WhatsOnPagerAdapter extends PagerAdapter {

    private Activity mActivity;
    private String mFrom = "";
    private ArrayList<Events> mWhatsEvents;
    private EventListingDB mEventListingDB;
    Animation slide_in_left;
    Animation slide_out_left;

    public WhatsOnPagerAdapter(Activity mActivity, ArrayList<Events> mWhatsEvents, String mFrom) {
        this.mActivity = mActivity;
        this.mWhatsEvents = mWhatsEvents;

        slide_in_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_in_left);

        slide_out_left = AnimationUtils.loadAnimation(mActivity,
                R.anim.anim_slide_out_left);
        mEventListingDB = new EventListingDB(mActivity);
        this.mFrom = mFrom;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        final Events eventObject = mWhatsEvents.get(position);
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.event_row, collection, false);

        TextView txtEventInfo = (TextView) view.findViewById(R.id.txtEventInfo);
        TextView txtEventDate = (TextView) view.findViewById(R.id.txtEventDate);
        final ProgressBar progressImageLoader = (ProgressBar) view.findViewById(R.id.progressImageLoader);
        ImageView imgEvent = (ImageView) view.findViewById(R.id.imgEvent);
        ImageView imgInfo = (ImageView) view.findViewById(R.id.imgInfo);
        final ImageView imgFavourite = (ImageView) view.findViewById(R.id.imgFavourite);
        ImageView imgShare = (ImageView) view.findViewById(R.id.imgShare);
        LinearLayout linearParent = (LinearLayout) view.findViewById(R.id.linearParent);

        final LinearLayout linearHolder = (LinearLayout) view.findViewById(R.id.linearHolder);
        Button btnBuyTickets = (Button) view.findViewById(R.id.btnBuyTickets);

        txtEventInfo.setText(Html.fromHtml(eventObject.getMobileDescription()));
        txtEventDate.setText(eventObject.getFrom() + " to " + eventObject.getTo());

        if (eventObject.isFavourite().equalsIgnoreCase("true")) {
            imgFavourite.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_favourite_selected));
        } else {
            imgFavourite.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_favourite));
        }

        Picasso.with(mActivity).load(eventObject.getImage()).fit().centerCrop()
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

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Uri screenshotUri;
                try {
                    screenshotUri = new Uri("http://www.google.com/");
                }
                catch (URISyntaxException e) {
                }

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                 screenshotUri = Uri.parse("file:///sdcard/image.jpg");
                sharingIntent.setType("image*//*");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Body text of the new status");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                mActivity.startActivity(Intent.createChooser(sharingIntent, "Share image using"));*/
//                OperaUtils.ShareEventDetails(mActivity, eventObject.getDescription(), eventObject.getImage());
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
                Intent in = new Intent(mActivity, CommonWebViewActivity.class);
                in.putExtra("URL", eventObject.getBuyNowLink());
                in.putExtra("Header", mActivity.getResources().getString(R.string.buy_tickets));
                mActivity.startActivity(in);
            }
        });

        linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mActivity, EventDetailsActivity.class);
                in.putExtra("EventId", eventObject.getEventId());
                in.putExtra("EventInternalName", eventObject.getInternalName());
                in.putExtra("IsFavourite", eventObject.isFavourite());
                mActivity.startActivity(in);

                if (!mFrom.equalsIgnoreCase("HomePage")) {
                    mActivity.finish();
                }
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