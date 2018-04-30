package com.opera.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 1000632 on 4/30/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    static final String DB_NAME = "DUBAI_OPERA.DB";
    // database version
    static final int DB_VERSION = 1;

    //RESTAURANT LISTING
    // Table Name
    public static final String TABLE_OTHER_RESTAURANTS = "TABLE_OTHER_RESTAURANTS";
    public static final String REASTAURANT_ID = "_id";
    public static final String REASTAURANT_NAME = "REASTAURANT_NAME";
    public static final String REASTAURANT_PLACE = "REASTAURANT_PLACE";
    public static final String REASTAURANT_DETAILS = "REASTAURANT_DETAILS";
    public static final String REASTAURANT_IMAGE_URL = "REASTAURANT_IMAGE_URL";

    private static final String CREATE_TABLE_REASTAURANT = "create table " + TABLE_OTHER_RESTAURANTS + "(" + REASTAURANT_ID
            + " INTEGER, " + REASTAURANT_NAME + " TEXT, " + REASTAURANT_PLACE + " TEXT, "+ REASTAURANT_DETAILS + " TEXT, " + REASTAURANT_IMAGE_URL + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
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
}
