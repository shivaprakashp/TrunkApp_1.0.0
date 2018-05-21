package com.opera.app.dagger;

import com.opera.app.activities.ContactUsActivity;
import com.opera.app.activities.EditProfileActivity;
import com.opera.app.activities.EventDetailsActivity;
import com.opera.app.activities.LoginActivity;
import com.opera.app.activities.MainActivity;
import com.opera.app.activities.MyProfileActivity;
import com.opera.app.activities.NotificationActivity;
import com.opera.app.activities.OtherRestaurantsActivity;
import com.opera.app.activities.RegisterActivity;
import com.opera.app.activities.ReserveATableActivity;
import com.opera.app.activities.SettingsActivity;
import com.opera.app.activities.WalletActivity;
import com.opera.app.fragments.DiningFragment;
import com.opera.app.fragments.GenresEventsFragment;
import com.opera.app.fragments.HomeFragment;
import com.opera.app.services.SettingsService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 1000779 on 1/22/2018.
 */

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApiComponent {
    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(EditProfileActivity editProfileActivity);
    void inject(MyProfileActivity mProfileFragment);
    void inject(SettingsActivity mSettingsActivity);
    void inject(SettingsService mSettingsService);
    void inject(OtherRestaurantsActivity mOtherRestaurantsActivity);
    void inject(ReserveATableActivity mReserveATableActivity);
    void inject(ContactUsActivity mContactUsActivity);
    void inject(DiningFragment diningFragment);
    void inject(HomeFragment mHomeFragment);
    void inject(EventDetailsActivity mEventDetailsActivity);
    void inject(GenresEventsFragment mGenresEventsFragment);
    void inject(WalletActivity walletActivity);
    void inject(NotificationActivity notificationActivity);
}
