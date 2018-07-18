package com.opera.app.database.orders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.opera.app.database.OperaDBHandler;
import com.opera.app.pojo.favouriteandsettings.OrderHistory;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDB {

    public static final String TABLE_ORDER_HISTORY = "TABLE_ORDER_HISTORY";

    private static final String orderId = "orderId";
    private static final String eventId = "eventId";
    private static final String dateTime = "dateTime";
    private static final String eventName = "eventName";
    private static final String mobileDescription = "mobileDescription";
    private static final String feedBackUrl = "feedBackUrl";
    private static final String startTime = "startTime";
    private static final String endTime = "endTime";


    public static final String CREATE_ORDER_HISTORY =
            "CREATE TABLE "+ TABLE_ORDER_HISTORY + "(" +orderId+" TEXT,"
                    +eventId+" TEXT,"
                    +dateTime+" TEXT,"
                    +eventName+" TEXT,"
                    +mobileDescription+" TEXT,"
                    +feedBackUrl+" TEXT,"
                    +startTime+" TEXT,"
                    +endTime+" TEXT"+
                    ")";

    private SQLiteDatabase database;
    SQLiteOpenHelper openHelper;


    public OrderHistoryDB(Context context) {
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

    public void insertOrders(List<OrderHistory> orderHistoryList){
        ContentValues contentValue = new ContentValues();
        for (int i = 0 ; i < orderHistoryList.size() ; i++){
            contentValue.put(orderId, orderHistoryList.get(i).getOrderId());
            contentValue.put(eventId, orderHistoryList.get(i).getEventId());
            contentValue.put(dateTime, orderHistoryList.get(i).getDateTime());
            contentValue.put(eventName, orderHistoryList.get(i).getEventName());
            contentValue.put(mobileDescription, orderHistoryList.get(i).getMobileDescription());
            contentValue.put(feedBackUrl, orderHistoryList.get(i).getFeedBackUrl());
            contentValue.put(startTime, orderHistoryList.get(i).getStartTime());
            contentValue.put(endTime, orderHistoryList.get(i).getEndTime());

            long row = database.insert(TABLE_ORDER_HISTORY, null, contentValue);
        }
    }

    public ArrayList<OrderHistory> orderHistories(){

        ArrayList<OrderHistory> orderHistories = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_ORDER_HISTORY, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    OrderHistory history = new OrderHistory();
                    history.setOrderId(cursor.getString(cursor.getColumnIndex(orderId)));
                    history.setEventId(cursor.getString(cursor.getColumnIndex(eventId)));
                    history.setDateTime(cursor.getString(cursor.getColumnIndex(dateTime)));
                    history.setEventName(cursor.getString(cursor.getColumnIndex(eventName)));
                    history.setMobileDescription(cursor.getString(cursor.getColumnIndex(mobileDescription)));
                    history.setFeedBackUrl(cursor.getString(cursor.getColumnIndex(feedBackUrl)));
                    history.setStartTime(cursor.getString(cursor.getColumnIndex(startTime)));
                    history.setEndTime(cursor.getString(cursor.getColumnIndex(endTime)));
                    orderHistories.add(history);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return orderHistories;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
