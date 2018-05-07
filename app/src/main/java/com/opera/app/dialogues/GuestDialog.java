package com.opera.app.dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.activities.PreLoginActivity;
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.TextViewWithFont;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuestDialog extends Dialog {

    private Context context;
    private String title,message;

    @BindView(R.id.txtTitle)
    TextViewWithFont txtTitle;

    @BindView(R.id.txtMessage)
    TextViewWithFont txtMessage;

    @BindView(R.id.btnLogin)
    ButtonWithFont btnLogin;

    @BindView(R.id.btnCancel)
    ButtonWithFont btnCancel;

    BaseActivity mBaseActivity;

    public GuestDialog(@NonNull Context context, String title, String message) {
        super(context);

        this.context = context;
        this.title = title;
        this.message = message;
        mBaseActivity = (BaseActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_two_button);

        ButterKnife.bind(this);
        txtTitle.setText(title);
        txtMessage.setText(message);
        btnLogin.setOnClickListener(buttonLogin);
        btnCancel.setOnClickListener(buttonClose);
    }
    private View.OnClickListener buttonLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*SessionManager sessionManager = new SessionManager(context);
            sessionManager.clearLoginSession();*/
            mBaseActivity.openActivityWithClearPreviousActivities((Activity) context, PreLoginActivity.class);
            dismiss();
        }
    };

    private View.OnClickListener buttonClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();

        }
    };
}
