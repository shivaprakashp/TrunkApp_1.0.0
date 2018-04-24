package com.opera.app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(URL);
        mWebView.requestFocus();

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(Header);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mDialog.dismiss();
        }
    }


    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
