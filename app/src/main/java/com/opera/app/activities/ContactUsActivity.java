package com.opera.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.utils.LanguageManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 1000632 on 4/3/2018.
 */

public class ContactUsActivity extends BaseActivity {

    private Activity mActivity;

    @BindView(R.id.toolbar_contactUs)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.edtFullName)
    View edtFullName;

    @BindView(R.id.edtPhoneNumber)
    View edtPhoneNumber;

    @BindView(R.id.edtEmail)
    View edtEmail;

    @BindView(R.id.edtMessage)
    View edtMessage;

    @BindView(R.id.spinnerEnquiryType)
    Spinner spinnerEnquiryType;

    @BindView(R.id.imgNumber)
    ImageView mImageNumber;

    @BindView(R.id.txtNumber)
    TextView mTextNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = ContactUsActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_contactus);

        initToolbar();
        initView();
    }

    private void initView() {
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.contact_us));


        EditTextWithFont mEdtFullName = (EditTextWithFont) edtFullName.findViewById(R.id.edt);
        mEdtFullName.setHint(getString(R.string.full_name));
        mEdtFullName.setInputType(InputType.TYPE_CLASS_TEXT);

        EditTextWithFont mEdtPhoneNumber = (EditTextWithFont) edtPhoneNumber.findViewById(R.id.edt);
        mEdtPhoneNumber.setHint(getString(R.string.phone_number));
        mEdtPhoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

        EditTextWithFont mEdtEmail = (EditTextWithFont) edtEmail.findViewById(R.id.edt);
        mEdtEmail.setHint(getString(R.string.email2));
        mEdtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        EditTextWithFont mEdtMessage = (EditTextWithFont) edtMessage.findViewById(R.id.edt);
        mEdtMessage.setHint(getString(R.string.message));
        mEdtMessage.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        mEdtMessage.setSingleLine(false);

        String[] arrEnquiryOptions = {getResources().getString(R.string.enquiry_type), getResources().getString(R.string.press_enquiry), getResources().getString(R.string.ticketting_enquiry),
                getResources().getString(R.string.venue_booking), getResources().getString(R.string.careers)};

        ArrayAdapter<String> adapterEnquiry = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrEnquiryOptions);
        spinnerEnquiryType.setAdapter(adapterEnquiry);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.imgNumber})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgNumber:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mTextNumber.getText().toString().trim()));
                startActivity(intent);
                break;

        }
    }
}
