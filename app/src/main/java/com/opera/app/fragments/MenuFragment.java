package com.opera.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opera.app.R;
import com.opera.app.activities.ContactUsActivity;
import com.opera.app.activities.MyProfileActivity;
import com.opera.app.activities.RegisterActivity;
import com.opera.app.activities.SettingsActivity;
import com.opera.app.customwidget.TextViewWithFont;
import com.opera.app.preferences.SessionManager;
import com.opera.app.utils.LanguageManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuFragment extends BaseFragment {

    private Intent intent;
    private SessionManager manager;

    @BindView(R.id.menu_profile)
    View menu_profile;

    @BindView(R.id.menu_opera_tour)
    View menu_opera_tour;

    @BindView(R.id.menu_wallet)
    View menu_wallet;

    @BindView(R.id.menu_notification)
    View menu_notification;

    @BindView(R.id.menu_promotion)
    View menu_promotion;

    @BindView(R.id.menu_gift_card)
    View menu_gift_card;

    @BindView(R.id.menu_feedback)
    View menu_feedback;

    @BindView(R.id.menu_contact)
    View menu_contact;

    @BindView(R.id.menu_setting)
    View menu_setting;

    @BindView(R.id.menu_save_card)
    View menu_save_card;

    @BindView(R.id.txt_menu_guest)
    TextView tv_menu_guest;

    @BindView(R.id.txtCreateAccount)
    TextViewWithFont creatAccount;

    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mActivity = getActivity();
        //For Language setting
        LanguageManager.createInstance().CommonLanguageFunction(mActivity);
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initMenu();
        initUser();
    }

    private void initUser(){
        manager = new SessionManager(mActivity);
        if (manager.isUserLoggedIn()){
            tv_menu_guest.setText(getResources().getString(R.string.menu_guest)+" "
                    +manager.getUserLoginData().getData().getProfile().getFirstName() + "! ");
            creatAccount.setVisibility(View.GONE);
        }else{
            tv_menu_guest.setText(R.string.menu_welcome_guest);
       }
    }

    private void initMenu() {

        //first row
        ImageView imgProfile = (ImageView) menu_profile.findViewById(R.id.menu_icon);
        TextViewWithFont txtProfile = (TextViewWithFont) menu_profile.findViewById(R.id.menu_icon_text);
        imgProfile.setImageDrawable(getActivity().getDrawable(R.drawable.ic_profile));
        txtProfile.setText(getActivity().getString(R.string.menu_profile));

        ImageView imgOpera = (ImageView) menu_opera_tour.findViewById(R.id.menu_icon);
        TextViewWithFont txtOpera = (TextViewWithFont) menu_opera_tour.findViewById(R.id.menu_icon_text);
        imgOpera.setImageDrawable(getActivity().getDrawable(R.drawable.ic_dubai_opera_tour));
        txtOpera.setText(getActivity().getString(R.string.menu_opera_tour));

        ImageView imgWallet = (ImageView) menu_wallet.findViewById(R.id.menu_icon);
        TextViewWithFont txtWallet = (TextViewWithFont) menu_wallet.findViewById(R.id.menu_icon_text);
        imgWallet.setImageDrawable(getActivity().getDrawable(R.drawable.ic_wallet));
        txtWallet.setText(getActivity().getString(R.string.menu_wallet));

        //second row
        ImageView imgNotify = (ImageView) menu_notification.findViewById(R.id.menu_icon);
        TextViewWithFont txtNotify = (TextViewWithFont) menu_notification.findViewById(R.id.menu_icon_text);
        imgNotify.setImageDrawable(getActivity().getDrawable(R.drawable.ic_notifications));
        txtNotify.setText(getActivity().getString(R.string.menu_notification));

        ImageView imgPromotion = (ImageView) menu_promotion.findViewById(R.id.menu_icon);
        TextViewWithFont txtPromotion = (TextViewWithFont) menu_promotion.findViewById(R.id.menu_icon_text);
        imgPromotion.setImageDrawable(getActivity().getDrawable(R.drawable.ic_promotions));
        txtPromotion.setText(getActivity().getString(R.string.menu_promotion));

        ImageView imgGift = (ImageView) menu_gift_card.findViewById(R.id.menu_icon);
        TextViewWithFont txtGift = (TextViewWithFont) menu_gift_card.findViewById(R.id.menu_icon_text);
        imgGift.setImageDrawable(getActivity().getDrawable(R.drawable.ic_gift_cards));
        txtGift.setText(getActivity().getString(R.string.menu_gift_cards));

        //third row
        ImageView imgFeedback = (ImageView) menu_feedback.findViewById(R.id.menu_icon);
        TextViewWithFont txtfeedback = (TextViewWithFont) menu_feedback.findViewById(R.id.menu_icon_text);
        imgFeedback.setImageDrawable(getActivity().getDrawable(R.drawable.ic_feedback));
        txtfeedback.setText(getActivity().getString(R.string.menu_feedback));

        ImageView imgContact = (ImageView) menu_contact.findViewById(R.id.menu_icon);
        TextViewWithFont txtContact = (TextViewWithFont) menu_contact.findViewById(R.id.menu_icon_text);
        imgContact.setImageDrawable(getActivity().getDrawable(R.drawable.ic_contact_us));
        txtContact.setText(getActivity().getString(R.string.menu_contact_us));

        ImageView imgSetting = (ImageView) menu_setting.findViewById(R.id.menu_icon);
        TextViewWithFont txtSetting = (TextViewWithFont) menu_setting.findViewById(R.id.menu_icon_text);
        imgSetting.setImageDrawable(getActivity().getDrawable(R.drawable.ic_settings));
        txtSetting.setText(getActivity().getString(R.string.menu_settings));

        //fourth row
        ImageView imgSaveCard = (ImageView) menu_save_card.findViewById(R.id.menu_icon);
        TextViewWithFont txtSaveCard = (TextViewWithFont) menu_save_card.findViewById(R.id.menu_icon_text);
        imgSaveCard.setImageDrawable(getActivity().getDrawable(R.drawable.ic_saved_cards));
        txtSaveCard.setText(getActivity().getString(R.string.menu_save_cards));

        menu_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        menu_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manager.isUserLoggedIn()) {
                    intent = new Intent(getActivity(), MyProfileActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mActivity, getActivity().getString(R.string.guest_msg), Toast.LENGTH_SHORT).show();
                }
            }
        });

        menu_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.txtCreateAccount)
    public void createAccount(){
        intent = new Intent(getActivity(), RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
