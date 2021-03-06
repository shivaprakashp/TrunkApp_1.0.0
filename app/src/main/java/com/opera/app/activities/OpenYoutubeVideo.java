package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 1000632 on 5/15/2018.
 */

public class OpenYoutubeVideo extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener{

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private String YoutubeVideo="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.open_youtube_video);

        Activity mActivity = OpenYoutubeVideo.this;
        Intent in=getIntent();
        YoutubeVideo=in.getStringExtra("YoutubeVideo");
        YoutubeVideo= getYouTubeId(YoutubeVideo);

        YouTubePlayerView youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(AppConstants.YOUTUBE_DEVELOPER_KEY, (YouTubePlayer.OnInitializedListener) mActivity);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(YoutubeVideo);

            // Hiding player controls
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.error_youtube), Toast.LENGTH_LONG).show();
        }
    }

    private String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }
}
