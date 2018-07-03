package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.activities.GenresEventsActivity;
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterGenres extends RecyclerView.Adapter<AdapterGenres.MyViewHolder> {

    private ArrayList<GenreList> mGenresListingData;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgEvent;
        public ProgressBar progressImageLoader;
        public LinearLayout linearHolder, linearParent;
        public TextView txtGenresName;
        public Button mBtnFindShows;

        public MyViewHolder(View view) {
            super(view);
            imgEvent = view.findViewById(R.id.imgEvent);
            progressImageLoader = view.findViewById(R.id.progressImageLoader);
            linearParent = view.findViewById(R.id.linearParent);
            txtGenresName = view.findViewById(R.id.txtGenresName);
            mBtnFindShows = view.findViewById(R.id.mBtnFindShows);
        }
    }

    public AdapterGenres(Activity mActivity, ArrayList<GenreList> mGenresListingData) {
        this.mActivity = mActivity;
        this.mGenresListingData = mGenresListingData;
    }

    public void RefreshList(ArrayList<GenreList> mGenresListingData) {
        this.mGenresListingData = mGenresListingData;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row_for_genres, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GenreList mGenrePojo = mGenresListingData.get(position);

        holder.txtGenresName.setText(mGenrePojo.getGenere());

        Picasso.with(mActivity).load(mGenrePojo.getImage())
                .into(holder.imgEvent, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressImageLoader.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressImageLoader.setVisibility(View.GONE);
                    }
                });

        holder.mBtnFindShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mActivity, GenresEventsActivity.class);
                in.putExtra("SelectedGenre", mGenrePojo.getGenere());
                mActivity.startActivity(in);
                }
        });

        holder.linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               }
        });
    }

    @Override
    public int getItemCount() {
        return mGenresListingData.size();
    }
}
