package com.opera.app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;

import butterknife.BindView;

/**
 * Created by 1000632 on 4/4/2018.
 */

public class CommonWebViewActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView mWebView;

    private String URL = "",Header="";
    private ProgressDialog mDialog;
    private Activity mActivity;

    @BindView(R.id.toolbar_contactUs)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        initToolbar();
        webView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
    }

    private void webView() {
        mActivity = CommonWebViewActivity.this;
        mDialog = new ProgressDialog(mActivity);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        Intent in = getIntent();
        URL = in.getStringExtra("URL");
        Header = in.getStringExtra("Header");

//        Apple URL - https://embed.music.apple.com/us/album/haçienda-classiçal/1159323021
        //Apple complete URL - https://itunes.apple.com/us/album/ha%C3%A7ienda-classi%C3%A7al/1159323021

        mWebView.loadUrl("https://itunes.apple.com/us/album/ha%C3%A7ienda-classi%C3%A7al/1159323021");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.requestFocus();

        /*TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(Header);*/
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if( URLUtil.isNetworkUrl(url) ) {
                return false;
            }
            //For apple music
            if (url.equalsIgnoreCase("apple-music://itunes.apple.com/us/album/ha%C3%A7ienda-classi%C3%A7al/1159323021?ign-refClientId=3zRt4FTz37xz4e8z9mGzY5aaMjsT")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity( intent );
            } else {
                // do something if app is not installed
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mDialog.dismiss();
        }

    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("error",e.toString());
        }

        return false;
    }


    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
