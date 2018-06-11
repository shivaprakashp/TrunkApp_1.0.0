package com.opera.app.database.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opera.app.database.OperaDBHandler;
import com.opera.app.pojo.notifications.Notification;
import com.opera.app.pojo.promotions.PromotionDetails;
import com.opera.app.pojo.promotions.PromotionsPojo;

import java.util.ArrayList;

public class PromotionDetailsDB {
    // Table Name
    public static final String TABLE_PROMOTION_DETAILS = "TABLE_PROMOTION_DETAILS";

    // Column Name
    private static final String PROMOTION_ID = "PROMOTION_ID";
    private static final String PROMOTION_TITLE = "PROMOTION_TITLE";
    private static final String PROMOTION_IMAGE= "PROMOTION_IMAGE";
    private static final String PROMOTION_DESCRIPTION = "PROMOTION_DESCRIPTION";
    private static final String PROMOTION_PRICE = "PROMOTION_PRICE";
    private static final String PROMOTION_VALID_FROM = "PROMOTION_VALID_FROM";
    private static final String PROMOTION_VALID_TO = "PROMOTION_VALID_TO";
    private static final String PROMOTION_DESCRIPTION_HTML = "PROMOTION_DESCRIPTION_HTML";


    //creating table
    public static final String CREATE_TABLE_PROMOTION =
            "CREATE TABLE " + TABLE_PROMOTION_DETAILS + "(" + PROMOTION_ID + " TEXT,"
                    + PROMOTION_TITLE + " TEXT,"
                    + PROMOTION_IMAGE + " TEXT,"
                    + PROMOTION_DESCRIPTION + " TEXT,"
                    + PROMOTION_PRICE + " TEXT,"
                    + PROMOTION_VALID_FROM + " TEXT,"
                    + PROMOTION_VALID_TO + " TEXT,"
                    + PROMOTION_DESCRIPTION_HTML + " TEXT);";


    private SQLiteDatabase database;
    SQLiteOpenHelper openHelper;

    public PromotionDetailsDB(Context context) {
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
    public void insertPromotions(ArrayList<PromotionDetails> mPromotions) {
        ContentValues contentValue = new ContentValues();
        try {
            for (int i = 0; i < mPromotions.size(); i++) {
                contentValue.put(PROMOTION_ID, mPromotions.get(i).getId());
                contentValue.put(PROMOTION_TITLE, mPromotions.get(i).getTitle());
                contentValue.put(PROMOTION_IMAGE, mPromotions.get(i).getImage());
                contentValue.put(PROMOTION_DESCRIPTION, mPromotions.get(i).getDescription());
                contentValue.put(PROMOTION_PRICE, mPromotions.get(i).getPrice());
                contentValue.put(PROMOTION_VALID_FROM, mPromotions.get(i).getValidFrom());
                contentValue.put(PROMOTION_VALID_TO, mPromotions.get(i).getValidTo());
                contentValue.put(PROMOTION_DESCRIPTION_HTML, mPromotions.get(i).getDescriptionHtml());

                long row = database.insert(TABLE_PROMOTION_DETAILS, null, contentValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PromotionDetails> fetchPromotionDetails() {

        ArrayList<PromotionDetails> dataArrayList = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_PROMOTION_DETAILS, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    PromotionDetails mPromotionData = new PromotionDetails();

                    mPromotionData.setId(cursor.getString(cursor.getColumnIndex(PROMOTION_ID)));
                    mPromotionData.setTitle(cursor.getString(cursor.getColumnIndex(PROMOTION_TITLE)));
                    mPromotionData.setImage(cursor.getString(cursor.getColumnIndex(PROMOTION_IMAGE)));
                    mPromotionData.setDescription(cursor.getString(cursor.getColumnIndex(PROMOTION_DESCRIPTION)));
                    mPromotionData.setPrice(cursor.getString(cursor.getColumnIndex(PROMOTION_PRICE)));
                    mPromotionData.setValidFrom(cursor.getString(cursor.getColumnIndex(PROMOTION_VALID_FROM)));
                    mPromotionData.setValidTo(cursor.getString(cursor.getColumnIndex(PROMOTION_VALID_TO)));
                    mPromotionData.setDescriptionHtml(cursor.getString(cursor.getColumnIndex(PROMOTION_DESCRIPTION_HTML)));

                    dataArrayList.add(mPromotionData);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("ErrorMessage", e.getMessage());
        }

        return dataArrayList;
    }

    public void deleteCompleteTable(String mTableName) {
        database.delete(mTableName, null, null);
    }
}
