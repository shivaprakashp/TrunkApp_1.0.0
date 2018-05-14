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
import com.opera.app.pojo.events.eventlisiting.EventTime;
import com.opera.app.pojo.events.eventlisiting.Events;
import com.opera.app.pojo.events.eventlisiting.GenreList;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 1000632 on 5/2/2018.
 */

public class EventListingDB {

    public static final String TABLE_EVENT_LISTING = "TABLE_EVENT_LISTING";
    private static final String EVENT_ID = "_id";
    private static final String EVENT_NAME = "EVENT_NAME";
    private static final String EVENT_IMAGE = "EVENT_IMAGE";
    private static final String EVENT_INFO = "EVENT_INFO";
    private static final String EVENT_BUY_NOW = "EVENT_BUY_NOW";
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
    private static final String EVENT_IS_FAVOURITE = "EVENT_IS_FAVOURITE";

    /*//Part of Genre JSON Object
    private static final String EVENT_GENRES_ID = "EVENT_GENRES_ID";
    private static final String EVENT_GENRES_INTERNAL_NAME = "EVENT_GENRES_INTERNAL_NAME";
    private static final String EVENT_GENRE = "EVENT_GENRE";
    private static final String EVENT_GENRE_DESCRIPTION = "EVENT_GENRE_DESCRIPTION";
    private static final String EVENT_GENRES_IMAGE = "EVENT_GENRES_IMAGE";
    //*/

    private static final String EVENT_GENRE = "EVENT_GENRE";
    private static final String EVENT_TIMES = "EVENT_TIMES";

    public static final String CREATE_TABLE_EVENT_LISTING =
            "CREATE TABLE " + TABLE_EVENT_LISTING + "(" + EVENT_ID + " TEXT,"
                    + EVENT_NAME + " TEXT,"
                    + EVENT_IMAGE + " TEXT,"
                    + EVENT_INFO + " TEXT,"
                    + EVENT_BUY_NOW + " TEXT,"
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
                    + EVENT_IS_FAVOURITE + " TEXT,"
                    + EVENT_GENRE + " TEXT,"
                    + EVENT_TIMES + " TEXT);";

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
                contentValue.put(EVENT_BUY_NOW, mEventListingData.get(i).getBuyNowLink());
                contentValue.put(EVENT_IS_WHATS_ON, mEventListingData.get(i).getWhatsOn());
                contentValue.put(EVENT_FROM_DATE, mEventListingData.get(i).getFrom());
                contentValue.put(EVENT_TO_DATE, mEventListingData.get(i).getTo());
                contentValue.put(EVENT_PRICE_FROM, mEventListingData.get(i).getPriceFrom());
                contentValue.put(EVENT_VIDEO, mEventListingData.get(i).getVideo());
                contentValue.put(EVENT_ACTIVE, mEventListingData.get(i).getActive());
                contentValue.put(EVENT_START_TIME, mEventListingData.get(i).getStartTime());
                contentValue.put(EVENT_END_TIME, mEventListingData.get(i).getEndTime());
                contentValue.put(EVENT_INTERNAL_NAME, mEventListingData.get(i).getInternalName());
                contentValue.put(EVENT_IS_HIGHLIGHTED, mEventListingData.get(i).getHighlighted());
                contentValue.put(EVENT_IS_FAVOURITE, "false");            // need to update

                /*contentValue.put(EVENT_GENRES_ID, mEventListingData.get(i).getGenreList().getId());
                contentValue.put(EVENT_GENRES_INTERNAL_NAME, mEventListingData.get(i).getGenreList().getInternalName());
                contentValue.put(EVENT_GENRE, mEventListingData.get(i).getGenreList().getGenere());
                contentValue.put(EVENT_GENRE_DESCRIPTION, mEventListingData.get(i).getGenreList().getDescription());
                contentValue.put(EVENT_GENRES_IMAGE, mEventListingData.get(i).getGenreList().getImage());*/
                String mEventGenres = gson.toJson(mEventListingData.get(i).getGenreList());
                contentValue.put(EVENT_GENRE, mEventGenres);

                String mEventDatesAndTimes = gson.toJson(mEventListingData.get(i).getEventTime());
                contentValue.put(EVENT_TIMES, mEventDatesAndTimes);

                long row = database.insert(TABLE_EVENT_LISTING, null, contentValue);

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
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_LISTING, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Events mEvents = new Events();
                    GenreList mGenreList = new GenreList();

                    mEvents.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEvents.setName(cursor.getString(cursor.getColumnIndex(EVENT_NAME)));
                    mEvents.setImage(cursor.getString(cursor.getColumnIndex(EVENT_IMAGE)));
                    mEvents.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_INFO)));
                    mEvents.setBuyNowLink(cursor.getString(cursor.getColumnIndex(EVENT_BUY_NOW)));
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
                    mEvents.setFavourite(cursor.getString(cursor.getColumnIndex(EVENT_IS_FAVOURITE)));

                   /* mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_ID)));
                    mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_INTERNAL_NAME)));
                    mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRE)));
                    mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRE_DESCRIPTION)));
                    mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_IMAGE)));
                    mEvents.setGenreList(mGenreList);*/

                    Type type2 = new TypeToken<ArrayList<GenreList>>() {
                    }.getType();
                    ArrayList<GenreList> mArrayEventGenres = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_GENRE)), type2);

                    Type type1 = new TypeToken<ArrayList<EventTime>>() {
                    }.getType();
                    ArrayList<EventTime> mArrayEventDateAndTime = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_TIMES)), type1);

                    mEvents.setGenreList(mArrayEventGenres);
                    mEvents.setEventTime(mArrayEventDateAndTime);

                    dataArrayEvents.add(mEvents);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ErrorMessage", e.getMessage());
        }

        return dataArrayEvents;
    }

    public ArrayList<Events> fetchEventsOfSpecificGenres(String mGenreId) {

        ArrayList<Events> dataArrayEvents = new ArrayList<>();
        /*Gson gson = new Gson();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_LISTING, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Events mEvents = new Events();
                    GenreList mGenreList = new GenreList();

                    mEvents.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEvents.setName(cursor.getString(cursor.getColumnIndex(EVENT_NAME)));
                    mEvents.setImage(cursor.getString(cursor.getColumnIndex(EVENT_IMAGE)));
                    mEvents.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_INFO)));
                    mEvents.setBuyNowLink(cursor.getString(cursor.getColumnIndex(EVENT_BUY_NOW)));
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

                    *//*mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_ID)));
                    mGenreList.setInternalName(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_INTERNAL_NAME)));
                    mGenreList.setGenere(cursor.getString(cursor.getColumnIndex(EVENT_GENRE)));
                    mGenreList.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_GENRE_DESCRIPTION)));
                    mGenreList.setImage(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_IMAGE)));*//*

                    Type type2 = new TypeToken<ArrayList<GenreList>>() {
                    }.getType();
                    ArrayList<GenreList> mArrayEventGenres = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_GENRE)), type2);

                    Type type1 = new TypeToken<ArrayList<EventTime>>() {
                    }.getType();
                    ArrayList<EventTime> mArrayEventDateAndTime = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_TIMES)), type1);

                    mEvents.setGenreList(mArrayEventGenres);
                    mEvents.setEventTime(mArrayEventDateAndTime);

                    if (mGenreId.equalsIgnoreCase(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_ID)))) {
                        dataArrayEvents.add(mEvents);
                    }
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ErrorMessage", e.getMessage());
        }*/

        return dataArrayEvents;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
