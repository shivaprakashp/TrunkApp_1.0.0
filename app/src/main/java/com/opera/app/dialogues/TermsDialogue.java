package com.opera.app.dialogues;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.opera.app.R;
import com.opera.app.customwidget.ButtonWithFont;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsDialogue extends Dialog {


    @BindView(R.id.btnError)
    ButtonWithFont btnError;

    public TermsDialogue(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_terms_conditions);

        ButterKnife.bind(this);
        btnError.setOnClickListener(buttonClose);
    }

    private View.OnClickListener buttonClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();

        }
    };
}
