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

    @OnClick({R.id.linearReadMore,R.id.btnOtherRestaurants,R.id.mBtnReserveATable})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearReadMore:
                if (txtShowmore.getText().toString().equalsIgnoreCase(getResources().getString(R.string.read_more))) {
                    txtShowmore.setMaxLines(1000);//your TextView
                    txtShowmore.setText(getResources().getString(R.string.read_less));
                } else {
                    txtShowmore.setMaxLines(3);//your TextView
                    txtShowmore.setText(getResources().getString(R.string.read_more));
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
