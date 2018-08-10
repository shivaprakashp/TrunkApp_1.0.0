package com.opera.app.fragments.wallet;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.opera.app.R;
import com.opera.app.enums.WalletEnums;

public class WalletFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private WalletEnums enums;

    public WalletFragmentPagerAdapter(Context context, FragmentManager fm, WalletEnums enums) {
        super(fm);
        this.context = context;
        this.enums = enums;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TodayWalletFragment();
            case 1:
                return new UpcomingWalletFragment();
            case 2:
                if (!enums.equals(WalletEnums.GIFT)) {
                    return new CompletedWalletFragment();
                }
        }

        return null;
    }

    @Override
    public int getCount() {
        int NoOfTabs = 0;
        if (!enums.equals(WalletEnums.GIFT)) {
            NoOfTabs = 3;
        } else {
            NoOfTabs = 2;

        }
        return NoOfTabs;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                if (!enums.equals(WalletEnums.GIFT)) {
                    return context.getString(R.string.walletToday);
                } else {
                    return context.getString(R.string.walletActivate);
                }

            case 1:
                if (!enums.equals(WalletEnums.GIFT)) {
                    return context.getString(R.string.walletUpcoming);
                } else {
                    return context.getString(R.string.walletComplete);
                }

            case 2:
                if (!enums.equals(WalletEnums.GIFT)) {
                    return context.getString(R.string.walletComplete);
                }

            default:
                return null;
        }
    }
}
