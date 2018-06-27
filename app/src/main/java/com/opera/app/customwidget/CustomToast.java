package com.opera.app.customwidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.R;

/**
 * Created by 1000779 on 18-12-2017.
 */

//custom toast is used to show message on the error part
public class CustomToast {
    private Context context;
    private int backColor;
    private int textColor;

    public CustomToast(Context context) {
        this.backColor = context.getResources().getColor(R.color.colorBurgendy);
        this.textColor = context.getResources().getColor(R.color.colorWhite);
        this.context = context;
    }

    public void setBackground(int backColor)
    {
        this.backColor = backColor;
    }
    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }
    public void showErrorToast(String text) {

        if (context != null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View toastRoot = inflater.inflate(R.layout.custom_toast, null);
            LinearLayout linearLayout = (LinearLayout)toastRoot.findViewById(R.id.whole_layout);
            linearLayout.setBackgroundColor(backColor);
            TextViewWithFont toastText = (TextViewWithFont) toastRoot.findViewById(R.id.toast_Tv);
            toastText.setText(text);
            toastText.setTextColor(textColor);
            TextViewWithFont toastTitle = (TextViewWithFont) toastRoot.findViewById(R.id.toast_successTv);
            toastTitle.setText(R.string.editError);
            toastTitle.setTextColor(textColor);
            Toast toast = new Toast(context);
            toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP | Gravity.TOP| Gravity.FILL_HORIZONTAL,
                    0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
        }

    }
}