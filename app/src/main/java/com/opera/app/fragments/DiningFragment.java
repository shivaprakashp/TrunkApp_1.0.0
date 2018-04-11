package com.opera.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.activities.EditProfileActivity;
import com.opera.app.activities.MainActivity;
import com.opera.app.activities.OtherRestaurantsActivity;
import com.opera.app.activities.ReserveATableActivity;
import com.opera.app.customwidget.ExpandableTextView;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiningFragment extends BaseFragment {

    private Activity mActivity;
    private Intent in;

    @BindView(R.id.linearReadMore)
    LinearLayout mLinearReadMore;

    @BindView(R.id.txtShowmore)
    TextView txtShowmore;

    @BindView(R.id.expandableTextView)
    ExpandableTextView mExpandableTextView;

    @BindView(R.id.btnOtherRestaurants)
    Button mBtnOtherRestaurants;

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
        View view = inflater.inflate(R.layout.fragment_dining, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.linearReadMore, R.id.btnOtherRestaurants, R.id.mBtnReserveATable})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearReadMore:
                if (mExpandableTextView.isExpanded()) {
                    mExpandableTextView.collapse();
                    txtShowmore.setText(R.string.read_more);
                } else {
                    mExpandableTextView.expand();
                    txtShowmore.setText(R.string.read_less);
                }
                break;
            case R.id.btnOtherRestaurants:
                openActivity(mActivity, OtherRestaurantsActivity.class);
                break;
            case R.id.mBtnReserveATable:
                openActivity(mActivity, ReserveATableActivity.class);
                break;


        }
    }

    ;


}
