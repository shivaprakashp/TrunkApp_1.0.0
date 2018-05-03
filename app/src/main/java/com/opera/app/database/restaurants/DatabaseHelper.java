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

import java.util.ArrayList;

/**
 * Created by 1000632 on 4/30/2018.
 */

public class DatabaseHelper{

    //RESTAURANT LISTING
    // Table Name
    public static final String TABLE_OTHER_RESTAURANTS = "TABLE_OTHER_RESTAURANTS";
    private static final String REASTAURANT_ID = "_id";
    private static final String REASTAURANT_NAME = "REASTAURANT_NAME";
    private static final String REASTAURANT_PLACE = "REASTAURANT_PLACE";
    private static final String REASTAURANT_DETAILS = "REASTAURANT_DETAILS";
    private static final String REASTAURANT_IMAGE_URL = "REASTAURANT_IMAGE_URL";
    private static final String REASTAURANT_BOOKING_URL = "REASTAURANT_BOOKING_URL";

    //creating table
    public static final String CREATE_TABLE_REASTAURANT =
            "CREATE TABLE " + TABLE_OTHER_RESTAURANTS + "(" + REASTAURANT_ID + " INTEGER,"
                    + REASTAURANT_NAME + " TEXT,"
                    + REASTAURANT_PLACE + " TEXT,"
                    + REASTAURANT_DETAILS + " TEXT,"
                    + REASTAURANT_IMAGE_URL + " TEXT,"
                    + REASTAURANT_BOOKING_URL + " TEXT);";


    private SQLiteDatabase database;
    SQLiteOpenHelper openHelper;

    public DatabaseHelper(Context context) {
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
    public void insertOtherRestaurants(ArrayList<RestaurantsData> mRestaurantListing) {
        ContentValues contentValue = new ContentValues();
        try {
            for (int i = 0; i < mRestaurantListing.size(); i++) {
                contentValue.put(DatabaseHelper.REASTAURANT_ID, mRestaurantListing.get(i).getRestId());
                contentValue.put(DatabaseHelper.REASTAURANT_NAME, mRestaurantListing.get(i).getRestName());
                contentValue.put(DatabaseHelper.REASTAURANT_PLACE, mRestaurantListing.get(i).getRestPlace());
                contentValue.put(DatabaseHelper.REASTAURANT_DETAILS, mRestaurantListing.get(i).getRestDetails());
                contentValue.put(DatabaseHelper.REASTAURANT_IMAGE_URL, mRestaurantListing.get(i).getRestImage());
                contentValue.put(DatabaseHelper.REASTAURANT_BOOKING_URL, mRestaurantListing.get(i).getRestBookUrl());
                long row = database.insert(DatabaseHelper.TABLE_OTHER_RESTAURANTS, null, contentValue);

                Log.e("row", row + "");
            }
        }catch (SQLException e){e.printStackTrace();}
    }

    public ArrayList<RestaurantsData> fetchOtherRestaurantDetails() {

        ArrayList<RestaurantsData> dataArrayList = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_OTHER_RESTAURANTS, null);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    RestaurantsData mRestaurantData = new RestaurantsData();

                    mRestaurantData.setRestId(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_ID)));
                    mRestaurantData.setRestName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_NAME)));
                    mRestaurantData.setRestPlace(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_PLACE)));
                    mRestaurantData.setRestDetails(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_DETAILS)));
                    mRestaurantData.setRestImage(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_IMAGE_URL)));
                    mRestaurantData.setRestBookUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.REASTAURANT_BOOKING_URL)));
                    dataArrayList.add(mRestaurantData);
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