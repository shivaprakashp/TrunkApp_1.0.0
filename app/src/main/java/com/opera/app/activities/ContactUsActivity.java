package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.SuccessDialogue;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.contactUs.ContactUs;
import com.opera.app.pojo.contactUs.ContactUsResponse;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
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
 * Created by 1000632 on 4/3/2018.
 */

public class ContactUsActivity extends BaseActivity {

    @Inject
    Retrofit retrofit;
    private Api api;
    private Activity mActivity;
    private CustomToast customToast;

    @BindView(R.id.toolbar_contactUs)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.edtFullName)
    View edtFullName;

    @BindView(R.id.edtEmail)
    View edtEmail;

    @BindView(R.id.edtMessage)
    EditText edtMessage;

    @BindView(R.id.spinnerEnquiryType)
    Spinner spinnerEnquiryType;

    @BindView(R.id.imgNumber)
    ImageView mImageNumber;

    @BindView(R.id.txtNumber)
    TextView mTextNumber;

    @BindView(R.id.linearTwitter)
    LinearLayout mLinearTwitter;

    @BindView(R.id.linearInstagram)
    LinearLayout mLinearInstagram;

    @BindView(R.id.linearFacebook)
    LinearLayout mLinearFacebook;


    @BindView(R.id.btnSendMessage)
    Button mBtnSend;

    @BindView(R.id.edit_edtMobile)
    View edit_edtMobile;

    CustomSpinner spinnerCountryCode;

    EditTextWithFont mEdtFullName, mEdtEmail, mEdtMobileNumber;
    String countryCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = ContactUsActivity.this;
        //For Language activity_setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_contactus);

        initToolbar();
        initView();
        initSpinner();
        //throw new RuntimeException("This is a crash");
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initView() {
        SessionManager manager = new SessionManager(mActivity);

        ((MainApplication) getApplication()).getNetComponent().inject(ContactUsActivity.this);
        api = retrofit.create(Api.class);

        customToast = new CustomToast(mActivity);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.contact_us));

        mEdtFullName = edtFullName.findViewById(R.id.edt);
        mEdtFullName.setHint(getString(R.string.full_name));
        mEdtFullName.setInputType(InputType.TYPE_CLASS_TEXT);
        mEdtFullName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getFirstName() != null && manager.getUserLoginData().getData().getProfile().getLastName() != null) {
            mEdtFullName.setText(new StringBuilder().append(manager.getUserLoginData().getData().getProfile().getFirstName()).append(" ").append(manager.getUserLoginData().getData().getProfile().getLastName()).toString());
        }
        mEdtFullName.setFilters(new InputFilter[]{OperaUtils.filterSpaceExceptFirst, new InputFilter.LengthFilter(30)});

        mEdtEmail = edtEmail.findViewById(R.id.edt);
        mEdtEmail.setHint(getString(R.string.email));
        mEdtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mEdtEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getEmail() != null) {
            mEdtEmail.setText(manager.getUserLoginData().getData().getProfile().getEmail());
        }
        mEdtEmail.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(50)});

        mEdtMobileNumber = edit_edtMobile.findViewById(R.id.edtMobile);
//        mEdtMobileNumber.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        mEdtMobileNumber.setHint(getString(R.string.mobile));
        mEdtMobileNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEdtMobileNumber.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        if (manager.getUserLoginData() != null && manager.getUserLoginData().getData().getProfile().getMobileNumber() != null) {
            if (manager.getUserLoginData().getData().getProfile().getMobileNumber().contains("+")) {
                String Number = manager.getUserLoginData().getData().getProfile().getMobileNumber();
                String mobile = Number.substring(Number.lastIndexOf(")") + 1);
                mEdtMobileNumber.setText(mobile);
            } else {
                mEdtMobileNumber.setText(manager.getUserLoginData().getData().getProfile().getMobileNumber());
            }
        }
        mEdtMobileNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        //---------------Country Code----------------
        spinnerCountryCode = edit_edtMobile.findViewById(R.id.spinnerCountryCode);
        ArrayAdapter<String> CountryCodeAdapter = new ArrayAdapter<>(
                mActivity, R.layout.custom_spinner_number_drop_down,
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.country_code))));
        spinnerCountryCode.setTitle(getResources().getString(R.string.select) + " " + getResources().getString(R.string.country_code));
        spinnerCountryCode.setAdapter(CountryCodeAdapter);
        if (manager.getUserLoginData() != null) {
            if (manager.getUserLoginData().getData().getProfile().getMobileNumber().contains("+")) {

                countryCode = manager.getUserLoginData().getData().getProfile().getMobileNumber().substring(manager.getUserLoginData().getData().getProfile().getMobileNumber().indexOf("(") + 1,
                        manager.getUserLoginData().getData().getProfile().getMobileNumber().indexOf(")")).replaceAll("\\s", "");
                int mPosition = 0;
                for (int j = 0; j < Arrays.asList(getResources().getStringArray(R.array.country_code)).size(); j++) {
                    String number = Arrays.asList(getResources().getStringArray(R.array.country_code)).get(j).substring(Arrays.asList(getResources().getStringArray(R.array.country_code)).get(j).indexOf("(") + 1,
                            Arrays.asList(getResources().getStringArray(R.array.country_code)).get(j).indexOf(")")).replaceAll("\\s", "");
                    if (number.equals(countryCode)) {
                        mPosition = j;
                        break;
                    }
                }
                spinnerCountryCode.setSelection(mPosition);
            }
        }
        spinnerCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerCountryCode.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.country_code_with_asterisk))) {
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);
                    countryCode = spinnerCountryCode.getSelectedItem().toString().substring(spinnerCountryCode.getSelectedItem().toString().indexOf("(") + 1,
                            spinnerCountryCode.getSelectedItem().toString().indexOf(")")).replaceAll("\\s", "");

                    ((TextView) parent.getChildAt(0)).setText(new StringBuilder().append("+ ").append(countryCode).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinner() {
        String[] arrEnquiryOptions = {getResources().getString(R.string.enquiry_type), getResources().getString(R.string.press_enquiry), getResources().getString(R.string.ticketting_enquiry),
                getResources().getString(R.string.venue_booking), getResources().getString(R.string.careers)};

        ArrayAdapter<String> adapterEnquiry = new ArrayAdapter<>(mActivity, R.layout.custom_spinner, arrEnquiryOptions);
        spinnerEnquiryType.setAdapter(adapterEnquiry);
        spinnerEnquiryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerEnquiryType.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.enquiry_type))) {
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

    @OnClick({R.id.imgNumber, R.id.linearTwitter, R.id.linearInstagram, R.id.linearFacebook, R.id.btnSendMessage, R.id.txtNumber})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgNumber:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mTextNumber.getText().toString().trim()));
                startActivity(intent);
                break;
            case R.id.txtNumber:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mTextNumber.getText().toString().trim()));
                startActivity(intent);
                break;
            case R.id.linearTwitter:
                if (Connections.isConnectionAlive(mActivity)) {
                    intent = new Intent(mActivity, CommonWebViewActivity.class);
                    intent.putExtra("URL", OperaUtils.OPERA_TWITTER_URL);
                    intent.putExtra("Header", getResources().getString(R.string.social_media));
                    startActivity(intent);
                } else {
                    customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
                }
                break;
            case R.id.linearInstagram:
                if (Connections.isConnectionAlive(mActivity)) {
                    intent = new Intent(mActivity, CommonWebViewActivity.class);
                    intent.putExtra("URL", OperaUtils.OPERA_INSTAGRAM_URL);
                    intent.putExtra("Header", getResources().getString(R.string.social_media));
                    startActivity(intent);
                } else {
                    customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
                }
                break;
            case R.id.linearFacebook:
                if (Connections.isConnectionAlive(mActivity)) {
                    intent = new Intent(mActivity, CommonWebViewActivity.class);
                    intent.putExtra("URL", OperaUtils.OPERA_FACEBOOK_URL);
                    intent.putExtra("Header", getResources().getString(R.string.social_media));
                    startActivity(intent);
                } else {
                    customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
                }
                break;
            case R.id.btnSendMessage:
                if (Connections.isConnectionAlive(mActivity)) {
                    ContactUsData();
                } else {
                    customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
                }
                break;
        }
    }

    private void ContactUsData() {
        MainController controller = new MainController(mActivity);
        if (validateCheck()) {
            controller.contactUs(taskComplete, api, contactUs());
        }
    }

    private ContactUs contactUs() {

        ContactUs contactDate = new ContactUs();

        contactDate.setFullName(mEdtFullName.getText().toString());
        contactDate.setPhoneNumber("(" + countryCode + ")" + mEdtMobileNumber.getText().toString().trim());
        contactDate.setEmail(mEdtEmail.getText().toString().trim());
        contactDate.setEnquiryType(spinnerEnquiryType.getSelectedItem().toString());
        contactDate.setMessage(edtMessage.getText().toString().trim());

        return contactDate;

    }

    private boolean validateCheck() {
        //Removing previous validations
        mEdtFullName.setError(null);
        mEdtMobileNumber.setError(null);
        mEdtEmail.setError(null);
        edtMessage.setError(null);

        //FullName
        if (TextUtils.isEmpty(mEdtFullName.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorFullName));
            return false;
        }
        //country code
        else if (spinnerCountryCode.getSelectedItem() == null) {
            customToast.showErrorToast(getResources().getString(R.string.errorCountryCode));
            return false;
        }
        //mobile
        else if (TextUtils.isEmpty(mEdtMobileNumber.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorMobile));
            return false;
        } else if (mEdtMobileNumber.getText().toString().length() < 9 || mEdtMobileNumber.getText().toString().length() > 10) {
            customToast.showErrorToast(getString(R.string.errorLengthMobile));
            return false;
        }
        //email
        else if (TextUtils.isEmpty(mEdtEmail.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorEmailId));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEdtEmail.getText()).matches()) {
            customToast.showErrorToast(getString(R.string.errorEmailInvalid));
            return false;
        }
        //enquiry type
        else if (spinnerEnquiryType.getSelectedItem().toString().equals(getResources().getString(R.string.enquiry_type))) {
            customToast.showErrorToast(getResources().getString(R.string.errorEnquiryType));
            return false;
        }

        return true;
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            if (response.body() != null) {
                ContactUsResponse mPostResponse = (ContactUsResponse) response.body();
                SuccessDialogue dialogue = new SuccessDialogue(mActivity, mPostResponse.getMessage(), getResources().getString(R.string.contactUs_header), getResources().getString(R.string.ok), "ContactUs");
                dialogue.show();
            } else if (response.errorBody() != null) {
                try {
                    ErrorDialogue dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
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
}
