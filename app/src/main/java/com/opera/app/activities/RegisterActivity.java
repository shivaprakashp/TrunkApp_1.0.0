package com.opera.app.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.SuccessDialogue;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.PrivacyDialogue;
import com.opera.app.dialogues.TermsDialogue;
import com.opera.app.fragments.DatePickerFragment;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.registration.Registration;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
    private String blockCharacterSet = "~#^|$%&*!1234567890";

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

    @BindView(R.id.ckbTerms)
    CheckBox ckbTerms;

    EditTextWithFont edtEmail,
            edtPassword,
            edtRePass,
            edtFirstName,
            edtLastName,
            edtMobile,
            edtCity;

    @BindView(R.id.txtTermsCondition)
    TextViewWithFont txtTermsCondition;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response) {
            if (response.body() != null) {
                RegistrationResponse registrationResponse =
                        (RegistrationResponse) response.body();

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
        initSpannableText();
    }

    private void initView() {
        mActivity = RegisterActivity.this;

        ((MainApplication) getApplication()).getNetComponent().inject(RegisterActivity.this);
        api = retrofit.create(Api.class);

        //edittext
        edtEmail = (EditTextWithFont) reg_edtEmail.findViewById(R.id.edt);
        edtEmail.setHint(getString(R.string.email));
        edtEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtPassword = (EditTextWithFont) reg_edtPassword.findViewById(R.id.edt);
        edtPassword.setHint(getString(R.string.pass));
        edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtRePass = (EditTextWithFont) reg_edtRePass.findViewById(R.id.edt);
        edtRePass.setHint(getString(R.string.re_pass));
        edtRePass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        //edtRePass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edtRePass.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtFirstName = (EditTextWithFont) reg_edtFirstName.findViewById(R.id.edt);
        edtFirstName.setHint(getString(R.string.firstname));
        edtFirstName.setFilters(new InputFilter[]{filter});
        edtFirstName.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtLastName = (EditTextWithFont) reg_edtLastName.findViewById(R.id.edt);
        edtLastName.setHint(getString(R.string.lastname));
        edtLastName.setFilters(new InputFilter[]{filter});
        edtLastName.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtDob = (EditTextWithFont) reg_edtDob.findViewById(R.id.edt);
        edtDob.setHint(getString(R.string.dob));
        edtDob.setFocusable(false);
        edtDob.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtMobile = (EditTextWithFont) reg_edtMobile.findViewById(R.id.edt);
        edtMobile.setHint(getString(R.string.mobile));
        edtMobile.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtMobile.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        edtCity = (EditTextWithFont) reg_edtCity.findViewById(R.id.edt);
        edtCity.setHint(getString(R.string.city));
        edtCity.setImeOptions(EditorInfo.IME_ACTION_DONE);

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

        edtDob.performClick();
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
                openActivity(mActivity, MainActivity.class);
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
        registration.setPhoneNumber("");
        registration.setInterest("");
        registration.setNationality(spinnerNationality.getSelectedItem().toString().trim());
        registration.setDateOfBirth(edtDob.getText().toString().trim() != null ?
                edtDob.getText().toString().trim() : "");
        registration.setMobileNumber(edtMobile.getText().toString().trim() != null ?
                edtMobile.getText().toString().trim() : "");
        registration.setCity(edtCity.getText().toString().trim() != null ?
                edtCity.getText().toString().trim() : "");
        registration.setCountry(spinnerCountry.getSelectedItem().toString().trim());

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
        if (TextUtils.isEmpty(edtEmail.getText().toString().trim()) &&
                TextUtils.isEmpty(edtPassword.getText().toString().trim()) &&
                TextUtils.isEmpty(edtRePass.getText().toString().trim()) &&
                TextUtils.isEmpty(edtFirstName.getText().toString().trim()) &&
                TextUtils.isEmpty(edtLastName.getText().toString().trim()) &&
                TextUtils.isEmpty(edtMobile.getText().toString().trim()) &&
                TextUtils.isEmpty(edtCity.getText().toString().trim()) &&
                TextUtils.isEmpty(edtDob.getText().toString().trim())) {
            edtEmail.setError(getString(R.string.errorEmailId));
            edtPassword.setError(getString(R.string.errorPassword));
            edtRePass.setError(getString(R.string.errorRePassword));
            edtFirstName.setError(getString(R.string.errorFirstName));
            edtLastName.setError(getString(R.string.errorLastName));
            edtMobile.setError(getString(R.string.errorMobile));
            edtCity.setError(getString(R.string.errorCity));
            edtDob.setError(getString(R.string.errorDob));
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
        //password
        else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            edtPassword.setError(getString(R.string.errorPassword));
            return false;
        } else if (edtPassword.getText().toString().length() < 3 || edtFirstName.getText().toString().length() > 16) {
            edtPassword.setError(getString(R.string.errorLengthPassword));
            return false;
        } else if (edtEmail.getText().toString().equalsIgnoreCase(
                edtPassword.getText().toString())) {
            edtPassword.setError(getString(R.string.errorSamePasswordAsUsername));
            return false;
        }
        //re-enterPassword
        else if (TextUtils.isEmpty(edtRePass.getText().toString())) {
            edtRePass.setError(getString(R.string.errorRePassword));
            return false;
        } else if (!edtPassword.getText().toString().equalsIgnoreCase(
                edtRePass.getText().toString())) {
            edtRePass.setError(getString(R.string.errorMismatchPassword));
            return false;
        } else if (edtRePass.getText().toString().length() < 3 || edtFirstName.getText().toString().length() > 16) {
            edtRePass.setError(getString(R.string.errorLengthPassword));
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
        //nationality
        else if (spinnerNationality.getSelectedItem().toString().equals(getResources().getString(R.string.nationality))) {
            Toast.makeText(mActivity, getResources().getString(R.string.errorNationality), Toast.LENGTH_LONG).show();
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
        else if (spinnerState.getSelectedItem().toString().equals(getResources().getString(R.string.state))) {
            Toast.makeText(mActivity, getResources().getString(R.string.errorState), Toast.LENGTH_LONG).show();
            return false;
        }
        //country
        else if (spinnerCountry.getSelectedItem().toString().equals(getResources().getString(R.string.country))) {
            Toast.makeText(mActivity, getResources().getString(R.string.errorCountry), Toast.LENGTH_LONG).show();
            return false;
        }
        //TermsCheckbox
        else if (!ckbTerms.isChecked()) {
            Toast.makeText(mActivity, getResources().getString(R.string.errorTerms), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[\\p{L}0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[\\p{L}0-9][\\\\p{L}0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[\\p{L}0-9][\\\\p{L}0-9\\-]{0,25}" +
                    ")+"
    );

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

}
