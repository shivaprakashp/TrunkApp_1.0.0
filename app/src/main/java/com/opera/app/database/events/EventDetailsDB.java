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
import com.opera.app.pojo.events.EventKey;
import com.opera.app.pojo.events.EventTime;
import com.opera.app.pojo.events.GenreType;
import com.opera.app.pojo.events.eventdetails.EventDetails;
import com.opera.app.pojo.events.eventdetails.EventDetailsPojo;
import com.opera.app.pojo.events.eventdetails.EventVenue;
import com.opera.app.pojo.events.eventdetails.FavouriteEvents;
import com.opera.app.pojo.events.events;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 1000632 on 5/4/2018.
 */

public class EventDetailsDB {

    public static final String TABLE_EVENT_INNER_DETAILS = "TABLE_EVENT_DETAILS";
    private static final String EVENT_ID = "_id";
    private static final String EVENT_TITLE = "EVENT_TITLE";
    private static final String EVENT_IMAGE = "EVENT_IMAGE";
    private static final String EVENT_INFO = "EVENT_INFO";
    private static final String EVENT_PERFORM_TYPE = "EVENT_PERFORM_TYPE";
    private static final String EVENT_GENRE_TYPE = "EVENT_GENRE_TYPE";
    private static final String EVENT_VIDEO_URL = "EVENT_VIDEO_URL";
    private static final String EVENT_FAVOURITE = "EVENT_FAVOURITE";
    private static final String EVENT_VENUE = "EVENT_VENUE";


    //creating table
    public static final String CREATE_TABLE_EVENT_INNER_DETAILS =
            "CREATE TABLE " + TABLE_EVENT_INNER_DETAILS + "(" + EVENT_ID + " INTEGER,"
                    + EVENT_TITLE + " TEXT,"
                    + EVENT_IMAGE + " TEXT,"
                    + EVENT_INFO + " TEXT,"
                    + EVENT_PERFORM_TYPE + " TEXT,"
                    + EVENT_GENRE_TYPE + " TEXT,"
                    + EVENT_VIDEO_URL + " TEXT,"
                    + EVENT_FAVOURITE + " TEXT,"
                    + EVENT_VENUE + " TEXT);";

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
    public void insertIntoEventsDetails(ArrayList<EventDetails> mEventDetailsData) {
        ContentValues contentValue = new ContentValues();
        Gson gson = new Gson();
        try {
            for (int i = 0; i < mEventDetailsData.size(); i++) {
                contentValue.put(EVENT_ID, mEventDetailsData.get(i).getEventId());
                contentValue.put(EVENT_TITLE, mEventDetailsData.get(i).getEventTitle());
                contentValue.put(EVENT_IMAGE, mEventDetailsData.get(i).getEventImage());
                contentValue.put(EVENT_INFO, mEventDetailsData.get(i).getEventDetail());
                contentValue.put(EVENT_PERFORM_TYPE, mEventDetailsData.get(i).getEventPerformType());
                contentValue.put(EVENT_GENRE_TYPE, mEventDetailsData.get(i).getEventGenreType());
                contentValue.put(EVENT_VIDEO_URL, mEventDetailsData.get(i).getEventVideo());

                String mEventFavourties = gson.toJson(mEventDetailsData.get(i).getFavouriteEvents());
                String mEventVenues = gson.toJson(mEventDetailsData.get(i).getEventVenue());

                contentValue.put(EVENT_FAVOURITE, mEventFavourties);
                contentValue.put(EVENT_VENUE, mEventVenues);
                long row = database.insert(TABLE_EVENT_INNER_DETAILS, null, contentValue);

                Log.e("row", row + "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<EventDetails> fetchSpecificEventDetails() {

        ArrayList<EventDetails> dataArrayEventDetails = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_INNER_DETAILS, null);
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    EventDetails mEventDetails = new EventDetails();

                    mEventDetails.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEventDetails.setEventTitle(cursor.getString(cursor.getColumnIndex(EVENT_TITLE)));
                    mEventDetails.setEventImage(cursor.getString(cursor.getColumnIndex(EVENT_IMAGE)));
                    mEventDetails.setEventDetail(cursor.getString(cursor.getColumnIndex(EVENT_INFO)));
                    mEventDetails.setEventPerformType(cursor.getString(cursor.getColumnIndex(EVENT_PERFORM_TYPE)));
                    mEventDetails.setEventGenreType(cursor.getString(cursor.getColumnIndex(EVENT_GENRE_TYPE)));
                    mEventDetails.setEventVideo(cursor.getString(cursor.getColumnIndex(EVENT_VIDEO_URL)));

                    Type type1 = new TypeToken<ArrayList<FavouriteEvents>>() {
                    }.getType();
                    ArrayList<FavouriteEvents> favEvent = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_FAVOURITE)), type1);

                    Type type2 = new TypeToken<ArrayList<EventVenue>>() {
                    }.getType();
                    ArrayList<EventVenue> venueEvents = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_VENUE)), type2);

                    mEventDetails.setFavouriteEvents(favEvent);
                    mEventDetails.setEventVenue(venueEvents);
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
