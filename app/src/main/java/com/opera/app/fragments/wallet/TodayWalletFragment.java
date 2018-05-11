package com.opera.app.fragments.wallet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.app.R;
import com.opera.app.fragments.wallet.helper.TodayWalletView;
import com.opera.app.preferences.wallet.WalletPreference;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWalletFragment extends Fragment {

    private TodayWalletView walletView;

    public TodayWalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today_wallet, container, false);

        walletView = (TodayWalletView) view.findViewById(R.id.helperWalletEvent);

        WalletPreference preference = new WalletPreference(getActivity());
        Log.i("data",preference.getWalletData().getMessage());
        walletView.setEvents(preference.getWalletData().getEvents());
        return view;
    }

}
