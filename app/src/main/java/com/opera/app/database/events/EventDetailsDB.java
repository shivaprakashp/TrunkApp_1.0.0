package com.opera.app.database.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.opera.app.database.OperaDBHandler;
import com.opera.app.pojo.events.eventdetails.EventDetails;
import com.opera.app.pojo.events.eventdetails.EventVenue;
import com.opera.app.pojo.events.eventdetails.FavouriteEvents;
import com.opera.app.pojo.events.eventdetails.InnerEventDetails;

import java.util.ArrayList;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class EventDetailsDB {

    public static final String TABLE_EVENT_INNER_DETAILS = "TABLE_EVENT_DETAILS";
    private static final String EVENT_ID = "_id";
    private static final String EVENT_NAME = "EVENT_NAME";
    private static final String EVENT_IMAGE = "EVENT_IMAGE";
    private static final String EVENT_INFO = "EVENT_INFO";
    private static final String EVENT_BUY_NOW_LINK = "EVENT_BUY_NOW_LINK";
    private static final String EVENT_IS_WHATS_ON = "EVENT_IS_WHATS_ON";
    private static final String EVENT_FROM_DATE = "EVENT_FROM_DATE";
    private static final String EVENT_TO_DATE = "EVENT_TO_DATE";
    private static final String EVENT_PRICE_FROM = "EVENT_PRICE_FROM";
    private static final String EVENT_VIDEO = "EVENT_VIDEO";
    private static final String EVENT_ACTIVE = "EVENT_ACTIVE";
    private static final String EVENT_START_TIME = "EVENT_START_TIME";
    private static final String EVENT_END_TIME = "EVENT_END_TIME";
    private static final String EVENT_INTERNAL_NAME = "EVENT_INTERNAL_NAME";


    //creating table
    public static final String CREATE_TABLE_EVENT_INNER_DETAILS =
            "CREATE TABLE " + TABLE_EVENT_INNER_DETAILS + "(" + EVENT_ID + " INTEGER,"
                    + EVENT_NAME + " TEXT,"
                    + EVENT_IMAGE + " TEXT,"
                    + EVENT_INFO + " TEXT,"
                    + EVENT_BUY_NOW_LINK + " TEXT,"
                    + EVENT_IS_WHATS_ON + " TEXT,"
                    + EVENT_FROM_DATE + " TEXT,"
                    + EVENT_TO_DATE + " TEXT,"
                    + EVENT_PRICE_FROM + " TEXT,"
                    + EVENT_VIDEO + " TEXT,"
                    + EVENT_ACTIVE + " TEXT,"
                    + EVENT_START_TIME + " TEXT,"
                    + EVENT_END_TIME + " TEXT,"
                    + EVENT_INTERNAL_NAME + " TEXT);";

    private SQLiteDatabase database;
    SQLiteOpenHelper openHelper;

    public EventDetailsDB(Context context) {
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

    //insterting data
    public void insertIntoEventsDetails(InnerEventDetails mEventDetailsData) {
        ContentValues contentValue = new ContentValues();
        Gson gson = new Gson();
        try {
//            for (int i = 0; i < mEventDetailsData.size(); i++) {
                contentValue.put(EVENT_ID, mEventDetailsData.getEventId());
                contentValue.put(EVENT_NAME, mEventDetailsData.getName());
                contentValue.put(EVENT_IMAGE, mEventDetailsData.getImage());
                contentValue.put(EVENT_INFO, mEventDetailsData.getDescription());
                contentValue.put(EVENT_BUY_NOW_LINK, mEventDetailsData.getBuyNowLink());
                contentValue.put(EVENT_IS_WHATS_ON, mEventDetailsData.getWhatsOn());
                contentValue.put(EVENT_FROM_DATE, mEventDetailsData.getFrom());
                contentValue.put(EVENT_TO_DATE, mEventDetailsData.getTo());
                contentValue.put(EVENT_PRICE_FROM, mEventDetailsData.getPriceFrom());
                contentValue.put(EVENT_VIDEO, mEventDetailsData.getVideo());
                contentValue.put(EVENT_ACTIVE, mEventDetailsData.getActive());
                contentValue.put(EVENT_START_TIME, mEventDetailsData.getStartTime());
                contentValue.put(EVENT_END_TIME, mEventDetailsData.getEndTime());
                contentValue.put(EVENT_INTERNAL_NAME, mEventDetailsData.getInternalName());
                long row = database.insert(TABLE_EVENT_INNER_DETAILS, null, contentValue);

                Log.e("row", row + "");
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<InnerEventDetails> fetchSpecificEventDetails() {

        ArrayList<InnerEventDetails> dataArrayEventDetails = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_INNER_DETAILS, null);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    InnerEventDetails mEventDetails = new InnerEventDetails();

                    mEventDetails.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEventDetails.setName(cursor.getString(cursor.getColumnIndex(EVENT_NAME)));
                    mEventDetails.setImage(cursor.getString(cursor.getColumnIndex(EVENT_IMAGE)));
                    mEventDetails.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_INFO)));
                    mEventDetails.setBuyNowLink(cursor.getString(cursor.getColumnIndex(EVENT_BUY_NOW_LINK)));
                    mEventDetails.setWhatsOn(cursor.getString(cursor.getColumnIndex(EVENT_IS_WHATS_ON)));
                    mEventDetails.setFrom(cursor.getString(cursor.getColumnIndex(EVENT_FROM_DATE)));
                    mEventDetails.setTo(cursor.getString(cursor.getColumnIndex(EVENT_TO_DATE)));
                    mEventDetails.setPriceFrom(cursor.getString(cursor.getColumnIndex(EVENT_PRICE_FROM)));
                    mEventDetails.setVideo(cursor.getString(cursor.getColumnIndex(EVENT_VIDEO)));
                    mEventDetails.setActive(cursor.getString(cursor.getColumnIndex(EVENT_ACTIVE)));
                    mEventDetails.setStartTime(cursor.getString(cursor.getColumnIndex(EVENT_START_TIME)));
                    mEventDetails.setEndTime(cursor.getString(cursor.getColumnIndex(EVENT_END_TIME)));
                    mEventDetails.setInternalName(cursor.getString(cursor.getColumnIndex(EVENT_INTERNAL_NAME)));

                    dataArrayEventDetails.add(mEventDetails);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ErrorMessage", e.getMessage());
        }

        return dataArrayEventDetails;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
