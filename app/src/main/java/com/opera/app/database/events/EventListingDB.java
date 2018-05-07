package com.opera.app.database.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opera.app.database.OperaDBHandler;
import com.opera.app.database.restaurants.DatabaseHelper;
import com.opera.app.pojo.events.EventKey;
import com.opera.app.pojo.events.EventTime;
import com.opera.app.pojo.events.GenreType;
import com.opera.app.pojo.events.events;
import com.opera.app.pojo.restaurant.RestaurantsData;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 1000632 on 5/2/2018.
 */

public class EventListingDB {

    public static final String TABLE_EVENT_DETAILS = "TABLE_EVENT_DETAILS";
    private static final String EVENT_ID = "_id";
    private static final String EVENT_TITLE = "EVENT_TITLE";
    private static final String EVENT_IMAGE = "EVENT_IMAGE";
    private static final String EVENT_INFO = "EVENT_INFO";
    private static final String EVENT_IS_HIGHLIGHTED = "EVENT_IS_HIGHLIGHTED";
    private static final String EVENT_IS_WHATS_ON = "EVENT_IS_WHATS_ON";
    private static final String EVENT_DATE = "EVENT_DATE";
    private static final String EVENT_TIME = "EVENT_TIME";
    private static final String EVENT_KEY = "EVENT_KEY";
    private static final String EVENT_GENRE_TYPE = "EVENT_GENRE_TYPE";

    //creating table
    public static final String CREATE_TABLE_EVENT_DETAILS =
            "CREATE TABLE " + TABLE_EVENT_DETAILS + "(" + EVENT_ID + " INTEGER,"
                    + EVENT_TITLE + " TEXT,"
                    + EVENT_IMAGE + " TEXT,"
                    + EVENT_INFO + " TEXT,"
                    + EVENT_IS_HIGHLIGHTED + " TEXT,"
                    + EVENT_IS_WHATS_ON + " TEXT,"
                    + EVENT_DATE + " TEXT,"
                    + EVENT_TIME + " TEXT,"
                    + EVENT_KEY + " TEXT,"
                    + EVENT_GENRE_TYPE + " TEXT);";

    private SQLiteDatabase database;
    SQLiteOpenHelper openHelper;

    public EventListingDB(Context context) {
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
    public void insertOtherEvents(ArrayList<events> mEventListingData) {
        ContentValues contentValue = new ContentValues();
        Gson gson = new Gson();
        try {
            for (int i = 0; i < mEventListingData.size(); i++) {
                contentValue.put(EVENT_ID, mEventListingData.get(i).getEventId());
                contentValue.put(EVENT_TITLE, mEventListingData.get(i).getEventTitle());
                contentValue.put(EVENT_IMAGE, mEventListingData.get(i).getEventThumbImage());
                contentValue.put(EVENT_INFO, mEventListingData.get(i).getEventInfo());
                contentValue.put(EVENT_IS_HIGHLIGHTED, mEventListingData.get(i).getEventIsHighlighted());
                contentValue.put(EVENT_IS_WHATS_ON, mEventListingData.get(i).getEventIsWhatsOn());
                contentValue.put(EVENT_DATE, mEventListingData.get(i).getEventDate());

                String mEventTimes = gson.toJson(mEventListingData.get(i).getEventTime());
                String mEventKeys = gson.toJson(mEventListingData.get(i).getEventKey());
                String mGenreType = gson.toJson(mEventListingData.get(i).getGenreType());

                contentValue.put(EVENT_TIME, mEventTimes);
                contentValue.put(EVENT_KEY, mEventKeys);
                contentValue.put(EVENT_GENRE_TYPE, mGenreType);
                long row = database.insert(TABLE_EVENT_DETAILS, null, contentValue);

                Log.e("row", row + "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<events> fetchAllEvents() {

        ArrayList<events> dataArrayEvents = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_DETAILS, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    events mEvents = new events();

                    mEvents.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEvents.setEventTitle(cursor.getString(cursor.getColumnIndex(EVENT_TITLE)));
                    mEvents.setEventThumbImage(cursor.getString(cursor.getColumnIndex(EVENT_IMAGE)));
                    mEvents.setEventInfo(cursor.getString(cursor.getColumnIndex(EVENT_INFO)));
                    mEvents.setEventIsHighlighted(cursor.getString(cursor.getColumnIndex(EVENT_IS_HIGHLIGHTED)));
                    mEvents.setEventIsWhatsOn(cursor.getString(cursor.getColumnIndex(EVENT_IS_WHATS_ON)));
                    mEvents.setEventDate(cursor.getString(cursor.getColumnIndex(EVENT_DATE)));

                    Type type1 = new TypeToken<ArrayList<EventTime>>() {
                    }.getType();
                    ArrayList<EventTime> eventTimeTypeArray = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_TIME)), type1);

                    Type type2 = new TypeToken<ArrayList<EventKey>>() {
                    }.getType();
                    ArrayList<EventKey> eventKeyArray = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_KEY)), type2);

                    Type type3 = new TypeToken<ArrayList<GenreType>>() {
                    }.getType();
                    ArrayList<GenreType> eventGenreTypeArray = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_GENRE_TYPE)), type3);

                    mEvents.setEventTime(eventTimeTypeArray);
                    mEvents.setEventKey(eventKeyArray);
                    mEvents.setGenreType(eventGenreTypeArray);
                    dataArrayEvents.add(mEvents);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ErrorMessage", e.getMessage());
        }

        return dataArrayEvents;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
