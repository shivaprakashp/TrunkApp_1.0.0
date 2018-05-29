package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.app.R;
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.pojo.events.eventdetails.EventDetails;
import com.opera.app.pojo.events.eventlisiting.Events;

import java.util.ArrayList;
import java.util.List;

public class CalendarRecyclerView extends RecyclerView.Adapter<CalendarRecyclerView.CalendarViewHolder> {

    private Context context;
    private List<Events> arrayList;

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public TextViewWithFont txtCalendarEventName, txtCalendarTime;
        public ButtonWithFont btnCalendarDetail;

        public CalendarViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            txtCalendarTime = (TextViewWithFont) itemView.findViewById(R.id.txtCalendarTime);
            txtCalendarEventName = (TextViewWithFont) itemView.findViewById(R.id.txtCalendarEventName);
            btnCalendarDetail = (ButtonWithFont) itemView.findViewById(R.id.btnCalendarDetail);
        }
    }
    public CalendarRecyclerView(List<Events> arrayList){
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
        holder.txtCalendarTime.setText(startTime[0]+"\n"+startTime[1]);
        holder.txtCalendarEventName.setText(events.getName());

        holder.btnCalendarDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra("", events.getEventId());
                intent.putExtra("", events.getInternalName());
                intent.putExtra("", events.getEventId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
