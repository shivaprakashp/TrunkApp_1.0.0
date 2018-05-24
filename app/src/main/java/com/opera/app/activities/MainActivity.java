package com.opera.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dialogues.GuestDialog;
import com.opera.app.fragments.DiningFragment;
import com.opera.app.fragments.EventsFragment;
import com.opera.app.fragments.HomeFragment;
import com.opera.app.fragments.ListenFragment;
import com.opera.app.fragments.MenuFragment;
import com.opera.app.fragments.controller.FragNavController;
import com.opera.app.pojo.restaurant.RestaurantsData;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import butterknife.BindArray;
import butterknife.BindView;

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
    private String mTabSelected="";
    private RestaurantsData data;
    private SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = MainActivity.this;
        manager = new SessionManager(mActivity);
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_main);

        //toolbar title disable
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initToolbar();
        initTabs();
        intentData(savedInstanceState);

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

    private void intentData(Bundle bundle){
        data = (RestaurantsData) getIntent().getSerializableExtra(AppConstants.GETRESTAURANTLISTING.GETRESTAURANTLISTING);
        if (data!=null){
            initFragmentControl(bundle, 2);
            switchTab(2);
            bottomTabLayout.getTabAt(2).select();

        }else {
            initFragmentControl(bundle, 0);

        }
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

    private void initFragmentControl(Bundle savedInstanceState, int position) {
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.content_frame)
                .transactionListener(this)
                .rootFragmentListener(this, TABS.length)
                .build();

        switchTab(position);
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
                return DiningFragment.newDiningFragment(data);
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
        switch (position) {
            case 0:
                mTabSelected="Home";
                toolbar.setVisibility(View.VISIBLE);
                LayoutInflater homeInflater =(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //For Language setting
                LanguageManager.createInstance().CommonLanguageFunction(mActivity);
                View homeView = homeInflater.inflate(R.layout.view_home_toolbar, null);

                homeView.findViewById(R.id.img_plan_visit).setVisibility(View.VISIBLE);
                homeView.findViewById(R.id.img_wallet).setVisibility(View.VISIBLE);
                homeView.findViewById(R.id.img_profile).setVisibility(View.VISIBLE);

                homeView.findViewById(R.id.img_plan_visit).setOnClickListener(calendarPage);
                homeView.findViewById(R.id.img_wallet).setOnClickListener(walletPage);
                homeView.findViewById(R.id.img_profile).setOnClickListener(profilePage);

                toolBarLayout(homeView);
                break;

            case 1:
                mTabSelected="Event";
                toolbar.setVisibility(View.VISIBLE);
                LayoutInflater eventInflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //For Language setting
                LanguageManager.createInstance().CommonLanguageFunction(mActivity);
                View eventView = eventInflater.inflate(R.layout.view_events_toolbar, null);

                eventView.findViewById(R.id.imgCalendar).setVisibility(View.VISIBLE);
                eventView.findViewById(R.id.imgSearch).setVisibility(View.VISIBLE);

                eventView.findViewById(R.id.imgCalendar).setOnClickListener(calendarPage);
                eventView.findViewById(R.id.imgSearch).setOnClickListener(searchPage);

                toolBarLayout(eventView);
                break;

            case 2:
                mTabSelected="Dining";
                toolbar.setVisibility(View.VISIBLE);
                LayoutInflater diningInflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //For Language setting
                LanguageManager.createInstance().CommonLanguageFunction(mActivity);
                View diningView = diningInflater.inflate(R.layout.view_dining_toolbar, null);
                toolBarLayout(diningView);
                break;

            case 3:
                mTabSelected="Listen";
                toolbar.setVisibility(View.VISIBLE);
                LayoutInflater listenInflater =(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //For Language setting
                LanguageManager.createInstance().CommonLanguageFunction(mActivity);
                View listenView = listenInflater.inflate(R.layout.view_listen_toolbar, null);
                toolBarLayout(listenView);
                break;

            case 4:
                mTabSelected="Menu";
                toolbar.setVisibility(View.GONE);
                break;

            default:
                break;
        }
    }

    private View.OnClickListener searchPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openActivity(mActivity, SearchEventActivity.class);
        }
    };

    private View.OnClickListener calendarPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        openActivity(mActivity, CalendarActivity.class);
        }
    };

    private View.OnClickListener walletPage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mTabSelected.equalsIgnoreCase("Event")){
                openActivity(mActivity, SearchEventActivity.class);
            }else{
                openActivity(mActivity, WalletActivity.class);
            }

        }
    };

    private View.OnClickListener profilePage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (manager.isUserLoggedIn()) {
                openActivity(mActivity, MyProfileActivity.class);
            } else {
                //Toast.makeText(mActivity, getActivity().getString(R.string.guest_msg), Toast.LENGTH_SHORT).show();
                GuestDialog dialog = new GuestDialog(mActivity, mActivity.getString(R.string.guest_title), mActivity.getString(R.string.guest_msg) );
                dialog.show();
            }
        }
    };

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
