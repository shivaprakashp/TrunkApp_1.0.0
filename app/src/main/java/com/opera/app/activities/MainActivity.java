package com.opera.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.fragments.BaseFragment;
import com.opera.app.fragments.DiningFragment;
import com.opera.app.fragments.EventsFragment;
import com.opera.app.fragments.HomeFragment;
import com.opera.app.fragments.ListenFragment;
import com.opera.app.fragments.MenuFragment;
import com.opera.app.fragments.controller.FragNavController;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
        FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private int[] tabSelected = {
            R.drawable.ic_home,
            R.drawable.ic_events,
            R.drawable.ic_dining,
            R.drawable.ic_listern,
            R.drawable.ic_menu
    };

    @BindArray(R.array.tab_name)
    String[] TABS;

    @BindView(R.id.bottom_tab_layout)
    TabLayout bottomTabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FragNavController mNavController;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = MainActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_main);

        //toolbar title disable
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initToolbar();
        initTabs();
        initFragmentControl(savedInstanceState);

        bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mNavController.clearStack();
                switchTab(tab.getPosition());

            }
        });
    }



    private void initToolbar() {
        setSupportActionBar(toolbar);


    }
    private void initTabs() {
        if (bottomTabLayout != null) {
            for (int i = 0; i < TABS.length; i++) {
                bottomTabLayout.addTab(bottomTabLayout.newTab());
                TabLayout.Tab tab = bottomTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(getTabView(i));
            }
        }
    }

    private void initFragmentControl(Bundle savedInstanceState) {
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.content_frame)
                .transactionListener(this)
                .rootFragmentListener(this, TABS.length)
                .build();

        switchTab(0);
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_item_bottom, null);
        try {

            ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
            TextViewWithFont text = (TextViewWithFont) view.findViewById(R.id.tab_text);

            //icon.setImageDrawable(getDrawable(tabSelected[position]));
            icon.setImageDrawable(OperaUtils.setDrawableImage(MainActivity.this, tabSelected[position],
                    tabSelected[position], position));
            text.setText(TABS[position]);

            ColorStateList colors = ContextCompat.getColorStateList(this,R.color.color_bottom_state);
            text.setTextColor(colors);
        }catch (Exception e){e.printStackTrace();}

        return view;
    }

    private void switchTab(int position) {
        mNavController.switchTab(position);
        updateToolBar(position);
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {

            case FragNavController.TAB1:
                return new HomeFragment();
            case FragNavController.TAB2:
                return new EventsFragment();
            case FragNavController.TAB3:
                return new DiningFragment();
            case FragNavController.TAB4:
                return new ListenFragment();
            case FragNavController.TAB5:
                return new MenuFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }


    @Override
    public void onTabTransaction(Fragment fragment, int index) {
// If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            //Toolbar have to implement here
            //updateToolbar();

        }
    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
//do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
//Toolbar have to implement here
            //updateToolbar();


        }
    }

    public void updateToolBar(int position) {
        Log.i("Position", position + "");
        switch (position) {
            case 0:
                toolbar.setVisibility(View.VISIBLE);
                LayoutInflater homeInflater =(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //For Language setting
                LanguageManager.createInstance().CommonLanguageFunction(mActivity);
                View homeView = homeInflater.inflate(R.layout.view_home_toolbar, null);
                toolBarLayout(homeView);
                break;

            case 1:

                toolbar.setVisibility(View.VISIBLE);
                LayoutInflater eventInflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //For Language setting
                LanguageManager.createInstance().CommonLanguageFunction(mActivity);
                View eventView = eventInflater.inflate(R.layout.view_events_toolbar, null);
                toolBarLayout(eventView);
                break;

            case 2:
                toolbar.setVisibility(View.VISIBLE);
                LayoutInflater diningInflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //For Language setting
                LanguageManager.createInstance().CommonLanguageFunction(mActivity);
                View diningView = diningInflater.inflate(R.layout.view_dining_toolbar, null);
                toolBarLayout(diningView);
                break;

            case 3:
                toolbar.setVisibility(View.VISIBLE);
                LayoutInflater listenInflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //For Language setting
                LanguageManager.createInstance().CommonLanguageFunction(mActivity);
                View listenView = listenInflater.inflate(R.layout.view_listen_toolbar, null);
                toolBarLayout(listenView);
                break;

            case 4:
                toolbar.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }

    private void toolBarLayout(View view){
        toolbar.removeAllViews();
        toolbar.addView(view, Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

}
