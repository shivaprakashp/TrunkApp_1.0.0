package com.opera.app.fragments.wallet;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.opera.app.R;
import com.opera.app.enums.WalletEnums;

public class WalletFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public WalletFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new TodayWalletFragment();
            case 1: return new TodayWalletFragment();
            case 2: return new TodayWalletFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(R.string.walletToday);
            case 1:
                return context.getString(R.string.walletUpcoming);
            case 2:
                return context.getString(R.string.walletComplete);
            default:
                return null;
        }
    }
}
