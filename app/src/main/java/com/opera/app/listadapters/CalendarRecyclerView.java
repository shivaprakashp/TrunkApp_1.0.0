package com.opera.app.listadapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.app.R;
import com.opera.app.activities.BuyTicketWebView;
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.pojo.events.eventlisiting.Events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CalendarRecyclerView extends RecyclerView.Adapter<CalendarRecyclerView.CalendarViewHolder> {

    private Context context;
    private List<Events> arrayList;
    private String currentDay, eventTime;
    String[] startTime;

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public TextViewWithFont txtCalendarEventName, txtCalendarTime;
        public ButtonWithFont btnCalendarDetail, btnBuyTickets;

        public CalendarViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            txtCalendarTime = itemView.findViewById(R.id.txtCalendarTime);
            txtCalendarEventName = itemView.findViewById(R.id.txtCalendarEventName);
            btnCalendarDetail = itemView.findViewById(R.id.btnCalendarDetail);
            btnBuyTickets = itemView.findViewById(R.id.btnBuyTickets);
        }
    }

    public CalendarRecyclerView(List<Events> arrayList, String currentDay) {
        this.arrayList = arrayList;
        this.currentDay = currentDay;
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

        for (int k = 0; k < events.getEventTime().size(); k++) {
            if (currentDay.equalsIgnoreCase(events.getEventTime().get(k).getFromTime().split("T")[0])) {
                startTime = events.getStartTime().split(" ");
                eventTime = events.getEventTime().get(k).getFromTime().split("T")[1].substring(0, 2) + ":" + events.getEventTime().get(k).getFromTime().split("T")[1].substring(2, 4);
            }
        }

        try {
            holder.txtCalendarTime.setText(new SimpleDateFormat("K:mm").format(new SimpleDateFormat("H:mm").parse(eventTime)) + "\n" + startTime[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

        holder.btnBuyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, BuyTicketWebView.class);
                in.putExtra("URL", events.getBuyNowLink());
                in.putExtra("Header", context.getResources().getString(R.string.buy_tickets));
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
