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
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.TextViewWithFont;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ErrorDialogue extends Dialog {

    private Activity mActivity;
    private String message;
    private BaseActivity mBaseActivity;

    @BindView(R.id.txtErrorMessage)
    TextViewWithFont txtErrorMessage;

    @BindView(R.id.btnError)
    ButtonWithFont btnError;

    public ErrorDialogue(@NonNull Activity mActivity, String message) {
        super(mActivity);

        this.mActivity = mActivity;
        this.message = message;
        mBaseActivity = (BaseActivity) mActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_error_dialogue);

        ButterKnife.bind(this);
        txtErrorMessage.setText(message);
        btnError.setOnClickListener(buttonClose);
    }

    private View.OnClickListener buttonClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (message.contains("Account already available.")) {
                mBaseActivity.openActivity(mActivity, LoginActivity.class);
                dismiss();
            } else {
                dismiss();
            }
        }
    };
}
