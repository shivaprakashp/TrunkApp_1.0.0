package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.activities.RestaurantCompleteDetails;
import com.opera.app.constants.AppConstants;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.dialogues.FindOutMoreDialogue;
import com.opera.app.pojo.restaurant.RestaurantsData;
import com.opera.app.preferences.SessionManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 1000632 on 4/9/2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> implements Filterable {

    private ArrayList<RestaurantsData> mRestaurantList;
    private Activity mActivity;
    private SessionManager manager;
    private CustomToast customToast;

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<RestaurantsData> FilteredArrList = new ArrayList<RestaurantsData>();

                if (mRestaurantList == null) {
                    mRestaurantList = new ArrayList<RestaurantsData>(mRestaurantList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mRestaurantList.size();
                    results.values = mRestaurantList;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mRestaurantList.size(); i++) {
                        String data = mRestaurantList.get(i).restName;
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new RestaurantsData(mRestaurantList.get(i).getRestId(),
                                    mRestaurantList.get(i).getRestName(),
                                    mRestaurantList.get(i).getRestImage(),
                                    mRestaurantList.get(i).getRestPlace(),
                                    mRestaurantList.get(i).getRestLocation(),
                                    mRestaurantList.get(i).getRestBookUrl(),
                                    mRestaurantList.get(i).getRestStatus(),
                                    mRestaurantList.get(i).getRestDetails(),
                                    mRestaurantList.get(i).getOpenHour(),
                                    mRestaurantList.get(i).getPhoneNumber(),
                                    mRestaurantList.get(i).getEmail()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mRestaurantList = (ArrayList<RestaurantsData>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }
        };

        return filter;
    }

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

    public void RefreshList(ArrayList<RestaurantsData> mRestaurantList) {
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

                FindOutMoreDialogue dialogue = new FindOutMoreDialogue(mActivity, mRestaurantListing.getPhoneNumber(), mRestaurantListing.getEmail());
                dialogue.show();

                /*if (Connections.isConnectionAlive(mActivity)) {
                    if (manager.isUserLoggedIn()) {
                        if (mRestaurantListing.getRestId().equalsIgnoreCase(AppConstants.SEAN_CONOLLY_RESTAURANT_ID)) {
                            Intent intent = new Intent(mActivity, ReserveATableActivity.class);
                            mActivity.startActivity(intent);
                        } else {
                            Intent in = new Intent(mActivity, CommonWebViewActivity.class);
                            in.putExtra("URL", mRestaurantListing.getRestBookUrl());
                            in.putExtra("Header", mRestaurantListing.getRestName());
                            mActivity.startActivity(in);
                        }
                    } else {
                        GuestDialog dialog = new GuestDialog(mActivity, mActivity.getString(R.string.guest_title), mActivity.getString(R.string.guest_msg));
                        dialog.show();
                    }
                } else {
                    //Toast.makeText(mActivity, mActivity.getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                    customToast.showErrorToast(mActivity.getResources().getString(R.string.internet_error_msg));
                }*/
            }
        });

        holder.mImgRestaurantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, RestaurantCompleteDetails.class);
                intent.putExtra(AppConstants.GETRESTAURANTLISTING.GETRESTAURANTLISTING, mRestaurantListing);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }
}
