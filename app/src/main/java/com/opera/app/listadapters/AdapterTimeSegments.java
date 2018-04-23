package com.opera.app.listadapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.pojo.restaurant.booktable.Meal_Periods;

import java.util.ArrayList;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.pojo.restaurant.booktable.Meal_Periods;
import com.opera.app.pojo.restaurant.booktable.Time_Segment_Responses;
import com.opera.app.pojo.restaurant.booktable.Time_Segments;

import java.util.ArrayList;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class AdapterTimeSegments extends BaseAdapter {

    private ArrayList<Time_Segments> mTimeSegments;
    private Activity mActivity;

    public AdapterTimeSegments(Activity mActivity, ArrayList<Time_Segments> mTimeSegments) {
        this.mActivity = mActivity;
        this.mTimeSegments = mTimeSegments;
    }

    @Override
    public int getCount() {
        return mTimeSegments.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.custom_spinner_black_text, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String mCompleteTime = mTimeSegments.get(position).getMeal_Period_Time();
        String[] split = mCompleteTime.split("T");
        String mOnlyTime = split[1];

       /* Meal_Periods currentItem = (Meal_Periods) getItem(position);*/
        viewHolder.txtSpnnierName.setText(mOnlyTime);

        return convertView;
    }

    private class ViewHolder {
        TextView txtSpnnierName;

        public ViewHolder(View view) {
            txtSpnnierName = (TextView) view.findViewById(R.id.txtSpnnierName);
        }
    }

}
