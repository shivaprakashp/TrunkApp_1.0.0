package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.activities.CommonWebViewActivity;
import com.opera.app.activities.ReserveATableActivity;
import com.opera.app.constants.AppConstants;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.dialogues.GuestDialog;
import com.opera.app.pojo.restaurant.RestaurantsData;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 1000632 on 4/9/2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private ArrayList<RestaurantsData> mRestaurantList;
    private Activity mActivity;
    private SessionManager manager;
    private CustomToast customToast;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtRestaurantName, mTxtRestaurantPlace;
        public ImageView mImgRestaurantImage;
        public Button mBtnReserveATable;
        public ProgressBar progressImageLoader;

        public MyViewHolder(View view) {
            super(view);
            mTxtRestaurantName = (TextView) view.findViewById(R.id.mTxtRestaurantName);
            mTxtRestaurantPlace = (TextView) view.findViewById(R.id.mTxtRestaurantPlace);
            mImgRestaurantImage = (ImageView) view.findViewById(R.id.mImgRestaurantImage);
            mBtnReserveATable = (Button) view.findViewById(R.id.mBtnReserveATable);
            progressImageLoader = (ProgressBar) view.findViewById(R.id.progressImageLoader);

            manager = new SessionManager(mActivity);
            customToast = new CustomToast(mActivity);
        }
    }

    public RestaurantAdapter(Activity mActivity, ArrayList<RestaurantsData> mRestaurantList) {
        this.mActivity = mActivity;
        this.mRestaurantList = mRestaurantList;
    }

    public void RefreshList(ArrayList<RestaurantsData> mRestaurantList){
        this.mRestaurantList = mRestaurantList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resturant_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final RestaurantsData mRestaurantListing = mRestaurantList.get(position);
        holder.mTxtRestaurantName.setText(mRestaurantListing.getRestName());
        holder.mTxtRestaurantPlace.setText("at " + mRestaurantListing.getRestPlace());

        Picasso.with(mActivity).load(mRestaurantListing.getRestImage()).fit().centerCrop()
                .into(holder.mImgRestaurantImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressImageLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressImageLoader.setVisibility(View.GONE);
                    }
                });

        holder.mBtnReserveATable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Connections.isConnectionAlive(mActivity)) {
                    if (manager.isUserLoggedIn()) {
                        if (mRestaurantListing.getRestId().equalsIgnoreCase(AppConstants.SEAN_CONOLLY_RESTAURANT_ID)) {
                            Intent intent = new Intent(mActivity, ReserveATableActivity.class);
                            mActivity.startActivity(intent);
                        }
                        else {
                            Intent in = new Intent(mActivity, CommonWebViewActivity.class);
                            in.putExtra("URL", mRestaurantListing.getRestBookUrl());
                            in.putExtra("Header", mRestaurantListing.getRestName());
                            mActivity.startActivity(in);
                        }
                    }else {
                        GuestDialog dialog = new GuestDialog(mActivity, mActivity.getString(R.string.guest_title), mActivity.getString(R.string.guest_msg) );
                        dialog.show();
                    }
                } else {
                    //Toast.makeText(mActivity, mActivity.getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                    customToast.showErrorToast(mActivity.getResources().getString(R.string.internet_error_msg));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }
}
