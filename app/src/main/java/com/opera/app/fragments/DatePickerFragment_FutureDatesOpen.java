package com.opera.app.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by 1000632 on 4/20/2018.
 */


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DatePickerFragment_FutureDatesOpen extends DialogFragment {

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public DatePickerFragment_FutureDatesOpen() {
        // nothing to see here, move along
    }

    public DatePickerFragment_FutureDatesOpen(DatePickerDialog.OnDateSetListener callback) {
        mDateSetListener = (DatePickerDialog.OnDateSetListener) callback;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog mDatePickerDialog = null;
        mDatePickerDialog = new DatePickerDialog(getActivity(),
                mDateSetListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        mDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        return mDatePickerDialog;
    }

}