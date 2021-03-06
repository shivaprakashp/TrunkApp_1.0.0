package com.opera.app.database.restaurants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opera.app.database.OperaDBHandler;
import com.opera.app.pojo.restaurant.RestaurantsData;

public class SeanRestOpeation {

    public static final String TABLE_NAME = "OperaRestaurant";
    private static final String REST_ITEM_ID = "restItemId";
    private static final String REST_ID = "restId";
    private static final String REST_NAME = "restName";
    private static final String REST_IMAGE = "restImage";
    private static final String REST_PLACE = "restPlace";
    private static final String REST_LOCATION = "restLocation";
    private static final String REST_BOOK_URL = "restBookUrl";
    private static final String REST_STATUS = "restStatus";
    private static final String REST_DETAILS = "restDetails";
    private static final String REST_OPEN_HOURS = "openHour";
    private static final String REST_PHONE_NUMBER = "phoneNumber";
    private static final String REST_EMAIL = "email";
    private static final String REST_LATITUDE = "latitude";
    private static final String REST_LONGITUDE = "longitude";

    //creating table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" + REST_ITEM_ID + " TEXT," +
                    REST_ID+ " TEXT," +
                    REST_NAME + " TEXT," +
                    REST_IMAGE + " TEXT," +
                    REST_PLACE + " TEXT," +
                    REST_LOCATION + " TEXT," +
                    REST_BOOK_URL + " TEXT," +
                    REST_STATUS + " TEXT," +
                    REST_DETAILS + " TEXT," +
                    REST_OPEN_HOURS + " TEXT," +
                    REST_PHONE_NUMBER + " TEXT," +
                    REST_EMAIL + " TEXT," +
                    REST_LATITUDE + " TEXT," +
                    REST_LONGITUDE + " TEXT," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);";


    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;

    public SeanRestOpeation(Context context) {
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

    //insert values
    public RestaurantsData addSeanConnollyData(RestaurantsData data) {
        try {

            ContentValues values = new ContentValues();
            values.put(REST_ITEM_ID, data.getRestItemId());
            values.put(REST_ID, data.getRestId());
            values.put(REST_NAME, data.getRestName());
            values.put(REST_IMAGE, data.getRestImage());
            values.put(REST_PLACE, data.getRestPlace());
            values.put(REST_LOCATION, data.getRestLocation());
            values.put(REST_BOOK_URL, data.getRestBookUrl());
            values.put(REST_STATUS, data.getRestStatus());
            values.put(REST_DETAILS, data.getRestDetails());
            values.put(REST_OPEN_HOURS, data.getOpenHour());
            values.put(REST_PHONE_NUMBER, data.getPhoneNumber());
            values.put(REST_EMAIL, data.getEmail());
            values.put(REST_LATITUDE, data.getRestLatitude());
            values.put(REST_LONGITUDE, data.getRestLongitude());
            database.insert(TABLE_NAME, null, values);
        } catch (SQLException e) {
            Log.e("error1", e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    //returns the stored data
    public RestaurantsData getSeanConnolly(String mRestaurantId) {
        RestaurantsData data = new RestaurantsData();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " where restItemId = '" + mRestaurantId + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                data.setRestItemId(cursor.getString(0));
                data.setRestId(cursor.getString(1));
                data.setRestName(cursor.getString(2));
                data.setRestImage(cursor.getString(3));
                data.setRestPlace(cursor.getString(4));
                data.setRestLocation(cursor.getString(5));
                data.setRestBookUrl(cursor.getString(6));
                data.setRestStatus(cursor.getString(7));
                data.setRestDetails(cursor.getString(8));
                data.setOpenHour(cursor.getString(9));
                data.setPhoneNumber(cursor.getString(10));
                data.setEmail(cursor.getString(11));
                data.setRestLatitude(cursor.getString(12));
                data.setRestLongitude(cursor.getString(13));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("error2", e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return data;
    }

    //returns the stored data
    public RestaurantsData getSeanConnollySiteCoreId(String mRestaurantId) {
        RestaurantsData data = new RestaurantsData();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " where restId = '" + mRestaurantId + "'", null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                data.setRestItemId(cursor.getString(0));
                data.setRestId(cursor.getString(1));
                data.setRestName(cursor.getString(2));
                data.setRestImage(cursor.getString(3));
                data.setRestPlace(cursor.getString(4));
                data.setRestLocation(cursor.getString(5));
                data.setRestBookUrl(cursor.getString(6));
                data.setRestStatus(cursor.getString(7));
                data.setRestDetails(cursor.getString(8));
                data.setOpenHour(cursor.getString(9));
                data.setPhoneNumber(cursor.getString(10));
                data.setEmail(cursor.getString(11));
                data.setRestLatitude(cursor.getString(12));
                data.setRestLongitude(cursor.getString(13));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("error2", e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return data;
    }

    public void removeSeanConnolly(String mRestaurantId) {
        int row = database.delete(TABLE_NAME, "restId" + " = ?", new String[]{mRestaurantId});
        Log.e("deletedRow", row + "");
    }
}
