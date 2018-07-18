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

    @NonNull
    @Override
    public Worker.Result doWork() {
        try {
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

                }
            }
            data.notifyData("Dubai Opera","Cronol start at 3:00 PM Ejoy the show");
            return Worker.Result.SUCCESS;
        } catch (Throwable throwable) {
            return Worker.Result.FAILURE;
        }finally {
            orderHistoryDB.close();
        }
    }

}