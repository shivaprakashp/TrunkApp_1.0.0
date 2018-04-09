package com.opera.app.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class RegisterActivity extends BaseActivity{

    private Activity mActivity;
    public static EditTextWithFont edtDob;

    @BindView(R.id.btnCreateAccount)
    Button mButtonCreateAccount;

    @BindView(R.id.btnLogin)
    Button mButtonLogin;

    @BindView(R.id.textView_continue_as_guest)
    TextView mTvContinueAsGuest;

    @BindView(R.id.reg_edtEmail)
    View reg_edtEmail;

    @BindView(R.id.reg_edtPassword)
    View reg_edtPassword;

    @BindView(R.id.reg_edtRePass)
    View reg_edtRePass;

    @BindView(R.id.reg_edtFirstName)
    View reg_edtFirstName;

    @BindView(R.id.reg_edtLastName)
    View reg_edtLastName;

    @BindView(R.id.reg_edtDob)
    View reg_edtDob;

    @BindView(R.id.reg_edtMobile)
    View reg_edtMobile;

    @BindView(R.id.reg_edtCity)
    View reg_edtCity;

    @BindView(R.id.spinnerNationality)
    Spinner spinnerNationality;

    @BindView(R.id.spinnerState)
    Spinner spinnerState;

    @BindView(R.id.spinnerCountry)
    Spinner spinnerCountry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = RegisterActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_registration);

        initView();
    }

    private void initView() {
        mActivity = RegisterActivity.this;

        //edittext
        EditTextWithFont edtEmail = (EditTextWithFont) reg_edtEmail.findViewById(R.id.edt);
        edtEmail.setHint(getString(R.string.email));

        EditTextWithFont edtPassword = (EditTextWithFont) reg_edtPassword.findViewById(R.id.edt);
        edtPassword.setHint(getString(R.string.pass));

        EditTextWithFont edtRePass = (EditTextWithFont) reg_edtRePass.findViewById(R.id.edt);
        edtRePass.setHint(getString(R.string.re_pass));

        EditTextWithFont edtFirstName = (EditTextWithFont) reg_edtFirstName.findViewById(R.id.edt);
        edtFirstName.setHint(getString(R.string.firstname));

        EditTextWithFont edtLastName = (EditTextWithFont) reg_edtLastName.findViewById(R.id.edt);
        edtLastName.setHint(getString(R.string.lastname));

        edtDob = (EditTextWithFont) reg_edtDob.findViewById(R.id.edt);
        edtDob.setHint(getString(R.string.dob));
        edtDob.setFocusable(false);

        EditTextWithFont edtMobile = (EditTextWithFont) reg_edtMobile.findViewById(R.id.edt);
        edtMobile.setHint(getString(R.string.mobile));

        EditTextWithFont edtCity = (EditTextWithFont) reg_edtCity.findViewById(R.id.edt);
        edtCity.setHint(getString(R.string.city));


        //---------------Nationality----------------
        // Initializing a String Array
        String[] nationality_str = new String[]{
                getResources().getString(R.string.nationality)
        };
        final List<String> List = new ArrayList<>(Arrays.asList(nationality_str));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.custom_spinner, List) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.custom_spinner);

        spinnerNationality.setAdapter(spinnerArrayAdapter);
        spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    // Notify the selected item text
                    /*Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------State----------------
        String[] state_str = new String[]{
                getResources().getString(R.string.state)
        };
        final List<String> stateList = new ArrayList<>(Arrays.asList(state_str));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this, R.layout.custom_spinner, stateList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter1.setDropDownViewResource(R.layout.custom_spinner);

        spinnerState.setAdapter(spinnerArrayAdapter1);
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    // Notify the selected item text
                    /*Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------Country----------------
        String[] country_str = new String[]{
                getResources().getString(R.string.country)
        };
        final List<String> countryList = new ArrayList<>(Arrays.asList(country_str));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this, R.layout.custom_spinner, countryList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.custom_spinner);

        spinnerCountry.setAdapter(spinnerArrayAdapter2);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        edtDob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//your code
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "datePicker");

                }
                return false;
            }
        });
    }

    @OnClick({R.id.btnCreateAccount, R.id.btnLogin, R.id.textView_continue_as_guest})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateAccount:
                openActivity(mActivity, MainActivity.class);
                break;

            case R.id.btnLogin:
                openActivity(mActivity, LoginActivity.class);
                break;

            case R.id.textView_continue_as_guest:
                openActivity(mActivity, MainActivity.class);
                break;

        }
    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
//            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month = month + 1;
            edtDob.setText(year + "-" + month + "-" + day);
        }

    }
}
