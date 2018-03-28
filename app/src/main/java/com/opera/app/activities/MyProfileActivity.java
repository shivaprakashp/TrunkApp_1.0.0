package com.opera.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.fragments.LoyaltyPointsFragment;
import com.opera.app.fragments.ProfileFragment;
import com.opera.app.utils.OperaUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 58001 on 27-03-2018.
 */

public class MyProfileActivity extends BaseActivity {

    private Activity mActivity;
    private OperaUtils mOperaUtils = new OperaUtils();

    @BindView(R.id.tabhost)
    TabLayout mTabHost;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = MyProfileActivity.this;

        //For Language setting
        mOperaUtils.CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_my_profile);

        initView();
    }


    private void initView() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new ProfileFragment(), getResources().getString(R.string.tab_profile));
        adapter.addFragment(new LoyaltyPointsFragment(), getResources().getString(R.string.tab_loyalty));
        mViewPager.setAdapter(adapter);

        mTabHost.setupWithViewPager(mViewPager);
    }


    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
