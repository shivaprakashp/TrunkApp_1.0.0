package com.opera.app.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.profile.EditProfile;
import com.opera.app.pojo.profile.EditProfileResponse;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 58001 on 03-04-2018.
 */

public class EditProfileActivity extends BaseActivity{

    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private Api api;
    private SessionManager manager;
    private Activity mActivity;
    public static EditTextWithFont edtDob;

    @BindView(R.id.toolbar_edit_profile)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.btnCancel)
    Button mBtnCancel;

    @BindView(R.id.btnSave)
    Button mBtnSave;

    @BindView(R.id.edit_edtEmail)
    View edit_edtEmail;

    @BindView(R.id.edit_edtFirstName)
    View edit_edtFirstName;

    @BindView(R.id.edit_edtLastName)
    View edit_edtLastName;

    @BindView(R.id.edit_edtDob)
    View edit_edtDob;

    @BindView(R.id.edit_edtMobile)
    View edit_edtMobile;

    @BindView(R.id.edit_edtCity)
    View edit_edtCity;

    @BindView(R.id.edit_edtAddress)
    View edit_edtAddress;

    @BindView(R.id.spinnerNationality)
    Spinner spinnerNationality;

    @BindView(R.id.spinnerState)
    Spinner spinnerState;

    @BindView(R.id.spinnerCountry)
    Spinner spinnerCountry;

    EditTextWithFont edtEmail, edtFirstName, edtLastName, edtMobile, edtCity, edtAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = EditProfileActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_edit_password);

        initToolbar();
        initView();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initView() {
        manager = new SessionManager(mActivity);

        ((MainApplication) getApplication()).getNetComponent().inject(EditProfileActivity.this);
        api = retrofit.create(Api.class);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.my_profile));

        //edittext
        edtEmail = (EditTextWithFont) edit_edtEmail.findViewById(R.id.edt);
        edtEmail.setHint(getString(R.string.edit_email));
        edtEmail.setText(manager.getUserLoginData().getData().getProfile().getEmail());
        edtEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtFirstName = (EditTextWithFont) edit_edtFirstName.findViewById(R.id.edt);
        edtFirstName.setHint(getString(R.string.edit_firstname));
        edtFirstName.setText(manager.getUserLoginData().getData().getProfile().getFirstName());
        edtFirstName.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtLastName = (EditTextWithFont) edit_edtLastName.findViewById(R.id.edt);
        edtLastName.setHint(getString(R.string.edit_lastname));
        edtLastName.setText(manager.getUserLoginData().getData().getProfile().getLastName());
        edtLastName.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtDob = (EditTextWithFont) edit_edtDob.findViewById(R.id.edt);
        edtDob.setHint(getString(R.string.edit_dob));
        edtDob.setFocusable(false);
        edtDob.setText(manager.getUserLoginData().getData().getProfile().getDateOfBirth());
        edtDob.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtMobile = (EditTextWithFont) edit_edtMobile.findViewById(R.id.edt);
        edtMobile.setHint(getString(R.string.edit_mobile));
        edtMobile.setText(manager.getUserLoginData().getData().getProfile().getMobileNumber());
        edtMobile.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtCity = (EditTextWithFont) edit_edtCity.findViewById(R.id.edt);
        edtCity.setHint(getString(R.string.edit_city));
        edtCity.setText(manager.getUserLoginData().getData().getProfile().getCity());
        edtCity.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtAddress = (EditTextWithFont) edit_edtAddress.findViewById(R.id.edt);
        edtAddress.setHint(getString(R.string.edit_address));
        edtAddress.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        edtAddress.setSingleLine(false);
        edtAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);
        //edtMobile.setText(manager.getUserLoginData().getData().getProfile().getMobileNumber());

        //---------------Nationality----------------
        // Initializing a String Array
        String[] nationality_str = getResources().getStringArray(R.array.nationality);
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
        spinnerNationality.setSelection(spinnerArrayAdapter.getPosition(manager.getUserLoginData().getData().getProfile().getNationality()));
        spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------State----------------
        String[] state_str = getResources().getStringArray(R.array.states);
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
        //spinnerState.setSelection(spinnerArrayAdapter.getPosition(manager.getUserLoginData().getData().getProfile().get));
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //---------------Country----------------
        String[] country_str = getResources().getStringArray(R.array.country);
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
        spinnerCountry.setSelection(spinnerArrayAdapter.getPosition(manager.getUserLoginData().getData().getProfile().getCountry()));
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            month = month + 1;
            //edtDob.setText(year + "-" + month + "-" + day);
            edtDob.setText(day + "/" + (month) + "/" + year);
        }
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.btnSave,R.id.btnCancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:

                EditProfileData();

                break;

            case R.id.btnCancel:
                onBackPressed();
                break;
        }
    }

    private void EditProfileData(){
        MainController controller = new MainController(mActivity);
        if (validateCheck()){
            controller.editProfilePost(mActivity,taskComplete, api,
                    editProfileData());
        }
    }

    private EditProfile editProfileData(){

        EditProfile data = new EditProfile();

        //data.setEmail(edtEmail.getText().toString());
        data.setFirstName(edtFirstName.getText().toString()!=null?
                edtFirstName.getText().toString(): "");
        data.setLastName(edtLastName.getText().toString()!=null?
                edtLastName.getText().toString() : "");
        //data.setPhoneNumber("");
        //data.setInterest("");
        data.setNationality(spinnerNationality.getSelectedItem().toString().trim());
        data.setDateOfBirth(edtDob.getText().toString()!=null?
                edtDob.getText().toString() : "");
        data.setMobileNumber(edtMobile.getText().toString()!=null?
                edtMobile.getText().toString() : "");
        data.setCity(edtCity.getText().toString().trim() != null ?
                edtCity.getText().toString().trim() : "");
        data.setCountry(spinnerCountry.getSelectedItem().toString().trim());

        return data;

    }

    private boolean validateCheck(){

        //Removing previous validations
        edtEmail.setError(null);
        //edtPassword.setError(null);
        //edtRePass.setError(null);
        edtFirstName.setError(null);
        edtLastName.setError(null);
        edtMobile.setError(null);
        edtCity.setError(null);
        edtDob.setError(null);

        //validation of input field
        if (TextUtils.isEmpty(edtFirstName.getText().toString().trim()) &&
                TextUtils.isEmpty(edtLastName.getText().toString().trim()) &&
                TextUtils.isEmpty(edtEmail.getText().toString().trim()) &&
                TextUtils.isEmpty(edtDob.getText().toString().trim()) &&
                TextUtils.isEmpty(edtMobile.getText().toString().trim()) &&
                TextUtils.isEmpty(edtCity.getText().toString().trim())) {
            edtFirstName.setError(getString(R.string.errorFirstName));
            edtLastName.setError(getString(R.string.errorLastName));
            edtEmail.setError(getString(R.string.errorEmailId));
            edtDob.setError(getString(R.string.errorDob));
            edtMobile.setError(getString(R.string.errorMobile));
            edtCity.setError(getString(R.string.errorCity));
            return false;
        }
        //firstName
        else if (TextUtils.isEmpty(edtFirstName.getText().toString())) {
            edtFirstName.setError(getString(R.string.errorFirstName));
            return false;
        } else if (edtFirstName.getText().toString().length() < 3 || edtFirstName.getText().toString().length() > 30) {
            edtFirstName.setError(getString(R.string.errorLengthFirstName));
            return false;
        }
        //lastName
        else if (TextUtils.isEmpty(edtLastName.getText().toString())) {
            edtLastName.setError(getString(R.string.errorLastName));
            return false;
        } else if (edtLastName.getText().toString().length() < 3 || edtLastName.getText().toString().length() > 30) {
            edtLastName.setError(getString(R.string.errorLengthLastName));
            return false;
        }
        //email
        else if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError(getString(R.string.errorEmailId));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()) {
            edtEmail.setError(getString(R.string.errorUserEmail));
            return false;
        }
        //nationality
        else if (spinnerNationality.getSelectedItem().toString().equals(getResources().getString(R.string.nationality))){
            Toast.makeText(mActivity, getResources().getString(R.string.errorNationality) , Toast.LENGTH_LONG).show();
            return false;
        }
        //dob
        else if (TextUtils.isEmpty(edtDob.getText().toString())) {
            edtDob.setError(getString(R.string.errorDob));
            return false;
        }
        //mobile
        else if (TextUtils.isEmpty(edtMobile.getText().toString())) {
            edtMobile.setError(getString(R.string.errorMobile));
            return false;
        } else if (edtMobile.getText().toString().length() < 10 || edtMobile.getText().toString().length() > 10) {
            edtMobile.setError(getString(R.string.errorLengthMobile));
            return false;
        }
        //city
        else if (TextUtils.isEmpty(edtCity.getText().toString())) {
            edtCity.setError(getString(R.string.errorCity));
            return false;
        } else if (edtCity.getText().toString().length() < 2 || edtCity.getText().toString().length() > 15) {
            edtCity.setError(getString(R.string.errorLengthCity));
            return false;
        }
        //state
        else if (spinnerState.getSelectedItem().toString().equals(getResources().getString(R.string.state))){
            Toast.makeText(mActivity, getResources().getString(R.string.errorState) , Toast.LENGTH_LONG).show();
            return false;
        }
        //country
        else if (spinnerCountry.getSelectedItem().toString().equals(getResources().getString(R.string.country))){
            Toast.makeText(mActivity, getResources().getString(R.string.errorCountry) , Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response) {
            if (response.body()!=null){
                EditProfileResponse editProfileResponse =
                        (EditProfileResponse) response.body();
                Toast.makeText(mActivity, editProfileResponse.getMessage(), Toast.LENGTH_LONG).show();
                editProfileSession((EditProfileResponse) response.body());
                mActivity.finish();
            }else if (response.errorBody()!=null){
                try {
                    Toast.makeText(mActivity, jsonResponse(response), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t) {

        }
    };
    //maintain edit profile
    private void editProfileSession(EditProfileResponse editProfileResponse) {
        try {
            SessionManager sessionManager = new SessionManager(mActivity);
            sessionManager.createEditProfileSession(editProfileResponse);
            /*if (sessionManager.isUserLoggedIn()) {
                openActivityWithClearPreviousActivities(mActivity, MainActivity.class);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
