package com.opera.app.listadapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.pojo.events.eventlisiting.Events;

import java.util.ArrayList;

public class CalendarRecyclerView extends RecyclerView.Adapter<CalendarRecyclerView.CalendarViewHolder> {

    private ArrayList<Events> arrayList;

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public TextViewWithFont txtCalendarEventName, txtCalendarTime;

        public CalendarViewHolder(View itemView) {
            super(itemView);

            txtCalendarTime = (TextViewWithFont) itemView.findViewById(R.id.txtCalendarTime);
            txtCalendarEventName = (TextViewWithFont) itemView.findViewById(R.id.txtCalendarEventName);
        }
    }
    public CalendarRecyclerView(ArrayList<Events> arrayList){
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
        Events events = arrayList.get(position);

        String[] startTime = events.getStartTime().split(" ");
        holder.txtCalendarTime.setText(startTime[0]+"\n"+startTime[1]);
        holder.txtCalendarEventName.setText(events.getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
