package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
                        if (manager.getTourOfflineData().getEvents() != null && manager.getTourOfflineData().getEvents().size() > 0) {

                            OperaUtils.BuyTicketCommmonFunction(mActivity, manager.getTourOfflineData().getEvents().get(0).getBuyNowLink(), mActivity.getResources().getString(R.string.menu_opera_tour));
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

                    /*Spanned result;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        result = Html.fromHtml(mEventDataPojo.getEvents().get(0).getDescription(),Html.FROM_HTML_MODE_LEGACY);
                    } else {
                        result = Html.fromHtml(mEventDataPojo.getEvents().get(0).getDescription());
                    }
                    mTxtTourDetails.setText(result);
                    mTxtTourDetails. setMovementMethod(LinkMovementMethod.getInstance());*/

                    setTextViewHTML(mTxtTourDetails,mEventDataPojo.getEvents().get(0).getDescription());

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

    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
    {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                // Do something with span.getURL() to handle the link click...
                String mUrlBrowser="";
                if(!span.getURL().contains("http")){
                    mUrlBrowser="http://"+span.getURL();
                }else{
                    mUrlBrowser=span.getURL();
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrlBrowser));
                startActivity(browserIntent);

               /* Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }*/
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    protected void setTextViewHTML(TextView text, String html)
    {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
