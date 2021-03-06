package com.opera.app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.SuccessDialogue;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.ticketbooking.EventTicketBookingPojo;
import com.opera.app.pojo.ticketbooking.SuccessResponse;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 7/2/2018.
 */

public class BuyTicketWebView extends BaseActivity {

    private String URL = "";
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private Api api;
    @Inject
    Retrofit retrofit;

    /*Session manager is used to store language preference of user.
    * The selected lanugage used for further communication into the apps.*/
    private SessionManager manager;

    @BindView(R.id.webBuyTickets)
    WebView myWebView;

    @BindView(R.id.toolbarBuyTicketWebView)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = BuyTicketWebView.this;
        //For Language activity_setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_buy_ticket);

        clearCache(mActivity, 0);
        //instance of session has created
        manager = new SessionManager(mActivity);

        initToolbar();
        LoadWebView();

//        Log.e("Decrypt ", decodeURIComponent("\"%3a\"%2bDXYcLwZY2cPbM0fBWjWcwBpd7y15%2bHDOsIEBvsNAePO21inVm2kOo8PDZdoOAMbgxbRp1orOMFk97v1QEsBKA%3d%3d\"%7d"));

    }

    private void LoadWebView() {
        myWebView.setVisibility(View.VISIBLE);

        ((MainApplication) getApplication()).getNetComponent().inject(this);
        api = retrofit.create(Api.class);

        myWebView.getSettings().setUserAgentString(AppConstants.DTCM_USER_AGENT_STRING);

        CookieSyncManager.createInstance(myWebView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(AppConstants.DTCM_DOMAIN_NAME, "X-Auth-Token=" + manager.getUserLoginData().getData().getDtcmXAuthToken());    // USER_SESSION_TOKEN will replace with actual token of user
//        cookieManager.setCookie(AppConstants.DTCM_DOMAIN_NAME, "X-Auth-Token=" + AppConstants.USER_SESSION_TOKEN);    // USER_SESSION_TOKEN will replace with actual token of user

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        /*myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setAllowContentAccess(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);*/

        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.loadUrl(URL);
        cookieManager.setAcceptThirdPartyCookies(myWebView, true);
        myWebView.requestFocus();
    }


    private void initToolbar() {
        setSupportActionBar(toolbar);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);

        Intent in = getIntent();
        URL = in.getStringExtra("URL");
        String header = in.getStringExtra("Header");
        txtToolbarName.setText(header);

        mProgressDialog = new ProgressDialog(BuyTicketWebView.this);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
//            view.loadDataWithBaseURL(null,url, "text/html", "UTF-8",null);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("Page started", url);

            /*if(url.equalsIgnoreCase("https://uat.timesofmoney.com/direcpay/secure/PaymentTransactionServlet")){
                myWebView.getSettings().setUserAgentString(AppConstants.DTCM_USER_AGENT_STRING);
            }*/
            try {
                mProgressDialog.show();
                mProgressDialog.setMessage("Please wait...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("Page finished", url);
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*if(url.equalsIgnoreCase("https://dubaioperaw-mobile-uat.etixdubai.com/shows/deliverytype.aspx")){
                myWebView.getSettings().setUserAgentString("");
            }*/

            if (url.contains("vd-call")) {
                String mIsVdCall = "", mVdCallTokenNo = "", mVdCallTokenString = "", dataForDecryption = "", mJsonData = "";
                String[] urlStr = url.split(":");

                try {
                    mIsVdCall = urlStr[0];
                    mVdCallTokenNo = urlStr[1];
                    mVdCallTokenString = urlStr[2];
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!mVdCallTokenNo.equalsIgnoreCase("") && mVdCallTokenNo.equalsIgnoreCase("4")) {
                    String[] mActualVdToken = mVdCallTokenString.split("token");
                    dataForDecryption = mActualVdToken[1];

                    mJsonData = decodeURIComponent(mVdCallTokenString);
                    JSONObject obj = new JSONObject();
                    try {
                        obj = new JSONObject(mJsonData);
                        obj.remove("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("complete data", obj.toString());
//                    mJsonData = "{\"token" + mJsonData;

//                    myWebView.setVisibility(View.GONE);
                    Gson gson = new Gson();
                    EventTicketBookingPojo response = gson.fromJson(obj.toString(), EventTicketBookingPojo.class);

                    if (response.getTickets().size() > 0) {
                        CallViewOrderAPI(response);
                    }
                } else if (!mVdCallTokenNo.equalsIgnoreCase("") && mVdCallTokenNo.equalsIgnoreCase("1")) {
                    finish();
                }
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            Log.e("onReceivedSslError", error.toString());

            handler.proceed();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.e("onReceivedError", error.toString());

        }
    }

    private void CallViewOrderAPI(EventTicketBookingPojo mCompleteData) {

        MainController controller = new MainController(mActivity);
        controller.SaveOrderAPI(taskComplete, api, mCompleteData);
    }

    public String decodeURIComponent(String s) {
        if (s == null) {
            return null;
        }

        String result = null;

        try {
            result = URLDecoder.decode(s, "UTF-8");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    static int clearCacheFolder(final File dir, final int numDays) {

        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {

                    //first delete subdirectories recursively
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }

                    //then delete the files and subdirectories in this dir
                    //only empty directories can be deleted, so subdirs have been done first
                    if (child.lastModified() < new Date().getTime() - numDays * DateUtils.DAY_IN_MILLIS) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
//                Log.e(TAG, String.format("Failed to clean the cache, error %s", e.getMessage()));
            }
        }
        return deletedFiles;
    }

    /*
     * Delete the files older than numDays days from the application cache
     * 0 means all files.
     */
    public static void clearCache(final Context context, final int numDays) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
        cookieManager.removeExpiredCookie();

//        Log.i(TAG, String.format("Starting cache prune, deleting files older than %d days", numDays));
        int numDeletedFiles = clearCacheFolder(context.getCacheDir(), numDays);
//        Log.i(TAG, String.format("Cache pruning completed, %d files deleted", numDeletedFiles));
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            if (mRequestKey.equalsIgnoreCase(AppConstants.SAVEORDER.SAVEORDER)) {
                SuccessResponse mSuccessResponse = (SuccessResponse) response.body();

                if (mSuccessResponse.getStatus().equalsIgnoreCase(AppConstants.STATUS_SUCCESS)) {
                    try {
                        myWebView.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SuccessDialogue dialogue = new SuccessDialogue(mActivity, getResources().getString(R.string.ticket_booked_success), getResources().getString(R.string.success_header), getResources().getString(R.string.ok), "BookEvent");
                    dialogue.show();
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {

        }
    };
}
