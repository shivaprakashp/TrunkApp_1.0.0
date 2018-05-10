package com.opera.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.opera.app.database.events.EventDetailsDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.database.restaurants.DatabaseHelper;
import com.opera.app.database.restaurants.SeanRestOpeation;

public class OperaDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Opera.db";
    private static final int DATABASE_VERSION = 1;
    private static OperaDBHandler dbHelper = null;

    public OperaDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SeanRestOpeation.CREATE_TABLE);
        db.execSQL(DatabaseHelper.CREATE_TABLE_REASTAURANT);
        db.execSQL(EventListingDB.CREATE_TABLE_EVENT_LISTING);
        db.execSQL(EventDetailsDB.CREATE_TABLE_EVENT_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete older tables on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + SeanRestOpeation.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_OTHER_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS " + EventListingDB.TABLE_EVENT_LISTING);
        db.execSQL("DROP TABLE IF EXISTS " + EventDetailsDB.TABLE_EVENT_DETAILS);
        onCreate(db);
    }

    public static OperaDBHandler getInstance(Context context){

        if (dbHelper==null){
            dbHelper = new OperaDBHandler(context);
        }
        return dbHelper;
    }

}
