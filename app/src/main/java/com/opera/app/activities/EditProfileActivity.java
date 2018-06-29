package com.opera.app.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.CustomSpinner;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.SuccessDialogue;
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
    EditTextWithFont edtMobile;

    @BindView(R.id.edit_edtCity)
    View edit_edtCity;

    @BindView(R.id.edit_edtAddress)
    EditText edit_edtAddress;

    @BindView(R.id.spinnerNationality)
    CustomSpinner spinnerNationality;

    @BindView(R.id.spinnerState)
    CustomSpinner spinnerState;

    @BindView(R.id.spinnerCountry)
    CustomSpinner spinnerCountry;

    @BindView(R.id.spinnerCountryCode)
    CustomSpinner spinnerCountryCode;

    EditTextWithFont edtEmail, edtFirstName, edtLastName, edtCity;

    String countryCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = EditProfileActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_edit_profile);

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
        edtEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtEmail.setTextColor(getResources().getColor(R.color.dark_gray));
        edtEmail.setEnabled(false);
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getEmail() != null) {
            edtEmail.setText(manager.getUserLoginData().getData().getProfile().getEmail());
        }

        edtFirstName = (EditTextWithFont) edit_edtFirstName.findViewById(R.id.edt);
        edtFirstName.setHint(getString(R.string.edit_firstname));
        edtFirstName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtFirstName.requestFocus();
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getFirstName() != null) {
            edtFirstName.setText(manager.getUserLoginData().getData().getProfile().getFirstName());
        }
        edtFirstName.setFilters(new InputFilter[] { OperaUtils.filterSpaceExceptFirst, OperaUtils.filter, new InputFilter.LengthFilter(30) });

        edtLastName = (EditTextWithFont) edit_edtLastName.findViewById(R.id.edt);
        edtLastName.setHint(getString(R.string.edit_lastname));
        edtLastName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getLastName() != null) {
            edtLastName.setText(manager.getUserLoginData().getData().getProfile().getLastName());
        }
        edtLastName.setFilters(new InputFilter[] {OperaUtils.filterSpaceExceptFirst, OperaUtils.filter, new InputFilter.LengthFilter(30) });

        edtDob = (EditTextWithFont) edit_edtDob.findViewById(R.id.edt);
        edtDob.setHint(getString(R.string.edit_dob));
        edtDob.setFocusable(false);
        edtDob.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtDob.performClick();
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getDateOfBirth() != null) {
            edtDob.setText(manager.getUserLoginData().getData().getProfile().getDateOfBirth());
        }

        edtCity = (EditTextWithFont) edit_edtCity.findViewById(R.id.edt);
        edtCity.setHint(getString(R.string.edit_city));
        edtCity.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtCity.setFilters(new InputFilter[] { OperaUtils.filterSpaceExceptFirst, new InputFilter.LengthFilter(26) });
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getCity() != null) {
            edtCity.setText(manager.getUserLoginData().getData().getProfile().getCity());
        }

        /*edtAddress = (EditTextWithFont) edit_edtAddress.findViewById(R.id.edt);
        edtAddress.setHint(getString(R.string.edit_address));
        edtAddress.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        edtAddress.setSingleLine(false);
        edtAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtAddress.setFilters(new InputFilter[] { OperaUtils.filter, new InputFilter.LengthFilter(70) });
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getAddress() != null) {
            edtAddress.setText(manager.getUserLoginData().getData().getProfile().getAddress());
        }*/
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getAddress() != null) {
            edit_edtAddress.setText(manager.getUserLoginData().getData().getProfile().getAddress());
        }

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

        //edtMobile = (EditTextWithFont) edit_edtMobile.findViewById(R.id.edtMobile);
        edtMobile.setHint(getString(R.string.edit_mobile));
        edtMobile.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtMobile.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });

        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getMobileNumber() != null) {
            if (manager.getUserLoginData().getData().getProfile().getMobileNumber().contains("+")) {
                String Number = manager.getUserLoginData().getData().getProfile().getMobileNumber();
                String mobile = Number.substring(Number.lastIndexOf(")") + 1);
                edtMobile.setText(mobile);
            } else {
                edtMobile.setText(manager.getUserLoginData().getData().getProfile().getMobileNumber());
            }
        }

        //spinnerCountryCode = (CustomSpinner) edit_edtMobile.findViewById(R.id.spinnerCountryCode);
        //---------------Country Code----------------
        // Initializing a String Array
        ArrayAdapter<String> CountryCodeAdapter = new ArrayAdapter<>(
                mActivity, R.layout.custom_spinner_number_drop_down,
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.country_code))));
        spinnerCountryCode.setTitle(getResources().getString(R.string.select) + " " + getResources().getString(R.string.country_code));
        spinnerCountryCode.setAdapter(CountryCodeAdapter);
        if(manager.getUserLoginData().getData().getProfile().getMobileNumber().contains("+")) {
            SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(mActivity);
            //String name = sharedPreferences.getString("countryCode", "default value");
            countryCode = manager.getUserLoginData().getData().getProfile().getMobileNumber().toString().substring(manager.getUserLoginData().getData().getProfile().getMobileNumber().toString().indexOf("(") + 1,
                    manager.getUserLoginData().getData().getProfile().getMobileNumber().toString().indexOf(")")).replaceAll("\\s","");
            //String number  = name.replaceAll("[^0-9]", "");
            int mPosition=0;

            for(int j=0;j<Arrays.asList(getResources().getStringArray(R.array.country_code)).size();j++){
                if(Arrays.asList(getResources().getStringArray(R.array.country_code)).get(j).contains(countryCode)){
                    mPosition=j;
                    break;
                }
            }

            spinnerCountryCode.setSelection(mPosition);
        }

        spinnerCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerCountryCode.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.country_code_with_asterisk))){
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);

                        SharedPreferences sharedPreferences = PreferenceManager
                                .getDefaultSharedPreferences(mActivity);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("countryCode", spinnerCountryCode.getSelectedItem().toString());
                        editor.apply();
                        //countryCode = spinnerCountryCode.getSelectedItem().toString().substring(spinnerCountryCode.getSelectedItem().toString().indexOf("(") + 1, spinnerCountryCode.getSelectedItem().toString().indexOf(")"));
                    countryCode = spinnerCountryCode.getSelectedItem().toString().substring(spinnerCountryCode.getSelectedItem().toString().indexOf("(") + 1,
                            spinnerCountryCode.getSelectedItem().toString().indexOf(")")).replaceAll("\\s","");

                    ((TextView) parent.getChildAt(0)).setText("+ "+countryCode);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        /*data.setMobileNumber(edtMobile.getText().toString() != null ?
                edtMobile.getText().toString() : "");*/
        data.setMobileNumber("+("+countryCode +")"+ edtMobile.getText().toString().trim());
        data.setCity(edtCity.getText().toString().trim() != null ?
                edtCity.getText().toString().trim() : "");
        data.setState(spinnerState.getSelectedItem().toString().trim());
        data.setCountry(spinnerCountry.getSelectedItem().toString().trim());
        data.setAddress(edit_edtAddress.getText().toString() != null ?
                edit_edtAddress.getText().toString() : "");

        return data;
    }

    private boolean validateCheck() {

        //validation of input field
        //firstName
        if (TextUtils.isEmpty(edtFirstName.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorFirstName));
            return false;
        } /*else if (edtFirstName.getText().toString().length() < 1 || edtFirstName.getText().toString().length() > 30) {
            customToast.showErrorToast(getString(R.string.errorLengthFirstName));
            return false;
        }*/
        //lastName
        else if (TextUtils.isEmpty(edtLastName.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorLastName));
            return false;
        } /*else if (edtLastName.getText().toString().length() < 1 || edtLastName.getText().toString().length() > 30) {
            customToast.showErrorToast(getString(R.string.errorLengthLastName));
            return false;
        }*/
        //nationality
        else if (spinnerNationality.getSelectedItem() == null) {
            customToast.showErrorToast(getResources().getString(R.string.errorNationality));
            return false;
        }
        //dob
        else if (TextUtils.isEmpty(edtDob.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorDob));
            return false;
        }
        //country code
        else if (spinnerCountryCode.getSelectedItem() == null) {
            customToast.showErrorToast(getResources().getString(R.string.errorCountryCode));
            return false;
        }
        //mobile
        else if (TextUtils.isEmpty(edtMobile.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorMobile));
            return false;
        } else if (edtMobile.getText().toString().length() < 9 || edtMobile.getText().toString().length() > 10) {
            customToast.showErrorToast(getString(R.string.errorLengthMobile));
            return false;
        }
        //city
        else if (TextUtils.isEmpty(edtCity.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorCity));
            return false;
        } else if (edtCity.getText().toString().length() < 2 || edtCity.getText().toString().length() > 26) {
            customToast.showErrorToast(getString(R.string.errorLengthCity));
            return false;
        }
        //state
        else if (spinnerState.getSelectedItem() == null) {
            customToast.showErrorToast(getResources().getString(R.string.errorState));
            return false;
        }
        //country
        else if (spinnerCountry.getSelectedItem() == null) {
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
                //Toast.makeText(mActivity, editProfileResponse.getMessage(), Toast.LENGTH_LONG).show();
                //customToast.showErrorToast(editProfileResponse.getMessage());

                editProfileSession((EditProfileResponse) response.body());
                SuccessDialogue dialogue = new SuccessDialogue(mActivity, editProfileResponse.getMessage(), getResources().getString(R.string.success_header), getResources().getString(R.string.ok), "Editprofile");
                dialogue.show();

                //mActivity.finish();
            } else if (response.errorBody() != null) {
                try {
                    //Toast.makeText(mActivity, jsonResponse(response), Toast.LENGTH_LONG).show();
                    customToast.showErrorToast(jsonResponse(response));
                } catch (Exception e) {
                    //Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    customToast.showErrorToast(e.getMessage());
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
