package com.opera.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.opera.app.controller.MainController;
import com.opera.app.dagger.Api;
import com.opera.app.listener.TaskComplete;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response) {
            Log.i("ResponseData", response.body().toString());
        }

        @Override
        public void onTaskError(Call call, Throwable t) {
            Log.i("ResponseData", call.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MainApplication) getApplication()).getNetComponent().inject(this);

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDagger();
            }
        });
    }

    private void callDagger(){

        Api api = retrofit.create(Api.class);

        MainController controller = new MainController(MainActivity.this);
        controller.callData(taskComplete, api);
    }
}
