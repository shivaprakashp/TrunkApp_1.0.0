package com.opera.app.listadapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.customwidget.CustomToast;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtNotifyTitle, mTxtNotifyDesc, mTxtPriceFrom;
        public ImageView mImgNotifyImage;
        public ProgressBar progressImageLoader;

        public MyViewHolder(View view) {
            super(view);
            mTxtNotifyTitle = (TextView) view.findViewById(R.id.mTxtNotifyTitle);
            mTxtNotifyDesc = (TextView) view.findViewById(R.id.mTxtNotifyDesc);
            mTxtPriceFrom = (TextView) view.findViewById(R.id.mTxtPriceFrom);

            mImgNotifyImage = (ImageView) view.findViewById(R.id.mImgNotifyImage);
            progressImageLoader = (ProgressBar) view.findViewById(R.id.progressImageLoader);

            manager = new SessionManager(mActivity);
            customToast = new CustomToast(mActivity);
        }
    }

    public NotificationAdapter(Activity mActivity, ArrayList<Notification> mNotificationList) {
        this.mActivity = mActivity;
        this.mNotificationList = mNotificationList;
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
        holder.mTxtNotifyTitle.setText(mNotification.getNotifyTitle());
        holder.mTxtNotifyDesc.setText(mNotification.getNotifyDescribe());
        holder.mTxtPriceFrom.setText(mNotification.getPriceFrom());

        Picasso.with(mActivity).load(mNotification.getNotifyImage()).fit().centerCrop()
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
    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }
}
