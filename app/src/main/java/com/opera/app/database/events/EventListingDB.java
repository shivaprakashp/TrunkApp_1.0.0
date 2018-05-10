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
import com.opera.app.pojo.events.eventlisiting.EventDates;
import com.opera.app.pojo.events.eventlisiting.EventGenres;
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
    private static final String EVENT_IS_HIGHLIGHTED = "EVENT_IS_HIGHLIGHTED";
    private static final String EVENT_IS_WHATS_ON = "EVENT_IS_WHATS_ON";
    private static final String EVENT_ACTIVE = "EVENT_ACTIVE";
    private static final String EVENT_INTERNAL_NAME = "EVENT_INTERNAL_NAME";
    private static final String EVENT_FROM_DATE = "EVENT_FROM_DATE";
    private static final String EVENT_TO_DATE = "EVENT_TO_DATE";
    private static final String EVENT_GENRES = "EVENT_GENRES";
    private static final String EVENT_DATES = "EVENT_DATES";

   /* private static final String EVENT_BUY_NOW_LINK = "EVENT_BUY_NOW_LINK";
    private static final String EVENT_FROM_DATE = "EVENT_FROM_DATE";
    private static final String EVENT_TO_DATE = "EVENT_TO_DATE";
    private static final String EVENT_PRICE_FROM = "EVENT_PRICE_FROM";
    private static final String EVENT_VIDEO = "EVENT_VIDEO";
    private static final String EVENT_START_TIME = "EVENT_START_TIME";
    private static final String EVENT_END_TIME = "EVENT_END_TIME";*/

    //creating table
    public static final String CREATE_TABLE_EVENT_DETAILS =
            "CREATE TABLE " + TABLE_EVENT_DETAILS + "(" + EVENT_ID + " TEXT,"
                    + EVENT_NAME + " TEXT,"
                    + EVENT_IMAGE + " TEXT,"
                    + EVENT_INFO + " TEXT,"
                    + EVENT_IS_HIGHLIGHTED + " TEXT,"
                    + EVENT_IS_WHATS_ON + " TEXT,"
                    + EVENT_ACTIVE + " TEXT,"
                    + EVENT_INTERNAL_NAME + " TEXT,"
                    + EVENT_FROM_DATE + " TEXT,"
                    + EVENT_TO_DATE + " TEXT,"
                    + EVENT_GENRES + " TEXT,"
                    + EVENT_DATES + " TEXT);";

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
        String mEventStartDate = "", mEventEndDate = "";
        try {
            for (int i = 0; i < mEventListingData.size(); i++) {
                contentValue.put(EVENT_ID, mEventListingData.get(i).getEventId());
                contentValue.put(EVENT_NAME, mEventListingData.get(i).getName());
                contentValue.put(EVENT_IMAGE, mEventListingData.get(i).getImage());
                contentValue.put(EVENT_INFO, mEventListingData.get(i).getDescription());
                contentValue.put(EVENT_IS_HIGHLIGHTED, mEventListingData.get(i).getEventIsHighlighted());
                contentValue.put(EVENT_IS_WHATS_ON, mEventListingData.get(i).getEventIsWhatsOn());
                contentValue.put(EVENT_ACTIVE, mEventListingData.get(i).getActive());
                contentValue.put(EVENT_INTERNAL_NAME, mEventListingData.get(i).getInternalName());

                for (int j = 0; j < mEventListingData.get(i).getEventDates().size(); j++) {
                    if (j == 0) {
                        mEventStartDate = mEventListingData.get(i).getEventDates().get(j).getEventDate();
                    }

                    if (j == mEventListingData.get(i).getEventDates().size() - 1) {
                        mEventEndDate = mEventListingData.get(i).getEventDates().get(j).getEventDate();
                    }
                }

                contentValue.put(EVENT_FROM_DATE, mEventStartDate);

                contentValue.put(EVENT_TO_DATE, mEventEndDate);

                String mEventGenres = gson.toJson(mEventListingData.get(i).getEventGenres());
                String mEventDates = gson.toJson(mEventListingData.get(i).getEventDates());

                contentValue.put(EVENT_GENRES, mEventGenres);
                contentValue.put(EVENT_DATES, mEventDates);

                /*contentValue.put(EVENT_ACTIVE, mEventListingData.get(i).getActive());
                contentValue.put(EVENT_START_TIME, mEventListingData.get(i).getStartTime());
                contentValue.put(EVENT_END_TIME, mEventListingData.get(i).getEndTime());
                contentValue.put(EVENT_INTERNAL_NAME, mEventListingData.get(i).getInternalName());*/

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
                    mEvents.setEventIsHighlighted(cursor.getString(cursor.getColumnIndex(EVENT_IS_HIGHLIGHTED)));
                    mEvents.setEventIsWhatsOn(cursor.getString(cursor.getColumnIndex(EVENT_IS_WHATS_ON)));
                    mEvents.setActive(cursor.getString(cursor.getColumnIndex(EVENT_ACTIVE)));
                    mEvents.setInternalName(cursor.getString(cursor.getColumnIndex(EVENT_INTERNAL_NAME)));
                    mEvents.setStartDate(cursor.getString(cursor.getColumnIndex(EVENT_FROM_DATE)));
                    mEvents.setEndDate(cursor.getString(cursor.getColumnIndex(EVENT_TO_DATE)));

                    Type type1 = new TypeToken<ArrayList<EventGenres>>() {
                    }.getType();
                    ArrayList<EventGenres> mArrayEventGenre = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_GENRES)), type1);

                    Type type2 = new TypeToken<ArrayList<EventDates>>() {
                    }.getType();
                    ArrayList<EventDates> mArrayEventDates = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_DATES)), type2);

                    mEvents.setEventGenres(mArrayEventGenre);
                    mEvents.setEventDates(mArrayEventDates);


                    /*mEvents.setActive(cursor.getString(cursor.getColumnIndex(EVENT_ACTIVE)));
                    mEvents.setStartTime(cursor.getString(cursor.getColumnIndex(EVENT_START_TIME)));
                    mEvents.setEndTime(cursor.getString(cursor.getColumnIndex(EVENT_END_TIME)));
                    mEvents.setInternalName(cursor.getString(cursor.getColumnIndex(EVENT_INTERNAL_NAME)));*/

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
