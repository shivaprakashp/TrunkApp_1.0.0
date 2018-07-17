package com.opera.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.opera.app.database.events.EventDetailsDB;
import com.opera.app.database.events.EventGenresDB;
import com.opera.app.database.events.EventListingDB;
import com.opera.app.database.feedback.FeedbackListingDB;
import com.opera.app.database.notification.NotificationDetailsDB;
import com.opera.app.database.notification.PromotionDetailsDB;
import com.opera.app.database.orders.OrderHistoryDB;
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
        db.execSQL(EventGenresDB.CREATE_TABLE_GENRES_LISTING);
        db.execSQL(NotificationDetailsDB.CREATE_TABLE_NOTIFICATION);
        db.execSQL(PromotionDetailsDB.CREATE_TABLE_PROMOTION);
        db.execSQL(FeedbackListingDB.CREATE_TABLE_FEEDBACK);
        db.execSQL(OrderHistoryDB.CREATE_ORDER_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete older tables on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + SeanRestOpeation.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_OTHER_RESTAURANTS);
        db.execSQL("DROP TABLE IF EXISTS " + EventListingDB.TABLE_EVENT_LISTING);
        db.execSQL("DROP TABLE IF EXISTS " + EventDetailsDB.TABLE_EVENT_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + EventGenresDB.TABLE_GENRES_LISTING);
        db.execSQL("DROP TABLE IF EXISTS " + NotificationDetailsDB.TABLE_NOTIFICATION_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + PromotionDetailsDB.TABLE_PROMOTION_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + FeedbackListingDB.TABLE_FEEDBACK_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + OrderHistoryDB.TABLE_ORDER_HISTORY);
        onCreate(db);
    }

    public static OperaDBHandler getInstance(Context context){

        if (dbHelper==null){
            dbHelper = new OperaDBHandler(context);
        }
        return dbHelper;
    }
}
