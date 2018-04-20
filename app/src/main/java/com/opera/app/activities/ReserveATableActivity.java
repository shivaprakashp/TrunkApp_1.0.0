package com.opera.app.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.constants.AppConstants;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.SuccessDialogue;
import com.opera.app.fragments.DatePickerFragment;
import com.opera.app.listadapters.AdapterMealPeriod;
import com.opera.app.listadapters.AdapterTimeSegments;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.restaurant.RestaurantListing;
import com.opera.app.pojo.restaurant.booktable.GetMasterDetailsRequestPojo;
import com.opera.app.pojo.restaurant.booktable.Meal_Period_Response;
import com.opera.app.pojo.restaurant.booktable.Meal_Periods;
import com.opera.app.pojo.restaurant.booktable.RestaurantMasterDetails;
import com.opera.app.pojo.restaurant.booktable.Time_Segment_Responses;
import com.opera.app.pojo.restaurant.booktable.Time_Segments;
import com.opera.app.utils.LanguageManager;

import java.util.ArrayList;
import java.util.Calendar;

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
    private String mMaxPartySize="";
    private RestaurantMasterDetails mRestaurantMasterDetails;
    EditText editDOB;
    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private Api api;

    @BindView(R.id.edtNoOfGuests)
    EditText mEdtNoOfGuests;

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

    //Meal Period
    private AdapterMealPeriod mAdapterMealPeriod;
    private ArrayList<Meal_Periods> arrMealPeriods;

    //Time segments
    private AdapterTimeSegments mAdapterTimeSegments;
    private ArrayList<Time_Segment_Responses> arrTimeSegments;
    private ArrayList<Time_Segments> arrTimeSegmentsOnly;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = ReserveATableActivity.this;
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_reserve_table);

        initToolbar();

        initView();
    }

    private void initView() {
        ((MainApplication) getApplication()).getNetComponent().inject(ReserveATableActivity.this);
        api = retrofit.create(Api.class);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        DOB = (ImageView) findViewById(R.id.ivDOB);
        editDOB = (EditText) findViewById(R.id.editDOB);
        mEdtNoOfGuests.setText(mTxtNumberOfGuests.getText().toString());

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.reserve_a_table));

        EditTextWithFont edtWindowTable = (EditTextWithFont) reserve_edtWindowTable.findViewById(R.id.edt);
        edtWindowTable.setHint(getString(R.string.window_table));

        EditTextWithFont edtFulName = (EditTextWithFont) reserve_edtFulName.findViewById(R.id.edt);
        edtFulName.setHint(getString(R.string.full_name1));

        EditTextWithFont edtFulNo = (EditTextWithFont) reserve_edtFulNo.findViewById(R.id.edt);
        edtFulNo.setHint(getString(R.string.telephone_no));

        EditTextWithFont edtEmail = (EditTextWithFont) reserve_edtEmail.findViewById(R.id.edt);
        edtEmail.setHint(getString(R.string.email3));

        String[] arrMealPeriod = {getResources().getString(R.string.select_meal_priod)};

        /*ArrayAdapter<String> adapterMealPeriod = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrMealPeriod);
        mSpinnerMealPeriod.setAdapter(adapterMealPeriod);*/

        String[] arrSelectTime = {getResources().getString(R.string.select_time)};

        ArrayAdapter<String> adapterSelectTime = new ArrayAdapter<String>(mActivity, R.layout.custom_spinner, arrSelectTime);
        mSpinnerSelectTime.setAdapter(adapterSelectTime);
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

    @OnClick({R.id.txtMinus, R.id.txtPlus, R.id.editDOB, R.id.ivDOB})
    public void onClick(View v) {
        DialogFragment dialogFragment;
        switch (v.getId()) {
            case R.id.txtPlus:
                int valuePlus = Integer.parseInt(mTxtNumberOfGuests.getText().toString());
                valuePlus++;
                mTxtNumberOfGuests.setText(valuePlus + "");
                mEdtNoOfGuests.setText(valuePlus + "");

                ChangeTimeSegmentsField(valuePlus + "");
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
                        editDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        CallMasterService(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), "Date");

                break;

            case R.id.ivDOB:
                dialogFragment = new com.opera.app.fragments.DatePickerFragment_FutureDatesOpen(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //edtDob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                        editDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        CallMasterService(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                });
                dialogFragment.show(getSupportFragmentManager(), "Date");
                break;

        }
    }

    private void ChangeTimeSegmentsField(String mCurrentNofOfGuestesSelected) {
        arrTimeSegmentsOnly = new ArrayList<>();
        for (int i = 0; i < arrTimeSegments.size(); i++) {
            if (mCurrentNofOfGuestesSelected.equalsIgnoreCase(arrTimeSegments.get(i).getParty_Size())) {
                arrTimeSegmentsOnly.addAll(mRestaurantMasterDetails.getData().getTime_Segment_Responses().get(i).getTime_Segments());
            }
        }

        mAdapterTimeSegments = new AdapterTimeSegments(mActivity, arrTimeSegmentsOnly);
        mSpinnerSelectTime.setAdapter(mAdapterTimeSegments);
    }


    private void CallMasterService(String mReservationDate) {
        MainController controller = new MainController(mActivity);
        controller.bookRestaurant(taskComplete, api, new GetMasterDetailsRequestPojo(AppConstants.SEAN_CONOLLY_RESTAURANT_ID, mReservationDate));
    }

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            arrMealPeriods = new ArrayList<>();
            arrTimeSegments = new ArrayList<>();

            mRestaurantMasterDetails = (RestaurantMasterDetails) response.body();

            arrMealPeriods.addAll(mRestaurantMasterDetails.getData().getControl_Values_Response().getMeal_Period_Response().getMeal_Periods());
            arrTimeSegments.addAll(mRestaurantMasterDetails.getData().getTime_Segment_Responses());

            mAdapterMealPeriod = new AdapterMealPeriod(mActivity, arrMealPeriods);
            mSpinnerMealPeriod.setAdapter(mAdapterMealPeriod);

            //Setting time segment according to No of guests
            ChangeTimeSegmentsField(mEdtNoOfGuests.getText().toString().trim());

        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("error", t.toString());
        }

    };
}
