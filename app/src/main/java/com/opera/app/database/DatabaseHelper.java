package com.opera.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opera.app.pojo.restaurant.restaurantsData;

import java.util.ArrayList;

/**
 * Created by 1000632 on 4/30/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    static final String DB_NAME = "DUBAI_OPERA.DB";
    // database version
    static final int DB_VERSION = 1;

    private SQLiteDatabase database;
    private Context mContext;
    DatabaseHelper dbHelper;

    //RESTAURANT LISTING
    // Table Name
    public static final String TABLE_OTHER_RESTAURANTS = "TABLE_OTHER_RESTAURANTS";
    public static final String REASTAURANT_ID = "_id";
    public static final String REASTAURANT_NAME = "REASTAURANT_NAME";
    public static final String REASTAURANT_PLACE = "REASTAURANT_PLACE";
    public static final String REASTAURANT_DETAILS = "REASTAURANT_DETAILS";
    public static final String REASTAURANT_IMAGE_URL = "REASTAURANT_IMAGE_URL";
    public static final String REASTAURANT_BOOKING_URL = "REASTAURANT_BOOKING_URL";

    private static final String CREATE_TABLE_REASTAURANT = "create table " + TABLE_OTHER_RESTAURANTS + "(" + REASTAURANT_ID
            + " INTEGER, " + REASTAURANT_NAME + " TEXT, " + REASTAURANT_PLACE + " TEXT, " + REASTAURANT_DETAILS + " TEXT, " + REASTAURANT_IMAGE_URL + " TEXT, " + REASTAURANT_BOOKING_URL + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        context = context;
    }


    public DatabaseHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(mContext);
        database = getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_REASTAURANT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OTHER_RESTAURANTS);
        onCreate(db);
    }

    public void insertOtherRestaurants(ArrayList<restaurantsData> mRestaurantListing) {
        ContentValues contentValue = new ContentValues();
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
    }

    public Cursor fetchOtherRestaurantDetails() {
        String[] columns = new String[]{DatabaseHelper.REASTAURANT_ID, DatabaseHelper.REASTAURANT_NAME, DatabaseHelper.REASTAURANT_PLACE
                , DatabaseHelper.REASTAURANT_DETAILS, DatabaseHelper.REASTAURANT_IMAGE_URL, DatabaseHelper.REASTAURANT_BOOKING_URL};
        Cursor cursor = database.query(DatabaseHelper.TABLE_OTHER_RESTAURANTS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
