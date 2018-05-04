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
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.CustomSpinner;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.SuccessDialogue;
import com.opera.app.listadapters.AdapterMealPeriod;
import com.opera.app.listadapters.AdapterTimeSegments;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.restaurant.booktable.BookTableRequest;
import com.opera.app.pojo.restaurant.booktable.Patron;
import com.opera.app.pojo.restaurant.booktable.ReserveResponse;
import com.opera.app.pojo.restaurant.booktable.RespakReservation;
import com.opera.app.pojo.restaurant.getmasterdetails.GetMasterDetailsRequestPojo;
import com.opera.app.pojo.restaurant.getmasterdetails.Meal_Periods;
import com.opera.app.pojo.restaurant.getmasterdetails.RestaurantMasterDetails;
import com.opera.app.pojo.restaurant.getmasterdetails.Time_Segment_Responses;
import com.opera.app.pojo.restaurant.getmasterdetails.Time_Segments;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 1000632 on 4/5/2018.
 */

public class ReserveATableActivity extends BaseActivity {

    private Activity mActivity;
    private ImageView DOB;
    private EditTextWithFont edtEmail, edtFulName, edtFulNo;
    private String mMaxPartySize = "", mMealPeriodId = "", mSelectedTime = "";
    private int maxPartySize = 0;
    private RestaurantMasterDetails mRestaurantMasterDetails;
    private SessionManager mSessionManager;
    private String mCurrentSelectedItemTime = "";
    EditText editDOB;

    private ArrayAdapter<String> adapterSelectTime;
    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private Api api;

    @BindView(R.id.edtNoOfGuests)
    EditText mEdtNoOfGuests;

    @BindView(R.id.edtSpecialRequests)
    EditText mEdtSpecialRequests;

    @BindView(R.id.toolbarReserveATable)
    Toolbar toolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.reserve_edtWindowTable)
    View reserve_edtWindowTable;

    @BindView(R.id.reserve_edtFulName)
    View reserve_edtFulName;

    @BindView(R.id.reserve_edtFulNo)
    View reserve_edtFulNo;

    @BindView(R.id.reserve_edtEmail)
    View reserve_edtEmail;

    @BindView(R.id.txtMinus)
    TextView mTxtMinus;

    @BindView(R.id.txtPlus)
    TextView mTxtPlus;

    @BindView(R.id.txtNumberOfGuests)
    TextView mTxtNumberOfGuests;

    @BindView(R.id.spinnerMealPeriod)
    Spinner mSpinnerMealPeriod;

    @BindView(R.id.spinnerSelectTime)
    Spinner mSpinnerSelectTime;

    @BindView(R.id.spinnerSelectTitle)
    Spinner mSpinnerSelectTitle;

    @BindView(R.id.btnSubmit)
    Button mBtnSubmit;

    CustomSpinner spinnerCountryCode;
    String countryCode, dateOFBirth;

    //Meal Period
    private AdapterMealPeriod mAdapterMealPeriod;
    private ArrayList<Meal_Periods> arrMealPeriods = new ArrayList<>();

    //Time segments
    private AdapterTimeSegments mAdapterTimeSegments;
    private ArrayList<Time_Segment_Responses> arrTimeSegments = new ArrayList<>();
    private ArrayList<Time_Segments> arrTimeSegmentsOnly = new ArrayList<>();
    private ArrayList<Time_Segments> arrTimeSegmentsAllFilters = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = ReserveATableActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_reserve_table);

        initSessionData();
        initToolbar();
        initView();
        Onclicks();
        initSpinnervalues();
        initData();
    }

    private void initSessionData(){
        mSessionManager = new SessionManager(mActivity);
        ((MainApplication) getApplication()).getNetComponent().inject(ReserveATableActivity.this);
        api = retrofit.create(Api.class);

    }

    private void initSpinnervalues(){
        String[] arrMealPeriod = {getResources().getString(R.string.select_meal_priod)};

        ArrayAdapter<String> adapterMealPeriod = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrMealPeriod);
        mSpinnerMealPeriod.setAdapter(adapterMealPeriod);

        String[] arrSelectTime = {getResources().getString(R.string.select_time)};
        adapterSelectTime = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrSelectTime);
        mSpinnerSelectTime.setAdapter(adapterSelectTime);

        String[] arrSelectTitle = {getResources().getString(R.string.select_title), getResources().getString(R.string.mr), getResources().getString(R.string.ms), getResources().getString(R.string.mrs)};

        ArrayAdapter<String> adapterSelectTitle = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrSelectTitle);
        mSpinnerSelectTitle.setAdapter(adapterSelectTitle);

        spinnerCountryCode = (CustomSpinner) reserve_edtFulNo.findViewById(R.id.spinnerCountryCode);
        //---------------Country Code----------------
        // Initializing a String Array
        ArrayAdapter<String> CountryCodeAdapter = new ArrayAdapter<>(
                mActivity, R.layout.custom_spinner,
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.country_code))));
        spinnerCountryCode.setTitle(getResources().getString(R.string.select) + " " + getResources().getString(R.string.country_code));
        spinnerCountryCode.setAdapter(CountryCodeAdapter);
        if (mSessionManager.getUserLoginData() != null) {
            if (mSessionManager.getUserLoginData().getData().getProfile().getMobileNumber().contains("+")) {
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(mActivity);
                String name = sharedPreferences.getString("countryCode", "default value");
                spinnerCountryCode.setSelection(CountryCodeAdapter.getPosition(name));
            }
        }
        spinnerCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerCountryCode.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.country_code_with_asterisk))){
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);
                    //countryCode = spinnerCountryCode.getSelectedItem().toString().substring(spinnerCountryCode.getSelectedItem().toString().indexOf("(") + 1, spinnerCountryCode.getSelectedItem().toString().indexOf(")"));
                    countryCode = spinnerCountryCode.getSelectedItem().toString().substring(spinnerCountryCode.getSelectedItem().toString().indexOf("(") + 1,
                            spinnerCountryCode.getSelectedItem().toString().indexOf(")")).replaceAll("\\s","");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initData(){
          /*if (mSessionManager.getUserLoginData() != null && mSessionManager.getUserLoginData().getData().getProfile().getFirstName() != null) {
            edtFulName.setText(mSessionManager.getUserLoginData().getData().getProfile().getFirstName());
        }*/

        if (mSessionManager.getUserLoginData() != null && mSessionManager.getUserLoginData().getData().getProfile().getEmail() != null) {
            edtEmail.setText(mSessionManager.getUserLoginData().getData().getProfile().getEmail());
        }

        editDOB.setText(OperaUtils.getCurrentDate());
    }

    private void Onclicks() {
        mSpinnerMealPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (arrMealPeriods.size() > 0) {
                    mMealPeriodId = arrMealPeriods.get(position).getMeal_Period_ID();
                    ChangeTimeSegmentsField(mEdtNoOfGuests.getText().toString().trim());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinnerSelectTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mMealPeriodId=arrMealPeriods.get(position);
                mCurrentSelectedItemTime = mSpinnerSelectTime.getSelectedItem().toString();
                if (arrTimeSegmentsAllFilters.size() > 0) {
                    mSelectedTime = arrTimeSegmentsAllFilters.get(position).getMeal_Period_Time().split("T")[1];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerSelectTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mSpinnerSelectTitle.getSelectedItem().toString().equalsIgnoreCase(
                        getResources().getString(R.string.select_title))) {
                    ((TextView) parent.getChildAt(0)).setTextAppearance(mActivity,
                            R.style.label_black);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initView() {

        DOB = (ImageView) findViewById(R.id.ivDOB);
        editDOB = (EditText) findViewById(R.id.editDOB);
        mEdtNoOfGuests.setText(mTxtNumberOfGuests.getText().toString());

        EditTextWithFont edtWindowTable = (EditTextWithFont) reserve_edtWindowTable.findViewById(R.id.edt);
        edtWindowTable.setHint(getString(R.string.window_table));

        edtFulName = (EditTextWithFont) reserve_edtFulName.findViewById(R.id.edt);
        edtFulName.setInputType(InputType.TYPE_CLASS_TEXT);
        edtFulName.setMaxLines(1);
        edtFulName.setHint(getString(R.string.full_name));
        if (mSessionManager.getUserLoginData() != null && mSessionManager.getUserLoginData().getData().getProfile().getFirstName() != null && mSessionManager.getUserLoginData().getData().getProfile().getLastName() != null) {
            edtFulName.setText(mSessionManager.getUserLoginData().getData().getProfile().getFirstName() + " " + mSessionManager.getUserLoginData().getData().getProfile().getLastName());
        }
        edtFulName.setFilters(new InputFilter[] { OperaUtils.filterSpaceExceptFirst, OperaUtils.filter, new InputFilter.LengthFilter(30) });

        edtEmail = (EditTextWithFont) reserve_edtEmail.findViewById(R.id.edt);
        edtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edtEmail.setMaxLines(1);
        edtEmail.setHint(getString(R.string.email));

        edtFulNo = (EditTextWithFont) reserve_edtFulNo.findViewById(R.id.edtMobile);
        edtFulNo.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtFulNo.setMaxLines(1);
        edtFulNo.setHint(getString(R.string.mobile));
        edtFulNo.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        if (mSessionManager.getUserLoginData() != null && mSessionManager.getUserLoginData().getData().getProfile().getMobileNumber() != null) {
            if (mSessionManager.getUserLoginData().getData().getProfile().getMobileNumber().contains("+")) {
                String Number = mSessionManager.getUserLoginData().getData().getProfile().getMobileNumber();
                String mobile = Number.substring(Number.lastIndexOf(")") + 1);
                edtFulNo.setText(mobile);
            } else {
                edtFulNo.setText(mSessionManager.getUserLoginData().getData().getProfile().getMobileNumber());
            }
        }
        edtFulNo.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });

        CallMasterService(OperaUtils.splitDate()[2] + "-" + (OperaUtils.splitDate()[1] + 1) + "-" + OperaUtils.splitDate()[0]);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);

        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.reserve_a_table));

    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.txtMinus, R.id.txtPlus, R.id.editDOB, R.id.ivDOB, R.id.btnSubmit})
    public void onClick(View v) {
        DialogFragment dialogFragment;
        switch (v.getId()) {
            case R.id.txtPlus:
                int valuePlus = Integer.parseInt(mTxtNumberOfGuests.getText().toString());

                if (valuePlus != maxPartySize) {
                    valuePlus++;
                    mTxtNumberOfGuests.setText(valuePlus + "");
                    mEdtNoOfGuests.setText(valuePlus + "");

                    ChangeTimeSegmentsField(valuePlus + "");
                }

                break;

            case R.id.txtMinus:
                if (!mTxtNumberOfGuests.getText().toString().equalsIgnoreCase("1")) {
                    int valueMinus = Integer.parseInt(mTxtNumberOfGuests.getText().toString());
                    valueMinus--;
                    mTxtNumberOfGuests.setText(valueMinus + "");
                    mEdtNoOfGuests.setText(valueMinus + "");

                    ChangeTimeSegmentsField(valueMinus + "");
                }

                break;
            case R.id.editDOB:
                dialogFragment = new com.opera.app.fragments.DatePickerFragment_FutureDatesOpen(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //edtDob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
//                        editDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        dateOFBirth = (year + "-" + (month + 1) + "-" + dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        Date myDate = null;
                        try {
                            myDate = dateFormat.parse(dateOFBirth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String finalDate = timeFormat.format(myDate);
                        editDOB.setText(finalDate);
                        CallMasterService(dateOFBirth);
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), "Date");

                break;

            case R.id.ivDOB:
                dialogFragment = new com.opera.app.fragments.DatePickerFragment_FutureDatesOpen(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //edtDob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
//                        editDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        dateOFBirth = (year + "-" + (month + 1) + "-" + dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd");
                        Date myDate = null;
                        try {
                            myDate = dateFormat.parse(dateOFBirth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String finalDate = timeFormat.format(myDate);
                        editDOB.setText(finalDate);
                        CallMasterService(dateOFBirth);
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), "Date");
                break;

            case R.id.btnSubmit:
                if (validateCheck()) {
                    SubmitSaveReservation();
                }
                break;
        }
    }

    private boolean validateCheck() {
        //validation of input field
        if (mCurrentSelectedItemTime.equalsIgnoreCase(getResources().getString(R.string.select_time))) {
            customToast.showErrorToast(getString(R.string.errorSelectTime));
            return false;
        } else if (mSpinnerSelectTitle.getSelectedItem().toString().equals(getResources().getString(R.string.select_title))) {
            customToast.showErrorToast(getResources().getString(R.string.errorSelectTitle));
            return false;
        } else if (TextUtils.isEmpty(edtFulName.getText().toString().trim())) {
            customToast.showErrorToast(getString(R.string.errorFullName));
            return false;
        } else if (spinnerCountryCode.getSelectedItem().toString().equals(getResources().getString(R.string.country_code_with_asterisk))) {
            customToast.showErrorToast(getResources().getString(R.string.errorCountryCode));
            return false;
        } else if (TextUtils.isEmpty(edtFulNo.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorMobile));
            return false;
        } else if (edtFulNo.getText().toString().length() < 10 || edtFulNo.getText().toString().length() > 10) {
            customToast.showErrorToast(getString(R.string.errorLengthMobile));
            return false;
        } else if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorEmailId));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText()).matches()) {
            customToast.showErrorToast(getString(R.string.errorUserEmail));
            return false;
        }

        return true;
    }

    private void SubmitSaveReservation() {
        MainController controller = new MainController(mActivity);

        Patron patron = new Patron(edtFulName.getText().toString(),
                mSessionManager.getUserLoginData().getData().getProfile().getLastName(),
                "("+countryCode +")"+ edtFulNo.getText().toString().trim(),
                edtEmail.getText().toString().trim(), mSpinnerSelectTitle.getSelectedItem().toString());

        RespakReservation respakReservation = new RespakReservation(dateOFBirth, mSelectedTime,
                Integer.valueOf(mEdtNoOfGuests.getText().toString()),
                Integer.valueOf(mMealPeriodId));

        BookTableRequest tableResponse = new BookTableRequest(patron, respakReservation);

        controller.reserveTable(taskComplete, api, tableResponse);

    }

    private void ChangeTimeSegmentsField(String mCurrentNofOfGuestesSelected) {
        arrTimeSegmentsOnly = new ArrayList<>();
        arrTimeSegmentsAllFilters = new ArrayList<>();
        for (int i = 0; i < arrTimeSegments.size(); i++) {
            if (mCurrentNofOfGuestesSelected.equalsIgnoreCase(arrTimeSegments.get(i).getParty_Size())) {
                arrTimeSegmentsOnly.addAll(mRestaurantMasterDetails.getData().getTime_Segment_Responses().get(i).getTime_Segments());
            }
        }

        for (int j = 0; j < arrTimeSegmentsOnly.size(); j++) {
            if (mMealPeriodId.equalsIgnoreCase(arrTimeSegmentsOnly.get(j).getMeal_Period_ID())) {
                /*arrTimeSegmentsAllFilters.addAll(mRestaurantMasterDetails.getData().getTime_Segment_Responses().get(j).getTime_Segments());*/
                arrTimeSegmentsAllFilters.add(new Time_Segments(arrTimeSegmentsOnly.get(j).getTotal_Available(), arrTimeSegmentsOnly.get(j).getMeal_Period_ID(), arrTimeSegmentsOnly.get(j).getTotal_Booked()
                        , arrTimeSegmentsOnly.get(j).getList_Type(), arrTimeSegmentsOnly.get(j).getOnline_Capacity(), arrTimeSegmentsOnly.get(j).getOnline_Booked(), arrTimeSegmentsOnly.get(j).getSitting()
                        , arrTimeSegmentsOnly.get(j).getTotal_Capacity(), arrTimeSegmentsOnly.get(j).getMeal_Period_Time()));
            }
        }

        if (arrTimeSegmentsAllFilters.size() > 0) {
            mAdapterTimeSegments = new AdapterTimeSegments(mActivity, arrTimeSegmentsAllFilters);
            mSpinnerSelectTime.setAdapter(mAdapterTimeSegments);
        } else {
            String[] arrSelectTime = {getResources().getString(R.string.select_time)};
            adapterSelectTime = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrSelectTime);
            mSpinnerSelectTime.setAdapter(adapterSelectTime);
        }

    }


    private void CallMasterService(String mReservationDate) {
        MainController controller = new MainController(mActivity);
        controller.fetchMasterDetails(taskComplete, api, new GetMasterDetailsRequestPojo(AppConstants.SEAN_CONOLLY_RESTAURANT_ID, mReservationDate));
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            if (mRequestKey.equalsIgnoreCase(AppConstants.GETMASTERDETAILS.GETMASTERDETAILS)) {
                arrMealPeriods = new ArrayList<>();
                arrTimeSegments = new ArrayList<>();

                //Resetting No of guests
                mTxtNumberOfGuests.setText("1");
                mEdtNoOfGuests.setText("1");

                mRestaurantMasterDetails = (RestaurantMasterDetails) response.body();
                //arrMealPeriods.add(new Meal_Periods("",getResources().getString(R.string.select_meal_priod)));
                arrMealPeriods.addAll(mRestaurantMasterDetails.getData().getControl_Values_Response().getMeal_Period_Response().getMeal_Periods());
                if (mRestaurantMasterDetails.getData().getTime_Segment_Responses()!=null)
                arrTimeSegments.addAll(mRestaurantMasterDetails.getData().getTime_Segment_Responses());
                maxPartySize = Integer.parseInt(mRestaurantMasterDetails.getData().getControl_Values_Response().getMeal_Period_Response().getMax_Party_Size());

                mAdapterMealPeriod = new AdapterMealPeriod(mActivity, arrMealPeriods);
                mSpinnerMealPeriod.setAdapter(mAdapterMealPeriod);
            } else if (mRequestKey.equalsIgnoreCase(AppConstants.BOOKATABLE.BOOKATABLE)) {
                if (response.body() != null) {
                    ReserveResponse mBookTable =
                            (ReserveResponse) response.body();
                    SuccessDialogue dialogue = new SuccessDialogue(mActivity, mBookTable.getMessage(), getResources().getString(R.string.success_header), getResources().getString(R.string.ok), "BookTable");
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

        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("error", t.toString());
        }

    };
}
