package com.opera.app.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.opera.app.R;
import com.opera.app.activities.EditProfileActivity;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.utils.LanguageManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 58001 on 27-03-2018.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private Activity mActivity;
    private Intent in;

    @BindView(R.id.btnEditProfile)
    Button mBtnEditProfile;

    @BindView(R.id.btnChangePassword)
    Button mBtnChangePassword;

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

        initView();

        return view;
    }

    private void initView() {
        //button
        mBtnEditProfile.setOnClickListener(this);
        mBtnChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditProfile:
                in = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(in);
                break;

            case R.id.btnChangePassword:
                showDialog();
                break;
        }
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        dialog.setContentView(R.layout.popup_changepassword);

        //EditText
        EditTextWithFont edtCurrentPassword = (EditTextWithFont) dialog.findViewById(R.id.edtCurrentPassword);
        EditTextWithFont edtNewPassword = (EditTextWithFont) dialog.findViewById(R.id.edtNewPassword);
        EditTextWithFont edtConfNewPassword = (EditTextWithFont) dialog.findViewById(R.id.edtConfNewPassword);

        //Button
        Button btnSave = (Button) dialog.findViewById(R.id.btnSave);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
