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
            ErrorDialogue dialogue;
            if (response.body() != null) {
                RegistrationResponse mPostChangePassword = (RegistrationResponse) response.body();
                if (mPostChangePassword.getStatus().equalsIgnoreCase("success")) {
                    SessionManager sessionManager = new SessionManager(mActivity);
                    sessionManager.clearLoginSession();
                } else {
                    dialogue = new ErrorDialogue(mActivity, mPostChangePassword.getMessage());
                    dialogue.show();
                }

            } else if (response.errorBody() != null) {
                try {
                    dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
                } catch (Exception e) {
                    Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
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

        mEdtCurrentPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEdtCurrentPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        mEdtNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEdtNewPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        mEdtConfNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEdtConfNewPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);
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
                }
                break;

            case R.id.btnCancel:
                CloseChangePwdSheet();
                break;
            case R.id.btnSave:
                if (Connections.isConnectionAlive(mActivity)) {
                    if (checkValidation()) {
                        CloseChangePwdSheet();
                        sendChangePassword(mEdtCurrentPassword.getText().toString().trim(), mEdtNewPassword.getText().toString().trim());
                    }

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
        }
    }



    private boolean checkValidation() {
        //Removing previous validations
        mEdtCurrentPassword.setError(null);
        mEdtNewPassword.setError(null);
        mEdtConfNewPassword.setError(null);
        if (TextUtils.isEmpty(mEdtCurrentPassword.getText().toString().trim()) &&
                TextUtils.isEmpty(mEdtNewPassword.getText().toString().trim()) &&
                TextUtils.isEmpty(mEdtConfNewPassword.getText().toString().trim())) {
            mEdtCurrentPassword.setError(getString(R.string.errorCurrentPassword));
            mEdtNewPassword.setError(getString(R.string.errorNewPassword));
            mEdtConfNewPassword.setError(getString(R.string.errorConfirmNewPassword));
            return false;
        }

        //password
        else if (TextUtils.isEmpty(mEdtCurrentPassword.getText().toString())) {
            mEdtCurrentPassword.setError(getString(R.string.errorCurrentPassword));
            return false;
        } else if (mEdtCurrentPassword.getText().toString().length() < 3 || mEdtCurrentPassword.getText().toString().length() > 16) {
            mEdtCurrentPassword.setError(getString(R.string.errorLengthPassword));
            return false;
        }
        //re-enterPassword
        else if (TextUtils.isEmpty(mEdtNewPassword.getText().toString())) {
            mEdtNewPassword.setError(getString(R.string.errorNewPassword));
            return false;
        } else if (mEdtCurrentPassword.getText().toString().equalsIgnoreCase(
                mEdtNewPassword.getText().toString())) {
            mEdtNewPassword.setError(getString(R.string.errorPreviousAndNewPassword));
            return false;
        } else if (mEdtNewPassword.getText().toString().length() < 3 || mEdtNewPassword.getText().toString().length() > 16) {
            mEdtNewPassword.setError(getString(R.string.errorLengthPassword));
            return false;
        }  else if (TextUtils.isEmpty(mEdtConfNewPassword.getText().toString())) {
            mEdtConfNewPassword.setError(getString(R.string.errorConfirmNewPassword));
            return false;
        } else if (mEdtConfNewPassword.getText().toString().length() < 3 || mEdtConfNewPassword.getText().toString().length() > 16) {
            mEdtConfNewPassword.setError(getString(R.string.errorLengthPassword));
            return false;
        } else if (!mEdtConfNewPassword.getText().toString().trim().equalsIgnoreCase(mEdtNewPassword.getText().toString().trim())) {
            mEdtConfNewPassword.setError(getString(R.string.errorPasswordMatch));
            return false;
        }

        return true;
    }

    private void sendChangePassword(String mPwd, String mNewPwd) {

        MainController controller = new MainController(mActivity);
        controller.changePassword(taskComplete, api, new PostChangePassword(mPwd, mNewPwd));
    }
}
