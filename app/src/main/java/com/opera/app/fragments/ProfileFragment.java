package com.opera.app.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.opera.app.R;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.utils.LanguageManager;

import butterknife.BindView;

/**
 * Created by 58001 on 27-03-2018.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private Activity mActivity;

    @BindView(R.id.btnEditProfile)
    Button btnEditProfile;

    @BindView(R.id.btnChangePassword)
    Button btnChangePassword;

    @BindView(R.id.prof_edtCurrPass)
    View prof_edtCurrPass;

    @BindView(R.id.prof_edtNewPass)
    View prof_edtNewPass;

    @BindView(R.id.prof_edtRePass)
    View prof_edtRePass;


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

        //initView();

        return view;
    }

    private void initView() {
        //button
        btnEditProfile.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditProfile:

                break;

            case R.id.btnChangePassword:
                //showDialog();
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
        EditTextWithFont tv_forgotPassword = (EditTextWithFont) dialog.findViewById(R.id.tv_forgotPassword);
        EditTextWithFont et_username = (EditTextWithFont) dialog.findViewById(R.id.et_username);
        EditTextWithFont et_username1 = (EditTextWithFont) dialog.findViewById(R.id.et_username);

        //Button
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnSave = (Button) dialog.findViewById(R.id.btnSave);

        dialog.show();

    }
}
