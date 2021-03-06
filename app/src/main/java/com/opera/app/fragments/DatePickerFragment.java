package com.opera.app.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment {

    private OnDateSetListener mDateSetListener;

    public DatePickerFragment() {
        // nothing to see here, move along
    }

    public DatePickerFragment(OnDateSetListener callback) {
        mDateSetListener = callback;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog mDatePickerDialog;
        mDatePickerDialog =new DatePickerDialog(getActivity(),
                mDateSetListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        mDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        return mDatePickerDialog;
    }

}