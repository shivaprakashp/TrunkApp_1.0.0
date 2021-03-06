package com.opera.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.database.BookedEventsHistory;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.enums.WalletEnums;
import com.opera.app.fragments.wallet.WalletFragmentPagerAdapter;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.wallet.WalletDetails;
import com.opera.app.pojo.wallet.eventwallethistory.ParentDataForBookedEventHistory;
import com.opera.app.preferences.wallet.WalletPreference;
import com.opera.app.utils.Connections;
import com.opera.app.utils.OperaManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WalletActivity extends BaseActivity {

    private Activity mActivity;
    private BookedEventsHistory dbBookendEventsHistory;

    @BindView(R.id.toolbar_wallet)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.btnWalletEvent)
    ButtonWithFont btnEvent;

    @BindView(R.id.btnWalletRest)
    ButtonWithFont btnRest;

    @BindView(R.id.btnWalletGift)
    ButtonWithFont btnGift;

    @BindView(R.id.walletTabHost)
    TabLayout walletTabHost;

    @BindView(R.id.walletViewPager)
    ViewPager walletViewPager;

    @Inject
    Retrofit retrofit;
    private Api api;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {

            if (mRequestKey.equalsIgnoreCase(AppConstants.GETBOOKEDEVENTDETAILS.GETBOOKEDEVENTDETAILS)) {

                ParentDataForBookedEventHistory mBookedEventHistory = (ParentDataForBookedEventHistory) response.body();

                if(mBookedEventHistory!=null && mBookedEventHistory.getStatus().equalsIgnoreCase("success")){

                    if(mBookedEventHistory.getData()!=null){
                        dbBookendEventsHistory.open();
                        dbBookendEventsHistory.deleteCompleteTable(BookedEventsHistory.TABLE_BOOKED_EVENTS_HISTORY);

                        for (int i = 0; i < mBookedEventHistory.getData().size(); i++) {
                            for (int j = 0; j < mBookedEventHistory.getData().get(i).getOrderItems().size(); j++) {


                                String mGenresName = "";
                                if(mBookedEventHistory.getData().get(i).getOrderEvents()!=null){
                                    for (int k = 0; k < mBookedEventHistory.getData().get(i).getOrderEvents().getArrEventGenre().size(); k++) {
                                        if (mGenresName.equalsIgnoreCase("")) {
                                            mGenresName = mBookedEventHistory.getData().get(i).getOrderEvents().getArrEventGenre().get(k).getGenere();
                                        } else {
                                            mGenresName = new StringBuilder().append(mGenresName).append(",").append(mBookedEventHistory.getData().get(i).getOrderEvents().getArrEventGenre().get(k).getGenere()).toString();
                                        }
                                    }
                                }

                                if(mBookedEventHistory.getData().get(i).getOrderEvents()!=null){
                                    dbBookendEventsHistory.insertBookedEventsHistory(mBookedEventHistory.getData().get(i).getOrderItems().get(j).getOrderFrom(),
                                            mBookedEventHistory.getData().get(i).getOrderItems().get(j).getOrderLineItems(),
                                            mBookedEventHistory.getData().get(i).getOrderEvents().getEventId(),
                                            mBookedEventHistory.getData().get(i).getOrderEvents().getEventName(),
                                            mGenresName,
                                            mBookedEventHistory.getData().get(i).getId(),
                                            mBookedEventHistory.getData().get(i).getDateTime());
                                }else{
                                    dbBookendEventsHistory.insertBookedEventsHistory(mBookedEventHistory.getData().get(i).getOrderItems().get(j).getOrderFrom(),
                                            mBookedEventHistory.getData().get(i).getOrderItems().get(j).getOrderLineItems(),
                                            "",
                                            "",
                                            mGenresName,
                                            mBookedEventHistory.getData().get(i).getId(),
                                            mBookedEventHistory.getData().get(i).getDateTime());
                                }

                            }
                        }
                    }
                }else{
                    ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
                }
            } else {
                if (response.body() != null) {
                    WalletPreference preference = new WalletPreference(mActivity);
                    preference.deleteWalletData();
                    preference.setWalletData((WalletDetails) response.body());
                    initButtons(WalletEnums.EVENTS);
                } else if (response.errorBody() != null) {
                    try {
                        ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                        dialogue.show();
                    } catch (Exception e) {
                        //Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                        customToast.showErrorToast(e.getMessage());
                    }
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("error", "");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        injectView();
        initView();
        initToolbar();
        getHistory();
        GetEventsData();

    }

    private void GetEventsData() {
        MainController controller = new MainController(WalletActivity.this);
        controller.getBookedEventDetails(taskComplete, api);
    }

    private void initButtons(WalletEnums enums) {
        switch (enums) {
            case EVENTS:
                btnEvent.setBackgroundColor(getResources().getColor(R.color.colorLightBurgendy));
                btnRest.setBackgroundColor(getResources().getColor(R.color.colorDarkBurgendy));
                btnGift.setBackgroundColor(getResources().getColor(R.color.colorDarkBurgendy));
                initFragment(WalletEnums.EVENTS);

                break;
            case RESTAURANT:
                btnEvent.setBackgroundColor(getResources().getColor(R.color.colorDarkBurgendy));
                btnRest.setBackgroundColor(getResources().getColor(R.color.colorLightBurgendy));
                btnGift.setBackgroundColor(getResources().getColor(R.color.colorDarkBurgendy));
                initFragment(WalletEnums.RESTAURANT);

                break;
            case GIFT:
                btnEvent.setBackgroundColor(getResources().getColor(R.color.colorDarkBurgendy));
                btnRest.setBackgroundColor(getResources().getColor(R.color.colorDarkBurgendy));
                btnGift.setBackgroundColor(getResources().getColor(R.color.colorLightBurgendy));
                initFragment(WalletEnums.GIFT);

                break;
        }
    }

    private void initFragment(WalletEnums enums) {
        OperaManager.createInstance().setWalletData(enums);
        WalletFragmentPagerAdapter adapter = new WalletFragmentPagerAdapter(mActivity,
                getSupportFragmentManager(),enums);

        walletViewPager.setAdapter(adapter);
        walletTabHost.setupWithViewPager(walletViewPager);
    }

  /*  public static String getEnumData(){
        return enumData;
    }*/

    private void getHistory() {
        if (Connections.isConnectionAlive(mActivity)) {
            MainController controller = new MainController(WalletActivity.this);
            controller.getWalletDetails(taskComplete, api);
        } else {
            customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
        }
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.btnWalletEvent, R.id.btnWalletRest, R.id.btnWalletGift})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnWalletEvent:
                initButtons(WalletEnums.EVENTS);
                break;

            case R.id.btnWalletRest:
                initButtons(WalletEnums.RESTAURANT);
                break;

            case R.id.btnWalletGift:
                initButtons(WalletEnums.GIFT);
                break;
        }
    }

    private void injectView() {
        ((MainApplication) getApplication()).getNetComponent().inject(WalletActivity.this);
        api = retrofit.create(Api.class);
    }

    private void initView() {
        mActivity = WalletActivity.this;

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.walletTitle));

        dbBookendEventsHistory = new BookedEventsHistory(mActivity);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

}
