package com.opera.app.fragments.wallet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.database.BookedEventsHistory;
import com.opera.app.enums.WalletEnums;
import com.opera.app.fragments.wallet.helper.TodayWalletView;
import com.opera.app.pojo.wallet.eventwallethistory.CommonBookedHistoryData;
import com.opera.app.preferences.wallet.WalletPreference;
import com.opera.app.utils.OperaManager;

import java.util.ArrayList;


public class CompletedWalletFragment extends Fragment {

    private BookedEventsHistory dbBookendEventsHistory;

    public CompletedWalletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_completed_wallet, container, false);

        LinearLayout linearParent = view.findViewById(R.id.linearParent);
        TextViewWithFont txtWalletEventTitle = view.findViewById(R.id.txtWalletEventTitle);
        TextView txt_testing = view.findViewById(R.id.txt_testing);
        TodayWalletView walletView = view.findViewById(R.id.helperWalletEvent);
        init();

        int mTotalData = 0;
        WalletPreference preference = new WalletPreference(getActivity());
        if (WalletEnums.EVENTS.name().equalsIgnoreCase(OperaManager.createInstance().getEnums().name())) {

            dbBookendEventsHistory.open();
            ArrayList<CommonBookedHistoryData> mEventHistoryData = dbBookendEventsHistory.fetchBookedEventsHistory();
            dbBookendEventsHistory.close();

            mTotalData = walletView.setEvents(mEventHistoryData, "Completed");
        } else if (WalletEnums.RESTAURANT.name().equalsIgnoreCase(OperaManager.createInstance().getEnums().name())) {
            mTotalData = walletView.setRest(preference.getWalletData().getRestaurants(),"Completed");
        } else if (WalletEnums.GIFT.name().equalsIgnoreCase(OperaManager.createInstance().getEnums().name())) {
            mTotalData = walletView.setGift(preference.getWalletData().getGiftCard(),"Completed");
        }

        if (mTotalData > 0) {
            linearParent.setVisibility(View.VISIBLE);
            txtWalletEventTitle.setVisibility(View.GONE);
            txt_testing.setVisibility(View.GONE);
        } else {
            linearParent.setVisibility(View.GONE);
            txtWalletEventTitle.setVisibility(View.VISIBLE);
            txt_testing.setVisibility(View.GONE);
        }

        return view;
    }

    private void init() {
        dbBookendEventsHistory = new BookedEventsHistory(getActivity());
    }

}
