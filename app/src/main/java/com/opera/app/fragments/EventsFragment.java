package com.opera.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.app.R;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsFragment extends BaseFragment{

    private Activity mActivity;

    @BindView(R.id.tabhost)
    TabLayout mTabHost;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mActivity = getActivity();
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        View view = inflater.inflate(R.layout.fragment_events_2, container, false);

        //initTabs(view);
        return view;
    }

    private void initTabs(View view) {
        ButterKnife.bind(this, view);

        setupViewPager(mViewPager);
        mTabHost.setupWithViewPager(mViewPager);
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new AllEventsFragment(), getResources().getString(R.string.tab_all_events));
        adapter.addFragment(new TodayEventsFragment(), getResources().getString(R.string.tab_today_events));
        adapter.addFragment(new FavouritesEventsFragment(), getResources().getString(R.string.tab_favourites_events));
        adapter.addFragment(new GenresEventsFragment(), getResources().getString(R.string.tab_genres_events));
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
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
