package com.opera.app.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.opera.app.R;
import com.opera.app.utils.LanguageManager;

/**
 * Created by 58001 on 27-03-2018.
 */

public class ProfileFragment extends BaseFragment {

    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActivity = getActivity();
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        dialog.setContentView(R.layout.popup_changepassword);

        TextView tv_forgotPassword = (TextView) dialog.findViewById(R.id.tv_forgotPassword);
        EditText et_username = (EditText) dialog.findViewById(R.id.et_username);
        Button btnSend = (Button) dialog.findViewById(R.id.btnSend);

        dialog.show();

    }
}
