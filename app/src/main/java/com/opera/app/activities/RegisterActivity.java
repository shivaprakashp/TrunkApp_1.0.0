package com.opera.app.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.PrivacyDialogue;
import com.opera.app.dialogues.SuccessDialogue;
import com.opera.app.dialogues.TermsDialogue;
import com.opera.app.fragments.DatePickerFragment;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.registration.Registration;
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
 * Created by 1000632 on 3/22/2018.
 */

public class RegisterActivity extends BaseActivity {

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

    @BindView(R.id.reg_edtCity)
    View reg_edtCity;

    @BindView(R.id.spinnerNationality)
    CustomSpinner spinnerNationality;

    @BindView(R.id.spinnerState)
    CustomSpinner spinnerState;

    @BindView(R.id.spinnerCountry)
    CustomSpinner spinnerCountry;

    @BindView(R.id.ckbTerms)
    CheckBox ckbTerms;

    @BindView(R.id.ckbNewsLetters)
    CheckBox ckbNewsLetters;

    /*@BindView(R.id.reg_edtMobile)
    View reg_edtMobile;*/

    @BindView(R.id.edtMobile)
    EditText edtMobile;

    @BindView(R.id.spinnerCountryCode)
    CustomSpinner spinnerCountryCode;

    String countryCode;

    EditTextWithFont edtEmail,
            edtPassword,
            edtRePass,
            edtFirstName,
            edtLastName,
            edtCity;

    @BindView(R.id.txtTermsCondition)
    TextViewWithFont txtTermsCondition;

    private CustomToast customToast;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            if (response.body() != null) {

                SuccessDialogue dialogue = new SuccessDialogue(mActivity, getResources().getString(R.string.successMsg), getResources().getString(R.string.success_header), getResources().getString(R.string.ok), "Register");
                dialogue.show();
            } else if (response.errorBody() != null) {
                try {
                    ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
                } catch (Exception e) {
                    Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {

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
        initSpannableText();
        initSpinners();
    }

    private void initView() {
        mActivity = RegisterActivity.this;

        customToast = new CustomToast(mActivity);

        ((MainApplication) getApplication()).getNetComponent().inject(RegisterActivity.this);
        api = retrofit.create(Api.class);

        //edittext
        edtFirstName = (EditTextWithFont) reg_edtFirstName.findViewById(R.id.edt);
        edtFirstName.setHint(getString(R.string.firstname));
        edtFirstName.setInputType(InputType.TYPE_CLASS_TEXT);
        edtFirstName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtFirstName.setFilters(new InputFilter[]{OperaUtils.filterSpaceExceptFirst, OperaUtils.filter, new InputFilter.LengthFilter(30)});
        edtFirstName.requestFocus();

        edtLastName = (EditTextWithFont) reg_edtLastName.findViewById(R.id.edt);
        edtLastName.setHint(getString(R.string.lastname));
        edtLastName.setInputType(InputType.TYPE_CLASS_TEXT);
        edtLastName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtLastName.setFilters(new InputFilter[]{OperaUtils.filterSpaceExceptFirst, OperaUtils.filter, new InputFilter.LengthFilter(30)});

        edtEmail = (EditTextWithFont) reg_edtEmail.findViewById(R.id.edt);
        edtEmail.setHint(getString(R.string.email));
        edtEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edtEmail.setMaxLines(1);
        edtEmail.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(50)});

        edtPassword = (EditTextWithFont) reg_edtPassword.findViewById(R.id.edt);
        edtPassword.setHint(getString(R.string.pass));
        //edtPassword.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        edtPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtPassword.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(16)});
        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        edtRePass = (EditTextWithFont) reg_edtRePass.findViewById(R.id.edt);
        edtRePass.setHint(getString(R.string.re_pass));
        edtRePass.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtRePass.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(16)});
        edtRePass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edtRePass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        edtDob = (EditTextWithFont) reg_edtDob.findViewById(R.id.edt);
        edtDob.setHint(getString(R.string.dob));
        edtDob.setFocusable(false);
        edtDob.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtDob.performClick();

        edtCity = (EditTextWithFont) reg_edtCity.findViewById(R.id.edt);
        edtCity.setHint(getString(R.string.city));
        edtCity.setInputType(InputType.TYPE_CLASS_TEXT);
        edtCity.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtCity.setFilters(new InputFilter[]{OperaUtils.filterSpaceExceptFirst, new InputFilter.LengthFilter(26)});

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

        //edtMobile = (EditTextWithFont) reg_edtMobile.findViewById(R.id.edtMobile);
        edtMobile.setHint(getString(R.string.mobile));
        edtMobile.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtMobile.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        edtMobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        //spinnerCountryCode = (CustomSpinner) reg_edtMobile.findViewById(R.id.spinnerCountryCode);
        //---------------Country Code----------------
        // Initializing a String Array
        ArrayAdapter<String> CountryCodeAdapter = new ArrayAdapter<>(
                mActivity, R.layout.custom_spinner,
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.country_code))));
        spinnerCountryCode.setTitle(getResources().getString(R.string.select) + " " + getResources().getString(R.string.country_code));
        spinnerCountryCode.setAdapter(CountryCodeAdapter);
        spinnerCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerCountryCode.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.country_code_with_asterisk))) {
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);

                        SharedPreferences sharedPreferences = PreferenceManager
                                .getDefaultSharedPreferences(mActivity);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("countryCode", spinnerCountryCode.getSelectedItem().toString());
                        editor.apply();
                        //sp.edit().putString("countryCode", spinnerCountryCode.getSelectedItem().toString()).commit();
                        countryCode = spinnerCountryCode.getSelectedItem().toString().substring(spinnerCountryCode.getSelectedItem().toString().indexOf("(") + 1,
                                spinnerCountryCode.getSelectedItem().toString().indexOf(")")).replaceAll("\\s","");
                        //customToast.showErrorToast(spinnerCountryCode);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //find all spinner at one place
    private void initSpinners() {
        //---------------Nationality----------------
        // Initializing a String Array
        ArrayAdapter<String> nationalAdapter = new ArrayAdapter<>(
                mActivity, R.layout.custom_spinner,
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.nationality))));
        spinnerNationality.setTitle(getResources().getString(R.string.select) + " " + getResources().getString(R.string.edit_nationality));

        spinnerNationality.setAdapter(nationalAdapter);
        spinnerNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerNationality.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.nationality))) {
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
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerState.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.state))) {
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
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerCountry.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.country))) {
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //terms and privacy policy clickable
    private void initSpannableText() {
        try {

            TextView view = new TextView(mActivity);
            SpannableString spannableString = new SpannableString(getResources().
                    getString(R.string.reg_terms_policy));

            if (LanguageManager.createInstance().
                    GetSharedPreferences(mActivity,
                            LanguageManager.createInstance().mSelectedLanguage, "").
                    equalsIgnoreCase(LanguageManager.mLanguageEnglish)) {

                spannableString.setSpan(clickSpannString(true),
                        15, 20, 0);

                spannableString.setSpan(clickSpannString(false),
                        25, spannableString.length(), 0);


            } else {
                spannableString.setSpan(clickSpannString(true),
                        16, 20, 0);

                spannableString.setSpan(clickSpannString(false),
                        1, 14, 0);

            }

            txtTermsCondition.setMovementMethod(LinkMovementMethod.getInstance());
            txtTermsCondition.setText(spannableString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ClickableSpan clickSpannString(final boolean flag) {
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (flag) {
                    TermsDialogue dialogue = new TermsDialogue(mActivity);
                    dialogue.show();
                } else {
                    PrivacyDialogue dialogue = new PrivacyDialogue(mActivity);
                    dialogue.show();
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.WHITE);
            }
        };

        return clickableSpan;
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
                openActivityWithClearPreviousActivities(mActivity, MainActivity.class);
                break;

        }
    }

    private void registerUser() {
        MainController controller = new MainController(mActivity);
        if (validateCheck()) {
            controller.registerPost(taskComplete, api,
                    userRegistration());
        }

    }

    private Registration userRegistration() {

        Registration registration = new Registration();

        registration.setEmail(edtEmail.getText().toString().trim());
        registration.setPassword(edtPassword.getText().toString().trim());
        registration.setConfirmPassword(edtRePass.getText().toString().trim());
        registration.setFirstName(edtFirstName.getText().toString().trim() != null ?
                edtFirstName.getText().toString().trim() : "");
        registration.setLastName(edtLastName.getText().toString().trim() != null ?
                edtLastName.getText().toString().trim() : "");
        registration.setNationality(spinnerNationality.getSelectedItem().toString());
        registration.setDateOfBirth(edtDob.getText().toString().trim() != null ?
                edtDob.getText().toString().trim() : "");
        registration.setMobileNumber("+(" + countryCode + ")" + edtMobile.getText().toString().trim());
        registration.setCity(edtCity.getText().toString());
        registration.setCountry(spinnerCountry.getSelectedItem().toString());
        registration.setState(spinnerState.getSelectedItem().toString());
        registration.setJoinDate(OperaUtils.getCurrentDate());
        if (ckbNewsLetters.isChecked())
            registration.setWeeklyNewsLetters(true);
        else
            registration.setWeeklyNewsLetters(false);
        return registration;

    }

    private boolean validateCheck() {

        //Removing previous validations
        edtEmail.setError(null);
        edtPassword.setError(null);
        edtRePass.setError(null);
        edtFirstName.setError(null);
        edtLastName.setError(null);
        edtMobile.setError(null);
        edtCity.setError(null);
        edtDob.setError(null);

        //validation of input field
        //firstName
        if (TextUtils.isEmpty(edtFirstName.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorFirstName));
            return false;
        }/* else if (edtFirstName.getText().toString().length() < 1 || edtFirstName.getText().toString().length() > 30) {
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
        //email
        else if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorEmailId));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()) {
            customToast.showErrorToast(getString(R.string.errorEmailInvalid));
            return false;
        }
        //password
        else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorPasswordEmpty));
            return false;
        } else if (edtPassword.getText().toString().length() < 3 || edtPassword.getText().toString().length() > 16) {
            customToast.showErrorToast(getString(R.string.errorLengthPassword));
            return false;
        } else if (edtEmail.getText().toString().equalsIgnoreCase(
                edtPassword.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorSamePasswordAsUsername));
            return false;
        }
        //re-enterPassword
        else if (TextUtils.isEmpty(edtRePass.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorMismatchPassword));
            return false;
        } else if (!edtPassword.getText().toString().equalsIgnoreCase(
                edtRePass.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorMismatchPassword));
            return false;
        } else if (edtRePass.getText().toString().length() < 3 || edtFirstName.getText().toString().length() > 16) {
            customToast.showErrorToast(getString(R.string.errorLengthPassword));
            return false;
        }
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
        else if (spinnerCountryCode.getSelectedItem()== null) {
            customToast.showErrorToast(getResources().getString(R.string.errorCountryCode));
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
        //TermsCheckbox
        else if (!ckbTerms.isChecked()) {
            customToast.showErrorToast(getResources().getString(R.string.errorTerms));
            return false;
        }
        return true;
    }

}
