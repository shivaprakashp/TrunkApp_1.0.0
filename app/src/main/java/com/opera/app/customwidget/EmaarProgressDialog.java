package com.opera.app.customwidget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.opera.app.R;


/**
 * 100779
 * shivaprakash.patil@3i-infotech.com
 */
public class EmaarProgressDialog {


    private Dialog progressDialog;
    private Context context;
    private String message;

    private onDialogCancelListener cancelListener;

    public EmaarProgressDialog(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    public void setCancelListener(onDialogCancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void showDialog() {
        TextView mMessageView = null;
        try {
            progressDialog = new Dialog(new ContextThemeWrapper(context, R.style.AppTheme));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.dialogue_emaar_progress);
            ProgressBar mProgress = (ProgressBar) progressDialog.findViewById(R.id.progress_bar);
            mMessageView = (TextView) progressDialog.findViewById(R.id.progress_message);
            if (!TextUtils.isEmpty(message)) {
                mMessageView.setText(message);
            }
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancelListener.onCancel();
                }
            });

            int width = context.getResources().getDisplayMetrics().widthPixels;
            int height = context.getResources().getDisplayMetrics().heightPixels;
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#66000000")));
            progressDialog.getWindow().setLayout(width, height); //Controlling width and height.
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
    //            progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface onDialogCancelListener {
        void onCancel();
    }

}
