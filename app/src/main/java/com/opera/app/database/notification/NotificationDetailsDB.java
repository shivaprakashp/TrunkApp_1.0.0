package com.opera.app.database.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opera.app.database.OperaDBHandler;
import com.opera.app.pojo.notifications.Notification;

import java.util.ArrayList;

public class NotificationDetailsDB {

    // Table Name
    public static final String TABLE_NOTIFICATION_DETAILS = "TABLE_NOTIFICATION_DETAILS";

    // Column Name
    private static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
    private static final String NOTIFICATION_IMAGE_URL = "NOTIFICATION_IMAGE_URL";

    private static final String NOTIFICATION_DESCRIBE = "NOTIFICATION_DESCRIBE";
    private static final String NOTIFICATION_PRICE_FROM = "NOTIFICATION_PRICE_FROM";
   

    //creating table
    public static final String CREATE_TABLE_NOTIFICATION =
            "CREATE TABLE " + TABLE_NOTIFICATION_DETAILS + "(" + NOTIFICATION_TITLE + " INTEGER,"
                    + NOTIFICATION_IMAGE_URL + " TEXT,"
                    + NOTIFICATION_DESCRIBE + " TEXT,"
                    + NOTIFICATION_PRICE_FROM + " TEXT);";


    private SQLiteDatabase database;
    SQLiteOpenHelper openHelper;

    public NotificationDetailsDB(Context context) {
        openHelper = OperaDBHandler.getInstance(context);
    }


    public void open(){
        Log.i("Database","Database Opened");
        database = openHelper.getWritableDatabase();


    }
    public void close(){
        Log.i("Database", "Database Closed");
        openHelper.close();
    }

    //insterting data
    public void insertNotifications(ArrayList<Notification> mNotification) {
        ContentValues contentValue = new ContentValues();
        try {
            for (int i = 0; i < mNotification.size(); i++) {
                contentValue.put(NOTIFICATION_TITLE, mNotification.get(i).getNotifyTitle());
                contentValue.put(NOTIFICATION_IMAGE_URL, mNotification.get(i).getNotifyImage());
                contentValue.put(NOTIFICATION_DESCRIBE, mNotification.get(i).getNotifyDescribe());
                contentValue.put(NOTIFICATION_PRICE_FROM, mNotification.get(i).getPriceFrom());

                long row = database.insert(TABLE_NOTIFICATION_DETAILS, null, contentValue);
            }
        }catch (SQLException e){e.printStackTrace();}
    }

    public ArrayList<Notification> fetchNotificationDetails() {

        ArrayList<Notification> dataArrayList = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NOTIFICATION_DETAILS, null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                do {
                    Notification mNotificationData = new Notification();

                    mNotificationData.setNotifyTitle(cursor.getString(cursor.getColumnIndex(NOTIFICATION_TITLE)));
                    mNotificationData.setNotifyImage(cursor.getString(cursor.getColumnIndex(NOTIFICATION_IMAGE_URL)));
                    mNotificationData.setNotifyDescribe(cursor.getString(cursor.getColumnIndex(NOTIFICATION_DESCRIBE)));
                    mNotificationData.setPriceFrom(cursor.getString(cursor.getColumnIndex(NOTIFICATION_PRICE_FROM)));

                    dataArrayList.add(mNotificationData);
                } while (cursor.moveToNext());
            }
        }catch (SQLException e){
            Log.e("ErrorMessage", e.getMessage());
        }

        return dataArrayList;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
