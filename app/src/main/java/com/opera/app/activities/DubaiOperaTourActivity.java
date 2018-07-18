package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.GuestDialog;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.events.eventlisiting.AllEvents;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DubaiOperaTourActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;
    private Api api;
    private Activity mActivity;
    private SessionManager manager;
    private CustomToast customToast;

    @BindView(R.id.toolbar_edit_profile)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.btnBookNow)
    Button mBtnBookNow;

    @BindView(R.id.txt_tour_details)
    TextViewWithFont mTxtTourDetails;

    @BindView(R.id.tour_cover_image)
    ImageView mIvTourCoverImage;

    @BindView(R.id.progressImageLoader)
    ProgressBar mProgressImageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = DubaiOperaTourActivity.this;
        customToast = new CustomToast(mActivity);

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_dubai_opera_tour);

        initToolbar();
        initView();

        //Calling Google analytics
        OperaUtils.SendGoogleAnalyticsEvent(getResources().getString(R.string.dubai_opera_tour));
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        TextViewWithFont txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_opera_tour));

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.btnBookNow})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBookNow:
                if (manager.isUserLoggedIn()) {
                    if (Connections.isConnectionAlive(mActivity)) {
                        if (manager.getGiftCardOfflineData().getEvents() != null && manager.getGiftCardOfflineData().getEvents().size() > 0) {
                            Intent in = new Intent(mActivity, BuyTicketWebView.class);
                            in.putExtra("URL", manager.getGiftCardOfflineData().getEvents().get(0).getBuyNowLink());
                            in.putExtra("Header", getResources().getString(R.string.menu_opera_tour));
                            startActivity(in);
                        } else {
                            customToast.showErrorToast(getResources().getString(R.string.no_buy_link_available));
                        }
                    } else {
                        customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
                    }

                } else {
                    GuestDialog dialog = new GuestDialog(mActivity, mActivity.getString(R.string.guest_title), mActivity.getString(R.string.guest_msg));
                    dialog.show();
                }
                break;
        }
    }

    private void initView() {
        manager = new SessionManager(mActivity);
        ((MainApplication) getApplication()).getNetComponent().inject(DubaiOperaTourActivity.this);
        api = retrofit.create(Api.class);
        if (Connections.isConnectionAlive(mActivity)) {
            GetData();
        } else {
            if (manager.getTourOfflineData() != null && manager.getTourOfflineData().getEvents().get(0).getDescription() != null) {
                mTxtTourDetails.setText(Html.fromHtml(manager.getTourOfflineData().getEvents().get(0).getDescription()));
            }
            else {
                customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
            }
        }
    }

    private void GetData() {
        MainController controller = new MainController(mActivity);
        controller.getDubaiOperaTourDetails(taskComplete, api);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {

            AllEvents mEventDataPojo = (AllEvents) response.body();
            try {
                if (mEventDataPojo.getStatus().equalsIgnoreCase(AppConstants.STATUS_SUCCESS)) {
                    mTxtTourDetails.setText(Html.fromHtml(mEventDataPojo.getEvents().get(0).getDescription()));
                    Picasso.with(mActivity).load(mEventDataPojo.getEvents().get(0).getImage())
                            .into(mIvTourCoverImage, new Callback() {
                                @Override
                                public void onSuccess() {
                                    mProgressImageLoader.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    mProgressImageLoader.setVisibility(View.GONE);
                                }
                            });
                    manager.storeTourDataOffline((AllEvents) response.body());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("data", "error");
        }
    };
}
