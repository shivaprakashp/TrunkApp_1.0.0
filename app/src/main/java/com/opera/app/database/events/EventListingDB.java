package com.opera.app.database.events;

import android.app.Activity;
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
import com.opera.app.pojo.favouriteandsettings.Favourite;
import com.opera.app.preferences.SessionManager;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by 1000632 on 5/2/2018.
 */

public class EventListingDB {

    public static final String TABLE_EVENT_LISTING = "TABLE_EVENT_LISTING";
    private static final String EVENT_ID = "_id";
    private static final String EVENT_NAME = "EVENT_NAME";
    private static final String EVENT_PERFORMANCE_CODE_OUTER = "EVENT_PERFORMANCE_CODE_OUTER";
    private static final String EVENT_IMAGE = "EVENT_IMAGE";
    private static final String EVENT_INFO = "EVENT_INFO";
    private static final String EVENT_MOBILE_DESCRIPTION = "EVENT_MOBILE_DESCRIPTION";
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
    private static final String EVENT_FEEDBACK_URL = "EVENT_FEEDBACK_URL";
    private static final String EVENT_EVENT_URL = "EVENT_EVENT_URL";
    private static final String EVENT_SHARED_CONTENT = "EVENT_SHARED_CONTENT";
    private static final String EVENT_SHARED_CONTENT_TEXT = "EVENT_SHARED_CONTENT_TEXT";
    private static final String EVENT_HIGHLIGHTED_IMAGE = "EVENT_HIGHLIGHTED_IMAGE";
    private static final String EVENT_DETAILS_IMAGE = "EVENT_DETAILS_IMAGE";
    private static final String EVENT_WHATS_ON_IMAGE = "EVENT_WHATS_ON_IMAGE";

    private static final String EVENT_GENRE = "EVENT_GENRE";
    private static final String EVENT_TIMES = "EVENT_TIMES";

    public static final String CREATE_TABLE_EVENT_LISTING =
            "CREATE TABLE " + TABLE_EVENT_LISTING + "(" + EVENT_ID + " TEXT,"
                    + EVENT_NAME + " TEXT,"
                    + EVENT_PERFORMANCE_CODE_OUTER + " TEXT,"
                    + EVENT_IMAGE + " TEXT,"
                    + EVENT_INFO + " TEXT,"
                    + EVENT_MOBILE_DESCRIPTION + " TEXT,"
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
                    + EVENT_FEEDBACK_URL + " TEXT,"
                    + EVENT_EVENT_URL + " TEXT,"
                    + EVENT_SHARED_CONTENT + " TEXT,"
                    + EVENT_SHARED_CONTENT_TEXT + " TEXT,"
                    + EVENT_HIGHLIGHTED_IMAGE + " TEXT,"
                    + EVENT_DETAILS_IMAGE + " TEXT,"
                    + EVENT_WHATS_ON_IMAGE + " TEXT,"
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
    public void insertOtherEvents(Activity mActivity, ArrayList<Events> mEventListingData, ArrayList<Events> mEventFavouritesForGuest
            , ArrayList<Favourite> arrFavouriteDataOfLoggedInUser) {
        ContentValues contentValue = new ContentValues();
        SessionManager manager = new SessionManager(mActivity);
        Gson gson = new Gson();
        try {
            for (int i = 0; i < mEventListingData.size(); i++) {
                contentValue.put(EVENT_ID, mEventListingData.get(i).getEventId());
                contentValue.put(EVENT_NAME, mEventListingData.get(i).getName());
                contentValue.put(EVENT_PERFORMANCE_CODE_OUTER, mEventListingData.get(i).getEventPerfCode());
                contentValue.put(EVENT_IMAGE, mEventListingData.get(i).getImage());
                contentValue.put(EVENT_INFO, mEventListingData.get(i).getDescription());
                contentValue.put(EVENT_MOBILE_DESCRIPTION, mEventListingData.get(i).getMobileDescription());
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

                if (manager.isUserLoggedIn()) {
                    String IsFavourite = "false";
                    for (int j = 0; j < arrFavouriteDataOfLoggedInUser.size(); j++) {
                        if (mEventListingData.get(i).getEventId().equalsIgnoreCase(arrFavouriteDataOfLoggedInUser.get(j).getFavouriteId().toUpperCase())) {
                            IsFavourite = "true";
                        }
                    }
                    contentValue.put(EVENT_IS_FAVOURITE, IsFavourite);
                    // need to update
                } else {
                    //Handling for guest user
                    if (mEventFavouritesForGuest.size() > 0) {
                        for (int j = 0; j < mEventFavouritesForGuest.size(); j++) {
                            if (mEventListingData.get(i).getEventId().equalsIgnoreCase(mEventFavouritesForGuest.get(j).getEventId())) {
                                contentValue.put(EVENT_IS_FAVOURITE, mEventFavouritesForGuest.get(j).isFavourite());
                            }
                        }

                    } else {
                        contentValue.put(EVENT_IS_FAVOURITE, mEventListingData.get(i).isFavourite());
                    }

                }
                contentValue.put(EVENT_FEEDBACK_URL, mEventListingData.get(i).getFeedbackUrl());
                contentValue.put(EVENT_EVENT_URL, mEventListingData.get(i).getEventUrl());
                contentValue.put(EVENT_SHARED_CONTENT, mEventListingData.get(i).getSharedContent());
                contentValue.put(EVENT_SHARED_CONTENT_TEXT, mEventListingData.get(i).getSharedContentText());

                contentValue.put(EVENT_HIGHLIGHTED_IMAGE, mEventListingData.get(i).getHighlightedImage());
                contentValue.put(EVENT_DETAILS_IMAGE, mEventListingData.get(i).getEventDetailImage());
                contentValue.put(EVENT_WHATS_ON_IMAGE, mEventListingData.get(i).getWhatsOnImage());

                String mEventGenres = gson.toJson(mEventListingData.get(i).getGenreList());
                contentValue.put(EVENT_GENRE, mEventGenres);

                String mEventDatesAndTimes = gson.toJson(mEventListingData.get(i).getEventTime());
                contentValue.put(EVENT_TIMES, mEventDatesAndTimes);

                long row = database.insert(TABLE_EVENT_LISTING, null, contentValue);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void UpdateFavouriteData(String mEventId, String IsFavourite) {
        ContentValues cv = new ContentValues();
        cv.put(EVENT_IS_FAVOURITE, IsFavourite);
        database.update(TABLE_EVENT_LISTING, cv, "_id=" + "=\"" + mEventId + "\"", null);
    }


    public ArrayList<Events> fetchAllEvents() {

        ArrayList<Events> dataArrayEvents = new ArrayList<>();
        Gson gson = new Gson();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_LISTING, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Events mEvents = new Events();

                    mEvents.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEvents.setName(cursor.getString(cursor.getColumnIndex(EVENT_NAME)));
                    mEvents.setEventPerfCode(cursor.getString(cursor.getColumnIndex(EVENT_PERFORMANCE_CODE_OUTER)));
                    mEvents.setImage(cursor.getString(cursor.getColumnIndex(EVENT_IMAGE)));
                    mEvents.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_INFO)));
                    mEvents.setMobileDescription(cursor.getString(cursor.getColumnIndex(EVENT_MOBILE_DESCRIPTION)));
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
                    mEvents.setFeedbackUrl(cursor.getString(cursor.getColumnIndex(EVENT_FEEDBACK_URL)));
                    mEvents.setEventUrl(cursor.getString(cursor.getColumnIndex(EVENT_EVENT_URL)));
                    mEvents.setSharedContent(cursor.getString(cursor.getColumnIndex(EVENT_SHARED_CONTENT)));
                    mEvents.setSharedContentText(cursor.getString(cursor.getColumnIndex(EVENT_SHARED_CONTENT_TEXT)));
                    mEvents.setHighlightedImage(cursor.getString(cursor.getColumnIndex(EVENT_HIGHLIGHTED_IMAGE)));
                    mEvents.setEventDetailImage(cursor.getString(cursor.getColumnIndex(EVENT_DETAILS_IMAGE)));
                    mEvents.setWhatsOnImage(cursor.getString(cursor.getColumnIndex(EVENT_WHATS_ON_IMAGE)));

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
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return dataArrayEvents;
    }

    //For guest only
    public boolean IsFavouriteForSpecificEvent(String mEventId) {
        boolean IsFavourite = false;
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_LISTING + " where _id = '" + mEventId + "'", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Events mEvents = new Events();
                    mEvents.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEvents.setFavourite(cursor.getString(cursor.getColumnIndex(EVENT_IS_FAVOURITE)));

                    IsFavourite = cursor.getString(cursor.getColumnIndex(EVENT_IS_FAVOURITE)).equalsIgnoreCase("true");

                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return IsFavourite;
    }

    //For guest only
    public ArrayList<Events> fetchEventsWithFavouriteForGuest() {
        ArrayList<Events> dataArrayEvents = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_LISTING, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Events mEvents = new Events();
                    mEvents.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEvents.setFavourite(cursor.getString(cursor.getColumnIndex(EVENT_IS_FAVOURITE)));

                    dataArrayEvents.add(mEvents);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return dataArrayEvents;
    }

   /* //For guest only
    public ArrayList<Events> fetchIsFavouriteForSpcificEventId(String mEventId) {
        ArrayList<Events> dataArrayEvents = new ArrayList<>();
        Gson gson = new Gson();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_LISTING, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Events mEvents = new Events();
                    mEvents.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEvents.setFavourite(cursor.getString(cursor.getColumnIndex(EVENT_IS_FAVOURITE)));

                    dataArrayEvents.add(mEvents);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ErrorMessage", e.getMessage());
        }

        return dataArrayEvents;
    }*/

    public ArrayList<Events> fetchEventsOfSpecificGenres(ArrayList<GenreList> mGenreLists, String mEventId) {

        ArrayList<Events> dataArrayEvents = new ArrayList<>();
        Gson gson = new Gson();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_EVENT_LISTING, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Events mEvents = new Events();

                    mEvents.setEventId(cursor.getString(cursor.getColumnIndex(EVENT_ID)));
                    mEvents.setName(cursor.getString(cursor.getColumnIndex(EVENT_NAME)));
                    mEvents.setEventPerfCode(cursor.getString(cursor.getColumnIndex(EVENT_PERFORMANCE_CODE_OUTER)));
                    mEvents.setImage(cursor.getString(cursor.getColumnIndex(EVENT_IMAGE)));
                    mEvents.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_INFO)));
                    mEvents.setMobileDescription(cursor.getString(cursor.getColumnIndex(EVENT_MOBILE_DESCRIPTION)));
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
                    mEvents.setFeedbackUrl(cursor.getString(cursor.getColumnIndex(EVENT_FEEDBACK_URL)));
                    mEvents.setEventUrl(cursor.getString(cursor.getColumnIndex(EVENT_EVENT_URL)));
                    mEvents.setSharedContent(cursor.getString(cursor.getColumnIndex(EVENT_SHARED_CONTENT)));
                    mEvents.setSharedContentText(cursor.getString(cursor.getColumnIndex(EVENT_SHARED_CONTENT_TEXT)));
                    mEvents.setHighlightedImage(cursor.getString(cursor.getColumnIndex(EVENT_HIGHLIGHTED_IMAGE)));
                    mEvents.setEventDetailImage(cursor.getString(cursor.getColumnIndex(EVENT_DETAILS_IMAGE)));
                    mEvents.setWhatsOnImage(cursor.getString(cursor.getColumnIndex(EVENT_WHATS_ON_IMAGE)));

                    /*mGenreList.setId(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_ID)));
                    mGenreList.setInternalName(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_INTERNAL_NAME)));
                    mGenreList.setGenere(cursor.getString(cursor.getColumnIndex(EVENT_GENRE)));
                    mGenreList.setDescription(cursor.getString(cursor.getColumnIndex(EVENT_GENRE_DESCRIPTION)));
                    mGenreList.setImage(cursor.getString(cursor.getColumnIndex(EVENT_GENRES_IMAGE)));*/

                    Type type2 = new TypeToken<ArrayList<GenreList>>() {
                    }.getType();
                    ArrayList<GenreList> mArrayEventGenres = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_GENRE)), type2);

                    Type type1 = new TypeToken<ArrayList<EventTime>>() {
                    }.getType();
                    ArrayList<EventTime> mArrayEventDateAndTime = gson.fromJson(cursor.getString(cursor.getColumnIndex(EVENT_TIMES)), type1);

                    mEvents.setGenreList(mArrayEventGenres);
                    mEvents.setEventTime(mArrayEventDateAndTime);

                   /* for (int i = 0; i < mArrayEventGenres.size(); i++) {
                        for (int j = 0; j < mGenreLists.size(); j++) {
                            if (mArrayEventGenres.get(i).getId().equalsIgnoreCase(mGenreLists.get(j).getId())) {
                                dataArrayEvents.add(mEvents);
                                break;
                            }
                        }
                    }*/

                    for (int i = 0; i < mGenreLists.size(); i++) {
                        for (int j = 0; j < mArrayEventGenres.size(); j++) {
                            if (mGenreLists.get(i).getInternalName().equalsIgnoreCase(mArrayEventGenres.get(j).getInternalName()) && !cursor.getString(cursor.getColumnIndex(EVENT_ID)).equalsIgnoreCase(mEventId)) {
                                dataArrayEvents.add(mEvents);
                            }
                        }
                    }

                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return dataArrayEvents;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
