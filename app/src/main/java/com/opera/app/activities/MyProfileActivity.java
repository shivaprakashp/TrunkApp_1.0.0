package com.opera.app.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.BaseActivity;
import com.opera.app.MainApplication;
import com.opera.app.R;
import com.opera.app.controller.MainController;
import com.opera.app.customwidget.CircleImageView;
import com.opera.app.customwidget.CustomToast;
import com.opera.app.customwidget.EditTextWithFont;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.dagger.Api;
import com.opera.app.dialogues.ErrorDialogue;
import com.opera.app.dialogues.SuccessDialogue;
import com.opera.app.fragments.LoyaltyPointsFragment;
import com.opera.app.fragments.ProfileFragment;
import com.opera.app.listener.TaskComplete;
import com.opera.app.pojo.profile.PostChangePassword;
import com.opera.app.pojo.registration.RegistrationResponse;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.Connections;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    SharedPreferences sp;

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

    @BindView(R.id.tv_profile_info)
    TextViewWithFont profileInfo;

    private SessionManager manager;
    //injecting retrofit
    @Inject
    Retrofit retrofit;

    private Api api;

    EditTextWithFont mEdtCurrentPassword,
            mEdtNewPassword,
            mEdtConfNewPassword;

    private BottomSheetDialog dialog;
    private CustomToast customToast;

    private TaskComplete taskComplete = new TaskComplete() {
        @Override
        public void onTaskFinished(Response response, String mRequestKey) {
            ErrorDialogue dialogue;
            if (response.body() != null) {
                RegistrationResponse mPostChangePassword = (RegistrationResponse) response.body();
                if (mPostChangePassword.getStatus().equalsIgnoreCase("success")) {
                    SuccessDialogue dialog = new SuccessDialogue(mActivity, getResources().getString(R.string.changedPasswordSuccessMsg), getResources().getString(R.string.changedPassword_header), getResources().getString(R.string.ok), "MyProfileChangePassword");
                    dialog.show();
                    /*SessionManager sessionManager = new SessionManager(mActivity);
                    sessionManager.clearLoginSession();*/
                } else {
                    dialogue = new ErrorDialogue(mActivity, mPostChangePassword.getMessage());
                    dialogue.show();
                }

            } else if (response.errorBody() != null) {
                try {
                    dialogue = new ErrorDialogue(mActivity, jsonResponse(response));
                    dialogue.show();
                } catch (Exception e) {
                    //Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    customToast.showErrorToast(e.getMessage());
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
        getProfilePicture();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSessionData();
    }

    private void updateSessionData() {
        manager = new SessionManager(mActivity);
        if (manager.getUserLoginData() != null) {
            tv_profile_name.setText(manager.getUserLoginData().getData().getProfile().getFirstName() + " "
                    + manager.getUserLoginData().getData().getProfile().getLastName());

            if (manager.getUserLoginData().getData().getProfile().getJoinDate() != null && !manager.getUserLoginData().getData().getProfile().getJoinDate().isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "dd/MM/yyyy");
                Date myDate = null;
                try {
                    myDate = dateFormat.parse(manager.getUserLoginData().getData().getProfile().getJoinDate().toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat timeFormat = new SimpleDateFormat("MMMM dd, yyyy");
                String finalDate = timeFormat.format(myDate);
                profileInfo.setText(getString(R.string.profile_info) + " " + finalDate);
            } else {
                profileInfo.setText("");
            }
        }
    }

    private void initView() {

        customToast = new CustomToast(mActivity);

        ((MainApplication) mActivity.getApplication()).getNetComponent().inject(MyProfileActivity.this);
        api = retrofit.create(Api.class);

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

       /* img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelection();
            }
        });*/

    }

    //dismiss pop dialog
    private View.OnClickListener dismissDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
        }
    };

    //update save paasword
    private View.OnClickListener saveChangePassword = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (Connections.isConnectionAlive(mActivity)) {
                if (checkValidation()) {
                    //CloseChangePwdSheet();
                    sendChangePassword(mEdtCurrentPassword.getText().toString().trim(), mEdtNewPassword.getText().toString().trim());
                }
            } else {
                //Toast.makeText(mActivity, getResources().getString(R.string.internet_error_msg), Toast.LENGTH_LONG).show();
                customToast.showErrorToast(getResources().getString(R.string.internet_error_msg));
            }
        }
    };

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick({R.id.img_profile})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_profile:
                imageSelection();
                break;
        }
    }

    public void imageSelection() {
        dialog = new BottomSheetDialog(mActivity);
        View view = getLayoutInflater().inflate(R.layout.dialog_image_selection, null);
        dialog.setContentView(view);

        view.findViewById(R.id.imgClose).setOnClickListener(dismissDialog);
        view.findViewById(R.id.linearGallery).setOnClickListener(linearGallery);
        view.findViewById(R.id.linearCamera).setOnClickListener(linearCamera);

        dialog.show();
    }

    //gallery selection
    private View.OnClickListener linearGallery = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OperaUtils.createInstance().SelectGalleryImage(mActivity, PICK_IMAGE);
        }
    };

    //camera selection
    private View.OnClickListener linearCamera = new View.OnClickListener() {
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
    };

    public void changePassword() {
        dialog = new BottomSheetDialog(mActivity);
        View view = getLayoutInflater().inflate(R.layout.popup_changepassword, null);
        dialog.setContentView(view);

        view.findViewById(R.id.imgClose).setOnClickListener(dismissDialog);
        view.findViewById(R.id.btnCancel).setOnClickListener(dismissDialog);
        view.findViewById(R.id.btnSave).setOnClickListener(saveChangePassword);

        mEdtCurrentPassword = (EditTextWithFont) view.findViewById(R.id.edtCurrentPassword);
        mEdtNewPassword = (EditTextWithFont) view.findViewById(R.id.edtNewPassword);
        mEdtConfNewPassword = (EditTextWithFont) view.findViewById(R.id.edtConfNewPassword);

        mEdtCurrentPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEdtCurrentPassword.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(16)});
        mEdtCurrentPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        mEdtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEdtNewPassword.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(16)});
        mEdtNewPassword.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        mEdtConfNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEdtConfNewPassword.setFilters(new InputFilter[]{OperaUtils.filterSpace, new InputFilter.LengthFilter(16)});
        mEdtConfNewPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);

        dialog.show();
    }

    private boolean checkValidation() {
        //Removing previous validations
        mEdtCurrentPassword.setError(null);
        mEdtNewPassword.setError(null);
        mEdtConfNewPassword.setError(null);

        //password
        if (TextUtils.isEmpty(mEdtCurrentPassword.getText().toString()) &&
                (mEdtCurrentPassword.getText().toString().length() < 3 || mEdtCurrentPassword.getText().toString().length() > 16)) {
            customToast.showErrorToast(getString(R.string.errorCurrentPassword));
            return false;
        }
        //re-enterPassword
        else if (TextUtils.isEmpty(mEdtNewPassword.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorNewPassword));
            return false;
        } else if (mEdtCurrentPassword.getText().toString().equalsIgnoreCase(
                mEdtNewPassword.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorPreviousAndNewPassword));
            return false;

        } else if (mEdtNewPassword.getText().toString().length() < 3 || mEdtNewPassword.getText().toString().length() > 16) {
            customToast.showErrorToast(getString(R.string.errorLengthNewPassword));
            return false;
        } else if (TextUtils.isEmpty(mEdtConfNewPassword.getText().toString())) {
            customToast.showErrorToast(getString(R.string.errorConfirmNewPassword));
            return false;
        } else if (mEdtConfNewPassword.getText().toString().length() < 3 || mEdtConfNewPassword.getText().toString().length() > 16) {
            customToast.showErrorToast(getString(R.string.errorLengthPassword));
            return false;
        } else if (!mEdtConfNewPassword.getText().toString().trim().equalsIgnoreCase(mEdtNewPassword.getText().toString().trim())) {
            customToast.showErrorToast(getString(R.string.errorPasswordMatch));
            return false;
        }

        return true;
    }

    private void sendChangePassword(String mPwd, String mNewPwd) {

        MainController controller = new MainController(mActivity);
        controller.changePassword(taskComplete, api, new PostChangePassword(mPwd, mNewPwd));
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

        dialog.dismiss();

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
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmProfileImage.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                sp.edit().putString("dp", encodedImage).commit();

            } else {
                bmProfileImage = (Bitmap) data.getExtras().get("data");
                img_profile.setImageBitmap(bmProfileImage);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmProfileImage.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                sp.edit().putString("dp", encodedImage).commit();
            }
        }
    }

    public void getProfilePicture() {
        sp = getSharedPreferences("profilePicture", MODE_PRIVATE);

        if (!sp.getString("dp", "").equals("")) {
            byte[] decodedString = Base64.decode(sp.getString("dp", ""), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            img_profile.setImageBitmap(decodedByte);
        }
    }
}
