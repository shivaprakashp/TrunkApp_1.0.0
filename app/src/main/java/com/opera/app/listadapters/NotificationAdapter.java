package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.activities.RestaurantCompleteDetails;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.pojo.notifications.Notification;
import com.opera.app.preferences.SessionManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private ArrayList<Notification> mNotificationList;
    private Activity mActivity;
    private CustomToast customToast;
    private SessionManager manager;
    private EventListingDB mEventListingDB;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtNotifyTitle, mTxtNotifyDesc, mTxtPriceFrom;
        public RelativeLayout relativeParent;
        public ImageView mImgNotifyImage;
        public ProgressBar progressImageLoader;

        public MyViewHolder(View view) {
            super(view);
            mTxtNotifyTitle = (TextView) view.findViewById(R.id.mTxtNotifyTitle);
            mTxtNotifyDesc = (TextView) view.findViewById(R.id.mTxtNotifyDesc);
            mTxtPriceFrom = (TextView) view.findViewById(R.id.mTxtPriceFrom);
            relativeParent = (RelativeLayout) view.findViewById(R.id.relativeParent);

            mImgNotifyImage = (ImageView) view.findViewById(R.id.mImgNotifyImage);
            progressImageLoader = (ProgressBar) view.findViewById(R.id.progressImageLoader);

            manager = new SessionManager(mActivity);
            customToast = new CustomToast(mActivity);
        }
    }

    public NotificationAdapter(Activity Activity, ArrayList<Notification> mNotificationList) {
        this.mActivity = Activity;
        this.mNotificationList = mNotificationList;
        mEventListingDB = new EventListingDB(mActivity);
    }

    public void RefreshList(ArrayList<Notification> mNotificationList) {
        this.mNotificationList = mNotificationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Notification mNotification = mNotificationList.get(position);
        holder.mTxtNotifyTitle.setText(mNotification.getTitle());
        holder.mTxtNotifyDesc.setText(mNotification.getDescription());
        holder.mTxtPriceFrom.setText(mNotification.getPrice());

        Picasso.with(mActivity).load(mNotification.getImage()).fit().centerCrop()
                .into(holder.mImgNotifyImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressImageLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressImageLoader.setVisibility(View.GONE);
                    }
                });

        holder.relativeParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNotification.getPromotionType().equalsIgnoreCase(mActivity.getResources().getString(R.string.restaurant))) {

                    Intent in = new Intent(mActivity, RestaurantCompleteDetails.class);
                    in.putExtra("RestaurantId", mNotification.getPromotionItemId());
                    mActivity.startActivity(in);

                } else {

                    mEventListingDB.open();
                    boolean IsFavourite = mEventListingDB.IsFavouriteForSpecificEvent(mNotification.getPromotionItemId());
                    mEventListingDB.close();

                    Intent in = new Intent(mActivity, EventDetailsActivity.class);
                    in.putExtra("EventId", mNotification.getPromotionItemId());

                    if (IsFavourite) {
                        in.putExtra("IsFavourite", "true");
                    } else {
                        in.putExtra("IsFavourite", "false");
                    }
                    mActivity.startActivity(in);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }
}
