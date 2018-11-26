package com.test.signup.di.app;

import android.support.annotation.NonNull;

import com.test.signup.di.activity.ActivityComponent;
import com.test.signup.di.activity.ActivityModule;
import com.test.signup.di.qualifiers.LoggedIn;

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
