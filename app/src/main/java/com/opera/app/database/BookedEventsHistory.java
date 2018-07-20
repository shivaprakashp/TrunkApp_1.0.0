package com.opera.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.opera.app.pojo.wallet.eventwallethistory.CommonBookedHistoryData;
import com.opera.app.pojo.wallet.eventwallethistory.OrderLineItems;

import java.util.ArrayList;

/**
 * Created by 1000632 on 7/16/2018.
 */

public class BookedEventsHistory {

    public static final String TABLE_BOOKED_EVENTS_HISTORY = "TABLE_BOOKED_EVENTS_HISTORY";
    private static final String BOOKED_TICKET_EVENT_SEAT_BARCODE = "_id";
    private static final String BOOKED_EVENT_COMMON_TRANSACTION_ID = "BOOKED_EVENT_COMMON_TRANSACTION_ID";
    private static final String BOOKED_DATE_AND_TIME = "BOOKED_DATE_AND_TIME";
    private static final String BOOKED_EVENT_ORDER_TYPE = "BOOKED_EVENT_ORDER_TYPE";
    private static final String BOOKED_TICKET_EVENT_ID = "BOOKED_TICKET_EVENT_ID";
    private static final String BOOKED_TICKET_EVENT_NAME = "BOOKED_TICKET_EVENT_NAME";
    private static final String BOOKED_TICKET_EVENT_GENRE = "BOOKED_TICKET_EVENT_GENRE";
    private static final String BOOKED_TICKET_EVENT_SEAT_PERFORMANCE_CODE = "BOOKED_TICKET_EVENT_SEAT_PERFORMANCE_CODE";
    private static final String BOOKED_TICKET_EVENT_SEAT_PRICE = "BOOKED_TICKET_EVENT_SEAT_PRICE";

    private static final String BOOKED_TICKET_EVENT_SEAT_ROW = "BOOKED_TICKET_EVENT_SEAT_ROW";
    private static final String BOOKED_TICKET_EVENT_SEAT_RZSTR = "BOOKED_TICKET_EVENT_SEAT_RZSTR";
    private static final String BOOKED_TICKET_EVENT_SEAT_SEATS = "BOOKED_TICKET_EVENT_SEAT_SEATS";
    private static final String BOOKED_TICKET_EVENT_SEAT_SECTION = "BOOKED_TICKET_EVENT_SEAT_SECTION";

    //creating table
    public static final String CREATE_TABLE_BOOKED_EVENTS_HISTORY =
            "CREATE TABLE " + TABLE_BOOKED_EVENTS_HISTORY + "(" + BOOKED_TICKET_EVENT_SEAT_BARCODE + " TEXT,"
                    + BOOKED_EVENT_COMMON_TRANSACTION_ID + " TEXT,"
                    + BOOKED_DATE_AND_TIME + " TEXT,"
                    + BOOKED_EVENT_ORDER_TYPE + " TEXT,"
                    + BOOKED_TICKET_EVENT_ID + " TEXT,"
                    + BOOKED_TICKET_EVENT_NAME + " TEXT,"
                    + BOOKED_TICKET_EVENT_GENRE + " TEXT,"
                    + BOOKED_TICKET_EVENT_SEAT_PERFORMANCE_CODE + " TEXT,"
                    + BOOKED_TICKET_EVENT_SEAT_PRICE + " TEXT,"
                    + BOOKED_TICKET_EVENT_SEAT_ROW + " TEXT,"
                    + BOOKED_TICKET_EVENT_SEAT_RZSTR + " TEXT,"
                    + BOOKED_TICKET_EVENT_SEAT_SEATS + " TEXT,"
                    + BOOKED_TICKET_EVENT_SEAT_SECTION + " TEXT);";


    private SQLiteDatabase database;
    SQLiteOpenHelper openHelper;

    public BookedEventsHistory(Context context) {
        openHelper = OperaDBHandler.getInstance(context);
    }


    public void open() {
        Log.i("Database", "Database Opened");
        database = openHelper.getWritableDatabase();


    }

    public void close() {
        Log.i("Database", "Database Closed");
        openHelper.close();
    }

    //insterting data
    public void insertBookedEventsHistory(String mOrderType, ArrayList<OrderLineItems> mOrderLineItems, String mEventId, String mEventName, String mGenreName, String mTransactionCommonId, String mDateAndTime) {
        ContentValues contentValue = new ContentValues();
        try {
            for (int i = 0; i < mOrderLineItems.size(); i++) {
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_SEAT_BARCODE, mOrderLineItems.get(i).getBarcode());
                contentValue.put(BookedEventsHistory.BOOKED_EVENT_COMMON_TRANSACTION_ID, mTransactionCommonId);
                contentValue.put(BookedEventsHistory.BOOKED_DATE_AND_TIME, mDateAndTime);
                contentValue.put(BookedEventsHistory.BOOKED_EVENT_ORDER_TYPE, mOrderType);
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_ID, mEventId);
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_NAME, mEventName);
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_GENRE, mGenreName);
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_SEAT_PERFORMANCE_CODE, mOrderLineItems.get(i).getPerformanceCode());
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_SEAT_PRICE, mOrderLineItems.get(i).getPrice().getNet());
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_SEAT_ROW, mOrderLineItems.get(i).getSeat().getRow());
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_SEAT_RZSTR, mOrderLineItems.get(i).getSeat().getRzStr());
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_SEAT_SEATS, mOrderLineItems.get(i).getSeat().getSeats());
                contentValue.put(BookedEventsHistory.BOOKED_TICKET_EVENT_SEAT_SECTION, mOrderLineItems.get(i).getSeat().getSection());

                long row = database.insert(BookedEventsHistory.TABLE_BOOKED_EVENTS_HISTORY, null, contentValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CommonBookedHistoryData> fetchBookedEventsHistory(String mQueriedData) {

        ArrayList<CommonBookedHistoryData> dataArrayBookedEventsHistory = new ArrayList<>();
        Gson gson = new Gson();
        Cursor cursor = null;
        try {
             cursor = database.rawQuery("SELECT * FROM " + TABLE_BOOKED_EVENTS_HISTORY, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CommonBookedHistoryData mBookedEventHistory = new CommonBookedHistoryData();

                    mBookedEventHistory.setmBarcode(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_SEAT_BARCODE)));
                    mBookedEventHistory.setmCommonTransactionId(cursor.getString(cursor.getColumnIndex(BOOKED_EVENT_COMMON_TRANSACTION_ID)));
                    mBookedEventHistory.setmDateAndTime(cursor.getString(cursor.getColumnIndex(BOOKED_DATE_AND_TIME)));
                    mBookedEventHistory.setmOrderType(cursor.getString(cursor.getColumnIndex(BOOKED_EVENT_ORDER_TYPE)));

                    mBookedEventHistory.setmTicketEventId(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_ID)));
                    mBookedEventHistory.setmTicketEventName(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_NAME)));
                    mBookedEventHistory.setmTicketEventGenre(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_GENRE)));
                    mBookedEventHistory.setmEventSeatPerformanceCode(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_SEAT_PERFORMANCE_CODE)));
                    mBookedEventHistory.setmPrice(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_SEAT_PRICE)));

                    mBookedEventHistory.setmEventSeatRow(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_SEAT_ROW)));
                    mBookedEventHistory.setmEventSeatRZSTR(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_SEAT_RZSTR)));
                    mBookedEventHistory.setmEventSeatSeats(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_SEAT_SEATS)));
                    mBookedEventHistory.setmEventSeatSection(cursor.getString(cursor.getColumnIndex(BOOKED_TICKET_EVENT_SEAT_SECTION)));

                    dataArrayBookedEventsHistory.add(mBookedEventHistory);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ErrorMessage", e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return dataArrayBookedEventsHistory;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
