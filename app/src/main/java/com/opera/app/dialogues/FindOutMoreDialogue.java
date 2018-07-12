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

    private Activity mActivity;
    private String number, emailId;

    @BindView(R.id.btnCall)
    ButtonWithFont btnCall;

    @BindView(R.id.btnEmail)
    ButtonWithFont btnEmail;

    @BindView(R.id.imgClose)
    ImageView imgClose;

    public FindOutMoreDialogue(@NonNull Activity mActivity, String number, String emailId) {
        super(mActivity);
        this.mActivity = mActivity;
        this.number = number;
        this.emailId = emailId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            mActivity.startActivity(intent);
            dismiss();

        }
    };

    private View.OnClickListener buttonEmail = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String[] TO = {emailId};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            //emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Queries");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Please send more details about the restaurant.");
            try {
                mActivity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            } catch (android.content.ActivityNotFoundException e) {
                e.printStackTrace();
            }
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
