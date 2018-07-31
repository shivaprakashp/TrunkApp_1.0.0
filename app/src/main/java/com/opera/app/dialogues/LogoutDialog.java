package com.opera.app.dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.preferences.SessionManager;
import com.opera.app.services.UpdateSettingsInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogoutDialog extends Dialog {

    private Activity mActivity;
    private String title,message, btnTxt;
    private UpdateSettingsInterface mUpdateSettingsInterface;

    @BindView(R.id.txtTitle)
    TextViewWithFont txtTitle;

    @BindView(R.id.txtMessage)
    TextViewWithFont txtMessage;

    @BindView(R.id.btnLogin)
    ButtonWithFont btnOk;

    @BindView(R.id.btnCancel)
    ButtonWithFont btnCancel;

    private BaseActivity mBaseActivity;
    private SessionManager mSessionManager;

    public LogoutDialog(@NonNull Activity mActivity, String title, String message, String btnTxt, UpdateSettingsInterface mUpdateSettingsInterface) {
        super(mActivity);

        this.mActivity = mActivity;
        this.title = title;
        this.message = message;
        this.btnTxt = btnTxt;
        this.mUpdateSettingsInterface=mUpdateSettingsInterface;
        mBaseActivity = (BaseActivity) mActivity;
        mSessionManager = new SessionManager(mActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_two_button);

        ButterKnife.bind(this);
        txtTitle.setText(title);
        txtMessage.setText(message);
        btnOk.setText(btnTxt);

        btnOk.setOnClickListener(buttonOK);
        btnCancel.setOnClickListener(buttonClose);
    }
    private View.OnClickListener buttonOK = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mUpdateSettingsInterface.UpdateSettingsPage();
        }
    };

    private View.OnClickListener buttonClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();

        }
    };
}
