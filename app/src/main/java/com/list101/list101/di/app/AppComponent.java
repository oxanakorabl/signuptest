package com.list101.list101.di.app;

import android.support.annotation.NonNull;

import com.list101.list101.di.activity.ActivityComponent;
import com.list101.list101.di.activity.ActivityModule;
import com.list101.list101.di.qualifiers.LoggedIn;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, ApiModule.class})
@Singleton
@SuppressWarnings("PMD.TooManyMethods")
public interface AppComponent {

    @NonNull
    ActivityComponent plusActivityComponent(@NonNull ActivityModule activityModule);

    @LoggedIn
    boolean isLoggedIn();

}
