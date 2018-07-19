package com.opera.app.notification;

import android.support.annotation.NonNull;

import com.opera.app.activities.FeedbackActivity;
import com.opera.app.activities.MainActivity;
import com.opera.app.database.orders.OrderHistoryDB;
import com.opera.app.pojo.favouriteandsettings.OrderHistory;

import java.util.Calendar;

import androidx.work.Worker;


public class FeedBackWorker extends Worker {

    private static final String TAG = FeedBackWorker.class.getSimpleName();
    private OrderHistoryDB orderHistoryDB;
    private boolean flag = false;

    @NonNull
    @Override
    public Result doWork() {
        try {

            NotificationData data = new NotificationData(getApplicationContext(), FeedbackActivity.class);
            Calendar calendar = Calendar.getInstance();
            orderHistoryDB = new OrderHistoryDB(getApplicationContext());
            orderHistoryDB.open();

            if (orderHistoryDB.orderHistories()!=null){
                for (int i = 0 ; i < orderHistoryDB.orderHistories().size() ; i++){
                    OrderHistory history = orderHistoryDB.orderHistories().get(i);
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.clear();
                    String[] dateTime = history.getDateTime().split("T");
                    String[] dateYearMonth = dateTime[0].split("-");

                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    int month = Calendar.getInstance().get(Calendar.MONTH);
                    int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

                    if (dateYearMonth[0].equalsIgnoreCase(String.valueOf(year)) &&
                            dateYearMonth[1].equalsIgnoreCase(String.valueOf(month)) &&
                            dateYearMonth[2].equalsIgnoreCase(String.valueOf(day))){
                                            flag = true;

                        data.notifyData("Dubai Opera",
                                "We would love your Feedback. Click here to rate your experience.");
                    }else {
                        flag = false;
                    }
                }
            }

            return Result.SUCCESS;
        } catch (Throwable throwable) {
            return Result.FAILURE;
        }finally {
            orderHistoryDB.close();
        }
    }

}