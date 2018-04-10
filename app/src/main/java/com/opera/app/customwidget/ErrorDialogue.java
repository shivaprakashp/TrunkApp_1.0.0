package com.opera.app.customwidget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.opera.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ErrorDialogue extends Dialog {

    private Context context;
    private String message;

    @BindView(R.id.txtErrorMessage)
    TextViewWithFont txtErrorMessage;

    @BindView(R.id.btnError)
    ButtonWithFont btnError;

    public ErrorDialogue(@NonNull Context context, String message) {
        super(context);

        this.context = context;
        this.message = message;
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
            dismiss();

        }
    };
}
