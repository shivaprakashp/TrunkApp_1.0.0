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
import com.opera.app.pojo.events.eventlisiting.Events;

import java.util.List;

public class CalendarRecyclerView extends RecyclerView.Adapter<CalendarRecyclerView.CalendarViewHolder> {

    private Context context;
    private List<Events> arrayList;
    private Activity mActivity;

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public TextViewWithFont txtCalendarEventName, txtCalendarTime;
        public ButtonWithFont btnCalendarDetail;

        public CalendarViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            txtCalendarTime = itemView.findViewById(R.id.txtCalendarTime);
            txtCalendarEventName = itemView.findViewById(R.id.txtCalendarEventName);
            btnCalendarDetail = itemView.findViewById(R.id.btnCalendarDetail);
        }
    }

    public CalendarRecyclerView(List<Events> arrayList) {
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
        holder.txtCalendarTime.setText(events.getEventTime().get(0).getFromTime().split("T")[1].substring(0,2)
                +":"+events.getEventTime().get(0).getFromTime().split("T")[1].substring(2,4)+ "\n" + startTime[1]);
        holder.txtCalendarEventName.setText(events.getName());

        holder.btnCalendarDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra("EventId", events.getEventId());
                intent.putExtra("EventInternalName", events.getInternalName());
                intent.putExtra("IsFavourite", events.isFavourite());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
