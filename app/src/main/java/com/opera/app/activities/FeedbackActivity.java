package com.opera.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.feedback.FeedbackListingDB;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.listadapters.FeedbackAdapter;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.feedback.FeedbackResponse;
import com.opera.app.pojo.feedback.FeedbackResponseParent;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 6/4/2018.
 */

public class FeedbackActivity extends BaseActivity {

    private Activity mActivity;
    private FeedbackAdapter mAdapter;
    private ArrayList<FeedbackResponse> arrFeedbackListing=new ArrayList<>();
    private FeedbackListingDB mFeedbackListing;

    @BindView(R.id.toolbarRecycler)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.recyclerList)
    RecyclerView mRecyclerFeedback;

    @Inject
    Retrofit retrofit;
    private Api api;
    TextViewWithFont txtToolbarName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        mActivity = FeedbackActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.common_recycler);

        injectView();
        initView();
        initToolbar();
        getFeedback();
    }

    private void injectView() {
        ((MainApplication) getApplication()).getNetComponent().inject(FeedbackActivity.this);
        api = retrofit.create(Api.class);
    }

    private void initView() {
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_notification));

        mAdapter = new FeedbackAdapter(mActivity, arrFeedbackListing);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerFeedback.setLayoutManager(mLayoutManager);
        mRecyclerFeedback.setItemAnimator(new DefaultItemAnimator());
        mRecyclerFeedback.setAdapter(mAdapter);

        mFeedbackListing = new FeedbackListingDB(mActivity);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void getFeedback() {
        MainController controller = new MainController(mActivity);
        controller.getFeedbackDetails(taskComplete, api);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            FeedbackResponseParent mFeedbackResponseParent = (FeedbackResponseParent) response.body();
            if (response.body() != null)
                try {

                } catch (Exception e) {
                    e.printStackTrace();
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

        }
    };
}
