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
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opera.app.BaseActivity;
import com.opera.app.R;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.fragments.LoyaltyPointsFragment;
import com.opera.app.fragments.ProfileFragment;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;
import com.opera.app.utils.OperaUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 58001 on 27-03-2018.
 */

public class MyProfileActivity extends BaseActivity {

    private Activity mActivity;
    private static final int PICK_IMAGE = 1;
    private static final int ACCESS_CAMERA_PERMISSION = 2;
    private static final int CAMERA_REQUEST = 3;
    private Bitmap bmProfileImage;
    private SessionManager manager;

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
    TextView profile_name;

    /*@BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;

    @BindView(R.id.linearBottomSliding)
    LinearLayout linearBottomSliding;

    @BindView(R.id.linearGallery)
    LinearLayout linearGallery;

    @BindView(R.id.linearCamera)
    LinearLayout linearCamera;*/

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

        profile_name.setText(manager.getUserLoginData().getData().getName());
    }

    private View.OnClickListener backPress = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @OnClick(R.id.img_profile)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_profile:
                showDialog();

                break;
        }
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
