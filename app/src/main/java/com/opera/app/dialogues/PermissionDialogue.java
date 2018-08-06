package com.opera.app.dialogues;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;

import com.opera.app.R;
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.TextViewWithFont;

import butterknife.BindView;
import butterknife.ButterKnife;
/*Allow location permission.
* provide the permission to access geo location information.*/
public class PermissionDialogue extends Dialog {

    @BindView(R.id.btnPermissionAccept)
    ButtonWithFont btnPermissionAccept;

    private Context context;

    public PermissionDialogue(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_permission_dialogue);

        ButterKnife.bind(this);
        btnPermissionAccept.setOnClickListener(btnAccept);
    }

    /*take navigation to the setting page.
    * enable location service.*/
    private View.OnClickListener btnAccept = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + context.getPackageName()));
            myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
            myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myAppSettings);

            dismiss();
        }
    };
}