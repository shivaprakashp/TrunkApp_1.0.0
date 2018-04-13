package com.opera.app.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.ButtonWithFont;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.fragments.LoyaltyPointsFragment;
import com.opera.app.fragments.ProfileFragment;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.profile.PostChangePassword;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by 58001 on 27-03-2018.
 */

public class MyProfileActivity extends BaseActivity {

    private Activity mActivity;
    private static final int PICK_IMAGE = 1;
    private static final int ACCESS_CAMERA_PERMISSION = 2;
    private static final int CAMERA_REQUEST = 3;
    private Bitmap bmProfileImage;

    @BindView(R.id.tabhost)
    TabLayout mTabHost;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.toolbar_setting)
    Toolbar mToolbar;

    @BindView(R.id.imgCommonToolBack)
    View inc_set_toolbar;

    @BindView(R.id.txtCommonToolHome)
    View inc_set_toolbar_text;

    @BindView(R.id.inc_set_toolbar)
    LinearLayout mLinearLayout;

    @BindView(R.id.img_profile)
    ImageView img_profile;

    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;

    private SessionManager manager;
    private BottomSheetBehavior sheetBehavior;
    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private Api api;

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    @BindView(R.id.edtCurrentPassword)
    EditTextWithFont mEdtCurrentPassword;

    @BindView(R.id.edtNewPassword)
    EditTextWithFont mEdtNewPassword;

    @BindView(R.id.edtConfNewPassword)
    EditTextWithFont mEdtConfNewPassword;

    @BindView(R.id.btnCancel)
    ButtonWithFont mBtnCancel;

    @BindView(R.id.btnSave)
    ButtonWithFont mBtnSave;

    @BindView(R.id.imgClose)
    ImageView mImgClose;


    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            ErrorDialogue dialogue;
            if (response.body() != null) {
                RegistrationResponse mPostChangePassword = (RegistrationResponse) response.body();
                if (mPostChangePassword.getStatus().equalsIgnoreCase("success")) {
                    SessionManager sessionManager = new SessionManager(mActivity);
                    sessionManager.clearLoginSession();
                } else {
                    dialogue = new ErrorDialogue(mActivity, mPostChangePassword.getMessage());
                    dialogue.show();
                }

            } else if (response.errorBody() != null) {
                try {
                    dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
                } catch (Exception e) {
                    Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            Log.e("response", response.toString());
        }

        @Override
        public void onTaskError(Call call, Throwable t, String mRequestKey) {
            Log.e("Error", call.toString());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = MyProfileActivity.this;

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        setContentView(R.layout.activity_my_profile);

        initView();
    }


    private void initView() {

        ((MainApplication) mActivity.getApplication()).getNetComponent().inject(MyProfileActivity.this);
        api = retrofit.create(Api.class);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        manager = new SessionManager(mActivity);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        mLinearLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setVisibility(View.VISIBLE);
        inc_set_toolbar.findViewById(R.id.imgCommonToolBack).setOnClickListener(backPress);

        TextViewWithFont txtToolbarName = (TextViewWithFont) inc_set_toolbar_text.findViewById(R.id.txtCommonToolHome);
        txtToolbarName.setText(getString(R.string.my_profile));

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new ProfileFragment(), getResources().getString(R.string.tab_profile));
        adapter.addFragment(new LoyaltyPointsFragment(), getResources().getString(R.string.tab_loyalty));
        mViewPager.setAdapter(adapter);
        mTabHost.setupWithViewPager(mViewPager);

        if (manager.getUserLoginData()!= null) {
            tv_profile_name.setText(manager.getUserLoginData().getData().getProfile().getFirstName() + " " + manager.getUserLoginData().getData().getProfile().getLastName());
        }
    }

    public void changePassword(){
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.img_profile,  R.id.btnCancel, R.id.btnSave, R.id.imgClose })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_profile:
                showDialog();
                break;

            case R.id.btnCancel:
                CloseChangePwdSheet();
                break;
            case R.id.btnSave:
                if (Connections.isConnectionAlive(mActivity)) {
                    if (checkValidation()) {
                        CloseChangePwdSheet();
                        sendChangePassword(mEdtCurrentPassword.getText().toString().trim(), mEdtNewPassword.getText().toString().trim());
                    }

                } else {
                    Toast.makeText(mActivity, getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.imgClose:
                CloseChangePwdSheet();
                break;
        }
    }

    private void CloseChangePwdSheet() {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private boolean checkValidation() {
        //Removing previous validations
        mEdtCurrentPassword.setError(null);
        mEdtNewPassword.setError(null);
        mEdtConfNewPassword.setError(null);
        if (TextUtils.isEmpty(mEdtCurrentPassword.getText().toString().trim()) &&
                TextUtils.isEmpty(mEdtNewPassword.getText().toString().trim()) &&
                TextUtils.isEmpty(mEdtConfNewPassword.getText().toString().trim())) {
            mEdtCurrentPassword.setError(getString(R.string.errorCurrentPassword));
            mEdtNewPassword.setError(getString(R.string.errorNewPassword));
            mEdtConfNewPassword.setError(getString(R.string.errorConfirmNewPassword));
            return false;
        }

        //password
        else if (TextUtils.isEmpty(mEdtCurrentPassword.getText().toString())) {
            mEdtCurrentPassword.setError(getString(R.string.errorCurrentPassword));
            return false;
        } else if (mEdtCurrentPassword.getText().toString().length() < 3 || mEdtCurrentPassword.getText().toString().length() > 16) {
            mEdtCurrentPassword.setError(getString(R.string.errorLengthPassword));
            return false;
        }
        //re-enterPassword
        else if (TextUtils.isEmpty(mEdtNewPassword.getText().toString())) {
            mEdtNewPassword.setError(getString(R.string.errorNewPassword));
            return false;
        } else if (mEdtCurrentPassword.getText().toString().equalsIgnoreCase(
                mEdtNewPassword.getText().toString())) {
            mEdtNewPassword.setError(getString(R.string.errorPreviousAndNewPassword));
            return false;
        } else if (mEdtNewPassword.getText().toString().length() < 3 || mEdtNewPassword.getText().toString().length() > 16) {
            mEdtNewPassword.setError(getString(R.string.errorLengthPassword));
            return false;
        }  else if (TextUtils.isEmpty(mEdtConfNewPassword.getText().toString())) {
            mEdtConfNewPassword.setError(getString(R.string.errorConfirmNewPassword));
            return false;
        } else if (mEdtConfNewPassword.getText().toString().length() < 3 || mEdtConfNewPassword.getText().toString().length() > 16) {
            mEdtConfNewPassword.setError(getString(R.string.errorLengthPassword));
            return false;
        } else if (!mEdtConfNewPassword.getText().toString().trim().equalsIgnoreCase(mEdtNewPassword.getText().toString().trim())) {
            mEdtConfNewPassword.setError(getString(R.string.errorPasswordMatch));
            return false;
        }

        return true;
    }

    private void sendChangePassword(String mPwd, String mNewPwd) {

        MainController controller = new MainController(mActivity);
        controller.changePassword(taskComplete, api, new PostChangePassword(mPwd, mNewPwd),
                mActivity.getResources().getString(R.string.changePasswordRequest));
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        dialog.setContentView(R.layout.dialog_image_selection);

        //LinearLayout
        LinearLayout linearGallery = (LinearLayout) dialog.findViewById(R.id.linearGallery);
        LinearLayout linearCamera = (LinearLayout) dialog.findViewById(R.id.linearCamera);

        linearGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperaUtils.createInstance().SelectGalleryImage(mActivity, PICK_IMAGE);
            }
        });
        linearCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OperaUtils.createInstance().CheckMarshmallowOrNot()) {
                    if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, ACCESS_CAMERA_PERMISSION);
                    } else {
                        OperaUtils.createInstance().SelectCameraImage(mActivity, CAMERA_REQUEST);
                    }
                } else {
                    OperaUtils.createInstance().SelectCameraImage(mActivity, CAMERA_REQUEST);
                }
            }
        });
        dialog.show();
    }

    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                if (data != null) {
                    try {
                        bmProfileImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                img_profile.setImageBitmap(bmProfileImage);
            } else {
                bmProfileImage = (Bitmap) data.getExtras().get("data");
                img_profile.setImageBitmap(bmProfileImage);
            }

        }
        //CollapseBottomSliding();
    }


   /* @Override
    public void onBackPressed() {
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            CollapseBottomSliding();
        } else {
            super.onBackPressed();
        }
    }

    public void CollapseBottomSliding() {
        sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == ACCESS_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                OperaUtils.createInstance().getSnackbar(sliding_layout, getResources().getString(R.string.permissionGranted)).show();
                OperaUtils.createInstance().SelectCameraImage(mActivity, CAMERA_REQUEST);
            } else {
                OperaUtils.createInstance().getSnackbar(sliding_layout, getResources().getString(R.string.permissionDenied)).show();
            }
        }
    }*/

}
