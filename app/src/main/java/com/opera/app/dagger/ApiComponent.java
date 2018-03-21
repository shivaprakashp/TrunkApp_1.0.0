package com.opera.app.dagger;

import com.opera.app.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 1000779 on 1/22/2018.
 */

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApiComponent {
    void inject(MainActivity mainActivity);
}
