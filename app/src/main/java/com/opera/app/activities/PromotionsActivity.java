package com.opera.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.notification.PromotionDetailsDB;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.listadapters.PromotionsAdapter;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.promotions.PromotionDetails;
import com.opera.app.pojo.promotions.PromotionsPojo;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PromotionsActivity extends BaseActivity {

    private Activity mActivity;
    private ArrayList<PromotionDetails> mPromotionDetails = new ArrayList<>();
    private PromotionsAdapter mAdapter;
    private PromotionDetailsDB dbManagerPromotion;

    @BindView(R.id.toolbarRecycler)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerNotificatons;

    @Inject
    Retrofit retrofit;
    private Api api;
    TextViewWithFont txtToolbarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        mActivity = PromotionsActivity.this;
        //For Language activity_setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.common_recycler);

        injectView();
        initView();
        initToolbar();
        getPromotions();

    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            PromotionsPojo mPromotionsPojo = (PromotionsPojo) response.body();
            if (response.body() != null) {
                try {
                    if (mPromotionsPojo.getStatus().equalsIgnoreCase("success")) {
                        dbManagerPromotion.open();
                        dbManagerPromotion.deleteCompleteTable(PromotionDetailsDB.TABLE_PROMOTION_DETAILS);
                        dbManagerPromotion.insertPromotions(mPromotionsPojo.getPromotionsData());
                        fetchDataFromPromotionDB();
                    }
                } catch (Exception e) {
                    Log.e("Message", e.getMessage());
                    e.printStackTrace();
                }
            }
            else if (response.errorBody() != null) {
                try {
                    ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
                } catch (Exception e) {
                    //Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    customToast.showErrorToast(e.getMessage());
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            dbManagerPromotion.open();
            fetchDataFromPromotionDB();
            dbManagerPromotion.close();
        }
    };

    private void injectView() {
        ((MainApplication) getApplication()).getNetComponent().inject(PromotionsActivity.this);
        api = retrofit.create(Api.class);
    }

    private void initView() {
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_promotion));

        mAdapter = new PromotionsAdapter(mActivity, mPromotionDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerNotificatons.setLayoutManager(mLayoutManager);
        mRecyclerNotificatons.setItemAnimator(new DefaultItemAnimator());
        mRecyclerNotificatons.setAdapter(mAdapter);

        dbManagerPromotion = new PromotionDetailsDB(mActivity);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void getPromotions() {
        MainController controller = new MainController(PromotionsActivity.this);
        controller.getPromotionDetails(taskComplete, api);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void fetchDataFromPromotionDB() {
        mAdapter.RefreshList(dbManagerPromotion.fetchPromotionDetails());
        dbManagerPromotion.close();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mActivity!=null){
            mActivity = null;
        }
    }
}
