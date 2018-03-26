package com.opera.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.app.R;
import com.opera.app.utils.OperaUtils;

public class DiningFragment extends BaseFragment{

    private Activity mActivity;
    private OperaUtils mOperaUtils = new OperaUtils();

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
        mOperaUtils.CommonLanguageFunction(mActivity);
        View view = inflater.inflate(R.layout.fragment_dining, container, false);

        return view;
    }
}
