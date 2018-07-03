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
import android.webkit.CookieManager;
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

    private Button btnOpenApple, btnEmbedUrl, btnItunesUrl,btnBuyTickers;
    private Activity activity;
    private WebView webBuyTickets;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_act);

        activity = TestAct.this;
        mDialog=new ProgressDialog(TestAct.this);
        btnOpenApple = findViewById(R.id.btnOpenApple);
        btnEmbedUrl = findViewById(R.id.btnEmbedUrl);
        btnItunesUrl = findViewById(R.id.btnItunesUrl);
        btnBuyTickers = findViewById(R.id.btnBuyTickers);
        webBuyTickets= findViewById(R.id.webBuyTickets);

        btnBuyTickers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                mDialog.setMessage("Please wait...");
                btnBuyTickers.setVisibility(View.GONE);
                webBuyTickets.setVisibility(View.VISIBLE);

                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setCookie("https://dubaioperaw-mobile-uat.etixdubai.com/", "X-Auth-Token=6LfpK%2fuCFNVVVH5FDBzs03mcFvUiAUABbo6J6fxTWCwqe5KVLn0HHDL3clMDBO3ayg5woEOTr4%2fJuDtVmhYOTg%3d%3d");
                webBuyTickets.loadUrl("https://dubaioperaw-mobile-uat.etixdubai.com/shows/show.aspx?sh=TEST2PC");
                webBuyTickets.getSettings().setJavaScriptEnabled(true);
                webBuyTickets.getSettings().setDomStorageEnabled(true);
                webBuyTickets.setWebViewClient(new MyWebViewClient());
                cookieManager.setAcceptThirdPartyCookies(webBuyTickets, true);
                webBuyTickets.requestFocus();

                /*webBuyTickets.getSettings().setJavaScriptEnabled(true);
                webBuyTickets.getSettings().setSaveFormData(false);

                CookieSyncManager.createInstance(TestAct.this);
                CookieSyncManager.getInstance().startSync();
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                CookieManager.getInstance().setAcceptThirdPartyCookies(webBuyTickets, true);

                *//*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String token2= mPreferences.getString("auth_token","");*//*

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("x-auth-token", "G7%2blsuNjQxg1ZUrsh1yofoBwyZ84djnIKPRTpJzkjcRhBY%2bZUlySwpYeWa%2fGVKjF4sda8aPjlKXCQUs%2bYl2oaQ%3d%3d");

                webBuyTickets.getSettings().setAppCacheEnabled(true);
                webBuyTickets.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view,String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
                webBuyTickets.loadUrl("https://dubaioperaw-mobile-uat.etixdubai.com/shows/show.aspx?sh=TEST2PC", map);*/



                /*String myURL = "https://dubaioperaw-mobile-uat.etixdubai.com/shows/show.aspx?sh=TEST2PC";

                android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
                cookieManager.setCookie("https://dubaioperaw-mobile-uat.etixdubai.com/", "X-Auth-Token=G7%2blsuNjQxg1ZUrsh1yofoBwyZ84djnIKPRTpJzkjcRhBY%2bZUlySwpYeWa%2fGVKjF4sda8aPjlKXCQUs%2bYl2oaQ%3d%3d");
                cookieManager.setAcceptCookie(true);
                cookieManager.acceptCookie();
                cookieManager.setAcceptFileSchemeCookies(true);
                cookieManager.getInstance().setAcceptCookie(true);
                cookieManager.getCookie(myURL);
                webBuyTickets.loadUrl(myURL);*/
            }
        });

        btnOpenApple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("apple-music://itunes.apple.com/us/album/views/1108737195"));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    OpenAnApp();
                }
            }
        });

        btnEmbedUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, CommonWebViewActivity.class);
                in.putExtra("URL", "https://embed.music.apple.com/us/album/haçienda-classiçal/1159323021");
                in.putExtra("Header", "Home");
                startActivity(in);
            }
        });

        btnItunesUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(activity, CommonWebViewActivity.class);
//                in.putExtra("URL","https://itunes.apple.com/us/album/ha%C3%A7ienda-classi%C3%A7al/1159323021");
                in.putExtra("URL","https://itunes.apple.com/us/album/hacienda/1164539623");             // Second URL
//                in.putExtra("URL", "https://itunes.apple.com/us/album/views/1108737195");             // Third URL


                in.putExtra("Header", "Home");
                startActivity(in);
            }
        });
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
            mDialog.dismiss();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("PageStarted",url.toString());
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
}
