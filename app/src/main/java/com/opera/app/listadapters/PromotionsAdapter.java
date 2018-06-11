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
import com.opera.app.pojo.notifications.Notification;
import com.opera.app.pojo.promotions.PromotionDetails;
import com.opera.app.preferences.SessionManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 1000632 on 6/11/2018.
 */

public class PromotionsAdapter extends RecyclerView.Adapter<PromotionsAdapter.MyViewHolder> {

    Activity mActivity;
    ArrayList<PromotionDetails> mPromotionList;

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

        }
    }

    public PromotionsAdapter(Activity mActivity, ArrayList<PromotionDetails> mPromotionList) {
        this.mActivity = mActivity;
        this.mPromotionList = mPromotionList;
    }

    public void RefreshList(ArrayList<PromotionDetails> mPromotionList) {
        this.mPromotionList = mPromotionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PromotionDetails mPromotionData = mPromotionList.get(position);
        holder.mTxtNotifyTitle.setText(mPromotionData.getTitle());
        holder.mTxtNotifyDesc.setText(mPromotionData.getDescription());
        holder.mTxtPriceFrom.setText(mPromotionData.getPrice());

        Picasso.with(mActivity).load(mPromotionData.getImage()).fit()
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
        return mPromotionList.size();
    }
}
