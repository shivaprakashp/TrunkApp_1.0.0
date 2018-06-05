package com.opera.app.activities;

import android.app.Activity;
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
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.GuestDialog;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.events.eventlisiting.AllEvents;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GiftCardActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;
    private Api api;
    private Activity mActivity;
    private SessionManager manager;

    @BindView(R.id.toolbar_edit_profile)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.btnBuyTickets)
    Button btnBuyTickets;

    @BindView(R.id.txt_gift_card_details)
    TextViewWithFont mTxtGiftCardDetails;


    @BindView(R.id.voucher_cover_image)
    ImageView mIvVoucherCoverImage;

    @BindView(R.id.progressImageLoader)
    ProgressBar mProgressImageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = GiftCardActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_gift_card);

        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.menu_gift_cards));

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
    }
    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.btnBuyTickets})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuyTickets:
                if (manager.isUserLoggedIn()) {

                } else {
                    GuestDialog dialog = new GuestDialog(mActivity, mActivity.getString(R.string.guest_title), mActivity.getString(R.string.guest_msg));
                    dialog.show();
                }
                break;
        }
    }
    private void initView() {
        manager = new SessionManager(mActivity);
        ((MainApplication) getApplication()).getNetComponent().inject(GiftCardActivity.this);
        api = retrofit.create(Api.class);
        if (Connections.isConnectionAlive(mActivity)) {
            GetData();
        }
        else {
            if (manager.getGiftCardOfflineData() != null && manager.getGiftCardOfflineData().getEvents().get(0).getDescription() != null) {
                mTxtGiftCardDetails.setText(Html.fromHtml(manager.getGiftCardOfflineData().getEvents().get(0).getDescription()));
            }
        }
    }

    private void GetData() {
        MainController controller = new MainController(mActivity);
        controller.getGiftCardDetails(taskComplete, api);
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {

            AllEvents mEventDataPojo = (AllEvents) response.body();
            try {
                if (mEventDataPojo.getStatus().equalsIgnoreCase("success")) {
                    mTxtGiftCardDetails.setText(Html.fromHtml(mEventDataPojo.getEvents().get(0).getDescription()));
                    Picasso.with(mActivity).load(mEventDataPojo.getEvents().get(0).getImage())
                            .into(mIvVoucherCoverImage, new Callback() {
                                @Override
                                public void onSuccess() {
                                    mProgressImageLoader.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    mProgressImageLoader.setVisibility(View.GONE);
                                }
                            });
                    manager.storeGiftCardDataOffline((AllEvents) response.body());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("data", "error");
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
