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
import com.opera.app.database.events.EventListingDB;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.utils.OperaUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarRecyclerView extends RecyclerView.Adapter<CalendarRecyclerView.CalendarViewHolder> {

    private Context context;
    private List<Events> arrayList;
    private EventListingDB mEventListingDB;
    private String currentDay, eventTime;
    SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
    SimpleDateFormat f3 = new SimpleDateFormat("hh:mm a", Locale.US);

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
        mEventListingDB = new EventListingDB(context);
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

        String startTime = "";

        for (int k = 0; k < events.getEventTime().size(); k++) {
            if (currentDay.equalsIgnoreCase(events.getEventTime().get(k).getFromTime().split("T")[0])) {
                /*startTime = events.getStartTime().split(" ");
                eventTime = events.getEventTime().get(k).getFromTime().split("T")[1].substring(0, 2) + ":" + events.getEventTime().get(k).getFromTime().split("T")[1].substring(2, 4);*/
                try {
                    Date mDate = f.parse(events.getEventTime().get(k).getFromTime());
                    /*String mDateStr=f2.format(mDate);
                    Log.e("Converted only date", mDateStr);*/
                    startTime = f3.format(mDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            holder.txtCalendarTime.setText(startTime);
            /*holder.txtCalendarTime.setText(new SimpleDateFormat("K:mm").format(new SimpleDateFormat("H:mm").parse(eventTime)) + "\n" + startTime[1]);*/
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.txtCalendarEventName.setText(events.getName());

        holder.btnCalendarDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra("EventId", events.getEventId());
                intent.putExtra("EventInternalName", events.getInternalName());

                mEventListingDB.open();
                if (mEventListingDB.IsFavouriteForSpecificEvent(events.getEventId())) {
                    intent.putExtra("IsFavourite", "true");
                } else {
                    intent.putExtra("IsFavourite", "false");
                }
                mEventListingDB.close();

                /*intent.putExtra("IsFavourite", events.isFavourite());*/
                context.startActivity(intent);

            }
        });

        holder.btnBuyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperaUtils.BuyTicketCommmonFunction((Activity) context, events.getBuyNowLink(), context.getResources().getString(R.string.buy_tickets));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
