package com.opera.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.app.R;
import com.opera.app.listadapters.CoverFlowAdapter;
import com.opera.app.pojo.GameEntity;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class HomeFragment extends BaseFragment {

    private Activity mActivity;
    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<>(0);

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mData.add(new GameEntity(R.drawable.ic_profile_bg, R.string.empty));
        mData.add(new GameEntity(R.drawable.bg_login, R.string.empty));
        mData.add(new GameEntity(R.drawable.ic_profile_bg, R.string.empty));
        mData.add(new GameEntity(R.drawable.bg_login, R.string.empty));

        mAdapter = new CoverFlowAdapter(getActivity());
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) view.findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        return view;
    }
}
