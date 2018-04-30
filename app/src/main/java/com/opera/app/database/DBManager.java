package com.opera.app.database;

/**
 * Created by 1000632 on 4/30/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.opera.app.pojo.restaurant.restaurantsData;
import java.util.ArrayList;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertOtherRestaurants(ArrayList<restaurantsData> mRestaurantListing) {
        ContentValues contentValue = new ContentValues();
        for (int i = 0; i < mRestaurantListing.size(); i++) {
            contentValue.put(DatabaseHelper.REASTAURANT_ID, mRestaurantListing.get(i).getRestId());
            contentValue.put(DatabaseHelper.REASTAURANT_NAME, mRestaurantListing.get(i).getRestName());
            contentValue.put(DatabaseHelper.REASTAURANT_PLACE, mRestaurantListing.get(i).getRestPlace());
            contentValue.put(DatabaseHelper.REASTAURANT_DETAILS, mRestaurantListing.get(i).getRestDetails());
            contentValue.put(DatabaseHelper.REASTAURANT_IMAGE_URL, mRestaurantListing.get(i).getRestImage());
            long row = database.insert(DatabaseHelper.TABLE_OTHER_RESTAURANTS, null, contentValue);

            Log.e("row", row + "");
        }


    }

    public Cursor fetchOtherRestaurantDetails() {
        String[] columns = new String[]{DatabaseHelper.REASTAURANT_ID, DatabaseHelper.REASTAURANT_NAME, DatabaseHelper.REASTAURANT_PLACE, DatabaseHelper.REASTAURANT_DETAILS, DatabaseHelper.REASTAURANT_IMAGE_URL};
        Cursor cursor = database.query(DatabaseHelper.TABLE_OTHER_RESTAURANTS, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

   /* public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }*/

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }

}