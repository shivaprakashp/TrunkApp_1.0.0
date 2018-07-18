package com.opera.app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.opera.app.R;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class TestAct extends Activity {

    private WebView webBuyTickets;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_act);

        mProgressDialog = new ProgressDialog(TestAct.this);
        Button btnPlaylistUrl = findViewById(R.id.btnPlaylistUrl);
        Button btnCuratorPageUrl = findViewById(R.id.btnCuratorPageUrl);
        /*btnItunesUrl = findViewById(R.id.btnItunesUrl);
        btnBuyTickers = findViewById(R.id.btnBuyTickers);*/
        webBuyTickets = findViewById(R.id.webBuyTickets);

        btnPlaylistUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadWebView("https://tools.applemusic.com/embed/v1/playlist/pl.f4d106fed2bd41149aaacabb233eb5eb?country=ae");
            }
        });

        btnCuratorPageUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadWebView("https://geo.itunes.apple.com/us/curator/the-metropolitan-opera-house/1110087349?mt=1&app=music");
            }
        });

       /* btnEmbedUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, CommonWebViewActivity.class);
                in.putExtra("URL", "https://embed.music.apple.com/us/album/haçienda-classiçal/1159323021");
                in.putExtra("Header", "Home");
                startActivity(in);
            }
        });*/

       /* btnItunesUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, CommonWebViewActivity.class);
//                in.putExtra("URL","https://itunes.apple.com/us/album/ha%C3%A7ienda-classi%C3%A7al/1159323021");
                in.putExtra("URL","https://itunes.apple.com/us/album/hacienda/1164539623");             // Second URL
//                in.putExtra("URL", "https://itunes.apple.com/us/album/views/1108737195");             // Third URL


                in.putExtra("Header", "Home");
                startActivity(in);
            }
        });*/
    }

    private void OpenAnApp() {
        final String appPackageName = "com.apple.android.music"; // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressDialog.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("PageStarted", url);
            mProgressDialog.setMessage(getResources().getString(R.string.loading));
            mProgressDialog.show();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);

        }
    }

    private void LoadWebView(String mUrl) {

        webBuyTickets.setVisibility(View.VISIBLE);

        webBuyTickets.getSettings().setJavaScriptEnabled(true);
        webBuyTickets.setWebViewClient(new MyWebViewClient());
        webBuyTickets.loadUrl(mUrl);
        webBuyTickets.requestFocus();
    }
}
