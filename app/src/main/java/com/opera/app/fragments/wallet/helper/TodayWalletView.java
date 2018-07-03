package com.opera.app.fragments.wallet.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.pojo.wallet.Event;
import com.opera.app.pojo.wallet.GiftCard;
import com.opera.app.pojo.wallet.Restaurant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public int setEvents(List<Event> eventList, String mFrom) {

        int mAvailableData=0;
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        TextViewWithFont txtEventTitle,
                txtWalletEventGenre,
                txtWalletEventDate,
                txtWalletEventDoor,
                txtWalletEventSection,
                txtWalletEventRow,
                txtWalletEventSeat,
                txtBarCode;
        ImageView barCode;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        if (eventList != null) {
            for (int i = 0; i < eventList.size(); i++) {
                Event event = eventList.get(i);

                rowView = inflater
                        .inflate(R.layout.helper_wallet_event, null, false);

                txtEventTitle = rowView.findViewById(R.id.txtWalletEventTitle);
                txtWalletEventGenre = rowView.findViewById(R.id.txtWalletEventGenre);
                txtWalletEventDate = rowView.findViewById(R.id.txtWalletEventDate);
                txtWalletEventDoor = rowView.findViewById(R.id.txtWalletEventDoor);
                txtWalletEventSection = rowView.findViewById(R.id.txtWalletEventSection);
                txtWalletEventRow = rowView.findViewById(R.id.txtWalletEventRow);
                txtWalletEventSeat = rowView.findViewById(R.id.txtWalletEventSeat);
                txtBarCode = rowView.findViewById(R.id.txtBarCode);
                barCode = rowView.findViewById(R.id.imgEventBarCode);

                txtEventTitle.setText(event.getEventName());
                txtWalletEventGenre.setText(event.getEventGenre());
                txtWalletEventDate.setText(event.getShowDate() + " " + event.getShowTime());
                txtWalletEventDoor.setText(event.getDoorNo());
                txtWalletEventSection.setText(event.getSection());
                txtWalletEventRow.setText(event.getSeatRow());
                txtWalletEventSeat.setText(event.getSeatNo());

                try {

                    BitMatrix bitMatrix = multiFormatWriter.encode(event.getReserveId(), BarcodeFormat.CODABAR,
                            400, 80);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    barCode.setImageBitmap(bitmap);

                    txtBarCode.setText(event.getReserveId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.addView(rowView);
            }
        }

        return mAvailableData;
    }

    public int setRest(List<Restaurant> restaurantList, String mFrom) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        int mAvailableData=0;
        Date mCurrentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfToDisplay = new SimpleDateFormat("dd MMM yyyy");
        String strDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        try {
            mCurrentDate = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextViewWithFont dateReservation, mealPeriod, preferTime, referNo, bookDate, txtWalletRestTitle, txtBarCode;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        ImageView barCode;

        if (restaurantList != null) {
            for (int i = 0; i < restaurantList.size(); i++) {

                Restaurant restaurant = restaurantList.get(i);

                rowView = inflater
                        .inflate(R.layout.helper_wallet_rest, null, false);

                barCode = rowView.findViewById(R.id.imgRestBarCode);
                txtBarCode = rowView.findViewById(R.id.txtBarCode);
                txtWalletRestTitle = rowView.findViewById(R.id.txtWalletRestTitle);
                dateReservation = rowView.findViewById(R.id.txtDateReserve);
                mealPeriod = rowView.findViewById(R.id.txtDinner);
                preferTime = rowView.findViewById(R.id.txtPrefTime);
                referNo = rowView.findViewById(R.id.txtReferNo);
                bookDate = rowView.findViewById(R.id.txtBookDate);

                Date dateOgFormat = null,dateOgFormatReservation = null;
                String formattedTime = "",formattedTimeReservationDate = "";
                try {
                    dateOgFormat = sdf.parse(restaurant.getBookingDate());
                    formattedTime = sdfToDisplay.format(dateOgFormat);

                    dateOgFormatReservation = sdf.parse(restaurant.getReservationDate());
                    formattedTimeReservationDate = sdfToDisplay.format(dateOgFormatReservation);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                txtWalletRestTitle.setText(restaurant.getRestaurantName());
                dateReservation.setText(" " + formattedTimeReservationDate);
                mealPeriod.setText(" " + restaurant.getMealPeriodId());
                preferTime.setText(" " + restaurant.getPreferredTime());
                referNo.setText(" " + restaurant.getBookingReferenceNumber());
                bookDate.setText(" " + formattedTime);

                try {
                    txtBarCode.setText(restaurant.getFullReservationID());
                    BitMatrix bitMatrix = multiFormatWriter.encode(restaurant.getFullReservationID(), BarcodeFormat.CODE_128,
                            400, 80);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    barCode.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mFrom.equalsIgnoreCase("Completed") && mCurrentDate.after(dateOgFormat)) {
                    mAvailableData++;
                    this.addView(rowView);
                } else if (mFrom.equalsIgnoreCase("Today") && mCurrentDate.equals(dateOgFormat)) {
                    mAvailableData++;
                    this.addView(rowView);
                } else if (mFrom.equalsIgnoreCase("Upcoming") && mCurrentDate.before(dateOgFormat)) {
                    mAvailableData++;
                    this.addView(rowView);
                }

            }
        }

        return mAvailableData;
    }

    public int  setGift(List<GiftCard> cardList, String mFrom) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        int mAvailableData=0;

        TextViewWithFont txtVoucherAmount;

        if (cardList != null) {
            for (int i = 0; i < cardList.size(); i++) {

                GiftCard giftCard = cardList.get(i);

                rowView = inflater
                        .inflate(R.layout.helper_wallet_gift, null, false);

                txtVoucherAmount = rowView.findViewById(R.id.txtVoucherAmount);

                txtVoucherAmount.setText(giftCard.getVoucherAmount());

                this.addView(rowView);
            }
        }
        return mAvailableData;
    }
}
