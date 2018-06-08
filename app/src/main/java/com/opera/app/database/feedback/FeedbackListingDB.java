package com.opera.app.database.feedback;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opera.app.database.OperaDBHandler;
import com.opera.app.pojo.feedback.FeedbackResponse;
import com.opera.app.pojo.notifications.Notification;

import java.util.ArrayList;

/**
 * Created by 1000632 on 6/4/2018.
 */

public class FeedbackListingDB {

    // Table Name
    public static final String TABLE_FEEDBACK_DETAILS = "TABLE_FEEDBACK_DETAILS";

    // Column Name
    private static final String FEEDBACK_SHOW_NAME = "FEEDBACK_SHOW_NAME";
    private static final String FEEDBACK_SHOW_DESCRIPTION = "FEEDBACK_SHOW_DESCRIPTION";

    private static final String FEEDBACK_DATE = "FEEDBACK_DATE";
    private static final String FEEDBACK_SHOW_IMAGE = "FEEDBACK_SHOW_IMAGE";


    //creating table
    public static final String CREATE_TABLE_FEEDBACK =
            "CREATE TABLE " + TABLE_FEEDBACK_DETAILS + "(" + FEEDBACK_SHOW_NAME + " TEXT,"            // NEED TO CHANGE UNIQUE
                    + FEEDBACK_SHOW_DESCRIPTION + " TEXT,"
                    + FEEDBACK_DATE + " TEXT,"
                    + FEEDBACK_SHOW_IMAGE + " TEXT);";


    private SQLiteDatabase database;
    SQLiteOpenHelper openHelper;

    public FeedbackListingDB(Context context) {
        openHelper = OperaDBHandler.getInstance(context);
    }


    public void open(){
        Log.i("Database","Database Opened");
        database = openHelper.getWritableDatabase();


    }
    public void close(){
        Log.i("Database", "Database Closed");
        openHelper.close();
    }



    //insterting data
    public void insertFeedback(ArrayList<FeedbackResponse> mFeedbackResponses) {
        ContentValues contentValue = new ContentValues();
        try {
            for (int i = 0; i < mFeedbackResponses.size(); i++) {
                contentValue.put(FEEDBACK_SHOW_NAME, mFeedbackResponses.get(i).getmShowName());
                contentValue.put(FEEDBACK_SHOW_DESCRIPTION, mFeedbackResponses.get(i).getmShowDescription());
                contentValue.put(FEEDBACK_DATE, mFeedbackResponses.get(i).getmDateAndTime());
                contentValue.put(FEEDBACK_SHOW_IMAGE, mFeedbackResponses.get(i).getmShowName());

                long row = database.insert(TABLE_FEEDBACK_DETAILS, null, contentValue);
            }
        }catch (SQLException e){e.printStackTrace();}
    }

    public ArrayList<FeedbackResponse> fetchFeedbackDetails() {

        ArrayList<FeedbackResponse> dataArrayList = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_FEEDBACK_DETAILS, null);
            if (cursor != null && cursor.getCount()>0) {
                cursor.moveToFirst();
                do {
                    FeedbackResponse mFeedbackData = new FeedbackResponse();

                    mFeedbackData.setmShowName(cursor.getString(cursor.getColumnIndex(FEEDBACK_SHOW_NAME)));
                    mFeedbackData.setmShowDescription(cursor.getString(cursor.getColumnIndex(FEEDBACK_SHOW_DESCRIPTION)));
                    mFeedbackData.setmDateAndTime(cursor.getString(cursor.getColumnIndex(FEEDBACK_DATE)));
                    mFeedbackData.setmShowImage(cursor.getString(cursor.getColumnIndex(FEEDBACK_SHOW_IMAGE)));

                    dataArrayList.add(mFeedbackData);
                } while (cursor.moveToNext());
            }
        }catch (SQLException e){
            Log.e("ErrorMessage", e.getMessage());
        }

        return dataArrayList;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
