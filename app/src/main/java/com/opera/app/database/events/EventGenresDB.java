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
import com.opera.app.pojo.events.eventlisiting.GenreList;

import java.util.ArrayList;

public class EventGenresDB {

    public static final String TABLE_GENRES_LISTING = "TABLE_GENRES_LISTING";

    //Part of Genre JSON Object
    private static final String EVENT_GENRES_ID = "EVENT_GENRES_ID";
    private static final String EVENT_GENRES_INTERNAL_NAME = "EVENT_GENRES_INTERNAL_NAME";
    private static final String EVENT_GENRE = "EVENT_GENRE";
    private static final String EVENT_GENRE_DESCRIPTION = "EVENT_GENRE_DESCRIPTION";
    private static final String EVENT_GENRES_IMAGE = "EVENT_GENRES_IMAGE";
    //

    private static final String EVENT_TIMES = "EVENT_TIMES";

    public static final String CREATE_TABLE_GENRES_LISTING =
            "CREATE TABLE " + TABLE_GENRES_LISTING + "(" + EVENT_GENRES_ID + " TEXT,"
                    + EVENT_GENRES_INTERNAL_NAME + " TEXT,"
                    + EVENT_GENRE + " TEXT,"
                    + EVENT_GENRE_DESCRIPTION + " TEXT,"
                    + EVENT_GENRES_IMAGE + " TEXT,"
                    + EVENT_TIMES + " TEXT);";

    private SQLiteDatabase database;
    SQLiteOpenHelper openHelper;

    public EventGenresDB(Context context) {
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
    public void insertEventsGenres(ArrayList<GenreList> mGenresListingData) {
        ContentValues contentValue = new ContentValues();
        Gson gson = new Gson();
        String mEventStartDate = "", mEventEndDate = "";
        try {
            for (int i = 0; i < mGenresListingData.size(); i++) {
               
                contentValue.put(EVENT_GENRES_ID, mGenresListingData.get(i).getId());
                contentValue.put(EVENT_GENRES_INTERNAL_NAME, mGenresListingData.get(i).getInternalName());
                contentValue.put(EVENT_GENRE, mGenresListingData.get(i).getGenere());
                contentValue.put(EVENT_GENRE_DESCRIPTION, mGenresListingData.get(i).getDescription());
                contentValue.put(EVENT_GENRES_IMAGE, mGenresListingData.get(i).getImage());

                long row = database.insert(TABLE_GENRES_LISTING, null, contentValue);

                Log.e("row", row + "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GenreList> fetchAllEvents() {

        ArrayList<GenreList> dataArrayEvents = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_GENRES_LISTING, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    GenreList mGenreList = new GenreList();

                    mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_ID)));
                    mGenreList.setInternalName(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_INTERNAL_NAME)));
                    mGenreList.setGenere(cursor.getString(cursor.getColumnIndex(EVENT_GENRE)));
                    mGenreList.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_GENRE_DESCRIPTION)));
                    mGenreList.setImage(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_IMAGE)));

                    dataArrayEvents.add(mGenreList);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ErrorMessage", e.getMessage());
        }

        return dataArrayEvents;
    }

    public ArrayList<GenreList> fetchEventsOfSpecificGenres(String mGenreId) {

        ArrayList<GenreList> dataArrayEvents = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_GENRES_LISTING, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    GenreList mGenreList = new GenreList();

                    mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_ID)));
                    mGenreList.setInternalName(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_INTERNAL_NAME)));
                    mGenreList.setGenere(cursor.getString(cursor.getColumnIndex(EVENT_GENRE)));
                    mGenreList.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_GENRE_DESCRIPTION)));
                    mGenreList.setImage(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_IMAGE)));

                    if (mGenreId.equalsIgnoreCase(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_ID)))) {
                        dataArrayEvents.add(mGenreList);
                    }
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
