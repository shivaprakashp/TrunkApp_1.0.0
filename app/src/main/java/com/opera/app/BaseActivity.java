package com.opera.app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * Created by 1000779 on 3/22/2018.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    protected void bindViews() {
        ButterKnife.bind(this);

    }

    public void openActivity(Activity activity, Class<?> startClass) {
        Intent intent = new Intent(activity, startClass);
        startActivity(intent);
        //finish();
    }

    public String jsonResponse(Response response) {

        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            return jObjError.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
