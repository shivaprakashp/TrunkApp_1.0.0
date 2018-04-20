package com.opera.app.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
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
import com.opera.app.customwidget.CustomSpinner;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.fragments.DatePickerFragment;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.profile.EditProfile;
import com.opera.app.pojo.profile.EditProfileResponse;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 58001 on 03-04-2018.
 */

public class EditProfileActivity extends BaseActivity {

    private CustomToast customToast;
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
    CustomSpinner spinnerNationality;

    @BindView(R.id.spinnerState)
    CustomSpinner spinnerState;

    @BindView(R.id.spinnerCountry)
    CustomSpinner spinnerCountry;

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
        initSpinners();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.my_profile));

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);
    }

    private void initView() {
        customToast = new CustomToast(mActivity);
        manager = new SessionManager(mActivity);

        ((MainApplication) getApplication()).getNetComponent().inject(EditProfileActivity.this);
        api = retrofit.create(Api.class);

        //edittext
        edtEmail = (EditTextWithFont) edit_edtEmail.findViewById(R.id.edt);
        edtEmail.setHint(getString(R.string.edit_email));
        edtEmail.setText(manager.getUserLoginData().getData().getProfile().getEmail());
        edtEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtEmail.setEnabled(false);

        edtFirstName = (EditTextWithFont) edit_edtFirstName.findViewById(R.id.edt);
        edtFirstName.setHint(getString(R.string.edit_firstname));
        edtFirstName.setText(manager.getUserLoginData().getData().getProfile().getFirstName());
        edtFirstName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtFirstName.requestFocus();
        edtFirstName.setFilters(new InputFilter[] { OperaUtils.filterSpace, OperaUtils.filter, new InputFilter.LengthFilter(30) });

        edtLastName = (EditTextWithFont) edit_edtLastName.findViewById(R.id.edt);
        edtLastName.setHint(getString(R.string.edit_lastname));
        edtLastName.setText(manager.getUserLoginData().getData().getProfile().getLastName());
        edtLastName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtLastName.setFilters(new InputFilter[] {OperaUtils.filterSpace, OperaUtils.filter, new InputFilter.LengthFilter(30) });

        edtDob = (EditTextWithFont) edit_edtDob.findViewById(R.id.edt);
        edtDob.setHint(getString(R.string.edit_dob));
        edtDob.setFocusable(false);
        edtDob.setText(manager.getUserLoginData().getData().getProfile().getDateOfBirth());
        edtDob.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtDob.performClick();

        edtMobile = (EditTextWithFont) edit_edtMobile.findViewById(R.id.edt);
        edtMobile.setHint(getString(R.string.edit_mobile));
        edtMobile.setText(manager.getUserLoginData().getData().getProfile().getMobileNumber());
        edtMobile.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtMobile.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });

        edtCity = (EditTextWithFont) edit_edtCity.findViewById(R.id.edt);
        edtCity.setHint(getString(R.string.edit_city));
        edtCity.setText(manager.getUserLoginData().getData().getProfile().getCity());
        edtCity.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtCity.setFilters(new InputFilter[] { OperaUtils.filter, new InputFilter.LengthFilter(26) });

        edtAddress = (EditTextWithFont) edit_edtAddress.findViewById(R.id.edt);
        edtAddress.setHint(getString(R.string.edit_address));
        edtAddress.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        edtAddress.setSingleLine(false);
        edtAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtAddress.setText(manager.getUserLoginData().getData().getProfile().getAddress());
        edtAddress.setFilters(new InputFilter[] { OperaUtils.filter, new InputFilter.LengthFilter(150) });

        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //edtDob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        edtDob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), "Date");
            }
        });
    }

    //find all spinner at one place
    private void initSpinners(){
        //---------------Nationality----------------
        // Initializing a String Array
        ArrayAdapter<String> nationalAdapter = new ArrayAdapter<>(
                mActivity, R.layout.custom_spinner,
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.nationality))));
        spinnerNationality.setTitle(getResources().getString(R.string.select) + " " + getResources().getString(R.string.edit_nationality));
        spinnerNationality.setAdapter(nationalAdapter);
        spinnerNationality.setSelection(nationalAdapter.getPosition(manager.getUserLoginData().getData().getProfile().getNationality()));
        spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerNationality.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.nationality))){
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //---------------State----------------
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(mActivity,
                R.layout.custom_spinner,
                new ArrayList<String>(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.states)))));

        spinnerState.setTitle(getResources().getString(R.string.select) + " " + getResources().getString(R.string.edit_state));
        spinnerState.setAdapter(stateAdapter);
        spinnerState.setSelection(stateAdapter.getPosition(manager.getUserLoginData().getData().getProfile().getState()));
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerState.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.state))){
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //---------------Country----------------
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(mActivity,
                R.layout.custom_spinner,
                new ArrayList<String>(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.country)))));
        spinnerCountry.setTitle(getResources().getString(R.string.select) + " " + getResources().getString(R.string.edit_country));
        spinnerCountry.setAdapter(countryAdapter);
        spinnerCountry.setSelection(countryAdapter.getPosition(manager.getUserLoginData().getData().getProfile().getCountry()));
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerCountry.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.country))){
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.btnSave, R.id.btnCancel})
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

    private void EditProfileData() {
        MainController controller = new MainController(mActivity);
        if (validateCheck()) {
            controller.editProfilePost(taskComplete, api,
                    editProfileData());
        }
    }

    private EditProfile editProfileData() {

        EditProfile data = new EditProfile();

        data.setFirstName(edtFirstName.getText().toString() != null ?
                edtFirstName.getText().toString() : "");
        data.setLastName(edtLastName.getText().toString() != null ?
                edtLastName.getText().toString() : "");
        data.setNationality(spinnerNationality.getSelectedItem().toString().trim());
        data.setDateOfBirth(edtDob.getText().toString() != null ?
                edtDob.getText().toString() : "");
        data.setMobileNumber(edtMobile.getText().toString() != null ?
                edtMobile.getText().toString() : "");
        data.setCity(edtCity.getText().toString().trim() != null ?
                edtCity.getText().toString().trim() : "");
        data.setState(spinnerState.getSelectedItem().toString().trim());
        data.setCountry(spinnerCountry.getSelectedItem().toString().trim());
        data.setAddress(edtAddress.getText().toString() != null ?
                edtAddress.getText().toString() : "");

        return data;
    }

    private boolean validateCheck() {

        //Removing previous validations
        /*edtEmail.setError(null);
        edtFirstName.setError(null);
        edtLastName.setError(null);
        edtMobile.setError(null);
        edtCity.setError(null);
        edtDob.setError(null);*/

        //firstName
        if (TextUtils.isEmpty(edtFirstName.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorFirstName));
            return false;
        } else if (edtFirstName.getText().toString().length() < 3 || edtFirstName.getText().toString().length() > 30) {
            customToast.showErrorToast(getString(R.string.errorLengthFirstName));
            return false;
        }
        //lastName
        else if (TextUtils.isEmpty(edtLastName.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorLastName));
            return false;
        } else if (edtLastName.getText().toString().length() < 3 || edtLastName.getText().toString().length() > 30) {
            customToast.showErrorToast(getString(R.string.errorLengthLastName));
            return false;
        }
        //email
        else if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorEmailId));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()) {
            customToast.showErrorToast(getString(R.string.errorUserEmail));
            return false;
        }
        //nationality
        else if (spinnerNationality.getSelectedItem().toString().equals(getResources().getString(R.string.nationality))) {
            customToast.showErrorToast(getResources().getString(R.string.errorNationality));
            return false;
        }
        //dob
        else if (TextUtils.isEmpty(edtDob.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorDob));
            return false;
        }
        //mobile
        else if (TextUtils.isEmpty(edtMobile.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorMobile));
            return false;
        } else if (edtMobile.getText().toString().length() < 10 || edtMobile.getText().toString().length() > 10) {
            customToast.showErrorToast(getString(R.string.errorLengthMobile));
            return false;
        }
        //city
        else if (TextUtils.isEmpty(edtCity.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorCity));
            return false;
        } else if (edtCity.getText().toString().length() < 2 || edtCity.getText().toString().length() > 15) {
            customToast.showErrorToast(getString(R.string.errorLengthCity));
            return false;
        }
        //state
        else if (spinnerState.getSelectedItem().toString().equals(getResources().getString(R.string.state))) {
            customToast.showErrorToast(getResources().getString(R.string.errorState));
            return false;
        }
        //country
        else if (spinnerCountry.getSelectedItem().toString().equals(getResources().getString(R.string.country))) {
            customToast.showErrorToast(getResources().getString(R.string.errorCountry));
            return false;
        }
        return true;
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            if (response.body() != null) {
                EditProfileResponse editProfileResponse =
                        (EditProfileResponse) response.body();
                Toast.makeText(mActivity, editProfileResponse.getMessage(), Toast.LENGTH_LONG).show();
                editProfileSession((EditProfileResponse) response.body());
                mActivity.finish();
            } else if (response.errorBody() != null) {
                try {
                    Toast.makeText(mActivity, jsonResponse(response), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
        }
    };

    //maintain edit profile
    private void editProfileSession(EditProfileResponse editProfileResponse) {
        try {
            SessionManager sessionManager = new SessionManager(mActivity);
            sessionManager.createEditProfileSession(editProfileResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
