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
    private static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    private static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";
    private static final String NOTIFICATION_IMAGE_URL = "NOTIFICATION_IMAGE_URL";
    private static final String NOTIFICATION_DESCRIBE = "NOTIFICATION_DESCRIBE";
    private static final String NOTIFICATION_PRICE_FROM = "NOTIFICATION_PRICE_FROM";
    private static final String NOTIFICATION_VALID_FROM = "NOTIFICATION_VALID_FROM";
    private static final String NOTIFICATION_VALID_TO = "NOTIFICATION_VALID_TO";
    private static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
    private static final String NOTIFICATION_NOTIFICATION_ITEM_ID = "NOTIFICATION_NOTIFICATION_ITEM_ID";
    private static final String NOTIFICATION_DESCRIPTION_HTML = "NOTIFICATION_DESCRIPTION_HTML";

    //creating table
    public static final String CREATE_TABLE_NOTIFICATION =
            "CREATE TABLE " + TABLE_NOTIFICATION_DETAILS + "(" + NOTIFICATION_ID + " INTEGER,"
                    + NOTIFICATION_TITLE + " TEXT,"
                    + NOTIFICATION_IMAGE_URL + " TEXT,"
                    + NOTIFICATION_DESCRIBE + " TEXT,"
                    + NOTIFICATION_PRICE_FROM + " TEXT,"
                    + NOTIFICATION_VALID_FROM + " TEXT,"
                    + NOTIFICATION_VALID_TO + " TEXT,"
                    + NOTIFICATION_TYPE + " TEXT,"
                    + NOTIFICATION_NOTIFICATION_ITEM_ID + " TEXT,"
                    + NOTIFICATION_DESCRIPTION_HTML + " TEXT);";

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
                contentValue.put(NOTIFICATION_ID, mNotification.get(i).getId());
                contentValue.put(NOTIFICATION_TITLE, mNotification.get(i).getTitle());
                contentValue.put(NOTIFICATION_IMAGE_URL, mNotification.get(i).getImage());
                contentValue.put(NOTIFICATION_DESCRIBE, mNotification.get(i).getDescription());
                contentValue.put(NOTIFICATION_PRICE_FROM, mNotification.get(i).getPrice());
                contentValue.put(NOTIFICATION_VALID_FROM, mNotification.get(i).getValidFrom());
                contentValue.put(NOTIFICATION_VALID_TO, mNotification.get(i).getValidTo());
                contentValue.put(NOTIFICATION_DESCRIPTION_HTML, mNotification.get(i).getDescriptionHtml());
                contentValue.put(NOTIFICATION_TYPE, mNotification.get(i).getPromotionType());
                contentValue.put(NOTIFICATION_NOTIFICATION_ITEM_ID, mNotification.get(i).getPromotionItemId());

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
                    mNotificationData.setId(cursor.getString(cursor.getColumnIndex(NOTIFICATION_ID)));
                    mNotificationData.setTitle(cursor.getString(cursor.getColumnIndex(NOTIFICATION_TITLE)));
                    mNotificationData.setImage(cursor.getString(cursor.getColumnIndex(NOTIFICATION_IMAGE_URL)));
                    mNotificationData.setDescription(cursor.getString(cursor.getColumnIndex(NOTIFICATION_DESCRIBE)));
                    mNotificationData.setPrice(cursor.getString(cursor.getColumnIndex(NOTIFICATION_PRICE_FROM)));
                    mNotificationData.setValidFrom(cursor.getString(cursor.getColumnIndex(NOTIFICATION_VALID_FROM)));
                    mNotificationData.setValidTo(cursor.getString(cursor.getColumnIndex(NOTIFICATION_VALID_TO)));
                    mNotificationData.setDescriptionHtml(cursor.getString(cursor.getColumnIndex(NOTIFICATION_DESCRIPTION_HTML)));
                    mNotificationData.setPromotionType(cursor.getString(cursor.getColumnIndex(NOTIFICATION_TYPE)));
                    mNotificationData.setPromotionItemId(cursor.getString(cursor.getColumnIndex(NOTIFICATION_NOTIFICATION_ITEM_ID)));

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
