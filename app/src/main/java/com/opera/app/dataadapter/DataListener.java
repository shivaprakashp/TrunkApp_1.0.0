package com.opera.app.dataadapter;

import android.app.ProgressDialog;
import android.content.Context;

import com.opera.app.R;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.RequestProperties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 1000779 on 1/24/2018.
 */

public class DataListener {

    private ProgressDialog dialog;
    private Context context;
    protected TaskComplete taskComplete = null;
    private RequestProperties properties;

    public DataListener(Context context, TaskComplete taskComplete, RequestProperties properties) {
        this.context = context;
        this.taskComplete = taskComplete;
        dialog = new ProgressDialog(context);
        this.properties = properties;
    }

    public void dataLoad(Call call) {
        try {
            dialog.setMessage(context.getResources().getString(R.string.loading));
            dialog.setCancelable(false);
            dialog.show();
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    dialog.dismiss();
                    taskComplete.onTaskFinished(response,properties.getRequestKey());
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                   dialog.dismiss();
                   taskComplete.onTaskError(call, t,properties.getRequestKey());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
