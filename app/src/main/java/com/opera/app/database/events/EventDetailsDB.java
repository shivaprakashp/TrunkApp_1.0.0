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
import com.opera.app.pojo.events.eventlisiting.EventTime;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.events.eventlisiting.GenreList;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class EventDetailsDB {

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
    private static final String EVENT_IS_HIGHLIGHTED = "EVENT_IS_HIGHLIGHTED";

    //Part of Genre JSON Object
    private static final String EVENT_GENRES_ID = "EVENT_GENRES_ID";
    private static final String EVENT_GENRES_INTERNAL_NAME = "EVENT_GENRES_INTERNAL_NAME";
    private static final String EVENT_GENRE = "EVENT_GENRE";
    private static final String EVENT_GENRE_DESCRIPTION = "EVENT_GENRE_DESCRIPTION";
    private static final String EVENT_GENRES_IMAGE = "EVENT_GENRES_IMAGE";
    //

    private static final String EVENT_TIMES = "EVENT_TIMES";

    public static final String CREATE_TABLE_EVENT_DETAILS =
            "CREATE TABLE " + TABLE_EVENT_DETAILS + "(" + EVENT_ID + " TEXT,"
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
                    + EVENT_INTERNAL_NAME + " TEXT,"
                    + EVENT_IS_HIGHLIGHTED + " TEXT,"
                    + EVENT_GENRES_ID + " TEXT,"
                    + EVENT_GENRES_INTERNAL_NAME + " TEXT,"
                    + EVENT_GENRE + " TEXT,"
                    + EVENT_GENRE_DESCRIPTION + " TEXT,"
                    + EVENT_GENRES_IMAGE + " TEXT,"
                    + EVENT_TIMES + " TEXT);";


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
    public void insertIntoEventsDetails(ArrayList<Events> mEventDetailsData) {
        ContentValues contentValue = new ContentValues();
        Gson gson = new Gson();
        try {
            contentValue.put(EVENT_ID, mEventDetailsData.get(0).getEventId());
            contentValue.put(EVENT_NAME, mEventDetailsData.get(0).getName());
            contentValue.put(EVENT_IMAGE, mEventDetailsData.get(0).getImage());
            contentValue.put(EVENT_INFO, mEventDetailsData.get(0).getDescription());
            contentValue.put(EVENT_BUY_NOW_LINK, mEventDetailsData.get(0).getBuyNowLink());
            contentValue.put(EVENT_IS_WHATS_ON, mEventDetailsData.get(0).getWhatsOn());
            contentValue.put(EVENT_FROM_DATE, mEventDetailsData.get(0).getFrom());
            contentValue.put(EVENT_TO_DATE, mEventDetailsData.get(0).getTo());
            contentValue.put(EVENT_PRICE_FROM, mEventDetailsData.get(0).getPriceFrom());
            contentValue.put(EVENT_VIDEO, mEventDetailsData.get(0).getVideo());
            contentValue.put(EVENT_ACTIVE, mEventDetailsData.get(0).getActive());
            contentValue.put(EVENT_START_TIME, mEventDetailsData.get(0).getStartTime());
            contentValue.put(EVENT_END_TIME, mEventDetailsData.get(0).getEndTime());
            contentValue.put(EVENT_INTERNAL_NAME, mEventDetailsData.get(0).getInternalName());
            contentValue.put(EVENT_IS_HIGHLIGHTED, mEventDetailsData.get(0).getHighlighted());

           /* contentValue.put(EVENT_GENRES_ID, mEventDetailsData.get(0).getGenreList().getId());
            contentValue.put(EVENT_GENRES_INTERNAL_NAME, mEventDetailsData.get(0).getGenreList().getInternalName());
            contentValue.put(EVENT_GENRE, mEventDetailsData.get(0).getGenreList().getGenere());
            contentValue.put(EVENT_GENRE_DESCRIPTION, mEventDetailsData.get(0).getGenreList().getDescription());
            contentValue.put(EVENT_GENRES_IMAGE, mEventDetailsData.get(0).getGenreList().getImage());*/

            String mEventDatesAndTimes = gson.toJson(mEventDetailsData.get(0).getEventTime());
            contentValue.put(EVENT_TIMES, mEventDatesAndTimes);

            long row = database.insert(TABLE_EVENT_DETAILS, null, contentValue);

                Log.e("row", row + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Events> fetchSpecificEventDetails() {

        ArrayList<Events> dataArrayEventDetails = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_DETAILS, null);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    Events mEvents = new Events();
                    GenreList mGenreList=new GenreList();

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
                    mEvents.setHighlighted(cursor.getString(cursor.getColumnIndex(EVENT_IS_HIGHLIGHTED)));

                    mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_ID)));
                    mGenreList.setInternalName(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_INTERNAL_NAME)));
                    mGenreList.setGenere(cursor.getString(cursor.getColumnIndex(EVENT_GENRE)));
                    mGenreList.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_GENRE_DESCRIPTION)));
                    mGenreList.setImage(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_IMAGE)));
//                    mEvents.setGenreList(mGenreList);

                    Type type1 = new TypeToken<ArrayList<EventTime>>() {
                    }.getType();
                    ArrayList<EventTime> mArrayEventDateAndTime = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_TIMES)), type1);

                    mEvents.setEventTime(mArrayEventDateAndTime);

                    dataArrayEventDetails.add(mEvents);
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
