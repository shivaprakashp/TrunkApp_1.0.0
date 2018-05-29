package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.opera.app.R;
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.pojo.events.eventlisiting.Events;

import java.util.ArrayList;
import java.util.List;

public class CalendarRecyclerView extends RecyclerView.Adapter<CalendarRecyclerView.CalendarViewHolder> {

    private List<Events> arrayList;
    private Activity mActivity;

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public TextViewWithFont txtCalendarEventName, txtCalendarTime;
        public LinearLayout linearParent;

        public CalendarViewHolder(View itemView) {
            super(itemView);

            linearParent = (LinearLayout) itemView.findViewById(R.id.linearParent);
            txtCalendarTime = (TextViewWithFont) itemView.findViewById(R.id.txtCalendarTime);
            txtCalendarEventName = (TextViewWithFont) itemView.findViewById(R.id.txtCalendarEventName);
        }
    }

    public CalendarRecyclerView(Activity mActivity, List<Events> arrayList) {
        this.mActivity = mActivity;
        this.arrayList = arrayList;
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_calendar_view, parent, false);

        return new CalendarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        final Events events = arrayList.get(position);

        String[] startTime = events.getStartTime().split(" ");
        holder.txtCalendarTime.setText(startTime[0] + "\n" + startTime[1]);
        holder.txtCalendarEventName.setText(events.getName());

        holder.linearParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mActivity, EventDetailsActivity.class);
                in.putExtra("EventId", events.getEventId());
                in.putExtra("EventInternalName", events.getInternalName());
                in.putExtra("IsFavourite", events.isFavourite());
                mActivity.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
