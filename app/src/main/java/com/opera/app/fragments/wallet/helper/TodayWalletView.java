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
import com.opera.app.pojo.wallet.GiftCard;
import com.opera.app.pojo.wallet.Restaurant;

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

    public void setRest(List<Restaurant> restaurantList){

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        TextViewWithFont dateReservation, mealPeriod, preferTime, referNo, bookDate;

        for ( int i = 0 ; i < restaurantList.size() ; i++ ){

            Restaurant restaurant = restaurantList.get(i);
            rowView = (ViewGroup) inflater
                    .inflate(R.layout.helper_wallet_rest, null, false);

            dateReservation = (TextViewWithFont) rowView.findViewById(R.id.txtDateReserve);
            mealPeriod = (TextViewWithFont) rowView.findViewById(R.id.txtDinner);
            preferTime = (TextViewWithFont) rowView.findViewById(R.id.txtPrefTime);
            referNo = (TextViewWithFont) rowView.findViewById(R.id.txtReferNo);
            bookDate = (TextViewWithFont) rowView.findViewById(R.id.txtBookDate);

            dateReservation.setText(" "+restaurant.getReserveDate());
            mealPeriod.setText(" "+restaurant.getMealPeriod());
            preferTime.setText(" "+restaurant.getPrefferTime());
            referNo.setText(" "+restaurant.getReferenceNo());
            bookDate.setText(" "+restaurant.getPreferDate());

            this.addView(rowView);
        }
    }

    public void setGift(List<GiftCard> cardList){

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        TextViewWithFont txtVoucherAmount;

        for (int i = 0 ; i < cardList.size() ; i++ ){

            GiftCard giftCard = cardList.get(i);

            rowView = (ViewGroup) inflater
                    .inflate(R.layout.helper_wallet_gift, null, false);

            txtVoucherAmount = (TextViewWithFont) rowView.findViewById(R.id.txtVoucherAmount);

            txtVoucherAmount.setText(giftCard.getVoucherAmount());

            this.addView(rowView);
        }
    }
}
