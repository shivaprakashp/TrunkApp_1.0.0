package com.opera.app.fragments.wallet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.database.BookedEventsHistory;
import com.opera.app.enums.WalletEnums;
import com.opera.app.fragments.wallet.helper.TodayWalletView;
import com.opera.app.pojo.wallet.eventwallethistory.CommonBookedHistoryData;
import com.opera.app.preferences.wallet.WalletPreference;
import com.opera.app.utils.OperaManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingWalletFragment extends Fragment {

    private BookedEventsHistory dbBookendEventsHistory;

    public UpcomingWalletFragment() {
        // Required empty public constructor\
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_wallet, container, false);

        TodayWalletView walletView = view.findViewById(R.id.helperWalletEvent);
        LinearLayout linearParent = view.findViewById(R.id.linearParent);
        TextViewWithFont txtWalletEventTitle = view.findViewById(R.id.txtWalletEventTitle);
        int mTotalData = 0;
        init();

        WalletPreference preference = new WalletPreference(getActivity());
        if (WalletEnums.EVENTS.name().equalsIgnoreCase(OperaManager.createInstance().getEnums().name())) {
            dbBookendEventsHistory.open();
            ArrayList<CommonBookedHistoryData> mEventHistoryData = dbBookendEventsHistory.fetchBookedEventsHistory(getResources().getString(R.string.event));
            dbBookendEventsHistory.close();

            mTotalData = walletView.setEvents(mEventHistoryData, "Upcoming");
        } else if (WalletEnums.RESTAURANT.name().equalsIgnoreCase(OperaManager.createInstance().getEnums().name())) {
            mTotalData = walletView.setRest(preference.getWalletData().getRestaurants(),"Upcoming");
        } else if (WalletEnums.GIFT.name().equalsIgnoreCase(OperaManager.createInstance().getEnums().name())) {
            dbBookendEventsHistory.open();
            ArrayList<CommonBookedHistoryData> mEventHistoryData = dbBookendEventsHistory.fetchBookedEventsHistory(getResources().getString(R.string.gift_card));
            dbBookendEventsHistory.close();

            mTotalData = walletView.setGift(mEventHistoryData,"Completed");
        }

        if (mTotalData > 0) {
            linearParent.setVisibility(View.VISIBLE);
            txtWalletEventTitle.setVisibility(View.GONE);
        } else {
            linearParent.setVisibility(View.GONE);
            txtWalletEventTitle.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void init() {
        dbBookendEventsHistory = new BookedEventsHistory(getActivity());
    }

}
