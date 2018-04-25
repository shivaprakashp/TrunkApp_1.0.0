package com.opera.app.dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.activities.LoginActivity;
import com.opera.app.activities.MainActivity;
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.preferences.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SuccessDialogue extends Dialog {

    private Activity mActivity;
    private BaseActivity mBaseActivity;
    private String message, mFrom, mPopUpBtnTxt, mPopUpHeader;

    @BindView(R.id.txtErrorMessage)
    TextViewWithFont txtErrorMessage;

    @BindView(R.id.txtErrorTitle)
    TextViewWithFont txtErrorTitle;

    @BindView(R.id.btnError)
    ButtonWithFont btnError;

    public SuccessDialogue(@NonNull Activity mActivity, String message, String mPopUpHeader, String mPopUpBtnTxt, String mFrom) {
        super(mActivity);

        this.mActivity = mActivity;
        this.message = message;
        this.mPopUpHeader = mPopUpHeader;
        this.mPopUpBtnTxt = mPopUpBtnTxt;
        this.mFrom = mFrom;
        mBaseActivity = (BaseActivity) mActivity;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_error_dialogue);

        ButterKnife.bind(this);
        txtErrorMessage.setText(message);
        btnError.setText(mPopUpBtnTxt);
        txtErrorTitle.setText(mPopUpHeader);
        btnError.setOnClickListener(buttonClose);

    }

    private View.OnClickListener buttonClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            if (mFrom.equalsIgnoreCase("Register")) {
                mBaseActivity.openActivity(mActivity, LoginActivity.class);
            } else if (mFrom.equalsIgnoreCase("setUserSettings")) {
                mBaseActivity.openActivityWithClearPreviousActivities(mActivity, MainActivity.class);
            } else if (mFrom.equalsIgnoreCase("ContactUs")) {
                mBaseActivity.openActivityWithClearPreviousActivities(mActivity, MainActivity.class);
            }else if (mFrom.equalsIgnoreCase("MyProfileChangePassword")) {
                SessionManager sessionManager = new SessionManager(mActivity);
                sessionManager.clearLoginSession();
            }
            mActivity.finish();
        }
    };
}