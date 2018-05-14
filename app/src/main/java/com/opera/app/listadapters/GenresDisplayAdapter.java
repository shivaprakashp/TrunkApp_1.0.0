package com.opera.app.listadapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.pojo.events.eventlisiting.GenreList;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/14/2018.
 */

public class GenresDisplayAdapter extends RecyclerView.Adapter<GenresDisplayAdapter.MyViewHolder> {

    Activity mActivity;
    ArrayList<GenreList> mGenresListing;

    public GenresDisplayAdapter(Activity mActivity, ArrayList<GenreList> mGenresListing) {
        this.mActivity = mActivity;
        this.mGenresListing = mGenresListing;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genres_title_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GenreList mEventPojo = mGenresListing.get(position);

        holder.txtGenreName.setText(mEventPojo.getGenere());

    }

    @Override
    public int getItemCount() {
        return mGenresListing.size();
    }

    public void RefreshList(ArrayList<GenreList> mGenresListing) {
        this.mGenresListing=mGenresListing;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtGenreName;

        public MyViewHolder(View view) {
            super(view);
            txtGenreName = (TextView) view.findViewById(R.id.txtGenreName);
            ;
        }
    }
}
