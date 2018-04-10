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
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
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
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.ErrorDialogue;
import com.opera.app.dagger.Api;
import com.opera.app.fragments.DatePickerFragment;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.registration.Registration;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 3/22/2018.
 */

public class RegisterActivity extends BaseActivity{

    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private Api api;

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

    EditTextWithFont edtEmail,
            edtPassword,
            edtRePass,
            edtFirstName,
            edtLastName,
            edtMobile,
            edtCity
    ;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response) {
            if (response.body()!=null){
                RegistrationResponse registrationResponse =
                        (RegistrationResponse) response.body();
                openActivity(mActivity, LoginActivity.class);
                mActivity.finish();
            }else if (response.errorBody()!=null){
                try {
                    ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
                } catch (Exception e) {
                    Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t) {

        }
    };

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

        ((MainApplication) getApplication()).getNetComponent().inject(RegisterActivity.this);
        api = retrofit.create(Api.class);

        //edittext
        edtEmail = (EditTextWithFont) reg_edtEmail.findViewById(R.id.edt);
        edtEmail.setHint(getString(R.string.email));

        edtPassword = (EditTextWithFont) reg_edtPassword.findViewById(R.id.edt);
        edtPassword.setHint(getString(R.string.pass));
        edtPassword.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        edtRePass = (EditTextWithFont) reg_edtRePass.findViewById(R.id.edt);
        edtRePass.setHint(getString(R.string.re_pass));
        edtRePass.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        edtRePass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        edtFirstName = (EditTextWithFont) reg_edtFirstName.findViewById(R.id.edt);
        edtFirstName.setHint(getString(R.string.firstname));

        edtLastName = (EditTextWithFont) reg_edtLastName.findViewById(R.id.edt);
        edtLastName.setHint(getString(R.string.lastname));

        edtDob = (EditTextWithFont) reg_edtDob.findViewById(R.id.edt);
        edtDob.setHint(getString(R.string.dob));
        edtDob.setFocusable(false);

        edtMobile = (EditTextWithFont) reg_edtMobile.findViewById(R.id.edt);
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


        edtDob.performClick();
        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtDob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                });

                dialogFragment.show(getSupportFragmentManager(), "Date");
            }
        });
    }

    @OnClick({R.id.btnCreateAccount, R.id.btnLogin, R.id.textView_continue_as_guest})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateAccount:
                registerUser();
                break;

            case R.id.btnLogin:
                openActivity(mActivity, LoginActivity.class);
                break;

            case R.id.textView_continue_as_guest:
                openActivity(mActivity, MainActivity.class);
                break;

        }
    }

    private void registerUser(){
        MainController controller = new MainController(mActivity);
        if (validateCheck()){
            controller.registerPost(taskComplete, api,
                    userRegistration());
        }

    }

    private Registration userRegistration(){

        Registration registration = new Registration();

        registration.setEmail(edtEmail.getText().toString());
        registration.setPassword(edtPassword.getText().toString());
        registration.setConfirmPassword(edtRePass.getText().toString());
        registration.setFirstName(edtFirstName.getText().toString()!=null?
                edtFirstName.getText().toString(): "");
        registration.setLastName(edtLastName.getText().toString()!=null?
                edtLastName.getText().toString() : "");
        registration.setPhoneNumber("");
        registration.setInterest("");
        registration.setNationality("");
        registration.setDateOfBirth(edtDob.getText().toString()!=null?
        edtDob.getText().toString() : "");
        registration.setMobileNumber(edtMobile.getText().toString()!=null?
        edtMobile.getText().toString() : "");
        registration.setCity("");
        registration.setCountry("");

        return registration;

    }

    private boolean validateCheck(){
        if (TextUtils.isEmpty(edtEmail.getText().toString())){
            edtEmail.setError(getString(R.string.errorEmailId));
            return false;
        }else if( !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()){
            edtEmail.setError(getString(R.string.errorUserEmail));
            return false;
        }else if(TextUtils.isEmpty(edtPassword.getText().toString())) {
            edtPassword.setError(getString(R.string.errorPassword));
            return false;
        }else if (TextUtils.isEmpty(edtRePass.getText().toString())){
            edtRePass.setError(getString(R.string.errorPassword));
            return false;
        }else if (!edtPassword.getText().toString().equalsIgnoreCase(
                edtRePass.getText().toString())){
            edtRePass.setError(getString(R.string.errorMismatchPassword));
            return false;
        }
        return true;
    }
}
