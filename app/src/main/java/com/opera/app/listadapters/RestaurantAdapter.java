package com.opera.app.listadapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.pojo.restaurant.RestaurantListing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1000632 on 4/9/2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private ArrayList<RestaurantListing> mRestaurantList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtRestaurantName, mTxtRestaurantPlace;
        public ImageView mImgRestaurantImage;
        public Button mBtnReserveATable;

        public MyViewHolder(View view) {
            super(view);
            mTxtRestaurantName = (TextView) view.findViewById(R.id.mTxtRestaurantName);
            mTxtRestaurantPlace = (TextView) view.findViewById(R.id.mTxtRestaurantPlace);
            mImgRestaurantImage = (ImageView) view.findViewById(R.id.mImgRestaurantImage);
            mBtnReserveATable = (Button) view.findViewById(R.id.mBtnReserveATable);
        }
    }

    public RestaurantAdapter(ArrayList<RestaurantListing> mRestaurantList) {
        this.mRestaurantList = mRestaurantList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resturant_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RestaurantListing mRestaurantListing = mRestaurantList.get(position);
        holder.mTxtRestaurantName.setText(mRestaurantListing.getmRestaurantName());
        holder.mTxtRestaurantPlace.setText(mRestaurantListing.getmRestaurantPlace());
    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }
}
