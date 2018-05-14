package com.opera.app.fragments.wallet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.app.R;
import com.opera.app.enums.WalletEnums;
import com.opera.app.fragments.wallet.helper.TodayWalletView;
import com.opera.app.preferences.wallet.WalletPreference;
import com.opera.app.utils.OperaManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWalletFragment extends Fragment {

    private TodayWalletView walletView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today_wallet, container, false);

        walletView = (TodayWalletView) view.findViewById(R.id.helperWalletEvent);

        WalletPreference preference = new WalletPreference(getActivity());
        if (WalletEnums.EVENTS.name().equalsIgnoreCase(OperaManager.createInstance().getEnums().name())){
            walletView.setEvents(preference.getWalletData().getEvents());
        }else if(WalletEnums.RESTAURANT.name().equalsIgnoreCase(OperaManager.createInstance().getEnums().name())){
            walletView.setRest(preference.getWalletData().getRestaurants());
        }else if(WalletEnums.GIFT.name().equalsIgnoreCase(OperaManager.createInstance().getEnums().name())){
            walletView.setGift(preference.getWalletData().getGiftCard());
        }


        return view;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
           Log.i("enums", bundle.getString("enum"));

        }
    }
}
