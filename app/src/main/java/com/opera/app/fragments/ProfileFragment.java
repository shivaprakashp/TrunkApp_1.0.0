package com.opera.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.activities.EditProfileActivity;
import com.opera.app.activities.MyProfileActivity;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 58001 on 27-03-2018.
 */

public class ProfileFragment extends BaseFragment {

    private Activity mActivity;
    private Intent in;
    private SessionManager manager;

    @BindView(R.id.btnEditProfile)
    Button mBtnEditProfile;

    @BindView(R.id.btnChangePassword)
    Button mBtnChangePassword;

    @BindView(R.id.tv_profile_address)
    TextView profile_address;

    @BindView(R.id.tv_profile_email)
    TextView profile_email;

    @BindView(R.id.tv_profile_mobile)
    TextView profile_mobile;

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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        updateSessionData();
    }

    private void updateSessionData() {
        manager = new SessionManager(mActivity);
        if (manager.getUserLoginData() != null ) {
            if (manager.getUserLoginData().getData().getProfile().getAddress() != null && !manager.getUserLoginData().getData().getProfile().getAddress().isEmpty()) {
                profile_address.setText(manager.getUserLoginData().getData().getProfile().getAddress());
            } else {
                profile_address.setText(manager.getUserLoginData().getData().getProfile().getCity() + ", "
                        + manager.getUserLoginData().getData().getProfile().getState() + ", "
                        + manager.getUserLoginData().getData().getProfile().getCountry());
            }
            profile_email.setText(manager.getUserLoginData().getData().getProfile().getEmail());
            profile_mobile.setText(manager.getUserLoginData().getData().getProfile().getMobileNumber());
        }
    }

    @OnClick({R.id.btnEditProfile, R.id.btnChangePassword})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditProfile:
                in = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(in);
                break;

            case R.id.btnChangePassword:
                /* showDialog();*/
                ((MyProfileActivity) getActivity()).changePassword();
                break;
        }
    }
}
