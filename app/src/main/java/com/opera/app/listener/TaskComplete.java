package com.opera.app.listener;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 1000779 on 1/24/2018.
 */

public interface TaskComplete {
    void onTaskFinished(Response response,String mRequestKey);
    void onTaskError(Call call, Throwable t,String mRequestKey);
}
