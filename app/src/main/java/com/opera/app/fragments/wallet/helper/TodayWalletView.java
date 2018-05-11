package com.opera.app.fragments.wallet.helper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.pojo.wallet.Event;

import java.util.List;

public class TodayWalletView extends LinearLayout {

    public TodayWalletView(Context context) {
        super(context);
        init(null, 0);
    }

    public TodayWalletView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TodayWalletView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void setEvents(List<Event> eventList){

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        TextViewWithFont txtEventTitle,
                txtWalletEventGenre,
                txtWalletEventDate,
                txtWalletEventDoor,
                txtWalletEventSection,
                txtWalletEventRow,
                txtWalletEventSeat;


        for ( int i = 0 ; i < eventList.size() ; i++ ){
            Event event = eventList.get(i);

            rowView = (ViewGroup) inflater
                    .inflate(R.layout.helper_wallet_event, null, false);

            txtEventTitle = (TextViewWithFont) rowView.findViewById(R.id.txtWalletEventTitle);
            txtWalletEventGenre = (TextViewWithFont) rowView.findViewById(R.id.txtWalletEventGenre);
            txtWalletEventDate = (TextViewWithFont) rowView.findViewById(R.id.txtWalletEventDate);
            txtWalletEventDoor = (TextViewWithFont) rowView.findViewById(R.id.txtWalletEventDoor);
            txtWalletEventSection = (TextViewWithFont) rowView.findViewById(R.id.txtWalletEventSection);
            txtWalletEventRow = (TextViewWithFont) rowView.findViewById(R.id.txtWalletEventRow);
            txtWalletEventSeat = (TextViewWithFont) rowView.findViewById(R.id.txtWalletEventSeat);

            txtEventTitle.setText(event.getEventName());
            txtWalletEventGenre.setText(event.getEventGenre());
            txtWalletEventDate.setText(event.getShowDate()+" "+event.getShowTime());
            txtWalletEventDoor.setText(event.getDoorNo());
            txtWalletEventSection.setText(event.getSection());
            txtWalletEventRow.setText(event.getSeatRow());
            txtWalletEventSeat.setText(event.getSeatNo());
            this.addView(rowView);
        }
    }
}
