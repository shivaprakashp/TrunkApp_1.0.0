package com.opera.app.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.fragments.BaseFragment;
import com.opera.app.fragments.controller.FragNavController;

import butterknife.BindArray;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements
        FragNavController.TransactionListener, FragNavController.RootFragmentListener{

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

    private FragNavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabs();
        initFragmentControl(savedInstanceState);
    }

    private void initTabs(){
        if (bottomTabLayout != null) {
            for (int i = 0; i < TABS.length; i++) {
                bottomTabLayout.addTab(bottomTabLayout.newTab());
                TabLayout.Tab tab = bottomTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(getTabView(i));
            }
        }
    }

    private void initFragmentControl(Bundle savedInstanceState){
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.content_frame)
                .transactionListener(this)
                .rootFragmentListener(this, TABS.length)
                .build();

        switchTab(0);
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_item_bottom, null);
        ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
        TextViewWithFont text = (TextViewWithFont) view.findViewById(R.id.tab_text);
        icon.setImageDrawable(getDrawable(tabSelected[position]));
        text.setText(TABS[position]);

        return view;
    }

    private void switchTab(int position) {
        mNavController.switchTab(position);
    }

    @Override
    public Fragment getRootFragment(int index) {
        return null;
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {

    }

    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {

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
