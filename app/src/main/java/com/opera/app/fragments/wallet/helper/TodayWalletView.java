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
import com.opera.app.pojo.wallet.Restaurant;
import com.opera.app.pojo.wallet.eventwallethistory.CommonBookedHistoryData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodayWalletView extends LinearLayout {

    View rowView;
    SimpleDateFormat sdfGlobal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

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

    public int setEvents(ArrayList<CommonBookedHistoryData> mBookedEventHistories, String mFrom) {

        int mAvailableData = 0;
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Date mCurrentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdfTimeReceived = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
//        SimpleDateFormat sdfToDisplay = new SimpleDateFormat("dd MMM yyyy");
        String strDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        try {
            mCurrentDate = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

        if (mBookedEventHistories != null) {
            for (int i = 0; i < mBookedEventHistories.size(); i++) {
                CommonBookedHistoryData mBookedHistoryEvents = mBookedEventHistories.get(i);

                if (inflater != null) {
                    rowView = inflater.inflate(R.layout.helper_wallet_event, null, false);
                }
                txtEventTitle = rowView.findViewById(R.id.txtWalletEventTitle);
                txtWalletEventGenre = rowView.findViewById(R.id.txtWalletEventGenre);
                txtWalletEventDate = rowView.findViewById(R.id.txtWalletEventDate);
                txtWalletEventDoor = rowView.findViewById(R.id.txtWalletEventDoor);
                txtWalletEventSection = rowView.findViewById(R.id.txtWalletEventSection);
                txtWalletEventRow = rowView.findViewById(R.id.txtWalletEventRow);
                txtWalletEventSeat = rowView.findViewById(R.id.txtWalletEventSeat);
                txtBarCode = rowView.findViewById(R.id.txtBarCode);
                barCode = rowView.findViewById(R.id.imgEventBarCode);

                /*String[] mDateTime = mBookedHistoryEvents.getmDateAndTime().split("T");*/

                String mDateFor = "", mTimeFor = "";
                Date mStandardDate = null;

                try {
                    mStandardDate=sdfGlobal.parse(mBookedHistoryEvents.getmDateAndTime());
                    mDateFor = sdfDate.format(mStandardDate);
                    mTimeFor = sdfTime.format(mStandardDate);

                    /*mStandardDate = sdf.parse(mDateFor);*/
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                /*try {
                    mDateFor = mDateTime[0];
                    mTimeFor = mDateTime[1];

                    mStandardTime = sdfTimeReceived.parse(mTimeFor);
                    mTimeFor = sdfTime.format(mStandardTime);

                    mStandardDate = sdf.parse(mDateFor);
                    mDateFor = sdfDate.format(mStandardDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                txtEventTitle.setText(mBookedHistoryEvents.getmTicketEventName());
                txtWalletEventGenre.setText(mBookedHistoryEvents.getmTicketEventGenre());
                txtWalletEventDate.setText(new StringBuilder().append(mDateFor).append(" ").append(mTimeFor).toString());

                txtWalletEventDoor.setText(mBookedHistoryEvents.getmEventSeatRZSTR());
                txtWalletEventSection.setText(mBookedHistoryEvents.getmEventSeatSection());
                txtWalletEventRow.setText(mBookedHistoryEvents.getmEventSeatRow());
                txtWalletEventSeat.setText(mBookedHistoryEvents.getmEventSeatSeats());
                txtBarCode.setText(mBookedHistoryEvents.getmBarcode());

                try {

                    BitMatrix bitMatrix = multiFormatWriter.encode(mBookedHistoryEvents.getmBarcode(), BarcodeFormat.CODABAR,
                            400, 80);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    barCode.setImageBitmap(bitmap);

                    txtBarCode.setText(mBookedHistoryEvents.getmBarcode());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mFrom.equalsIgnoreCase("Completed") && mCurrentDate.after(mStandardDate)) {
                    mAvailableData++;
                    this.addView(rowView);
                } else if (mFrom.equalsIgnoreCase("Today") && mCurrentDate.equals(mStandardDate)) {
                    mAvailableData++;
                    this.addView(rowView);
                } else if (mFrom.equalsIgnoreCase("Upcoming") && mCurrentDate.before(mStandardDate)) {
                    mAvailableData++;
                    this.addView(rowView);
                }
                /*if (mFrom.equalsIgnoreCase("Completed")) {
                    mAvailableData++;
                    this.addView(rowView);
                } else if (mFrom.equalsIgnoreCase("Today")) {
                    mAvailableData++;
                    this.addView(rowView);
                } else if (mFrom.equalsIgnoreCase("Upcoming")) {
                    mAvailableData++;
                    this.addView(rowView);
                }*/
            }
        }

        return mAvailableData;
    }

    public int setRest(List<Restaurant> restaurantList, String mFrom) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        int mAvailableData = 0;
        Date mCurrentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        SimpleDateFormat sdfToDisplay = new SimpleDateFormat("dd MMM yyyy",Locale.US);
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

                Date dateOgFormat = null, dateOgFormatReservation;
                String formattedTime = "", formattedTimeReservationDate = "";
                try {
                    dateOgFormat = sdf.parse(restaurant.getBookingDate());
                    formattedTime = sdfToDisplay.format(dateOgFormat);

                    dateOgFormatReservation = sdf.parse(restaurant.getReservationDate());
                    formattedTimeReservationDate = sdfToDisplay.format(dateOgFormatReservation);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                txtWalletRestTitle.setText(restaurant.getRestaurantName());
                dateReservation.setText(new StringBuilder().append(" ").append(formattedTime).toString());
                mealPeriod.setText(new StringBuilder().append(" ").append(restaurant.getMealPeriodId()).toString());
                preferTime.setText(new StringBuilder().append(" ").append(restaurant.getPreferredTime()).toString());
                referNo.setText(new StringBuilder().append(" ").append(restaurant.getBookingReferenceNumber()).toString());
                bookDate.setText(new StringBuilder().append(" ").append(formattedTimeReservationDate).toString());

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

    public int setGift(ArrayList<CommonBookedHistoryData> mEventHistoryData, String mFrom) {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Date mCurrentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdfTimeReceived = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        int mAvailableData = 0;

        TextViewWithFont txtAmountOfGiftCard, txtDateReserve, txtVoucherId, txtBarCode;

        ImageView barCode;

        if (mEventHistoryData != null) {
            for (int i = 0; i < mEventHistoryData.size(); i++) {

                CommonBookedHistoryData giftCard = mEventHistoryData.get(i);

                rowView = inflater
                        .inflate(R.layout.helper_wallet_gift, null, false);

                txtAmountOfGiftCard = rowView.findViewById(R.id.txtAmountOfGiftCard);
                txtDateReserve = rowView.findViewById(R.id.txtDateReserve);
                txtVoucherId = rowView.findViewById(R.id.txtVoucherId);
                txtBarCode = rowView.findViewById(R.id.txtBarCode);
                barCode = rowView.findViewById(R.id.imgEventBarCode);


                String mDateFor = "", mTimeFor = "";
                Date mStandardDate = null;

                try {
                    mStandardDate=sdfGlobal.parse(giftCard.getmDateAndTime());
                    mDateFor = sdfDate.format(mStandardDate);
                    mTimeFor = sdfTime.format(mStandardDate);

                    /*mStandardDate = sdf.parse(mDateFor);*/
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                /*String[] mDateTime = giftCard.getmDateAndTime().split("T");
                String mDateFor = "", mTimeFor = "";
                Date mStandardDate = null, mStandardTime;
                try {
                    mDateFor = mDateTime[0];
                    mTimeFor = mDateTime[1];

                    mStandardTime = sdfTimeReceived.parse(mTimeFor);
                    mTimeFor = sdfTime.format(mStandardTime);

                    mStandardDate = sdf.parse(mDateFor);
                    mDateFor = sdfDate.format(mStandardDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                txtAmountOfGiftCard.setText(giftCard.getmPrice());
                txtDateReserve.setText(new StringBuilder().append(mDateFor).append(" ").append(mTimeFor).toString());
                txtVoucherId.setText(giftCard.getmBarcode());

                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(giftCard.getmBarcode(), BarcodeFormat.CODABAR,
                            400, 80);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    barCode.setImageBitmap(bitmap);

                    txtBarCode.setText(giftCard.getmBarcode());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mFrom.equalsIgnoreCase("Activate") && monthsBetweenDates(mStandardDate, mCurrentDate) <= 12) {
                    mAvailableData++;
                    this.addView(rowView);
                } else if (mFrom.equalsIgnoreCase("Completed") && monthsBetweenDates(mStandardDate, mCurrentDate) > 12) {
                    mAvailableData++;
                    this.addView(rowView);
                }
            }
        }
        return mAvailableData;
    }


    public int monthsBetweenDates(Date purchaseDate, Date currentDate) {

        Calendar start = Calendar.getInstance();
        start.setTime(purchaseDate);

        Calendar end = Calendar.getInstance();
        end.setTime(currentDate);

        int monthsBetween = 0;
        int dateDiff = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH);

        if (dateDiff < 0) {
            int borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH);
            dateDiff = (end.get(Calendar.DAY_OF_MONTH) + borrrow) - start.get(Calendar.DAY_OF_MONTH);
            monthsBetween--;

            if (dateDiff > 0) {
                monthsBetween++;
            }
        } else {
            monthsBetween++;
        }
        monthsBetween += end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        monthsBetween += (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return monthsBetween;
    }
}
