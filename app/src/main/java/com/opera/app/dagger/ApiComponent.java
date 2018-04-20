package com.opera.app.dagger;

import com.opera.app.activities.EditProfileActivity;
import com.opera.app.activities.LoginActivity;
import com.opera.app.activities.MainActivity;
import com.opera.app.activities.MyProfileActivity;
import com.opera.app.activities.OtherRestaurantsActivity;
import com.opera.app.activities.RegisterActivity;
import com.opera.app.activities.ReserveATableActivity;
import com.opera.app.activities.SettingsActivity;
import com.opera.app.fragments.ProfileFragment;
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

}
