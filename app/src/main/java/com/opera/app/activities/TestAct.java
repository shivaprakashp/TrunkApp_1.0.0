package com.opera.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.opera.app.R;

import java.io.IOException;

/**
 * Created by 1000632 on 4/20/2018.
 */

public class TestAct extends Activity {

    private Button btnOpenApple, btnEmbedUrl, btnItunesUrl;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_act);

        activity = TestAct.this;

        btnOpenApple = (Button) findViewById(R.id.btnOpenApple);
        btnEmbedUrl = (Button) findViewById(R.id.btnEmbedUrl);
        btnItunesUrl = (Button) findViewById(R.id.btnItunesUrl);

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

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }


    }
}
