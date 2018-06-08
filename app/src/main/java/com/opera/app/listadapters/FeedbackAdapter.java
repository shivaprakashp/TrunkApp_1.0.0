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
import com.opera.app.pojo.feedback.FeedbackResponse;
import com.opera.app.preferences.SessionManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 1000632 on 6/4/2018.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    private Activity mActivity;
    private CustomToast customToast;
    private ArrayList<FeedbackResponse> mFeedbackListingArray;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtShowName,txtShowDesc,txtShowDateAndTime;
        private ImageView img_profile_chooser;

        public MyViewHolder(View view) {
            super(view);
            txtShowName = (TextView) view.findViewById(R.id.txtShowName);
            txtShowDesc = (TextView) view.findViewById(R.id.txtShowDesc);
            txtShowDateAndTime = (TextView) view.findViewById(R.id.txtShowDateAndTime);
            img_profile_chooser=(ImageView)view.findViewById(R.id.img_profile_chooser);

            customToast = new CustomToast(mActivity);
        }
    }

    public FeedbackAdapter(Activity mActivity, ArrayList<FeedbackResponse> mFeedbackListingArray) {
        this.mActivity = mActivity;
        this.mFeedbackListingArray = mFeedbackListingArray;
    }

    public void RefreshList(ArrayList<FeedbackResponse> mFeedbackListingArray) {
        this.mFeedbackListingArray = mFeedbackListingArray;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_feedback, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FeedbackResponse mFeedbackCurrentItem = mFeedbackListingArray.get(position);
        holder.txtShowName.setText(mFeedbackCurrentItem.getmShowName());
        holder.txtShowDesc.setText(mFeedbackCurrentItem.getmShowDescription());
        holder.txtShowDateAndTime.setText(mFeedbackCurrentItem.getmDateAndTime());

        Picasso.with(mActivity).load(mFeedbackCurrentItem.getmShowImage()).fit().centerCrop()
                .into(holder.img_profile_chooser, new Callback() {
                    @Override
                    public void onSuccess() {
                       // holder.progressImageLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                       // holder.progressImageLoader.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mFeedbackListingArray.size();
    }
}
