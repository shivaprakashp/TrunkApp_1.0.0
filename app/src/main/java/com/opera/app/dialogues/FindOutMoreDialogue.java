package com.opera.app.dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.opera.app.R;
import com.opera.app.customwidget.ButtonWithFont;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindOutMoreDialogue extends Dialog {

    private Intent intent;
    private Activity mActivity;
    private String number;

    @BindView(R.id.btnCall)
    ButtonWithFont btnCall;

    @BindView(R.id.btnEmail)
    ButtonWithFont btnEmail;

    @BindView(R.id.imgClose)
    ImageView imgClose;

    public FindOutMoreDialogue(@NonNull Activity mActivity, String number) {
        super(mActivity);
        this.mActivity = mActivity;
        this.number = number;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_find_out_more);

        ButterKnife.bind(this);
        btnCall.setOnClickListener(buttonCall);

        ButterKnife.bind(this);
        btnEmail.setOnClickListener(buttonEmail);

        ButterKnife.bind(this);
        imgClose.setOnClickListener(imageClose);
    }

    private View.OnClickListener buttonCall = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            mActivity.startActivity(intent);
            dismiss();

        }
    };

    private View.OnClickListener buttonEmail = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    private View.OnClickListener imageClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };
}
