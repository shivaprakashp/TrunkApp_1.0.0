package com.opera.app.notification;

import android.support.annotation.NonNull;
import android.util.Log;

import com.opera.app.R;
import com.opera.app.activities.MainActivity;
import com.opera.app.constants.AppConstants;
import com.opera.app.database.orders.OrderHistoryDB;
import com.opera.app.pojo.favouriteandsettings.OrderHistory;

import java.util.Calendar;

import androidx.work.Worker;


public class LoggerWorker extends Worker {

    private static final String TAG = LoggerWorker.class.getSimpleName();
    private OrderHistoryDB orderHistoryDB;
    private boolean flag = false;

    @NonNull
    @Override
    public Worker.Result doWork() {
        try {
            String eventName ;
            NotificationData data = new NotificationData(getApplicationContext(), MainActivity.class);
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

                                            eventName = history.getEventName();

                        data.notifyData("Dubai Opera",
                                eventName+" start at "+dateTime[1]+". Enjoy the show!");
                    }else {
                        flag = false;
                    }
                }
            }

            return Worker.Result.SUCCESS;
        } catch (Throwable throwable) {
            return Worker.Result.FAILURE;
        }finally {
            orderHistoryDB.close();
        }
    }

}