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
import com.opera.app.dialogues.SuccessDialogue;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.contactUs.ContactUs;
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
    private Intent intent;
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

    /*@BindView(R.id.edtMessage)
    View edtMessage;*/
    @BindView(R.id.edit_edtAddress)
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

    @BindView(R.id.edtMobile)
    EditText mEdtMobileNumber;

    @BindView(R.id.spinnerCountryCode)
    CustomSpinner spinnerCountryCode;

    EditTextWithFont mEdtFullName, mEdtEmail;
    String countryCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = ContactUsActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_contactus);

        initToolbar();
        initView();
        initSpinner();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initView() {

        ((MainApplication) getApplication()).getNetComponent().inject(ContactUsActivity.this);
        api = retrofit.create(Api.class);

        customToast = new CustomToast(mActivity);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.contact_us));

        mEdtFullName = (EditTextWithFont) edtFullName.findViewById(R.id.edt);
        mEdtFullName.setHint(getString(R.string.full_name));
        mEdtFullName.setInputType(InputType.TYPE_CLASS_TEXT);
        mEdtFullName.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mEdtFullName.setFilters(new InputFilter[] { OperaUtils.filterSpaceExceptFirst, OperaUtils.filter, new InputFilter.LengthFilter(30) });

        mEdtEmail = (EditTextWithFont) edtEmail.findViewById(R.id.edt);
        mEdtEmail.setHint(getString(R.string.email));
        mEdtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mEdtEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mEdtEmail.setFilters(new InputFilter[] { OperaUtils.filterSpace, new InputFilter.LengthFilter(50) });

        //mEdtMobileNumber = (EditTextWithFont) edtPhoneNumber.findViewById(R.id.edtMobile);
        mEdtMobileNumber.setHint(getString(R.string.mobile));
        mEdtMobileNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEdtMobileNumber.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mEdtMobileNumber.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });

        //spinnerCountryCode = (CustomSpinner) edtPhoneNumber.findViewById(R.id.spinnerCountryCode);
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
                        getResources().getString(R.string.country_code_with_asterisk))){
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);
                    if(position>0) {
                        countryCode = spinnerCountryCode.getSelectedItem().toString().substring(spinnerCountryCode.getSelectedItem().toString().indexOf("(") + 1, spinnerCountryCode.getSelectedItem().toString().indexOf(")"));
                    }
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

        ArrayAdapter<String> adapterEnquiry = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrEnquiryOptions);
        spinnerEnquiryType.setAdapter(adapterEnquiry);
        spinnerEnquiryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerEnquiryType.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.enquiry_type))){
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

    @OnClick({R.id.imgNumber, R.id.linearTwitter, R.id.linearInstagram, R.id.linearFacebook, R.id.btnSendMessage})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgNumber:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mTextNumber.getText().toString().trim()));
                startActivity(intent);
                break;
            case R.id.linearTwitter:
                intent = new Intent(mActivity, CommonWebViewActivity.class);
                intent.putExtra("URL", OperaUtils.OPERA_TWITTER_URL);
                intent.putExtra("Header", getResources().getString(R.string.social_media));
                startActivity(intent);
                break;
            case R.id.linearInstagram:
                intent = new Intent(mActivity, CommonWebViewActivity.class);
                intent.putExtra("URL", OperaUtils.OPERA_INSTAGRAM_URL);
                intent.putExtra("Header", getResources().getString(R.string.social_media));
                startActivity(intent);
                break;
            case R.id.linearFacebook:
                intent = new Intent(mActivity, CommonWebViewActivity.class);
                intent.putExtra("URL", OperaUtils.OPERA_FACEBOOK_URL);
                intent.putExtra("Header", getResources().getString(R.string.social_media));
                startActivity(intent);
                break;
            case R.id.btnSendMessage:
                ContactUsData();
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

        contactDate.setFullName(mEdtFullName.getText().toString().trim() != null ?
                mEdtFullName.getText().toString().trim() : "");
        contactDate.setPhoneNumber("("+countryCode +")"+ mEdtMobileNumber.getText().toString().trim());
        contactDate.setEmail(mEdtEmail.getText().toString().trim());
        contactDate.setEnquiryType(spinnerEnquiryType.getSelectedItem().toString());
        contactDate.setMessage(edtMessage.getText().toString().trim() != null ?
                edtMessage.getText().toString().trim() : "");

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
        } else if (mEdtMobileNumber.getText().toString().length() < 10 || mEdtMobileNumber.getText().toString().length() > 10) {
            customToast.showErrorToast(getString(R.string.errorLengthMobile));
            return false;
        }
        //email
        else if (TextUtils.isEmpty(mEdtEmail.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorEmailId));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mEdtEmail.getText()).matches()) {
            customToast.showErrorToast(getString(R.string.errorUserEmail));
            return false;
        }
        //enquiry type
        else if (spinnerEnquiryType.getSelectedItem().toString().equals(getResources().getString(R.string.enquiry_type))) {
            customToast.showErrorToast(getResources().getString(R.string.errorEnquiryType));
            return false;
        }
        //message
        else if (TextUtils.isEmpty(edtMessage.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorMessage));
            return false;
        } else if (edtMessage.getText().toString().length() > 70) {
            customToast.showErrorToast(getString(R.string.errorLengthMessage));
            return false;
        }
        return true;
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            if (response.body() != null) {

                SuccessDialogue dialogue = new SuccessDialogue(mActivity, getResources().getString(R.string.contactUsSuccessMsg), getResources().getString(R.string.contactUs_header), getResources().getString(R.string.ok), "ContactUs");
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
}
