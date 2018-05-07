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
import com.opera.app.pojo.events.eventlisiting.Events;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 1000632 on 5/2/2018.
 */

public class EventListingDB {

    public static final String TABLE_EVENT_DETAILS = "TABLE_EVENT_DETAILS";
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
    public static final String CREATE_TABLE_EVENT_DETAILS =
            "CREATE TABLE " + TABLE_EVENT_DETAILS + "(" + EVENT_ID + " INTEGER,"
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
    public void insertOtherEvents(ArrayList<Events> mEventListingData) {
        ContentValues contentValue = new ContentValues();
        Gson gson = new Gson();
        try {
            for (int i = 0; i < mEventListingData.size(); i++) {
                contentValue.put(EVENT_ID, mEventListingData.get(i).getEventId());
                contentValue.put(EVENT_NAME, mEventListingData.get(i).getName());
                contentValue.put(EVENT_IMAGE, mEventListingData.get(i).getImage());
                contentValue.put(EVENT_INFO, mEventListingData.get(i).getDescription());
                contentValue.put(EVENT_BUY_NOW_LINK, mEventListingData.get(i).getBuyNowLink());
                contentValue.put(EVENT_IS_WHATS_ON, mEventListingData.get(i).getWhatsOn());
                contentValue.put(EVENT_FROM_DATE, mEventListingData.get(i).getFrom());
                contentValue.put(EVENT_TO_DATE, mEventListingData.get(i).getTo());


                contentValue.put(EVENT_PRICE_FROM, mEventListingData.get(i).getPriceFrom());
                contentValue.put(EVENT_VIDEO, mEventListingData.get(i).getVideo());
                contentValue.put(EVENT_ACTIVE, mEventListingData.get(i).getActive());

                contentValue.put(EVENT_START_TIME, mEventListingData.get(i).getStartTime());
                contentValue.put(EVENT_END_TIME, mEventListingData.get(i).getEndTime());
                contentValue.put(EVENT_INTERNAL_NAME, mEventListingData.get(i).getInternalName());

                /*String mEventTimes = gson.toJson(mEventListingData.get(i).getEventTime());
                String mEventKeys = gson.toJson(mEventListingData.get(i).getEventKey());
                String mGenreType = gson.toJson(mEventListingData.get(i).getGenreType());*/
                long row = database.insert(TABLE_EVENT_DETAILS, null, contentValue);

                Log.e("row", row + "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Events> fetchAllEvents() {

        ArrayList<Events> dataArrayEvents = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_DETAILS, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Events mEvents = new Events();

                    mEvents.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEvents.setName(cursor.getString(cursor.getColumnIndex(EVENT_NAME)));
                    mEvents.setImage(cursor.getString(cursor.getColumnIndex(EVENT_IMAGE)));
                    mEvents.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_INFO)));
                    mEvents.setBuyNowLink(cursor.getString(cursor.getColumnIndex(EVENT_BUY_NOW_LINK)));
                    mEvents.setWhatsOn(cursor.getString(cursor.getColumnIndex(EVENT_IS_WHATS_ON)));
                    mEvents.setFrom(cursor.getString(cursor.getColumnIndex(EVENT_FROM_DATE)));

                    mEvents.setTo(cursor.getString(cursor.getColumnIndex(EVENT_TO_DATE)));
                    mEvents.setPriceFrom(cursor.getString(cursor.getColumnIndex(EVENT_PRICE_FROM)));
                    mEvents.setVideo(cursor.getString(cursor.getColumnIndex(EVENT_VIDEO)));
                    mEvents.setActive(cursor.getString(cursor.getColumnIndex(EVENT_ACTIVE)));
                    mEvents.setStartTime(cursor.getString(cursor.getColumnIndex(EVENT_START_TIME)));
                    mEvents.setEndTime(cursor.getString(cursor.getColumnIndex(EVENT_END_TIME)));
                    mEvents.setInternalName(cursor.getString(cursor.getColumnIndex(EVENT_INTERNAL_NAME)));

                  /*  Type type1 = new TypeToken<ArrayList<EventTime>>() {
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
                    mEvents.setGenreType(eventGenreTypeArray);*/
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
