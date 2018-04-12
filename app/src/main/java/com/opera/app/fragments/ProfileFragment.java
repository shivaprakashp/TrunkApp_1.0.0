package com.opera.app.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.activities.EditProfileActivity;
import com.opera.app.activities.LoginActivity;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.login.LoginResponse;
import com.opera.app.pojo.login.PostLogin;
import com.opera.app.pojo.profile.PostChangePassword;
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

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    @BindView(R.id.edtCurrentPassword)
    EditText mEdtCurrentPassword;

    @BindView(R.id.edtNewPassword)
    EditText mEdtNewPassword;

    @BindView(R.id.edtConfNewPassword)
    EditText mEdtConfNewPassword;

    @BindView(R.id.btnCancel)
    Button mBtnCancel;

    @BindView(R.id.btnSave)
    Button mBtnSave;

    @BindView(R.id.imgClose)
    ImageView mImgClose;

    private BottomSheetBehavior sheetBehavior;

    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private Api api;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response) {
            /*if (response.body() != null) {
                loginSession((LoginResponse) response.body());
            } else if (response.errorBody() != null) {
                try {
                    ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
                } catch (Exception e) {
                    Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }*/
            Log.e("response", response.toString());
        }

        @Override
        public void onTaskError(Call call, Throwable t) {
            Log.e("Error", call.toString());
        }
    };

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

        ((MainApplication) mActivity.getApplication()).getNetComponent().inject(ProfileFragment.this);
        api = retrofit.create(Api.class);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        manager = new SessionManager(mActivity);
        profile_address.setText(manager.getUserLoginData().getData().getProfile().getCity() + " , " + manager.getUserLoginData().getData().getProfile().getCountry());
        profile_email.setText(manager.getUserLoginData().getData().getProfile().getEmail());
        profile_mobile.setText(manager.getUserLoginData().getData().getProfile().getMobileNumber());
    }

    @OnClick({R.id.btnEditProfile, R.id.btnChangePassword, R.id.btnCancel, R.id.btnSave, R.id.imgClose})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditProfile:
                in = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(in);
                break;

            case R.id.btnChangePassword:
               /* showDialog();*/
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                    viewClickable(false);
                }
                break;

            case R.id.btnCancel:
                CloseChangePwdSheet();
                break;
            case R.id.btnSave:
                CloseChangePwdSheet();
                if (Connections.isConnectionAlive(mActivity)) {
                    if (checkValidation())
                        sendChangePassword(mEdtCurrentPassword.getText().toString().trim(), mEdtNewPassword.getText().toString().trim());
                } else {
                    Toast.makeText(mActivity, getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.imgClose:
                CloseChangePwdSheet();
                break;
        }
    }

    private void CloseChangePwdSheet() {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            viewClickable(true);
        }
    }

    //view make it clickable
    private void viewClickable(boolean flag) {
        mBtnEditProfile.setClickable(flag);
        mBtnChangePassword.setClickable(flag);

    }

    private boolean checkValidation() {
        //Removing previous validations
        mEdtCurrentPassword.setError(null);
        mEdtNewPassword.setError(null);
        mEdtConfNewPassword.setError(null);
    /*if (TextUtils.isEmpty(username.getText().toString().trim())) {
            username.setError(getString(R.string.errorUserName));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(username.getText().toString().trim()).matches()) {
            username.setError(getString(R.string.errorUserEmail));
            return false;
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError(getString(R.string.errorPassword));
            return false;
        }*/

        return true;
    }

    private void sendChangePassword(String mPwd, String mNewPwd) {

        MainController controller = new MainController(mActivity);
        controller.changePassword(taskComplete, api, new PostChangePassword(mPwd, mNewPwd));
    }
}
