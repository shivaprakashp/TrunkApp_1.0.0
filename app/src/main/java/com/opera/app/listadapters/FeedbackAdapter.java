package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.activities.CommonWebViewActivity;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.favouriteandsettings.OrderHistory;
import com.opera.app.utils.Connections;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 1000632 on 6/4/2018.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    private Activity mActivity;
    private ArrayList<OrderHistory> mFeedbackListingArray;
    private ArrayList<Events> mEventListingData = new ArrayList<>();
    private String[] startTime;
    private String eventDates;
    private String eventTime;
    private CustomToast customToast;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtShowName, txtShowDesc, txtShowDateAndTime;
        private LinearLayout linearFeedbackParent;
        private ImageView img_feedback;

        public MyViewHolder(View view) {
            super(view);
            linearFeedbackParent = view.findViewById(R.id.linearFeedbackParent);
            txtShowName = view.findViewById(R.id.txtShowName);
            txtShowDesc = view.findViewById(R.id.txtShowDesc);
            txtShowDateAndTime = view.findViewById(R.id.txtShowDateAndTime);
            img_feedback = view.findViewById(R.id.img_feedback);

            customToast = new CustomToast(mActivity);
        }
    }

    public FeedbackAdapter(Activity mActivity, ArrayList<OrderHistory> mFeedbackListingArray) {
        this.mActivity = mActivity;
        this.mFeedbackListingArray = mFeedbackListingArray;
    }

    public void RefreshList(ArrayList<OrderHistory> mFeedbackListingArray) {
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
        final OrderHistory mFeedbackCurrentItem = mFeedbackListingArray.get(position);
        holder.txtShowName.setText(mFeedbackCurrentItem.getEventName());
        holder.txtShowDesc.setText(R.string.feedback_comment);

//        startTime = mFeedbackCurrentItem.getStartTime().split(" ");
        //eventTime = mFeedbackCurrentItem.getDateTime().split("T")[1].substring(0, 2) + ":" + mFeedbackCurrentItem.getDateTime().split("T")[1].substring(2, 4);
        eventTime = mFeedbackCurrentItem.getDateTime().split("T")[1];
        eventDates = mFeedbackCurrentItem.getDateTime().split("T")[0];

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(eventDates);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String finalDate = timeFormat.format(myDate);

        holder.txtShowDateAndTime.setText(new StringBuilder().append(mActivity.getResources().getString(R.string.feedback_attended_time)).append(" ").append(finalDate).append(" ").append(eventTime).append(" "));

        EventListingDB mEventDetailsDB = new EventListingDB(mActivity);
        mEventDetailsDB.open();
        mEventListingData = mEventDetailsDB.fetchAllEvents();
        mEventDetailsDB.close();

        for (int i = 0; i < mEventListingData.size(); i++) {
            if (mEventListingData.get(i).getEventId().equals(mFeedbackCurrentItem.getEventId())) {
                Picasso.with(mActivity).load(mEventListingData.get(i).getHighlightedImage())
                        .into(holder.img_feedback, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                            }
                        });
            }
        }
        holder.linearFeedbackParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Connections.isConnectionAlive(mActivity)) {
                    Intent intent = new Intent(mActivity, CommonWebViewActivity.class);
                    intent.putExtra("URL", mFeedbackCurrentItem.getFeedBackUrl());
                    intent.putExtra("Header", mActivity.getResources().getString(R.string.menu_feedback));
                    mActivity.startActivity(intent);
                } else {
                    customToast.showErrorToast(mActivity.getResources().getString(R.string.internet_error_msg));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFeedbackListingArray.size();
    }
}
