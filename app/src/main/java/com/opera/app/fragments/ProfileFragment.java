package com.opera.app.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.activities.EditProfileActivity;
import com.opera.app.activities.LoginActivity;
import com.opera.app.activities.MyProfileActivity;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.profile.PostChangePassword;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {


        manager = new SessionManager(mActivity);
        profile_address.setText(manager.getUserLoginData().getData().getProfile().getCity() + " , " + manager.getUserLoginData().getData().getProfile().getCountry());
        profile_email.setText(manager.getUserLoginData().getData().getProfile().getEmail());
        profile_mobile.setText(manager.getUserLoginData().getData().getProfile().getMobileNumber());


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
